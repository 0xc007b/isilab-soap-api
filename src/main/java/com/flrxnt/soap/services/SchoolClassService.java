package com.flrxnt.soap.services;

import com.flrxnt.soap.entities.SchoolClass;
import com.flrxnt.soap.entities.Sector;
import com.flrxnt.soap.generated.CreateClassRequest;
import com.flrxnt.soap.generated.UpdateClassRequest;
import com.flrxnt.soap.mappers.SchoolClassMapper;
import com.flrxnt.soap.repositories.SchoolClassRepository;
import com.flrxnt.soap.repositories.SectorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service métier pour la gestion des SchoolClass (classes) pour les opérations SOAP.
 * Fournit des opérations CRUD avec quelques validations de base.
 */
@Service
@RequiredArgsConstructor
public class SchoolClassService {

    private final SchoolClassRepository classRepository;
    private final SectorRepository sectorRepository;
    private final SchoolClassMapper schoolClassMapper;

    // ---------- Read ----------

    @Transactional(readOnly = true)
    public List<com.flrxnt.soap.generated.SchoolClass> getAllClasses() {
        return classRepository.findAll()
            .stream()
            .map(schoolClassMapper::toSoapDto)
            .toList();
    }

    @Transactional(readOnly = true)
    public List<com.flrxnt.soap.generated.SchoolClass> getAllClassesBySector(Long sectorId) {
        ensureSectorExists(sectorId);
        return classRepository.findAllBySector_Id(sectorId)
            .stream()
            .map(schoolClassMapper::toSoapDto)
            .toList();
    }

    @Transactional(readOnly = true)
    public com.flrxnt.soap.generated.SchoolClass getClassById(Long id) {
        SchoolClass entity = getClassOrThrow(id);
        return schoolClassMapper.toSoapDto(entity);
    }

    // ---------- Create ----------

    @Transactional
    public com.flrxnt.soap.generated.SchoolClass createClass(CreateClassRequest request) {
        Sector sector = getSectorOrThrow(request.getSectorId());

        // Validation: nom unique dans le secteur (case-insensitive)
        if (classRepository.existsByClassNameIgnoreCaseAndSector_Id(request.getClassName(), sector.getId())) {
            throw new SectorService.SoapServiceException(
                "CONFLICT",
                "Class with the same name already exists in this sector"
            );
        }

        SchoolClass toSave = schoolClassMapper.toEntity(request);
        toSave.setSector(sector);

        SchoolClass saved = classRepository.save(toSave);
        return schoolClassMapper.toSoapDto(saved);
    }

    // ---------- Update ----------

    @Transactional
    public com.flrxnt.soap.generated.SchoolClass updateClass(Long id, UpdateClassRequest request) {
        SchoolClass existing = getClassOrThrow(id);
        Sector newSector = getSectorOrThrow(request.getSectorId());

        // Validation: nom unique dans le secteur ciblé (autre que l'entité courante)
        Optional<SchoolClass> byNameInSector =
            classRepository.findByClassNameIgnoreCaseAndSector_Id(request.getClassName(), newSector.getId());

        if (byNameInSector.isPresent() && !byNameInSector.get().getId().equals(id)) {
            throw new SectorService.SoapServiceException(
                "CONFLICT",
                "Another class with the same name already exists in this sector"
            );
        }

        schoolClassMapper.updateEntity(request, existing);
        if (existing.getSector() == null || !existing.getSector().getId().equals(newSector.getId())) {
            existing.setSector(newSector);
        }

        SchoolClass saved = classRepository.save(existing);
        return schoolClassMapper.toSoapDto(saved);
    }

    // ---------- Delete ----------

    @Transactional
    public boolean deleteClass(Long id) {
        SchoolClass entity = getClassOrThrow(id);
        classRepository.delete(entity);
        return true;
    }

    // ---------- Helpers ----------

    private SchoolClass getClassOrThrow(Long id) {
        return classRepository.findById(id).orElseThrow(() ->
            new SectorService.SoapServiceException("NOT_FOUND", "Class not found"));
    }

    private void ensureSectorExists(Long sectorId) {
        if (!sectorRepository.existsById(sectorId)) {
            throw new SectorService.SoapServiceException("NOT_FOUND", "Sector not found");
        }
    }

    private Sector getSectorOrThrow(Long id) {
        return sectorRepository.findById(id).orElseThrow(() ->
            new SectorService.SoapServiceException("NOT_FOUND", "Sector not found"));
    }
}

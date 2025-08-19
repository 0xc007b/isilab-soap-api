package com.flrxnt.soap.services;

import com.flrxnt.soap.entities.Sector;
import com.flrxnt.soap.generated.CreateSectorRequest;
import com.flrxnt.soap.generated.UpdateSectorRequest;
import com.flrxnt.soap.mappers.SectorMapper;
import com.flrxnt.soap.repositories.SectorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



import java.util.List;
import java.util.Optional;

/**
 * Service métier pour la gestion des Sectors (filières) pour les opérations SOAP.
 * Fournit des opérations CRUD avec quelques validations de base.
 */
@Service
@RequiredArgsConstructor
public class SectorService {

    private final SectorRepository sectorRepository;
    private final SectorMapper sectorMapper;

    // ---------- Read ----------

    @Transactional(readOnly = true)
    public List<com.flrxnt.soap.generated.Sector> getAllSectors() {
        return sectorRepository.findAll()
            .stream()
            .map(sectorMapper::toSoapDto)
            .toList();
    }

    @Transactional(readOnly = true)
    public com.flrxnt.soap.generated.Sector getSectorById(Long id) {
        Sector sector = getSectorOrThrow(id);
        return sectorMapper.toSoapDto(sector);
    }

    // ---------- Create ----------

    @Transactional
    public com.flrxnt.soap.generated.Sector createSector(CreateSectorRequest request) {
        // Validation: nom unique (case-insensitive)
        if (sectorRepository.existsByNameIgnoreCase(request.getName())) {
            throw new SoapServiceException(
                "CONFLICT",
                "Sector with the same name already exists"
            );
        }

        Sector toSave = sectorMapper.toEntity(request);
        Sector saved = sectorRepository.save(toSave);
        return sectorMapper.toSoapDto(saved);
    }

    // ---------- Update ----------

    @Transactional
    public com.flrxnt.soap.generated.Sector updateSector(Long id, UpdateSectorRequest request) {
        Sector sector = getSectorOrThrow(id);

        // Validation: nom unique (case-insensitive) pour un autre secteur
        Optional<Sector> existingByName = sectorRepository.findByNameIgnoreCase(request.getName());
        if (existingByName.isPresent() && !existingByName.get().getId().equals(id)) {
            throw new SoapServiceException(
                "CONFLICT",
                "Another sector with the same name already exists"
            );
        }

        sectorMapper.updateEntity(request, sector);
        Sector saved = sectorRepository.save(sector);
        return sectorMapper.toSoapDto(saved);
    }

    // ---------- Delete ----------

    @Transactional
    public boolean deleteSector(Long id) {
        Sector sector = getSectorOrThrow(id);
        sectorRepository.delete(sector);
        // Grâce au cascade = ALL sur Sector.classes, les classes rattachées seront supprimées.
        return true;
    }

    // ---------- Helpers ----------

    private Sector getSectorOrThrow(Long id) {
        return sectorRepository.findById(id).orElseThrow(() ->
            new SoapServiceException("NOT_FOUND", "Sector not found"));
    }

    /**
     * Exception personnalisée pour les erreurs de service SOAP.
     */
    public static class SoapServiceException extends RuntimeException {
        private final String faultCode;

        public SoapServiceException(String faultCode, String message) {
            super(message);
            this.faultCode = faultCode;
        }

        public String getFaultCode() {
            return faultCode;
        }
    }
}

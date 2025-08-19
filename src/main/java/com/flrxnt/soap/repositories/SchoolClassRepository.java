package com.flrxnt.soap.repositories;

import com.flrxnt.soap.entities.SchoolClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for managing {@link SchoolClass} entities.
 */
@Repository
public interface SchoolClassRepository extends JpaRepository<SchoolClass, Long> {

    /**
     * Find all classes within a given sector.
     *
     * @param sectorId the sector identifier
     * @return list of classes
     */
    List<SchoolClass> findAllBySector_Id(Long sectorId);

    /**
     * Find a class by id scoped to a sector.
     *
     * @param id       the class id
     * @param sectorId the sector id
     * @return optional class
     */
    Optional<SchoolClass> findByIdAndSector_Id(Long id, Long sectorId);

    /**
     * Find a class by name within a sector, case-insensitive.
     *
     * @param className the class name
     * @param sectorId  the sector id
     * @return optional class
     */
    Optional<SchoolClass> findByClassNameIgnoreCaseAndSector_Id(String className, Long sectorId);

    /**
     * Check existence of a class by name in a sector, case-insensitive.
     *
     * @param className the class name
     * @param sectorId  the sector id
     * @return true if exists
     */
    boolean existsByClassNameIgnoreCaseAndSector_Id(String className, Long sectorId);
}

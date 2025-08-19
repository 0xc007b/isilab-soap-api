package com.flrxnt.soap.repositories;

import com.flrxnt.soap.entities.Sector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for managing Sector entities.
 */
@Repository
public interface SectorRepository extends JpaRepository<Sector, Long> {

    /**
     * Find a sector by its name, case-insensitive.
     *
     * @param name sector name
     * @return optional sector
     */
    Optional<Sector> findByNameIgnoreCase(String name);

    /**
     * Check if a sector exists by its name, case-insensitive.
     *
     * @param name sector name
     * @return true if a sector with the given name exists
     */
    boolean existsByNameIgnoreCase(String name);
}

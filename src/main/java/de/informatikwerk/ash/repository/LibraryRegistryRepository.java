package de.informatikwerk.ash.repository;

import de.informatikwerk.ash.domain.LibraryRegistry;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the LibraryRegistry entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LibraryRegistryRepository extends JpaRepository<LibraryRegistry, Long> {

}

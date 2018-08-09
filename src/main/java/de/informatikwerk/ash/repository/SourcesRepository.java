package de.informatikwerk.ash.repository;

import de.informatikwerk.ash.domain.Sources;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Sources entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SourcesRepository extends JpaRepository<Sources, Long> {

}

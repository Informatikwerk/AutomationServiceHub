package de.informatikwerk.ash.repository;

import de.informatikwerk.ash.domain.ConfigElement;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ConfigElement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConfigElementRepository extends JpaRepository<ConfigElement, Long> {

}

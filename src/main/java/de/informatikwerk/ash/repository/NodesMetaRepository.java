package de.informatikwerk.ash.repository;

import de.informatikwerk.ash.domain.NodesMeta;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the NodesMeta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NodesMetaRepository extends JpaRepository<NodesMeta, Long> {

}

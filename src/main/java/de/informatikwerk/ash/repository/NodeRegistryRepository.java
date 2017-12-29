package de.informatikwerk.ash.repository;

import de.informatikwerk.ash.domain.NodeRegistry;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the NodeRegistry entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NodeRegistryRepository extends JpaRepository<NodeRegistry,Long> {
	NodeRegistry findByNodeId(String nodeId);

}

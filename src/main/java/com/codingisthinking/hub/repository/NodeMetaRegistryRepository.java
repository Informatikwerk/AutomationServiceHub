package com.codingisthinking.hub.repository;

import com.codingisthinking.hub.domain.NodeMetaRegistry;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the NodeMetaRegistry entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NodeMetaRegistryRepository extends JpaRepository<NodeMetaRegistry, Long> {

}

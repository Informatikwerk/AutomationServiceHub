package com.codingisthinking.hub.repository;

import com.codingisthinking.hub.domain.LibraryRegistry;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the LibraryRegistry entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LibraryRegistryRepository extends JpaRepository<LibraryRegistry, Long> {

}

package com.codingisthinking.hub.repository;

import com.codingisthinking.hub.domain.Guis;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Guis entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GuisRepository extends JpaRepository<Guis, Long> {

}

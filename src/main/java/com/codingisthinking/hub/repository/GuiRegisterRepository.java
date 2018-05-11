package com.codingisthinking.hub.repository;

import com.codingisthinking.hub.domain.GuiRegister;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the GuiRegister entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GuiRegisterRepository extends JpaRepository<GuiRegister, Long> {

}

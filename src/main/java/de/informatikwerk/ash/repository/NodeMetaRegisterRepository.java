package de.informatikwerk.ash.repository;

import de.informatikwerk.ash.domain.NodeMetaRegister;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the NodeMetaRegister entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NodeMetaRegisterRepository extends JpaRepository<NodeMetaRegister, Long> {

}

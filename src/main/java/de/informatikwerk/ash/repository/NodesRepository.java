package de.informatikwerk.ash.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import de.informatikwerk.ash.domain.Nodes;


/**
 * Spring Data JPA repository for the Nodes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NodesRepository extends JpaRepository<Nodes, Long> {

    @Query("select nodes from Nodes nodes where name=?1 and realm_key=?2")
    public List<Nodes> findByNameAndRealmkey(String name, String realmkey);

    @Query("select nodes from Nodes nodes where realm_key=?1")
    public List<Nodes> findAllForRealmkey(String realmkey);




}

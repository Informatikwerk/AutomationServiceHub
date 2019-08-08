package de.informatikwerk.ash.service;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.informatikwerk.ash.domain.Nodes;
import de.informatikwerk.ash.repository.NodesRepository;

/**
 * Service class for managing users.
 */
@Service
@Transactional
public class NodesService {

    @Autowired
    private EntityManager em;

    private final NodesRepository nodesRepository;

    private final Logger log = LoggerFactory.getLogger(NodesService.class);

    public NodesService(NodesRepository nodesRepository) {
        this.nodesRepository = nodesRepository;
    }


    /**
     * Get all the nodes for a realmkey.
     *
     * @param realmkey The realmkey of the nodes
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Nodes> findAllForRealmkey(String realmkey) {
        List<Nodes> nodes = null;

        Query q = em.createNamedQuery(Nodes.QUERY_FIND_BY_REALMKEY);
        q.setParameter(Nodes.PARAM_REALMKEY, realmkey);
        nodes = q.getResultList();

        return nodes;
    }


}

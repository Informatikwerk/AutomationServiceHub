package de.informatikwerk.espbroker.service;

import de.informatikwerk.espbroker.domain.NodeRegistry;
import de.informatikwerk.espbroker.repository.NodeRegistryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing NodeRegistry.
 */
@Service
@Transactional
public class NodeRegistryService {

    private final Logger log = LoggerFactory.getLogger(NodeRegistryService.class);

    private final NodeRegistryRepository nodeRegistryRepository;

    public NodeRegistryService(NodeRegistryRepository nodeRegistryRepository) {
        this.nodeRegistryRepository = nodeRegistryRepository;
    }

    /**
     * Save a nodeRegistry.
     *
     * @param nodeRegistry the entity to save
     * @return the persisted entity
     */
    public NodeRegistry save(NodeRegistry nodeRegistry) {
        log.debug("Request to save NodeRegistry : {}", nodeRegistry);
        return nodeRegistryRepository.save(nodeRegistry);
    }

    /**
     *  Get all the nodeRegistries.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<NodeRegistry> findAll(Pageable pageable) {
        log.debug("Request to get all NodeRegistries");
        return nodeRegistryRepository.findAll(pageable);
    }

    /**
     *  Get one nodeRegistry by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public NodeRegistry findOne(Long id) {
        log.debug("Request to get NodeRegistry : {}", id);
        return nodeRegistryRepository.findOne(id);
    }

    /**
     *  Delete the  nodeRegistry by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete NodeRegistry : {}", id);
        nodeRegistryRepository.delete(id);
    }
}

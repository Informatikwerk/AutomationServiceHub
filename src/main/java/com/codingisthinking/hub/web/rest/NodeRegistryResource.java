package com.codingisthinking.hub.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.codingisthinking.hub.domain.NodeRegistry;

import com.codingisthinking.hub.repository.NodeRegistryRepository;
import com.codingisthinking.hub.web.rest.errors.BadRequestAlertException;
import com.codingisthinking.hub.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing NodeRegistry.
 */
@RestController
@RequestMapping("/api")
public class NodeRegistryResource {

    private final Logger log = LoggerFactory.getLogger(NodeRegistryResource.class);

    private static final String ENTITY_NAME = "nodeRegistry";

    private final NodeRegistryRepository nodeRegistryRepository;

    public NodeRegistryResource(NodeRegistryRepository nodeRegistryRepository) {
        this.nodeRegistryRepository = nodeRegistryRepository;
    }

    /**
     * POST  /node-registries : Create a new nodeRegistry.
     *
     * @param nodeRegistry the nodeRegistry to create
     * @return the ResponseEntity with status 201 (Created) and with body the new nodeRegistry, or with status 400 (Bad Request) if the nodeRegistry has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/node-registries")
    @Timed
    public ResponseEntity<NodeRegistry> createNodeRegistry(@Valid @RequestBody NodeRegistry nodeRegistry) throws URISyntaxException {
        log.debug("REST request to save NodeRegistry : {}", nodeRegistry);
        if (nodeRegistry.getId() != null) {
            throw new BadRequestAlertException("A new nodeRegistry cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NodeRegistry result = nodeRegistryRepository.save(nodeRegistry);
        return ResponseEntity.created(new URI("/api/node-registries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /node-registries : Updates an existing nodeRegistry.
     *
     * @param nodeRegistry the nodeRegistry to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated nodeRegistry,
     * or with status 400 (Bad Request) if the nodeRegistry is not valid,
     * or with status 500 (Internal Server Error) if the nodeRegistry couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/node-registries")
    @Timed
    public ResponseEntity<NodeRegistry> updateNodeRegistry(@Valid @RequestBody NodeRegistry nodeRegistry) throws URISyntaxException {
        log.debug("REST request to update NodeRegistry : {}", nodeRegistry);
        if (nodeRegistry.getId() == null) {
            return createNodeRegistry(nodeRegistry);
        }
        NodeRegistry result = nodeRegistryRepository.save(nodeRegistry);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, nodeRegistry.getId().toString()))
            .body(result);
    }

    /**
     * GET  /node-registries : get all the nodeRegistries.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of nodeRegistries in body
     */
    @GetMapping("/node-registries")
    @Timed
    public List<NodeRegistry> getAllNodeRegistries() {
        log.debug("REST request to get all NodeRegistries");
        return nodeRegistryRepository.findAll();
        }

    /**
     * GET  /node-registries/:id : get the "id" nodeRegistry.
     *
     * @param id the id of the nodeRegistry to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the nodeRegistry, or with status 404 (Not Found)
     */
    @GetMapping("/node-registries/{id}")
    @Timed
    public ResponseEntity<NodeRegistry> getNodeRegistry(@PathVariable Long id) {
        log.debug("REST request to get NodeRegistry : {}", id);
        NodeRegistry nodeRegistry = nodeRegistryRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(nodeRegistry));
    }

    /**
     * DELETE  /node-registries/:id : delete the "id" nodeRegistry.
     *
     * @param id the id of the nodeRegistry to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/node-registries/{id}")
    @Timed
    public ResponseEntity<Void> deleteNodeRegistry(@PathVariable Long id) {
        log.debug("REST request to delete NodeRegistry : {}", id);
        nodeRegistryRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

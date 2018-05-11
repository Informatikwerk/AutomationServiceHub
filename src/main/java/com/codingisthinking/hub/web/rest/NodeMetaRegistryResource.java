package com.codingisthinking.hub.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.codingisthinking.hub.domain.NodeMetaRegistry;

import com.codingisthinking.hub.repository.NodeMetaRegistryRepository;
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
 * REST controller for managing NodeMetaRegistry.
 */
@RestController
@RequestMapping("/api")
public class NodeMetaRegistryResource {

    private final Logger log = LoggerFactory.getLogger(NodeMetaRegistryResource.class);

    private static final String ENTITY_NAME = "nodeMetaRegistry";

    private final NodeMetaRegistryRepository nodeMetaRegistryRepository;

    public NodeMetaRegistryResource(NodeMetaRegistryRepository nodeMetaRegistryRepository) {
        this.nodeMetaRegistryRepository = nodeMetaRegistryRepository;
    }

    /**
     * POST  /node-meta-registries : Create a new nodeMetaRegistry.
     *
     * @param nodeMetaRegistry the nodeMetaRegistry to create
     * @return the ResponseEntity with status 201 (Created) and with body the new nodeMetaRegistry, or with status 400 (Bad Request) if the nodeMetaRegistry has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/node-meta-registries")
    @Timed
    public ResponseEntity<NodeMetaRegistry> createNodeMetaRegistry(@Valid @RequestBody NodeMetaRegistry nodeMetaRegistry) throws URISyntaxException {
        log.debug("REST request to save NodeMetaRegistry : {}", nodeMetaRegistry);
        if (nodeMetaRegistry.getId() != null) {
            throw new BadRequestAlertException("A new nodeMetaRegistry cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NodeMetaRegistry result = nodeMetaRegistryRepository.save(nodeMetaRegistry);
        return ResponseEntity.created(new URI("/api/node-meta-registries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /node-meta-registries : Updates an existing nodeMetaRegistry.
     *
     * @param nodeMetaRegistry the nodeMetaRegistry to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated nodeMetaRegistry,
     * or with status 400 (Bad Request) if the nodeMetaRegistry is not valid,
     * or with status 500 (Internal Server Error) if the nodeMetaRegistry couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/node-meta-registries")
    @Timed
    public ResponseEntity<NodeMetaRegistry> updateNodeMetaRegistry(@Valid @RequestBody NodeMetaRegistry nodeMetaRegistry) throws URISyntaxException {
        log.debug("REST request to update NodeMetaRegistry : {}", nodeMetaRegistry);
        if (nodeMetaRegistry.getId() == null) {
            return createNodeMetaRegistry(nodeMetaRegistry);
        }
        NodeMetaRegistry result = nodeMetaRegistryRepository.save(nodeMetaRegistry);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, nodeMetaRegistry.getId().toString()))
            .body(result);
    }

    /**
     * GET  /node-meta-registries : get all the nodeMetaRegistries.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of nodeMetaRegistries in body
     */
    @GetMapping("/node-meta-registries")
    @Timed
    public List<NodeMetaRegistry> getAllNodeMetaRegistries() {
        log.debug("REST request to get all NodeMetaRegistries");
        return nodeMetaRegistryRepository.findAll();
        }

    /**
     * GET  /node-meta-registries/:id : get the "id" nodeMetaRegistry.
     *
     * @param id the id of the nodeMetaRegistry to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the nodeMetaRegistry, or with status 404 (Not Found)
     */
    @GetMapping("/node-meta-registries/{id}")
    @Timed
    public ResponseEntity<NodeMetaRegistry> getNodeMetaRegistry(@PathVariable Long id) {
        log.debug("REST request to get NodeMetaRegistry : {}", id);
        NodeMetaRegistry nodeMetaRegistry = nodeMetaRegistryRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(nodeMetaRegistry));
    }

    /**
     * DELETE  /node-meta-registries/:id : delete the "id" nodeMetaRegistry.
     *
     * @param id the id of the nodeMetaRegistry to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/node-meta-registries/{id}")
    @Timed
    public ResponseEntity<Void> deleteNodeMetaRegistry(@PathVariable Long id) {
        log.debug("REST request to delete NodeMetaRegistry : {}", id);
        nodeMetaRegistryRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

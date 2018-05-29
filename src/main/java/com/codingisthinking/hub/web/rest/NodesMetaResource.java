package com.codingisthinking.hub.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.codingisthinking.hub.domain.NodesMeta;

import com.codingisthinking.hub.repository.NodesMetaRepository;
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
 * REST controller for managing NodesMeta.
 */
@RestController
@RequestMapping("/api")
public class NodesMetaResource {

    private final Logger log = LoggerFactory.getLogger(NodesMetaResource.class);

    private static final String ENTITY_NAME = "nodesMeta";

    private final NodesMetaRepository nodesMetaRepository;

    public NodesMetaResource(NodesMetaRepository nodesMetaRepository) {
        this.nodesMetaRepository = nodesMetaRepository;
    }

    /**
     * POST  /nodes-metas : Create a new nodesMeta.
     *
     * @param nodesMeta the nodesMeta to create
     * @return the ResponseEntity with status 201 (Created) and with body the new nodesMeta, or with status 400 (Bad Request) if the nodesMeta has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/nodes-metas")
    @Timed
    public ResponseEntity<NodesMeta> createNodesMeta(@Valid @RequestBody NodesMeta nodesMeta) throws URISyntaxException {
        log.debug("REST request to save NodesMeta : {}", nodesMeta);
        if (nodesMeta.getId() != null) {
            throw new BadRequestAlertException("A new nodesMeta cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NodesMeta result = nodesMetaRepository.save(nodesMeta);
        return ResponseEntity.created(new URI("/api/nodes-metas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /nodes-metas : Updates an existing nodesMeta.
     *
     * @param nodesMeta the nodesMeta to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated nodesMeta,
     * or with status 400 (Bad Request) if the nodesMeta is not valid,
     * or with status 500 (Internal Server Error) if the nodesMeta couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/nodes-metas")
    @Timed
    public ResponseEntity<NodesMeta> updateNodesMeta(@Valid @RequestBody NodesMeta nodesMeta) throws URISyntaxException {
        log.debug("REST request to update NodesMeta : {}", nodesMeta);
        if (nodesMeta.getId() == null) {
            return createNodesMeta(nodesMeta);
        }
        NodesMeta result = nodesMetaRepository.save(nodesMeta);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, nodesMeta.getId().toString()))
            .body(result);
    }

    /**
     * GET  /nodes-metas : get all the nodesMetas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of nodesMetas in body
     */
    @GetMapping("/nodes-metas")
    @Timed
    public List<NodesMeta> getAllNodesMetas() {
        log.debug("REST request to get all NodesMetas");
        return nodesMetaRepository.findAll();
        }

    /**
     * GET  /nodes-metas/:id : get the "id" nodesMeta.
     *
     * @param id the id of the nodesMeta to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the nodesMeta, or with status 404 (Not Found)
     */
    @GetMapping("/nodes-metas/{id}")
    @Timed
    public ResponseEntity<NodesMeta> getNodesMeta(@PathVariable Long id) {
        log.debug("REST request to get NodesMeta : {}", id);
        NodesMeta nodesMeta = nodesMetaRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(nodesMeta));
    }

    /**
     * DELETE  /nodes-metas/:id : delete the "id" nodesMeta.
     *
     * @param id the id of the nodesMeta to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/nodes-metas/{id}")
    @Timed
    public ResponseEntity<Void> deleteNodesMeta(@PathVariable Long id) {
        log.debug("REST request to delete NodesMeta : {}", id);
        nodesMetaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

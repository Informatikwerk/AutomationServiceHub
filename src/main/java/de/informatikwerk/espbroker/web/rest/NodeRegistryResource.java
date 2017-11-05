package de.informatikwerk.espbroker.web.rest;

import com.codahale.metrics.annotation.Timed;
import de.informatikwerk.espbroker.domain.NodeRegistry;
import de.informatikwerk.espbroker.service.NodeRegistryService;
import de.informatikwerk.espbroker.web.rest.errors.BadRequestAlertException;
import de.informatikwerk.espbroker.web.rest.util.HeaderUtil;
import de.informatikwerk.espbroker.web.rest.util.PaginationUtil;
import de.informatikwerk.espbroker.service.dto.NodeRegistryCriteria;
import de.informatikwerk.espbroker.service.NodeRegistryQueryService;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    private final NodeRegistryService nodeRegistryService;

    private final NodeRegistryQueryService nodeRegistryQueryService;

    public NodeRegistryResource(NodeRegistryService nodeRegistryService, NodeRegistryQueryService nodeRegistryQueryService) {
        this.nodeRegistryService = nodeRegistryService;
        this.nodeRegistryQueryService = nodeRegistryQueryService;
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
    public ResponseEntity<NodeRegistry> createNodeRegistry(@RequestBody NodeRegistry nodeRegistry) throws URISyntaxException {
        log.debug("REST request to save NodeRegistry : {}", nodeRegistry);
        if (nodeRegistry.getId() != null) {
            throw new BadRequestAlertException("A new nodeRegistry cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NodeRegistry result = nodeRegistryService.save(nodeRegistry);
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
    public ResponseEntity<NodeRegistry> updateNodeRegistry(@RequestBody NodeRegistry nodeRegistry) throws URISyntaxException {
        log.debug("REST request to update NodeRegistry : {}", nodeRegistry);
        if (nodeRegistry.getId() == null) {
            return createNodeRegistry(nodeRegistry);
        }
        NodeRegistry result = nodeRegistryService.save(nodeRegistry);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, nodeRegistry.getId().toString()))
            .body(result);
    }

    /**
     * GET  /node-registries : get all the nodeRegistries.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of nodeRegistries in body
     */
    @GetMapping("/node-registries")
    @Timed
    public ResponseEntity<List<NodeRegistry>> getAllNodeRegistries(NodeRegistryCriteria criteria,@ApiParam Pageable pageable) {
        log.debug("REST request to get NodeRegistries by criteria: {}", criteria);
        Page<NodeRegistry> page = nodeRegistryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/node-registries");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
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
        NodeRegistry nodeRegistry = nodeRegistryService.findOne(id);
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
        nodeRegistryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

package de.informatikwerk.ash.web.rest;

import com.codahale.metrics.annotation.Timed;
import de.informatikwerk.ash.domain.Nodes;

import de.informatikwerk.ash.repository.NodesRepository;
import de.informatikwerk.ash.service.NodesService;
import de.informatikwerk.ash.web.rest.errors.BadRequestAlertException;
import de.informatikwerk.ash.web.rest.util.HeaderUtil;
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
 * REST controller for managing Nodes.
 */
@RestController
@RequestMapping("/api")
public class NodesResource {

    private final Logger log = LoggerFactory.getLogger(NodesResource.class);

    private static final String ENTITY_NAME = "nodes";

    private final NodesRepository nodesRepository;

    private final NodesService nodesService;

    public NodesResource(NodesRepository nodesRepository, NodesService nodesService) {
        this.nodesRepository = nodesRepository;
        this.nodesService = nodesService;
    }

    /**
     * POST  /nodes : Create a new nodes.
     *
     * @param nodes the nodes to create
     * @return the ResponseEntity with status 201 (Created) and with body the new nodes, or with status 400 (Bad Request) if the nodes has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/nodes")
    @Timed
    public ResponseEntity<Nodes> createNodes(@Valid @RequestBody Nodes nodes) throws URISyntaxException {
        log.debug("REST request to save Nodes : {}", nodes);
        if (nodes.getId() != null) {
            throw new BadRequestAlertException("A new nodes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Nodes result = nodesRepository.save(nodes);
        return ResponseEntity.created(new URI("/api/nodes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /nodes : Updates an existing nodes.
     *
     * @param nodes the nodes to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated nodes,
     * or with status 400 (Bad Request) if the nodes is not valid,
     * or with status 500 (Internal Server Error) if the nodes couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/nodes")
    @Timed
    public ResponseEntity<Nodes> updateNodes(@Valid @RequestBody Nodes nodes) throws URISyntaxException {
        log.debug("REST request to update Nodes : {}", nodes);
        if (nodes.getId() == null) {
            List<Nodes> nodesWithName = nodesService.findByName(nodes.getName());
            log.debug("Checking if Node with name {} already exists...",nodes.getName());
            if(nodesWithName != null && nodesWithName.size() > 0){
                nodes.setId(nodesWithName.get(0).getId());
                log.debug("...found existing Node with name {} which has the id {}",nodes.getName(),nodes.getId());
            } else {
                log.debug("...did not find existing Node with name {}",nodes.getName());
                return createNodes(nodes);
            }
        }
        Nodes result = nodesRepository.save(nodes);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, nodes.getId().toString()))
            .body(result);
    }

    /**
     * GET  /nodes : get all the nodes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of nodes in body
     */
    @GetMapping("/nodes")
    @Timed
    public List<Nodes> getAllNodes() {
        log.debug("REST request to get all Nodes");
        return nodesRepository.findAll();
        }

        /**
     * GET  /nodes : get all nodes for realmKey.
     *
     * @param realmkey the realmkey of the nodes to retrieve
     * @return the ResponseEntity with status 200 (OK) and the list of nodes in body
     */
    @GetMapping("/nodesForRealmkey/{realmkey}")
    @Timed
    public List<Nodes> getAllNodesForRealmkey(@PathVariable String realmkey) {
        log.debug("REST request to get all Nodes for realmkey: " + realmkey);
        return nodesService.findAllForRealmkey(realmkey);
    }

    /**
     * GET  /nodes/:id : get the "id" nodes.
     *
     * @param id the id of the nodes to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the nodes, or with status 404 (Not Found)
     */
    @GetMapping("/nodes/{id}")
    @Timed
    public ResponseEntity<Nodes> getNodes(@PathVariable Long id) {
        log.debug("REST request to get Nodes : {}", id);
        Nodes nodes = nodesRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(nodes));
    }

    /**
     * DELETE  /nodes/:id : delete the "id" nodes.
     *
     * @param id the id of the nodes to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/nodes/{id}")
    @Timed
    public ResponseEntity<Void> deleteNodes(@PathVariable Long id) {
        log.debug("REST request to delete Nodes : {}", id);
        nodesRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

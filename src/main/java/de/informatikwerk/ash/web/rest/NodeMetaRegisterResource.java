package de.informatikwerk.ash.web.rest;

import com.codahale.metrics.annotation.Timed;
import de.informatikwerk.ash.domain.NodeMetaRegister;

import de.informatikwerk.ash.repository.NodeMetaRegisterRepository;
import de.informatikwerk.ash.web.rest.errors.BadRequestAlertException;
import de.informatikwerk.ash.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing NodeMetaRegister.
 */
@RestController
@RequestMapping("/api")
public class NodeMetaRegisterResource {

    private final Logger log = LoggerFactory.getLogger(NodeMetaRegisterResource.class);

    private static final String ENTITY_NAME = "nodeMetaRegister";

    private final NodeMetaRegisterRepository nodeMetaRegisterRepository;

    public NodeMetaRegisterResource(NodeMetaRegisterRepository nodeMetaRegisterRepository) {
        this.nodeMetaRegisterRepository = nodeMetaRegisterRepository;
    }

    /**
     * POST  /node-meta-registers : Create a new nodeMetaRegister.
     *
     * @param nodeMetaRegister the nodeMetaRegister to create
     * @return the ResponseEntity with status 201 (Created) and with body the new nodeMetaRegister, or with status 400 (Bad Request) if the nodeMetaRegister has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/node-meta-registers")
    @Timed
    public ResponseEntity<NodeMetaRegister> createNodeMetaRegister(@RequestBody NodeMetaRegister nodeMetaRegister) throws URISyntaxException {
        log.debug("REST request to save NodeMetaRegister : {}", nodeMetaRegister);
        if (nodeMetaRegister.getId() != null) {
            throw new BadRequestAlertException("A new nodeMetaRegister cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NodeMetaRegister result = nodeMetaRegisterRepository.save(nodeMetaRegister);
        return ResponseEntity.created(new URI("/api/node-meta-registers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /node-meta-registers : Updates an existing nodeMetaRegister.
     *
     * @param nodeMetaRegister the nodeMetaRegister to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated nodeMetaRegister,
     * or with status 400 (Bad Request) if the nodeMetaRegister is not valid,
     * or with status 500 (Internal Server Error) if the nodeMetaRegister couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/node-meta-registers")
    @Timed
    public ResponseEntity<NodeMetaRegister> updateNodeMetaRegister(@RequestBody NodeMetaRegister nodeMetaRegister) throws URISyntaxException {
        log.debug("REST request to update NodeMetaRegister : {}", nodeMetaRegister);
        if (nodeMetaRegister.getId() == null) {
            return createNodeMetaRegister(nodeMetaRegister);
        }
        NodeMetaRegister result = nodeMetaRegisterRepository.save(nodeMetaRegister);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, nodeMetaRegister.getId().toString()))
            .body(result);
    }

    /**
     * GET  /node-meta-registers : get all the nodeMetaRegisters.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of nodeMetaRegisters in body
     */
    @GetMapping("/node-meta-registers")
    @Timed
    public List<NodeMetaRegister> getAllNodeMetaRegisters() {
        log.debug("REST request to get all NodeMetaRegisters");
        return nodeMetaRegisterRepository.findAll();
        }

    /**
     * GET  /node-meta-registers/:id : get the "id" nodeMetaRegister.
     *
     * @param id the id of the nodeMetaRegister to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the nodeMetaRegister, or with status 404 (Not Found)
     */
    @GetMapping("/node-meta-registers/{id}")
    @Timed
    public ResponseEntity<NodeMetaRegister> getNodeMetaRegister(@PathVariable Long id) {
        log.debug("REST request to get NodeMetaRegister : {}", id);
        NodeMetaRegister nodeMetaRegister = nodeMetaRegisterRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(nodeMetaRegister));
    }

    /**
     * DELETE  /node-meta-registers/:id : delete the "id" nodeMetaRegister.
     *
     * @param id the id of the nodeMetaRegister to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/node-meta-registers/{id}")
    @Timed
    public ResponseEntity<Void> deleteNodeMetaRegister(@PathVariable Long id) {
        log.debug("REST request to delete NodeMetaRegister : {}", id);
        nodeMetaRegisterRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

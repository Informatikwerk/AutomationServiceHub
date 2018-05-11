package com.codingisthinking.hub.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.codingisthinking.hub.domain.GuiRegister;

import com.codingisthinking.hub.repository.GuiRegisterRepository;
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
 * REST controller for managing GuiRegister.
 */
@RestController
@RequestMapping("/api")
public class GuiRegisterResource {

    private final Logger log = LoggerFactory.getLogger(GuiRegisterResource.class);

    private static final String ENTITY_NAME = "guiRegister";

    private final GuiRegisterRepository guiRegisterRepository;

    public GuiRegisterResource(GuiRegisterRepository guiRegisterRepository) {
        this.guiRegisterRepository = guiRegisterRepository;
    }

    /**
     * POST  /gui-registers : Create a new guiRegister.
     *
     * @param guiRegister the guiRegister to create
     * @return the ResponseEntity with status 201 (Created) and with body the new guiRegister, or with status 400 (Bad Request) if the guiRegister has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/gui-registers")
    @Timed
    public ResponseEntity<GuiRegister> createGuiRegister(@Valid @RequestBody GuiRegister guiRegister) throws URISyntaxException {
        log.debug("REST request to save GuiRegister : {}", guiRegister);
        if (guiRegister.getId() != null) {
            throw new BadRequestAlertException("A new guiRegister cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GuiRegister result = guiRegisterRepository.save(guiRegister);
        return ResponseEntity.created(new URI("/api/gui-registers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /gui-registers : Updates an existing guiRegister.
     *
     * @param guiRegister the guiRegister to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated guiRegister,
     * or with status 400 (Bad Request) if the guiRegister is not valid,
     * or with status 500 (Internal Server Error) if the guiRegister couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/gui-registers")
    @Timed
    public ResponseEntity<GuiRegister> updateGuiRegister(@Valid @RequestBody GuiRegister guiRegister) throws URISyntaxException {
        log.debug("REST request to update GuiRegister : {}", guiRegister);
        if (guiRegister.getId() == null) {
            return createGuiRegister(guiRegister);
        }
        GuiRegister result = guiRegisterRepository.save(guiRegister);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, guiRegister.getId().toString()))
            .body(result);
    }

    /**
     * GET  /gui-registers : get all the guiRegisters.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of guiRegisters in body
     */
    @GetMapping("/gui-registers")
    @Timed
    public List<GuiRegister> getAllGuiRegisters() {
        log.debug("REST request to get all GuiRegisters");
        return guiRegisterRepository.findAll();
        }

    /**
     * GET  /gui-registers/:id : get the "id" guiRegister.
     *
     * @param id the id of the guiRegister to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the guiRegister, or with status 404 (Not Found)
     */
    @GetMapping("/gui-registers/{id}")
    @Timed
    public ResponseEntity<GuiRegister> getGuiRegister(@PathVariable Long id) {
        log.debug("REST request to get GuiRegister : {}", id);
        GuiRegister guiRegister = guiRegisterRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(guiRegister));
    }

    /**
     * DELETE  /gui-registers/:id : delete the "id" guiRegister.
     *
     * @param id the id of the guiRegister to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/gui-registers/{id}")
    @Timed
    public ResponseEntity<Void> deleteGuiRegister(@PathVariable Long id) {
        log.debug("REST request to delete GuiRegister : {}", id);
        guiRegisterRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

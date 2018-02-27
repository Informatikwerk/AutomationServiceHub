package de.informatikwerk.ash.web.rest;

import com.codahale.metrics.annotation.Timed;
import de.informatikwerk.ash.domain.Gui;

import de.informatikwerk.ash.repository.GuiRepository;
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
 * REST controller for managing Gui.
 */
@RestController
@RequestMapping("/api")
public class GuiResource {

    private final Logger log = LoggerFactory.getLogger(GuiResource.class);

    private static final String ENTITY_NAME = "gui";

    private final GuiRepository guiRepository;

    public GuiResource(GuiRepository guiRepository) {
        this.guiRepository = guiRepository;
    }

    /**
     * POST  /guis : Create a new gui.
     *
     * @param gui the gui to create
     * @return the ResponseEntity with status 201 (Created) and with body the new gui, or with status 400 (Bad Request) if the gui has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/guis")
    @Timed
    public ResponseEntity<Gui> createGui(@RequestBody Gui gui) throws URISyntaxException {
        log.debug("REST request to save Gui : {}", gui);
        if (gui.getId() != null) {
            throw new BadRequestAlertException("A new gui cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Gui result = guiRepository.save(gui);
        return ResponseEntity.created(new URI("/api/guis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /guis : Updates an existing gui.
     *
     * @param gui the gui to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated gui,
     * or with status 400 (Bad Request) if the gui is not valid,
     * or with status 500 (Internal Server Error) if the gui couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/guis")
    @Timed
    public ResponseEntity<Gui> updateGui(@RequestBody Gui gui) throws URISyntaxException {
        log.debug("REST request to update Gui : {}", gui);
        if (gui.getId() == null) {
            return createGui(gui);
        }
        Gui result = guiRepository.save(gui);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, gui.getId().toString()))
            .body(result);
    }

    /**
     * GET  /guis : get all the guis.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of guis in body
     */
    @GetMapping("/guis")
    @Timed
    public List<Gui> getAllGuis() {
        log.debug("REST request to get all Guis");
        return guiRepository.findAll();
        }

    /**
     * GET  /guis/:id : get the "id" gui.
     *
     * @param id the id of the gui to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the gui, or with status 404 (Not Found)
     */
    @GetMapping("/guis/{id}")
    @Timed
    public ResponseEntity<Gui> getGui(@PathVariable Long id) {
        log.debug("REST request to get Gui : {}", id);
        Gui gui = guiRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(gui));
    }

    /**
     * DELETE  /guis/:id : delete the "id" gui.
     *
     * @param id the id of the gui to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/guis/{id}")
    @Timed
    public ResponseEntity<Void> deleteGui(@PathVariable Long id) {
        log.debug("REST request to delete Gui : {}", id);
        guiRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

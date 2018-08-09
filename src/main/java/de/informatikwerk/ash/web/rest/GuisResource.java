package de.informatikwerk.ash.web.rest;

import com.codahale.metrics.annotation.Timed;
import de.informatikwerk.ash.domain.Guis;
import de.informatikwerk.ash.repository.GuisRepository;
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
 * REST controller for managing Guis.
 */
@RestController
@RequestMapping("/api")
public class GuisResource {

    private final Logger log = LoggerFactory.getLogger(GuisResource.class);

    private static final String ENTITY_NAME = "guis";

    private final GuisRepository guisRepository;

    public GuisResource(GuisRepository guisRepository) {
        this.guisRepository = guisRepository;
    }

    /**
     * POST  /guis : Create a new guis.
     *
     * @param guis the guis to create
     * @return the ResponseEntity with status 201 (Created) and with body the new guis, or with status 400 (Bad Request) if the guis has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/guis")
    @Timed
    public ResponseEntity<Guis> createGuis(@Valid @RequestBody Guis guis) throws URISyntaxException {
        log.debug("REST request to save Guis : {}", guis);
        if (guis.getId() != null) {
            throw new BadRequestAlertException("A new guis cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Guis result = guisRepository.save(guis);
        return ResponseEntity.created(new URI("/api/guis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /guis : Updates an existing guis.
     *
     * @param guis the guis to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated guis,
     * or with status 400 (Bad Request) if the guis is not valid,
     * or with status 500 (Internal Server Error) if the guis couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/guis")
    @Timed
    public ResponseEntity<Guis> updateGuis(@Valid @RequestBody Guis guis) throws URISyntaxException {
        log.debug("REST request to update Guis : {}", guis);
        if (guis.getId() == null) {
            return createGuis(guis);
        }
        Guis result = guisRepository.save(guis);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, guis.getId().toString()))
            .body(result);
    }

    /**
     * GET  /guis : get all the guis.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of guis in body
     */
    @GetMapping("/guis")
    @Timed
    public List<Guis> getAllGuis() {
        log.debug("REST request to get all Guis");
        return guisRepository.findAll();
        }

    /**
     * GET  /guis/:id : get the "id" guis.
     *
     * @param id the id of the guis to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the guis, or with status 404 (Not Found)
     */
    @GetMapping("/guis/{id}")
    @Timed
    public ResponseEntity<Guis> getGuis(@PathVariable Long id) {
        log.debug("REST request to get Guis : {}", id);
        Guis guis = guisRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(guis));
    }

    /**
     * DELETE  /guis/:id : delete the "id" guis.
     *
     * @param id the id of the guis to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/guis/{id}")
    @Timed
    public ResponseEntity<Void> deleteGuis(@PathVariable Long id) {
        log.debug("REST request to delete Guis : {}", id);
        guisRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

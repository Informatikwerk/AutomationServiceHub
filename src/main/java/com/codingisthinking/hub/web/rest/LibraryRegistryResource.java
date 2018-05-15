package com.codingisthinking.hub.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.codingisthinking.hub.domain.LibraryRegistry;

import com.codingisthinking.hub.repository.LibraryRegistryRepository;
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
 * REST controller for managing LibraryRegistry.
 */
@RestController
@RequestMapping("/api")
public class LibraryRegistryResource {

    private final Logger log = LoggerFactory.getLogger(LibraryRegistryResource.class);

    private static final String ENTITY_NAME = "libraryRegistry";

    private final LibraryRegistryRepository libraryRegistryRepository;

    public LibraryRegistryResource(LibraryRegistryRepository libraryRegistryRepository) {
        this.libraryRegistryRepository = libraryRegistryRepository;
    }

    /**
     * POST  /library-registries : Create a new libraryRegistry.
     *
     * @param libraryRegistry the libraryRegistry to create
     * @return the ResponseEntity with status 201 (Created) and with body the new libraryRegistry, or with status 400 (Bad Request) if the libraryRegistry has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/library-registries")
    @Timed
    public ResponseEntity<LibraryRegistry> createLibraryRegistry(@Valid @RequestBody LibraryRegistry libraryRegistry) throws URISyntaxException {
        log.debug("REST request to save LibraryRegistry : {}", libraryRegistry);
        if (libraryRegistry.getId() != null) {
            throw new BadRequestAlertException("A new libraryRegistry cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LibraryRegistry result = libraryRegistryRepository.save(libraryRegistry);
        return ResponseEntity.created(new URI("/api/library-registries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /library-registries : Updates an existing libraryRegistry.
     *
     * @param libraryRegistry the libraryRegistry to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated libraryRegistry,
     * or with status 400 (Bad Request) if the libraryRegistry is not valid,
     * or with status 500 (Internal Server Error) if the libraryRegistry couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/library-registries")
    @Timed
    public ResponseEntity<LibraryRegistry> updateLibraryRegistry(@Valid @RequestBody LibraryRegistry libraryRegistry) throws URISyntaxException {
        log.debug("REST request to update LibraryRegistry : {}", libraryRegistry);
        if (libraryRegistry.getId() == null) {
            return createLibraryRegistry(libraryRegistry);
        }
        LibraryRegistry result = libraryRegistryRepository.save(libraryRegistry);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, libraryRegistry.getId().toString()))
            .body(result);
    }

    /**
     * GET  /library-registries : get all the libraryRegistries.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of libraryRegistries in body
     */
    @GetMapping("/library-registries")
    @Timed
    public List<LibraryRegistry> getAllLibraryRegistries() {
        log.debug("REST request to get all LibraryRegistries");
        return libraryRegistryRepository.findAll();
        }

    /**
     * GET  /library-registries/:id : get the "id" libraryRegistry.
     *
     * @param id the id of the libraryRegistry to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the libraryRegistry, or with status 404 (Not Found)
     */
    @GetMapping("/library-registries/{id}")
    @Timed
    public ResponseEntity<LibraryRegistry> getLibraryRegistry(@PathVariable Long id) {
        log.debug("REST request to get LibraryRegistry : {}", id);
        LibraryRegistry libraryRegistry = libraryRegistryRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(libraryRegistry));
    }

    /**
     * DELETE  /library-registries/:id : delete the "id" libraryRegistry.
     *
     * @param id the id of the libraryRegistry to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/library-registries/{id}")
    @Timed
    public ResponseEntity<Void> deleteLibraryRegistry(@PathVariable Long id) {
        log.debug("REST request to delete LibraryRegistry : {}", id);
        libraryRegistryRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

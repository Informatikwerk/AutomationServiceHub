package com.codingisthinking.hub.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.codingisthinking.hub.domain.LibraryRegistry;
import com.codingisthinking.hub.domain.Sources;

import com.codingisthinking.hub.repository.SourcesRepository;
import com.codingisthinking.hub.service.DownloadService;
import com.codingisthinking.hub.web.rest.errors.BadRequestAlertException;
import com.codingisthinking.hub.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Sources.
 */
@RestController
@RequestMapping("/api")
public class SourcesResource {

    private final Logger log = LoggerFactory.getLogger(SourcesResource.class);

    private static final String ENTITY_NAME = "sources";

    private final SourcesRepository sourcesRepository;

    private final DownloadService downloadService;

    public SourcesResource(SourcesRepository sourcesRepository, DownloadService downloadService) {
        this.sourcesRepository = sourcesRepository;
        this.downloadService = downloadService;
    }

    /**
     * POST  /sources : Create a new sources.
     *
     * @param sources the sources to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sources, or with status 400 (Bad Request) if the sources has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sources")
    @Timed
    public ResponseEntity<Sources> createSources(@Valid @RequestBody Sources sources) throws URISyntaxException {
        log.debug("REST request to save Sources : {}", sources);
        if (sources.getId() != null) {
            throw new BadRequestAlertException("A new sources cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Sources result = sourcesRepository.save(sources);
        return ResponseEntity.created(new URI("/api/sources/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sources : Updates an existing sources.
     *
     * @param sources the sources to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sources,
     * or with status 400 (Bad Request) if the sources is not valid,
     * or with status 500 (Internal Server Error) if the sources couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sources")
    @Timed
    public ResponseEntity<Sources> updateSources(@Valid @RequestBody Sources sources) throws URISyntaxException {
        log.debug("REST request to update Sources : {}", sources);
        if (sources.getId() == null) {
            return createSources(sources);
        }
        Sources result = sourcesRepository.save(sources);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, sources.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sources : get all the sources.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of sources in body
     */
    @GetMapping("/sources")
    @Timed
    public List<Sources> getAllSources() {
        log.debug("REST request to get all Sources");
        return sourcesRepository.findAll();
        }

    /**
     * GET  /sources/:id : get the "id" sources.
     *
     * @param id the id of the sources to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sources, or with status 404 (Not Found)
     */
    @GetMapping("/sources/{id}")
    @Timed
    public ResponseEntity<Sources> getSources(@PathVariable Long id) {
        log.debug("REST request to get Sources : {}", id);
        Sources sources = sourcesRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(sources));
    }

    /**
     * DELETE  /sources/:id : delete the "id" sources.
     *
     * @param id the id of the sources to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sources/{id}")
    @Timed
    public ResponseEntity<Void> deleteSources(@PathVariable Long id) {
        log.debug("REST request to delete Sources : {}", id);
        sourcesRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * GET  /sources/lib/:id : get the sources with lib "id" .
     *
     * @param id the id of the lib to retrieve related sources
     * @return the ResponseEntity with status 200 (OK) and with body the sources, or with status 404 (Not Found)
     */
    @GetMapping("/sources/lib/{id}")
    @Timed
    public List<Sources> getSourcesByLibraryRegistryId(@PathVariable Long id) {
        log.debug("REST request to get Sources for given lib id: {}", id);
        Sources sources = new Sources();
        LibraryRegistry libraryRegistry = new LibraryRegistry();
        libraryRegistry.setId(id);
        sources.setLibraryRegistry(libraryRegistry);
        return sourcesRepository.findAll(Example.of(sources));
    }

    /**
     * DELETE  /sources/lib/:id : delete the sources with lib "id".
     *
     * @param id the id of the library to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sources/lib/{id}")
    @Timed
    public ResponseEntity<Void> deleteSourcesByLibraryRegistryId(@PathVariable Long id) {
        log.debug("REST request to delete Sources with lib id: {}", id);
        Sources sources = new Sources();
        LibraryRegistry libraryRegistry = new LibraryRegistry();
        libraryRegistry.setId(id);
        sources.setLibraryRegistry(libraryRegistry);
        sourcesRepository.delete(sourcesRepository.findAll(Example.of(sources)));
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }


    /**
     * GET  /sources/lib/:id : get the sources with lib "id" in zip file format .
     *
     * @param id the id of the lib to retrieve related sources
     * @return the ResponseEntity with status 200 (OK) and with body the sources, or with status 404 (Not Found)
     */
    @GetMapping(value="/sources/lib/{id}/zip")
    @Timed
    public ResponseEntity<ByteArrayResource> getZipedSourcesByLibraryRegistryId(@PathVariable Long id) {
        log.debug("REST request to get Sources for given lib id in zip format: {}", id);
        Sources sources = new Sources();
        LibraryRegistry libraryRegistry = new LibraryRegistry();
        libraryRegistry.setId(id);
        sources.setLibraryRegistry(libraryRegistry);
        byte[] zipBytes = null;
        ByteArrayResource resource = null;
        try {
            zipBytes = downloadService.getZip(sourcesRepository.findAll(Example.of(sources)));
            resource = new ByteArrayResource(zipBytes);
        } catch (IOException e) {
            System.out.println("Exception on zip process.");
            e.printStackTrace();
        }
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename=" + "sources.zip")
            .contentType(MediaType.APPLICATION_OCTET_STREAM).contentLength(zipBytes.length)
            .body(resource);
    }
}

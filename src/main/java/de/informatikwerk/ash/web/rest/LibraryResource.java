package de.informatikwerk.ash.web.rest;

import com.codahale.metrics.annotation.Timed;
import de.informatikwerk.ash.web.rest.vm.LibraryLoadVM;
import de.informatikwerk.ash.web.rest.vm.LibrarySaveVM;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Library.
 */
@RestController
@RequestMapping("/api/library")
public class LibraryResource {

    private final Logger log = LoggerFactory.getLogger(LibraryResource.class);

    /**
     * POST  /library : Save Library.
     *
     * @param librarySaveVM the Library to save
     * @return the ResponseEntity with status 201 (Created) and with body the new LibrarySaveVM, or with status 400 (Bad Request) if the Library has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/library")
    @Timed
    public ResponseEntity postLibrary(@RequestBody LibrarySaveVM librarySaveVM) throws URISyntaxException {
        log.debug("REST request to save LibrarySaveVM : {}", librarySaveVM);
        //TODO please code the save of page data.
        return ResponseEntity.ok().build();
    }
    /**
     * GET  /library : get Library.
     *
     * @return the ResponseEntity with status 200 (OK) and with body the libraryLoadVM, or with status 404 (Not Found)
     */
    @GetMapping("/library")
    @Timed
    public ResponseEntity<LibraryLoadVM> getLibrary() {
        log.debug("REST request to get LibraryLoadVM");
        LibraryLoadVM libraryLoadVM = null;
        //TODO please code the load referential data or any utils data to load the page.
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(libraryLoadVM));
    }


}

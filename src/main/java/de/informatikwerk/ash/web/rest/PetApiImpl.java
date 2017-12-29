package de.informatikwerk.ash.web.rest;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

import de.informatikwerk.ash.domain.NodeRegistry;
import de.informatikwerk.ash.web.api.PetApi;
import de.informatikwerk.ash.web.api.model.Pet;
import de.informatikwerk.ash.web.rest.util.PaginationUtil;

@RestController
@RequestMapping("/api")
public class PetApiImpl implements PetApi {

	@GetMapping("/pets")
    @Timed
	public ResponseEntity<List<Pet>> findPetsByStatus(List<String> status) {
		// TODO Auto-generated method stub
		return PetApi.super.findPetsByStatus(status);
	}
	
    
}

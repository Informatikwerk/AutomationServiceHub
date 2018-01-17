/**
 * NOTE: This class is auto generated by the swagger code generator program (2.2.3).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package de.informatikwerk.ash.web.api;

import de.informatikwerk.ash.web.api.model.Pet;

import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import javax.validation.constraints.*;
import javax.validation.Valid;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-01-12T17:49:26.229+01:00")

@Api(value = "pet", description = "the pet API")
public interface PetApi {

    @ApiOperation(value = "Finds Pets by status", notes = "Multiple status values can be provided with comma separated strings", response = Pet.class, responseContainer = "List", tags={ "api", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "successful operation", response = Pet.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Invalid status value", response = Void.class) })
    
    @RequestMapping(value = "/pet/findByStatus2",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    default ResponseEntity<List<Pet>> findPetsByStatus( @NotNull@ApiParam(value = "Status values that need to be considered for filter", required = true, allowableValues = "available, pending, sold") @RequestParam(value = "status", required = true) List<String> status) {
        // do some magic!
        return new ResponseEntity<List<Pet>>(HttpStatus.OK);
    }

}

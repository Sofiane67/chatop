package fr.chatop.controller;

import fr.chatop.dto.ErrorEntity;
import fr.chatop.dto.RentalDTO;
import fr.chatop.dto.response.RentalsResponse;
import fr.chatop.dto.response.ResponseMessage;
import fr.chatop.entity.Rental;
import fr.chatop.exception.FileTypeException;
import fr.chatop.mapper.RentalMapper;
import fr.chatop.service.RentalService;
import fr.chatop.service.StorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.stream.Stream;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@Tag(name = "Rental", description = "Concerns all operations for rentals")
@AllArgsConstructor
@RestController
@Slf4j
@RequestMapping(path = "rentals", produces = APPLICATION_JSON_VALUE)
public class RentalController {
    private RentalService rentalService;
    private StorageService storageService;

    @Operation(
        summary = "All rentals",
        description = "Returns list of all rentals"
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Query executed successfully",
                content = {
                    @Content(
                        schema = @Schema(implementation = RentalDTO.class),
                        mediaType = "application/json",
                        examples = {
                            @ExampleObject(
                                name = "Success response",
                                value = "{\n" +
                                    "    \"rentals\": [\n" +
                                    "        {\n" +
                                    "            \"id\": 8,\n" +
                                    "            \"name\": \"Rental Example\",\n" +
                                    "            \"surface\": 500.0,\n" +
                                    "            \"price\": 899000.0,\n" +
                                    "            \"picture\": \"image url\",\n" +
                                    "            \"description\": \"Some rental description\",\n" +
                                    "            \"owner_id\": 19,\n" +
                                    "            \"created_at\": \"2023-11-01T16:39:52.947+00:00\",\n" +
                                    "            \"updated_at\": \"2023-11-01T16:39:52.947+00:00\"\n" +
                                    "        }\n" +
                                    "    ]\n" +
                                    "}",
                                summary = "Example of return when the query executed successfully"
                            )
                        }
                    )
                },
                headers = {
                    @Header(
                        name = HttpHeaders.AUTHORIZATION,
                        description = "Bearer token",
                        schema = @Schema(type = "string"),
                        required = true
                    )
                }
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Bad request",
                content = {
                    @Content(
                        schema = @Schema(),
                        mediaType = "application/json",
                        examples = {}
                    )
                }
            ),
            @ApiResponse(
                responseCode = "403",
                description = "Forbidden",
                content = {
                    @Content(
                        schema = @Schema(),
                        mediaType = "application/json",
                        examples = {}
                    )
                }
            )
        }
    )
    @SecurityRequirement(name = "Bearer Auth")
    @GetMapping
    public ResponseEntity<RentalsResponse>  getAllRental(){

        Stream<RentalDTO> rentals = this.rentalService.getAllRentals();
        RentalsResponse response = new RentalsResponse();
        response.setRentals(rentals);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(
        summary = "Rental information by id",
        description = "Returns a rental corresponding to the id passed as a parameter"
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Query executed successfully",
                content = {
                    @Content(
                        schema = @Schema(implementation = RentalDTO.class),
                        mediaType = "application/json",
                        examples = {
                            @ExampleObject(
                                name = "Success response",
                                value = "{\n" +
                                    " \"id\": 8,\n" +
                                    " \"name\": \"Rental Example\",\n" +
                                    " \"surface\": 500.0,\n" +
                                    " \"price\": 899000.0,\n" +
                                    " \"picture\": \"image url\",\n" +
                                    " \"description\": \"Some rental description\",\n" +
                                    " \"owner_id\": 19,\n" +
                                    " \"created_at\": \"2023-11-01T16:39:52.947+00:00\",\n" +
                                    " \"updated_at\": \"2023-11-01T16:39:52.947+00:00\"\n" +
                                    "}",
                                summary = "Example of return when the query executed successfully"
                            )
                        }
                    )
                },
                headers = {
                    @Header(
                        name = HttpHeaders.AUTHORIZATION,
                        description = "Bearer token",
                        schema = @Schema(type = "string"),
                        required = true
                    )
                }
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Bad request",
                content = {
                    @Content(
                        schema = @Schema(),
                        mediaType = "application/json",
                        examples = {}
                    )
                }
            ),
            @ApiResponse(
                responseCode = "403",
                description = "Forbidden",
                content = {
                    @Content(
                        schema = @Schema(),
                        mediaType = "application/json",
                        examples = {}
                    )
                }
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Rental not found",
                content = {
                    @Content(
                        schema = @Schema(implementation = ErrorEntity.class),
                        mediaType = "application/json",
                        examples = {
                            @ExampleObject(
                                name = "Rental not found",
                                value = "{" +
                                    "\n  \"code\": 404,\n \"status\": " +
                                    "\"NOT_FOUND\"\n ,\n \"message\": " +
                                    "\"Resource not found\"\n }",
                                summary = "Example response when a rental is not found"
                            )
                        }
                    )
                }
            )
        }
    )
    @SecurityRequirement(name = "Bearer Auth")
    @GetMapping(path = "{id}")
    public ResponseEntity<RentalDTO> getRental(@PathVariable int id){
        Rental rental = this.rentalService.getRentalById(id);
        return ResponseEntity.status(HttpStatus.OK).body(RentalMapper.mapper(rental));
    }


    @Operation(
        summary = "Rental creation",
        description = "Create a new rental"
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "201",
                description = "Created successfully",
                content = {
                    @Content(
                        schema = @Schema(implementation = ResponseMessage.class),
                        mediaType = "application/json",
                        examples = {
                            @ExampleObject(
                                name = "Successful creation",
                                value = "{" +
                                    "\n \"message\":\"Rental created !\"" +
                                    "}",
                                summary = "Example response when creating a successful rental"
                            )
                        }
                    )
                },
                headers = {
                    @Header(
                        name = HttpHeaders.AUTHORIZATION,
                        description = "Bearer token",
                        schema = @Schema(type = "string"),
                        required = true
                    ),
                }
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Bad request",
                content = {
                    @Content(
                        schema = @Schema(implementation = ErrorEntity.class),
                        mediaType = "application/json",
                        examples = {
                            @ExampleObject(
                                name = "Invalid email address format",
                                value = "{\n  \"code\": 400,\n \"status\": " +
                                    "\"BAD_REQUEST\"\n ,\n \"message\": " +
                                    "\"Maximum upload size exceeded\"\n }",
                                summary = "Example response when the weight of an image is too large"
                            )
                        }
                    )
                }
            )
        }
    )
    @SecurityRequirement(name = "Bearer Auth")
    @PostMapping(consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseMessage> createRental(
        @RequestParam("name") String name,
        @RequestParam("description") String description,
        @RequestParam("price") double price,
        @RequestParam("surface") double surface,
        @RequestParam(value = "picture") MultipartFile picture){

        String fileType = picture.getContentType();

        if(
            !(fileType.equals("image/jpeg") || fileType.equals("image/jpg") || fileType.equals("image/png"))
        ){
            throw new FileTypeException();
        }

        String filename = storageService.uploadFile(picture);
        Rental rental = new Rental();
        rental.setName(name);
        rental.setDescription(description);
        rental.setPrice(price);
        rental.setPicture(filename);
        rental.setSurface(surface);

        this.rentalService.createRental(rental);
        ResponseMessage response = new ResponseMessage();
        response.setMessage("Rental created !");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
        summary = "Rental modificaition",
        description = "Update a rental"
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Created successfully",
                content = {
                    @Content(
                        schema = @Schema(implementation = ResponseMessage.class),
                        mediaType = "application/json",
                        examples = {
                            @ExampleObject(
                                name = "Successful update",
                                value = "{" +
                                    "\n \"message\":\"Rental updated\"" +
                                    "}",
                                summary = "Example response when updating a successful rental"
                            )
                        }
                    )
                }
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Bad request",
                content = {
                    @Content(
                        schema = @Schema(implementation = ErrorEntity.class),
                        mediaType = "application/json",
                        examples = {
                            @ExampleObject(
                                name = "Rental not found",
                                value = "{" +
                                    "\n  \"code\": 404,\n \"status\": " +
                                    "\"NOT_FOUND\"\n ,\n \"message\": " +
                                    "\"Resource not found\"\n }",
                                summary = "Example response when a rental is not found"
                            )
                        }
                    )
                }
            )
        })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = RentalDTO.class),
            examples = @ExampleObject(
                name = "Example Request",
                value = "{\n  " +
                    "\"name\": \"rentalName\"," +
                    "\n  \"description\": \"some text\"\n," +
                    "\n  \"price\": \"99\"" +
                    "\n  \"surface\": \"99\"" +
                    "\n}",
                summary = "Example of a request to update a rental"

            )
        )
    )
    @SecurityRequirement(name = "Bearer Auth")
    @PutMapping(path = "{id}")
    public ResponseEntity<ResponseMessage> editRental(@RequestParam("name") String name,
                                                      @RequestParam("description") String description,
                                                      @RequestParam("price") double price,
                                                      @RequestParam("surface") double surface,
                                                      @PathVariable int id){

        Rental rental = new Rental();
        rental.setName(name);
        rental.setDescription(description);
        rental.setPrice(price);
        rental.setSurface(surface);

        this.rentalService.editRental(rental, id);
        ResponseMessage response = new ResponseMessage();
        response.setMessage("Rental updated");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}

package fr.chatop.controller;

import fr.chatop.dto.ErrorEntity;
import fr.chatop.dto.UserDTO;
import fr.chatop.entity.User;
import fr.chatop.mapper.UserMapper;
import fr.chatop.service.UserService;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "User", description = "Concerns all operations for a user")
@AllArgsConstructor
@RestController
@RequestMapping(path = "user", produces = APPLICATION_JSON_VALUE)
public class UserController {
    private UserService userService;

    @Operation(summary = "User information by id", description =
        "Allows you to obtain user information based on an identifier")
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Query executed successfully",
                content = {
                    @Content(
                        schema = @Schema(implementation = UserDTO.class),
                        mediaType = "application/json",
                        examples = {
                            @ExampleObject(
                                name = "Success response",
                                value = "{" +
                                    "\n  \"id\": 1," +
                                    "\n \"name\": \"John Doe\"," +
                                    "\n \"email\": \"john@doe.fr\"," +
                                    "\n \"created_at\": \"2023-10-29T11:47:46.425+00:00\", " +
                                    "\n \"updated_at\": \"2023-10-29T11:47:46.425+00:00\"" +
                                    "\n  }",
                                summary = "Example of return when the user information is returned"
                            ),
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
                responseCode = "404",
                description = "User not found",
                content = {
                    @Content(
                        schema = @Schema(implementation = ErrorEntity.class),
                        mediaType = "application/json",
                        examples = {
                            @ExampleObject(
                                name = "User not found",
                                value = "{\n  \"code\": 404,\n \"status\": " +
                                    "\"NOT_FOUND\"\n ,\n \"message\": " +
                                    "\"User not found\"\n }",
                                summary = "Example response when a user is not found"
                            )
                        }
                    )
                }
            )
        }
    )
    @SecurityRequirement(name = "Bearer Auth")
    @GetMapping(path = "{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable int id){
        User user = this.userService.getUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(UserMapper.mapper(user));
    }
}

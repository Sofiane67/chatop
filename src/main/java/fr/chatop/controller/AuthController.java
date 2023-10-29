package fr.chatop.controller;

import fr.chatop.config.jwt.JwtService;
import fr.chatop.dto.*;
import fr.chatop.dto.response.TokenResponse;
import fr.chatop.entity.User;
import fr.chatop.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@Tag(name = "Authentication", description = "Concerns everything related to authentication, registration, connection, profile")
@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping("auth")
public class AuthController {
    private AuthService authService;
    private AuthenticationManager authenticationManager;
    private JwtService jwtService;

    @Operation(summary = "Account creation", description =
        "Allow a user to create an account")
    @ApiResponses(
        value = {
        @ApiResponse(
            responseCode = "201",
            description = "Created successfully",
            content = {
                @Content(
                    schema = @Schema(implementation = TokenResponse.class),
                    mediaType = "application/json",
                    examples = {
                        @ExampleObject(
                            name = "Successful Registration",
                            value = "{\n  \"token\": \"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzb2ZpYW5lQGdtYWlsLmZyIiwiZW1haWwiOiJzb2ZpYW5lQGdtYWlsLmZyIiwiZXhwIjoxNjk4MzU4OTk3LCJpYXQiOjE2OTgzNTcxOTd9.xziLJaPMnvfIed1X9Ngc4hop-It9XElrUeND3-KM7Yo\"\n}",
                            summary = "Example response when registration successful"
                        )
                    }
                )
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
                                "\"Invalid email address\"\n }",
                            summary = "Example of Bad Request response when the email address is incorrect"
                        )
                    }
                )
            }
        )
    })
    @PostMapping(path = "register", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<TokenResponse> signUp(@RequestBody RegisterDTO signUpInformations){
        User user = this.authService.signUp(signUpInformations);
        Map <String, String> jwt = this.jwtService.generate(user.getEmail());
        String bearer = "Bearer "+jwt.get("token");

        TokenResponse response = new TokenResponse(jwt.get("token"));
        return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.AUTHORIZATION, bearer).body(response);
    }

    @Operation(summary = "Sign in", description =
        "Allows a user to log in")
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Log in successful",
                content = {
                    @Content(
                        schema = @Schema(implementation = TokenResponse.class),
                        mediaType = "application/json",
                        examples = {
                            @ExampleObject(
                                name = "Successful Registration",
                                value = "{\n  \"token\": \"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzb2ZpYW5lQGdtYWlsLmZyIiwiZW1haWwiOiJzb2ZpYW5lQGdtYWlsLmZyIiwiZXhwIjoxNjk4MzU4OTk3LCJpYXQiOjE2OTgzNTcxOTd9.xziLJaPMnvfIed1X9Ngc4hop-It9XElrUeND3-KM7Yo\"\n}",
                                summary = "Example response when registration successful"
                            )
                        }
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
                responseCode = "401",
                description = "Unauthorized access",
                content = {
                    @Content(
                        schema = @Schema(implementation = ErrorEntity.class),
                        mediaType = "application/json",
                        examples = {
                            @ExampleObject(
                                name = "Authentication failed",
                                value = "{\n  \"code\": 401,\n \"status\": " +
                                    "\"UNAUTHORIZED\"\n ,\n \"message\": " +
                                    "\"Authentication failed\"\n }",
                                summary = "Exemple de réponse non autorisée en cas d'échec de la connexion"
                            )
                        }
                    )
                }
            )
        })
    @PostMapping(path = "login", consumes = APPLICATION_JSON_VALUE)
    public  ResponseEntity<?> signIn(@RequestBody SignInDTO signInInformations){
      try {
        final Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(signInInformations.email(), signInInformations.password())
        );
          String jwt =  this.jwtService.generate(signInInformations.email()).get("token");
          TokenResponse response = new TokenResponse(jwt);
          return ResponseEntity.status(HttpStatus.OK).body(response);

      } catch (AuthenticationException e) {
        ErrorEntity error = new ErrorEntity(HttpStatus.UNAUTHORIZED.value(),
            HttpStatus.UNAUTHORIZED, "Authentication failed");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
      }
    }

  @Operation(summary = "Profile information", description =
      "Get profile information")
  @ApiResponses(
      value = {
          @ApiResponse(
              responseCode = "200",
              description = "query executed successfully",
              content = {
                  @Content(
                      schema = @Schema(implementation = UserDTO.class),
                      mediaType = "application/json",
                      examples = {
                          @ExampleObject(
                              name = "Successful Registration",
                              value = "{" +
                                  "\n  \"id\": 1," +
                                  "\n \"name\": \"John Doe\"," +
                                  "\n \"email\": \"john@doe.fr\"," +
                                  "\n \"created_at\": \"2023-10-29T11:47:46.425+00:00\", " +
                                  "\n \"updated_at\": \"2023-10-29T11:47:46.425+00:00\"" +
                                  "\n  }",
                              summary = "Example of return when the profile is returned"
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
      })
    @GetMapping(path = "me")
    public UserDTO getUserLogged(){
        return this.authService.getUserLogged();
    }
}

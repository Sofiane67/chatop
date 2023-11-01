package fr.chatop.controller;

import fr.chatop.dto.MessageDTO;
import fr.chatop.dto.response.ResponseMessage;
import fr.chatop.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Message", description = "Concerns all operations for messages")
@AllArgsConstructor
@RestController
@RequestMapping("messages")
public class MessageController {
  private MessageService messageService;

  @Operation(
      summary = "Message sending",
      description = "Allows a user to send a message"
  )
  @ApiResponses(
      value = {
          @ApiResponse(
              responseCode = "201",
              description = "Message sent successfully",
              content = {
                  @Content(
                      schema = @Schema(implementation = ResponseMessage.class),
                      mediaType = "application/json",
                      examples = {
                          @ExampleObject(
                              name = "Successful creation",
                              value = "{" +
                                  "\n \"message\":\"Message sended\"" +
                                  "}",
                              summary = "Example of response when the message is successfully sent"
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
          )
      })
  @io.swagger.v3.oas.annotations.parameters.RequestBody(
      content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = MessageDTO.class),
          examples = @ExampleObject(
              name = "Example Request",
              value = "{\n  " +
                  "\"rental_id\": 1," +
                  "\n \"user_id\": 2 \n," +
                  "\n \"message\": \"Some text\"" +
                  "\n}",
              summary = "Example query for a message"

          )
      )
  )
  @SecurityRequirement(name = "Bearer Auth")
  @PostMapping
  public ResponseEntity<ResponseMessage> createMessage(@RequestBody MessageDTO message){
    this.messageService.createMessage(message);
    ResponseMessage response = new ResponseMessage();
    response.setMessage("Message sended");
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }
}

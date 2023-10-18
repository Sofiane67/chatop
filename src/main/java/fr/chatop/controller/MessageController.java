package fr.chatop.controller;

import fr.chatop.dto.MessageDTO;
import fr.chatop.dto.response.ResponseMessage;
import fr.chatop.service.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("messages")
public class MessageController {
  private MessageService messageService;
  @PostMapping
  public ResponseEntity<ResponseMessage> createMessage(@RequestBody MessageDTO message){
    this.messageService.createMessage(message);
    ResponseMessage response = new ResponseMessage();
    response.setMessage("Message sended");
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }
}

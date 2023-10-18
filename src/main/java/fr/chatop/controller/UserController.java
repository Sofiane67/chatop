package fr.chatop.controller;

import fr.chatop.dto.UserDTO;
import fr.chatop.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@AllArgsConstructor
@RestController
@RequestMapping(path = "user", produces = APPLICATION_JSON_VALUE)
public class UserController {
    private UserService userService;

    @GetMapping(path = "{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable int id){
        UserDTO userDTO = this.userService.getUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(userDTO);
    }
}

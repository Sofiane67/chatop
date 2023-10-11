package fr.chatop.controller;

import fr.chatop.entity.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("auth")
public class AuthController {

    @PostMapping(path = "register", consumes = APPLICATION_JSON_VALUE)
    public String register(){
        return "INSCRIPTION OK";
    }
}

package fr.chatop.controller;

import fr.chatop.dto.RegisterDTO;
import fr.chatop.dto.SuccessResponse;
import fr.chatop.entity.User;
import fr.chatop.service.AuthService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("auth")
public class AuthController {
    AuthService authService;

    @PostMapping(path = "register", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<SuccessResponse> register(@RequestBody RegisterDTO registerInformations){
        this.authService.register(registerInformations);
        int statusCode = HttpStatus.OK.value();
        HttpStatus status = HttpStatus.OK;
        String message = "Inscription réussie";
        SuccessResponse response = new SuccessResponse(statusCode, status, message);
        return ResponseEntity.status(status).body(response);
    }
}

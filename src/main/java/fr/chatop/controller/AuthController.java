package fr.chatop.controller;

import fr.chatop.config.jwt.JwtService;
import fr.chatop.dto.AuthDTO;
import fr.chatop.dto.RegisterDTO;
import fr.chatop.dto.SuccessResponse;
import fr.chatop.dto.UserDTO;
import fr.chatop.entity.User;
import fr.chatop.service.AuthService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("auth")
public class AuthController {
    private AuthService authService;
    private AuthenticationManager authenticationManager;
    private JwtService jwtService;

    @PostMapping(path = "register", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<SuccessResponse> signUp(@RequestBody RegisterDTO signUpInformations){
        User user = this.authService.signUp(signUpInformations);
        Map <String, String> jwt = this.jwtService.generate(user.getEmail());
        String bearer = "Bearer "+jwt.get("token");

        int statusCode = HttpStatus.OK.value();
        HttpStatus status = HttpStatus.OK;
        String message = "Inscription réussie";
        SuccessResponse response = new SuccessResponse(statusCode, status, message);
        return ResponseEntity.status(status).header(HttpHeaders.AUTHORIZATION, bearer).body(response);
    }

    @PostMapping(path = "login", consumes = APPLICATION_JSON_VALUE)
    public Map<String,String> signIn(@RequestBody AuthDTO signInInformations){
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInInformations.email(), signInInformations.password())
        );
        if(authentication.isAuthenticated()) {
            return this.jwtService.generate(signInInformations.email());
        }
        return null;
    }

    @GetMapping(path = "me")
    public UserDTO getUserLogged(){
        return this.authService.getUserLogged();
    }
}

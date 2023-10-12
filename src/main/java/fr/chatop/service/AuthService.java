package fr.chatop.service;

import fr.chatop.dto.RegisterDTO;
import fr.chatop.entity.User;
import fr.chatop.exception.UserExistException;
import fr.chatop.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@AllArgsConstructor
@Service
public class AuthService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

    public void register(RegisterDTO registerInformation){
        boolean userExist = this.userRepository.findByEmail(registerInformation.email()).isPresent();
        if(userExist){
            throw new UserExistException();
        }

        User user = new User();
        user.setName(registerInformation.name());
        user.setEmail(registerInformation.email());
        String hashPassword = this.passwordEncoder.encode(registerInformation.password());
        user.setPassword(hashPassword);
        user.setCreatedAt(Instant.now());
        user.setUpdatedAt(Instant.now());
        this.userRepository.save(user);
    }

}

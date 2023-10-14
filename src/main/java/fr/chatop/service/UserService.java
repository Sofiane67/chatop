package fr.chatop.service;

import fr.chatop.exception.UserExistException;
import fr.chatop.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class UserService {
    private UserRepository userRepository;

    public boolean getUserByEmail(String email){
        boolean userExist = this.userRepository.findByEmail(email).isPresent();
        if(userExist){
            throw new UserExistException();
        }
        return userExist;
    }
}

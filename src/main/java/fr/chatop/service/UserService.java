package fr.chatop.service;

import fr.chatop.entity.User;
import fr.chatop.exception.UserExistException;
import fr.chatop.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class UserService {
    private UserRepository userRepository;

    public boolean verifyUserExist(String email) throws UserExistException{
        boolean userExist = this.userRepository.findByEmail(email).isPresent();
        if(userExist){
            throw new UserExistException();
        }
        return userExist;
    }

    public User getUserByEmail(String email) throws UsernameNotFoundException{
        return this.userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Utilisateur introuvable"));
    }
}

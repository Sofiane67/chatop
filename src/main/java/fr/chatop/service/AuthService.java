package fr.chatop.service;

import fr.chatop.dto.RegisterDTO;
import fr.chatop.dto.UserDTO;
import fr.chatop.entity.User;
import fr.chatop.mapper.UserMapper;
import fr.chatop.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Slf4j
@Service
public class AuthService implements UserDetailsService {
    private UserRepository userRepository;
    private UserService userService;
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = this.userService.getUserByEmail(email);
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getGrantedAuthorities());
    }

    private List<GrantedAuthority> getGrantedAuthorities(){
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return authorities;
    }
    public User signUp(RegisterDTO signUpInformations){
        boolean userExist = this.userService.verifyUserExist(signUpInformations.email());
        if(!userExist){
            User user = new User();
            user.setName(signUpInformations.name());
            user.setEmail(signUpInformations.email());
            String hashPassword = this.passwordEncoder.encode(signUpInformations.password());
            user.setPassword(hashPassword);
            return this.userRepository.save(user);
        }

        return null;
    }

    public UserDTO getUserLogged() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(!authentication.isAuthenticated()){
            return null;
        }

        String userEmail = authentication.getName();
        User user = this.userService.getUserByEmail(userEmail);

        return UserMapper.mapper(user);
    }
}

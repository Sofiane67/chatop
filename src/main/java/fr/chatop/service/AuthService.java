package fr.chatop.service;

import fr.chatop.dto.AuthDTO;
import fr.chatop.dto.RegisterDTO;
import fr.chatop.entity.User;
import fr.chatop.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.ArrayList;
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
        User user = this.userRepository.findByEmail(email).orElseThrow();
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getGrantedAuthorities());
    }

    private List<GrantedAuthority> getGrantedAuthorities(){
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return authorities;
    }
    public void signUp(RegisterDTO signUpInformations){
        boolean userExist = this.userService.getUserByEmail(signUpInformations.email());

        if(!userExist){
            User user = new User();
            user.setName(signUpInformations.name());
            user.setEmail(signUpInformations.email());
            String hashPassword = this.passwordEncoder.encode(signUpInformations.password());
            user.setPassword(hashPassword);
            user.setCreatedAt(Instant.now());
            user.setUpdatedAt(Instant.now());
            this.userRepository.save(user);
        }
    }
}

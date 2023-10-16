package fr.chatop.service;


import fr.chatop.dto.RentalDTO;
import fr.chatop.dto.UserDTO;
import fr.chatop.entity.Rental;
import fr.chatop.entity.User;
import fr.chatop.exception.RentalNotFoundException;
import fr.chatop.repository.RentalRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@AllArgsConstructor
@Service
public class RentalService {
    private RentalRepository rentalRepository;
    private AuthService authService;
    private UserService userService;

    public List<Rental> getAllRentals(){
        return rentalRepository.findAll();
    }

    public RentalDTO getRentalById(int id){
        Rental rental = this.rentalRepository.findById(id).orElseThrow(() -> new RentalNotFoundException());
        if(rental.getOwner() != null){
            int ownerId = rental.getOwner().getId();
            return new RentalDTO(rental.getId(), rental.getName(), rental.getSurface(), rental.getPrice(), rental.getPicture(), rental.getDescription(), ownerId, rental.getCreatedAt(), rental.getUpdatedAt());
        }
        return null;
    }

    public void createRental(Rental rental){
        UserDTO userLogged = this.authService.getUserLogged();
        User user = this.userService.getUserByEmail(userLogged.email());

        if(user != null){
            rental.setOwner(user);
            rental.setCreatedAt(Instant.now());
            rental.setUpdatedAt(Instant.now());
            this.rentalRepository.save(rental);
        }

    }
}

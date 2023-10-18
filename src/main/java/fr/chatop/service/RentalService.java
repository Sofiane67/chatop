package fr.chatop.service;

import fr.chatop.dto.RentalDTO;
import fr.chatop.dto.UserDTO;
import fr.chatop.entity.Rental;
import fr.chatop.entity.User;
import fr.chatop.exception.RentalNotFoundException;
import fr.chatop.mapper.RentalMapper;
import fr.chatop.repository.RentalRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.stream.Stream;

@AllArgsConstructor
@Service
public class RentalService {
    private RentalRepository rentalRepository;
    private AuthService authService;
    private UserService userService;

    public Stream<RentalDTO> getAllRentals(){
        return rentalRepository.findAll().stream().map(rental -> {
                return RentalMapper.mapper(rental);
            }
        );
    }

    public RentalDTO getRentalById(int id){
        Rental rental = this.rentalRepository.findById(id).orElseThrow(() -> new RentalNotFoundException());
        if(rental.getOwner() != null){
            return RentalMapper.mapper(rental);
        }
        return null;
    }

    public void createRental(Rental rental){
        UserDTO userLogged = this.authService.getUserLogged();
        User user = this.userService.getUserByEmail(userLogged.email());

        if(user != null){
            rental.setOwner(user);
            this.rentalRepository.save(rental);
        }

    }
}

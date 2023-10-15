package fr.chatop.service;

import fr.chatop.entity.Rental;
import fr.chatop.exception.RentalNotFoundException;
import fr.chatop.repository.RentalRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class RentalService {
    private RentalRepository rentalRepository;

    public List<Rental> getAllRentals(){
        return this.rentalRepository.findAll();
    }

    public Rental getRentalById(int id){
        return this.rentalRepository.findById(id).orElseThrow(() -> new RentalNotFoundException());
    }
}

package fr.chatop.controller;

import fr.chatop.entity.Rental;
import fr.chatop.service.RentalService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("rentals")
public class RentalController {
    private RentalService rentalService;

    @GetMapping
    public List<Rental> getAllRentals(){
        return this.rentalService.getAllRentals();
    }

    @GetMapping(path = "{id}")
    public Rental getRental(@PathVariable int id){
        return this.rentalService.getRentalById(id);
    }
}

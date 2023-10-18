package fr.chatop.controller;

import fr.chatop.dto.RentalDTO;
import fr.chatop.dto.response.RentalsResponse;
import fr.chatop.dto.response.ResponseMessage;
import fr.chatop.entity.Rental;
import fr.chatop.service.RentalService;
import fr.chatop.service.StorageService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.stream.Stream;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@AllArgsConstructor
@RestController
@RequestMapping(path = "rentals", produces = APPLICATION_JSON_VALUE)
public class RentalController {
    private RentalService rentalService;
    private StorageService storageService;
    @GetMapping
    public ResponseEntity<RentalsResponse>  getAllRental(){

        Stream<RentalDTO> rentals = this.rentalService.getAllRentals();
        RentalsResponse response = new RentalsResponse();
        response.setRentals(rentals);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(path = "{id}")
    public RentalDTO getRental(@PathVariable int id){
        return this.rentalService.getRentalById(id);
    }

    @PostMapping
    public ResponseEntity<ResponseMessage> createRental(@RequestParam("picture") MultipartFile picture,
                                                        @RequestParam("name") String name,
                                                        @RequestParam("description") String description,
                                                        @RequestParam("price") double price,
                                                        @RequestParam("surface") double surface){
        try {
            String filename = storageService.save(picture);

            Rental rental = new Rental();
            rental.setName(name);
            rental.setDescription(description);
            rental.setPrice(price);
            rental.setPicture(filename);
            rental.setSurface(surface);

            this.rentalService.createRental(rental);
            ResponseMessage response = new ResponseMessage();
            response.setMessage("Rental created !");

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            ResponseMessage response = new ResponseMessage();
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}

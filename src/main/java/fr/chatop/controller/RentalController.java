package fr.chatop.controller;

import fr.chatop.dto.RentalDTO;
import fr.chatop.dto.ResponseMessage;
import fr.chatop.entity.Rental;
import fr.chatop.service.RentalService;
import fr.chatop.service.StorageService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("rentals")
public class RentalController {
    private RentalService rentalService;
    private StorageService storageService;

    @GetMapping
    public List<Rental> getAllRentals(){
        return this.rentalService.getAllRentals();
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
            response.setStatus(HttpStatus.OK);
            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage("Uploaded the file successfully: " + picture.getOriginalFilename());

            return ResponseEntity.status(response.getStatus()).body(response);
        } catch (Exception e) {
            ResponseMessage response = new ResponseMessage();
            response.setStatus(HttpStatus.EXPECTATION_FAILED);
            response.setStatusCode(HttpStatus.EXPECTATION_FAILED.value());
            response.setMessage("Could not upload the file: " + picture.getOriginalFilename() + ". Error: " + e.getMessage());
            return ResponseEntity.status(response.getStatusCode()).body(response);
        }
    }
}

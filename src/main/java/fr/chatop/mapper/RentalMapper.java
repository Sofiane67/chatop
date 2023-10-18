package fr.chatop.mapper;

import fr.chatop.dto.RentalDTO;
import fr.chatop.entity.Rental;

public class RentalMapper {
  public static RentalDTO mapper(Rental rental){
    return new RentalDTO(
        rental.getId(),
        rental.getName(),
        rental.getSurface(),
        rental.getPrice(),
        rental.getPicture(),
        rental.getDescription(),
        rental.getOwner().getId(),
        rental.getCreatedAt(),
        rental.getUpdatedAt()
    );
  }
}

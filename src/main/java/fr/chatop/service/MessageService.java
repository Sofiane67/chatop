package fr.chatop.service;

import fr.chatop.dto.MessageDTO;
import fr.chatop.entity.Message;
import fr.chatop.entity.Rental;
import fr.chatop.entity.User;
import fr.chatop.repository.MessageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class MessageService {
  private MessageRepository messageRepository;
  private UserService userService;
  private RentalService rentalService;

  public void createMessage(MessageDTO message){
    User user = this.userService.getUserById(message.user_id());
    Rental rental = this.rentalService.getRentalById(message.rental_id());

    if(user != null && rental != null){
      Message newMessage = new Message();
      newMessage.setMessage(message.message());
      newMessage.setUser(user);
      newMessage.setRental(rental);
      this.messageRepository.save(newMessage);
    }
  }

}

package fr.chatop.mapper;

import fr.chatop.dto.UserDTO;
import fr.chatop.entity.User;

public class UserMapper {
  public static UserDTO mapper(User user){
    return new UserDTO(
        user.getId(),
        user.getName(),
        user.getEmail(),
        user.getCreatedAt(),
        user.getUpdatedAt()
    );
  }
}

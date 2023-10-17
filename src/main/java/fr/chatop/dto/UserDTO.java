package fr.chatop.dto;

import java.util.Date;

public record UserDTO(int id, String name, String email, Date created_at, Date updated_at) {
}

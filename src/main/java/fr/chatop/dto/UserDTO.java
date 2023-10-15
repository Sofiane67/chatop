package fr.chatop.dto;

import java.time.Instant;

public record UserDTO(int id, String name, String email, Instant createdAt, Instant updatedAt) {
}

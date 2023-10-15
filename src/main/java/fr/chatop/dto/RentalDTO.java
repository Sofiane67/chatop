package fr.chatop.dto;

import java.time.Instant;

public record RentalDTO(int id, String name, String email, Instant createdAt, Instant updatedAt) {
}

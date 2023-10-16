package fr.chatop.dto;

import java.time.Instant;

public record RentalDTO(int id, String name, double surface, double price, String picture, String description, int owner_id, Instant created_at, Instant updated_at) {
}
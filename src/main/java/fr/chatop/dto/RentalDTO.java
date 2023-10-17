package fr.chatop.dto;

import java.util.Date;

public record RentalDTO(int id, String name, double surface, double price, String picture, String description, int owner_id, Date created_at, Date updated_at) {
}
package fr.chatop.dto;

import org.springframework.http.HttpStatus;

public record ErrorEntity(int code, HttpStatus status, String message) {
}

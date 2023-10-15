package fr.chatop.exception;

public class RentalNotFoundException extends RuntimeException{
    public RentalNotFoundException(){
        super("Ressource introuvable");
    }
}

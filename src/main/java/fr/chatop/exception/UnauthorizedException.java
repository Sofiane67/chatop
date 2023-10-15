package fr.chatop.exception;

public class UnauthorizedException extends RuntimeException{
    public UnauthorizedException(){
        super("Authentification requise");
    }
}

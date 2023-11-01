package fr.chatop.exception;

public class InvalidEmailException extends RuntimeException{
    public InvalidEmailException(){
        super("Invalid email address");
    }
}

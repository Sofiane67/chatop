package fr.chatop.exception;

public class UserExistException extends RuntimeException{
    public UserExistException(){
        super("Email address already exists");
    }
}

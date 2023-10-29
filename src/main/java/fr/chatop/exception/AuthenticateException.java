package fr.chatop.exception;

public class AuthenticateException extends RuntimeException{
    public AuthenticateException(){
        super("Authenication failed");
    }
}

package fr.chatop.exception;

public class AuthenticateException extends RuntimeException{
    public AuthenticateException(){
        super("L'authentification a échoué");
    }
}

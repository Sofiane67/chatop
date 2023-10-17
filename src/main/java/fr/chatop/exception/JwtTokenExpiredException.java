package fr.chatop.exception;

public class JwtTokenExpiredException extends RuntimeException{
    public JwtTokenExpiredException(){
        super("La session a éxpriée");
    }
}
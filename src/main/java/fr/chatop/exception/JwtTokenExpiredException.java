package fr.chatop.exception;

public class JwtTokenExpiredException extends RuntimeException{
    public JwtTokenExpiredException(){
        super("Invalid email address");
    }
}

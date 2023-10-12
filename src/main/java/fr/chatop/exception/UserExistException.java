package fr.chatop.exception;

public class UserExistException extends RuntimeException{
    public UserExistException(){
        super("L'utilisateur avec cette adresse email existe déjà");
    }
}

package fr.chatop.exception;

public class FileTypeException extends RuntimeException{
    public FileTypeException(){
        super("Wrong file type");
    }
}

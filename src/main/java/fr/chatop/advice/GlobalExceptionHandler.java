package fr.chatop.advice;

import fr.chatop.dto.ErrorEntity;
import fr.chatop.exception.AuthenticateException;
import fr.chatop.exception.RentalNotFoundException;
import fr.chatop.exception.UnauthorizedException;
import fr.chatop.exception.UserExistException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserExistException.class)
    public @ResponseBody ErrorEntity handleUserExistException(UserExistException exception){
        return new ErrorEntity(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST ,exception.getMessage());
    }

    @ExceptionHandler(AuthenticateException.class)
    public @ResponseBody ErrorEntity handleAuthenticationException(AuthenticateException exception){
        return new ErrorEntity(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED, exception.getMessage());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public @ResponseBody ErrorEntity handleUnauthorizedException(UnauthorizedException exception){
        return new ErrorEntity(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED, exception.getMessage());
    }

    @ExceptionHandler(RentalNotFoundException.class)
    public @ResponseBody ErrorEntity handleRentalNotFoundException(RentalNotFoundException exception){
        return new ErrorEntity(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND, exception.getMessage());
    }

}

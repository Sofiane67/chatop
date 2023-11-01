package fr.chatop.advice;

import fr.chatop.dto.ErrorEntity;
import fr.chatop.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

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
    public ResponseEntity<ErrorEntity> handleRentalNotFoundException(RentalNotFoundException exception){
        ErrorEntity errorEntity = new ErrorEntity(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND,
            exception.getMessage());
        return  ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(errorEntity);
    }

    @ExceptionHandler(JwtTokenExpiredException.class)
    public @ResponseBody ErrorEntity handleJwtTokenExpiredException(JwtTokenExpiredException exception){
        return new ErrorEntity(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED, exception.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorEntity> handleUserNotFoundException(UserNotFoundException exception){
        ErrorEntity errorEntity = new ErrorEntity(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND,
            exception.getMessage());
        return  ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(errorEntity);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ErrorEntity> handleMaxSizeException(MaxUploadSizeExceededException exception) {
        ErrorEntity errorEntity = new ErrorEntity(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
            exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorEntity);
    }

    @ExceptionHandler(FileTypeException.class)
    public ResponseEntity<ErrorEntity> handleFileTypeException(FileTypeException exception) {
        ErrorEntity errorEntity = new ErrorEntity(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
            exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorEntity);
    }
}

package br.com.carlosjunior.blog.exception;

import br.com.carlosjunior.blog.payload.ErrorDetails;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // handle specific exceptions
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handlerResourceNotFoundException(ResourceNotFoundException exception,
                                                                         WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(   new Date(),
                                                        exception.getMessage(),
                                                        webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    // handle specific exceptions
    @ExceptionHandler(BlogAPIException.class)
    public ResponseEntity<ErrorDetails> handlerBlogAPIException(BlogAPIException exception,
                                                                         WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(   new Date(),
                                                        exception.getMessage(),
                                                        webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    // handle specific exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handlerGlobalException(Exception exception,
                                                                         WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(   new Date(),
                exception.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(  MethodArgumentNotValidException ex,
                                                                    HttpHeaders headers,
                                                                    HttpStatus status,
                                                                    WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach( (err) -> {
            String fieldName = ((FieldError)err).getField();
            String message = err.getDefaultMessage();
            errors.put(fieldName, message);
        } );
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

/*
    // handle specific exceptions
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handlerMethodArgumentNotValidException(MethodArgumentNotValidException exception,
                                                                         WebRequest webRequest){
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach( (err) -> {
            String fieldName = ((FieldError)err).getField();
            String message = err.getDefaultMessage();
            errors.put(fieldName, message);
        } );
        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }*/


}

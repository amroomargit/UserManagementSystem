package com.sword.usermanagementsystem.exceptions;

import com.sword.usermanagementsystem.dtos.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/* Remember that it goes least to most specific error handler. This is literally a class containing multiple error
handling methods */

/*Controller Advice is a Spring annotation that makes a class a global handler for exceptions and other concerns across
all controllers. It lets you write one centralized place to handle errors for all your controllers, instead of repeating
try–catch blocks everywhere.*/
@ControllerAdvice
public class GlobalExceptionHandler {

    /* The annotation below tells Spring: “If a MethodArgumentNotValidException is thrown (happens when @Valid fails),
    run this method.” */

    // This method handles @Valid validation errors
    //Exception object ex contains details about which fields failed and why
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<ErrorDTO> handleModelException(MethodArgumentNotValidException ex){

        /* Loops through all the failed field validations.
        error.getField() → the name of the field that failed (like "username" or "password").
        error.getDefaultMessage() → the message from the annotation (like "Username cannot be blank" or "Field cannot contain whitespace").
        Adds each pair to a map → { "username": "Username cannot be blank" }.*/
        Map<String,String> errors = new HashMap<>();
        for(FieldError error : ex.getBindingResult().getFieldErrors()){
            errors.put(error.getField(), error.getDefaultMessage());
        }

        /*Creates an ErrorDTO object using your custom DTO class. "Validation failed" is the general message.
        errors is the detailed field-to-message map.*/
        ErrorDTO errorDTO = new ErrorDTO("Validation failed", errors);

        //Returns an HTTP 400 Bad Request response. The errorDTO becomes the JSON body returned to the client.
        return ResponseEntity.badRequest().body(errorDTO);
    }

    //This method handles Business Exceptions
    @ExceptionHandler(BusinessException.class)
    public final ResponseEntity<ErrorDTO> handleBusinessException(BusinessException ex){
        ErrorDTO errorDTO = new ErrorDTO(ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }

    //This method handles generic exceptions
    public final ResponseEntity<ErrorDTO> handleException(Exception ex){
        ErrorDTO errorDTO = new ErrorDTO("Internal server error", null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDTO);
    }

}

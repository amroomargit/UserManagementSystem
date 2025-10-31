package com.sword.usermanagementsystem.exceptions;

/* This class defines a custom runtime exception called BusinessException. It extends RuntimeException, meaning it
represents errors that occur due to business logic (not validation or technical errors).

When you throw it, for example:
throw new BusinessException("User already exists");

it carries that message to your GlobalExceptionHandler, which can catch it and return a clean JSON response.*/
public class BusinessException extends RuntimeException{
    public BusinessException(String message){
        super(message);
    }
}

package com.gupshub.usermanagement.model.exception;

public class AuthorizationException extends RuntimeException {
    public AuthorizationException(String message){
        super(message);
    }
}

package com.gupshub.usermanagement.model.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DataConflictException extends RuntimeException{
    public DataConflictException(String message){
        super(message);
    }
}

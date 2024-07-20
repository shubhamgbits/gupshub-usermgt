package com.gupshub.usermanagement.model.response.common;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ErrorResponse extends Response {
    String error;

    public ErrorResponse(String status, String message, String error){
        this.status = status;
        this.message = message;
        this.error = error;
    }

    public static ErrorResponse get(String status, String error){
        return new ErrorResponse(status, "Error Occurred", error);
    }
}

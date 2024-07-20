package com.gupshub.usermanagement.model.exception;

import com.gupshub.usermanagement.model.response.common.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Created by skanval on 12/28/2022.
 */
public class DatabaseOperationException extends GenericException {

    public DatabaseOperationException(final String status, final String message) {
        super(status, message);
        setResponse(
                ResponseEntity
                        .status(HttpStatus.EXPECTATION_FAILED)
                        .body(ErrorResponse.get(DATABASE_ERROR_CODE, message))
        );
    }

    public DatabaseOperationException(final String message) {
        super(message);
        setResponse(
                ResponseEntity
                        .status(HttpStatus.EXPECTATION_FAILED)
                        .body(ErrorResponse.get(DATABASE_ERROR_CODE, message))
        );
    }

    public DatabaseOperationException(final HttpStatus httpStatus, final String status, final String message) {
        super(httpStatus, status, message);
    }
}

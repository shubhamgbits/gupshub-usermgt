package com.gupshub.usermanagement.model.exception;

import com.gupshub.usermanagement.model.response.common.ErrorResponse;
import com.gupshub.usermanagement.util.ServiceUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Created by skanval on 11/28/2022.
 */
public class BadRequestException extends GenericException {

    public BadRequestException(final String status, final String message) {
        super(status, message);
        setResponse(
                ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(ErrorResponse.get(ServiceUtil.BAD_REQUEST_ERROR_CODE, message))
        );
    }

    public BadRequestException(final String message) {
        super();
        setResponse(
                ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(ErrorResponse.get(ServiceUtil.BAD_REQUEST_ERROR_CODE, message))
        );
    }

}

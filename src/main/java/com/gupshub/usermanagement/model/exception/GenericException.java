package com.gupshub.usermanagement.model.exception;

import com.gupshub.usermanagement.model.response.common.ErrorResponse;
import com.gupshub.usermanagement.model.response.common.Response;
import com.gupshub.usermanagement.util.ServiceUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Created by skanval on 11/28/2022.
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class GenericException extends Exception implements ServiceUtil {
    private ResponseEntity<Response> response;

    public GenericException() {
        this.response = ERROR_RESPONSE;
    }

    public GenericException(final String message) {
        super(message);
        response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponse.get(SERVER_ERROR_CODE, message));
    }

    public GenericException(final String status, final String message) {
        super(message);
        response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponse.get(status, message));
    }

    public GenericException(final HttpStatus httpStatus, final String  status, final String message) {
        super(message);
        response = ResponseEntity.status(httpStatus).body(ErrorResponse.get(status, message));
    }

}

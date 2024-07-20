package com.gupshub.usermanagement.util;

import com.gupshub.usermanagement.model.response.common.ErrorResponse;
import com.gupshub.usermanagement.model.response.common.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface ServiceUtil {
    String SERVER_ERROR_CODE = "500";
    String INTERNAL_SERVER_ERROR_MESSAGE = "Internal Server Error";
    String DATABASE_ERROR_CODE = "502";
    String BAD_REQUEST_ERROR_CODE = "400";

    ResponseEntity<Response> ERROR_RESPONSE = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ErrorResponse.get(SERVER_ERROR_CODE, INTERNAL_SERVER_ERROR_MESSAGE));

}

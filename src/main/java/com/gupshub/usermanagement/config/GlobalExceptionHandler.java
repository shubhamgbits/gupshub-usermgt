package com.gupshub.usermanagement.config;

import com.gupshub.usermanagement.model.exception.AuthorizationException;
import com.gupshub.usermanagement.model.exception.BadRequestException;
import com.gupshub.usermanagement.model.exception.DataConflictException;
import com.gupshub.usermanagement.model.exception.DataNotFoundException;
import com.gupshub.usermanagement.model.response.common.ErrorResponse;
import com.gupshub.usermanagement.model.response.common.Response;
import com.gupshub.usermanagement.util.ServiceUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response> handleGlobalException(Exception e) {
        log.debug("Unexpected Error Occurred. Caught Exception: {}", e.getMessage());
        e.printStackTrace();
//        log.error("\nStack Trace : {}", CommonUtil.marshalJson(e.getStackTrace()));
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.get(
                        ServiceUtil.SERVER_ERROR_CODE,
                        ServiceUtil.INTERNAL_SERVER_ERROR_MESSAGE
                ));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Response> handleGlobalException(BadRequestException e) {
        log.debug("Caught BadRequestException : {}", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.get(
                        ServiceUtil.BAD_REQUEST_ERROR_CODE,
                        "Bad Request"
                ));
    }

    @ExceptionHandler(DataConflictException.class)
    public ResponseEntity<Response> handleDataConflictException(DataConflictException e) {
        log.debug("Caught DataConflictException : {}", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ErrorResponse.get(
                        ServiceUtil.DATA_CONFLICT,
                        e.getMessage()
                ));
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<Response> handleDataConflictException(DataNotFoundException e) {
        log.debug("Caught DataNotFoundException : {}", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.get(
                        ServiceUtil.DATA_NOT_FOUND,
                        e.getMessage()
                ));
    }

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<Response> handleAuthorizationException(AuthorizationException e) {
        log.debug("Caught AuthorizationException : {}", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ErrorResponse.get(
                        ServiceUtil.UNAUTHORIZED,
                        e.getMessage()
                ));
    }
}

package com.workspaceit.dccpos.restendpoint.advice;

import com.workspaceit.dccpos.util.ServiceResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class EndPointExceptionHandler {

    @ExceptionHandler(org.springframework.http.converter.HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleBadRequestException(org.springframework.http.converter.HttpMessageNotReadableException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ServiceResponse.getMsgInMap(ex.getMessage()));
    }
    @ExceptionHandler(InternalError.class)
    public ResponseEntity<?> handleInternalServerError(InternalError ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ServiceResponse.getMsgInMap(ex.getMessage()));
    }
}

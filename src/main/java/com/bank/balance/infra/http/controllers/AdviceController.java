package com.bank.balance.infra.http.controllers;

import com.bank.balance.app.exceptions.TransactionIdFoundException;
import com.bank.balance.infra.http.jsons.responses.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AdviceController {

    @ExceptionHandler(TransactionIdFoundException.class)
    public ResponseEntity<ErrorResponse> handlerAlreadyUserException(final TransactionIdFoundException exception) {
        final var response = ErrorResponse.from(exception.getMessage());
        return ResponseEntity.unprocessableEntity().body(response);
    }
}

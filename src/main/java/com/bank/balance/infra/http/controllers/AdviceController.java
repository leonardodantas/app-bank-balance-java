package com.bank.balance.infra.http.controllers;

import com.bank.balance.app.exceptions.TransactionIdFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AdviceController {


    @ExceptionHandler(TransactionIdFoundException.class)
    public ResponseEntity<String> handlerAlreadyUserException(final TransactionIdFoundException exception) {
        exception.getTransactionsId();
//        final var response = ErrorResponse.from(exception.getMessage());
//        return ResponseEntity.badRequest().body(response);
    }
}

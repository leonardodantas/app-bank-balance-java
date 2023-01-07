package com.bank.balance.infra.http.controllers;

import com.bank.balance.app.exceptions.ExistingTransactionsException;
import com.bank.balance.app.exceptions.TransactionIdFoundException;
import com.bank.balance.app.exceptions.TransactionTypeInvalidException;
import com.bank.balance.infra.exceptions.ConvertFileException;
import com.bank.balance.infra.exceptions.SaveEntityException;
import com.bank.balance.infra.http.jsons.responses.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class AdviceController {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handlerConstraintViolationException(final ConstraintViolationException exception) {
        final var response = ErrorResponse.from(exception.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(TransactionIdFoundException.class)
    public ResponseEntity<ErrorResponse> handlerTransactionIdFoundException(final TransactionIdFoundException exception) {
        final var response = ErrorResponse.from(exception.getMessage());
        return ResponseEntity.unprocessableEntity().body(response);
    }

    @ExceptionHandler(TransactionTypeInvalidException.class)
    public ResponseEntity<ErrorResponse> handlerTransactionTypeInvalidException(final TransactionTypeInvalidException exception) {
        final var response = ErrorResponse.from(exception.getMessage());
        return ResponseEntity.unprocessableEntity().body(response);
    }

    @ExceptionHandler(ExistingTransactionsException.class)
    public ResponseEntity<ErrorResponse> handlerExistingTransactionsException(final ExistingTransactionsException exception) {
        final var response = ErrorResponse.from(exception.getMessage());
        return ResponseEntity.unprocessableEntity().body(response);
    }

    @ExceptionHandler(ConvertFileException.class)
    public ResponseEntity<ErrorResponse> handlerConvertFileException(final ConvertFileException exception) {
        final var response = ErrorResponse.from(exception.getMessage());
        return ResponseEntity.unprocessableEntity().body(response);
    }

    @ExceptionHandler(SaveEntityException.class)
    public ResponseEntity<ErrorResponse> handlerSaveEntityException(final SaveEntityException exception) {
        final var response = ErrorResponse.from(exception.getMessage());
        return ResponseEntity.unprocessableEntity().body(response);
    }

}

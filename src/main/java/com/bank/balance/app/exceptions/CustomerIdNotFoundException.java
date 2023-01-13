package com.bank.balance.app.exceptions;

public class CustomerIdNotFoundException extends RuntimeException {
    public CustomerIdNotFoundException(final String customerId) {
        super(String.format("CustomerId %s não encontrado", customerId));
    }
}

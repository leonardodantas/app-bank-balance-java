package com.bank.balance.app.exceptions;

import java.time.LocalDate;

public class DaysSearchException extends RuntimeException {

    public DaysSearchException() {
        super(String.format("O periodo minimo para buscar deve ser igual a data de hoje menos 90 dias, ou seja %s", LocalDate.now().minusDays(90)));
    }
}

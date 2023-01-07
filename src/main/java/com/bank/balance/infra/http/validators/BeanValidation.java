package com.bank.balance.infra.http.validators;

import com.bank.balance.domain.UserBalanceEntry;
import com.bank.balance.domain.UsersBalancesEntries;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

@Component
public class BeanValidation {

    private final Validator validator;

    public BeanValidation(final Validator validator) {
        this.validator = validator;
    }

    public void validate(final UserBalanceEntry userBalanceEntry) {
        final var violations = validator.validate(userBalanceEntry);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

    public void validate(final UsersBalancesEntries usersBalanceEntries) {
        final var violations = validator.validate(usersBalanceEntries);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}

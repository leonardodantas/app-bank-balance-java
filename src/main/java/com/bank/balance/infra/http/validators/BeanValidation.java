package com.bank.balance.infra.http.validators;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.validation.ConstraintViolationException;
import javax.validation.Validation;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BeanValidation {

    public static void validate(final Object object) {
        final var factory = Validation.buildDefaultValidatorFactory();
        final var validator = factory.getValidator();
        final var violations = validator.validate(object);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}

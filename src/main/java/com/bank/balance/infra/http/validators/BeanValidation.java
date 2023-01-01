package com.bank.balance.infra.http.validators;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

@Component
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

package com.algaworks.algashop.ordering.domain.valueobject;

//5.26. Desafio: implementação de Value Objects para Customer

import com.algaworks.algashop.ordering.domain.validator.FieldValidations;

import java.util.Objects;

import static com.algaworks.algashop.ordering.domain.exception.ErrorMessages.VALIDATION_ERROR_EMAIL_IS_INVALID;

public record Email(String email) {

    public Email {
        FieldValidations.requiresValidEmail(email, VALIDATION_ERROR_EMAIL_IS_INVALID);
    }

    @Override
    public String toString() {
        return email;
    }
}

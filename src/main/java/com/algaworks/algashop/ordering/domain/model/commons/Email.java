package com.algaworks.algashop.ordering.domain.model.commons;

//5.26. Desafio: implementação de Value Objects para Customer

import com.algaworks.algashop.ordering.domain.model.FieldValidations;

import static com.algaworks.algashop.ordering.domain.model.ErrorMessages.VALIDATION_ERROR_EMAIL_IS_INVALID;

public record Email(String value) {

    public Email {
        FieldValidations.requiresValidEmail(value, VALIDATION_ERROR_EMAIL_IS_INVALID);
    }

    @Override
    public String toString() {
        return value;
    }
}

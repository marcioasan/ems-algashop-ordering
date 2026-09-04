package com.algaworks.algashop.ordering.domain.model.valueobject;

import java.time.LocalDate;
import java.util.Objects;

import static com.algaworks.algashop.ordering.domain.model.exception.ErrorMessages.VALIDATION_ERROR_BIRTHDATE_MUST_IN_PAST;

//5.26. Desafio: implementação de Value Objects para Customer

public record BirthDate(LocalDate value) {

    public BirthDate (LocalDate value){
        Objects.requireNonNull(value, "Birth date must not be null");

        if(value.isAfter(LocalDate.now())){
            throw new IllegalArgumentException(VALIDATION_ERROR_BIRTHDATE_MUST_IN_PAST);
        }
        this.value = value;
    }

    public Integer getAge() {
        return LocalDate.now().getYear() - value.getYear();
//        return (int) Duration.between(value, LocalDate.now()).toDays();// Solução da Algaworks
    }

    @Override
    public String toString() {
        return value.toString();
    }
}

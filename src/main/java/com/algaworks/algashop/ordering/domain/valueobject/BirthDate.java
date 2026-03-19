package com.algaworks.algashop.ordering.domain.valueobject;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Objects;

import static com.algaworks.algashop.ordering.domain.exception.ErrorMessages.VALIDATION_ERROR_BIRTHDATE_MUST_IN_PAST;

//5.26. Desafio: implementação de Value Objects para Customer

public record BirthDate(LocalDate birthDate) {

    public BirthDate (LocalDate birthDate){
        Objects.requireNonNull(birthDate, "Birth date must not be null");

        if(birthDate.isAfter(LocalDate.now())){
            throw new IllegalArgumentException(VALIDATION_ERROR_BIRTHDATE_MUST_IN_PAST);
        }
        this.birthDate = birthDate;
    }

    public Integer getAge() {
        return LocalDate.now().getYear() - birthDate.getYear();
//        return (int) Duration.between(birthDate, LocalDate.now()).toDays();// Solução da Algaworks
    }

    @Override
    public String toString() {
        return birthDate.toString();
    }
}

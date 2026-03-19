package com.algaworks.algashop.ordering.domain.valueobject;

import java.util.Objects;

public record Phone(String phone) {

    public Phone {
        Objects.requireNonNull(phone);
            if (phone.isBlank()) {
                throw new IllegalArgumentException();
            }
    }

    @Override
    public String toString() {
        return phone;
    }
}

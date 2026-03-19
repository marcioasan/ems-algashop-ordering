package com.algaworks.algashop.ordering.domain.valueobject;

import java.util.Objects;

public record Document(String document) {

    public Document {
        Objects.requireNonNull(document);
        if (document.isBlank()) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public String toString() {
        return document;
    }
}

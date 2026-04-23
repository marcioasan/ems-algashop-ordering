package com.algaworks.algashop.ordering.domain.valueobject.id;

import io.hypersistence.tsid.TSID;

import java.util.Objects;

//6.13. Implementando novos identificadores - 8'50"
public record OrderItemId(TSID value) {

    public OrderItemId {
        Objects.requireNonNull(value);
    }

    public OrderItemId(Long value) {
        this(TSID.from(value));
    }

    public OrderItemId(String value) {
        this(TSID.from(value));
    }

    @Override
    public String toString() {
        return value.toString();
    }
}

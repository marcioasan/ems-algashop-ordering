package com.algaworks.algashop.ordering.domain.valueobject.id;

import io.hypersistence.tsid.TSID;

import java.util.Objects;

//6.13. Implementando novos identificadores - 4'10"
public record OrderId(TSID value) {

    public OrderId {
        Objects.requireNonNull(value);
    }

    public OrderId(Long value) {
        this(TSID.from(value));
    }

    public OrderId(String value) {
        this(TSID.from(value));
    }

    @Override
    public String toString() {
        return value.toString();
    }
}

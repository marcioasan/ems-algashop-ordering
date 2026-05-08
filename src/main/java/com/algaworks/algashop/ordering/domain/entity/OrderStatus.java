package com.algaworks.algashop.ordering.domain.entity;

//6.14. Implementando Aggregate de Order

public enum OrderStatus {
    DRAFT,
    PLACED,
    PAID,
    READY,
    CANCELED
}

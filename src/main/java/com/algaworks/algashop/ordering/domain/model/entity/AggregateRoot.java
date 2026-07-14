package com.algaworks.algashop.ordering.domain.model.entity;

//8.3. Definindo um Repository no Domain Model - 1'
public interface AggregateRoot<ID> {
    ID id();
}

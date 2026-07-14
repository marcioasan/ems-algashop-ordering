package com.algaworks.algashop.ordering.domain.model.repository;

import com.algaworks.algashop.ordering.domain.model.entity.AggregateRoot;

import java.util.Optional;

//8.3. Definindo um Repository no Domain Model - 2'10"
public interface Repository<T extends AggregateRoot<ID>, ID> {
    Optional<T> ofId(ID id);
    boolean exists(ID id);
    void add(T aggregateRoot);
    int count();
}

package com.algaworks.algashop.ordering.domain.model;

//8.3. Definindo um Repository no Domain Model - 1'
//13.3. Implementando evento de Customer Registered - 1'30" extends DomainEventSource
public interface AggregateRoot<ID> extends DomainEventSource {
    ID id();
}

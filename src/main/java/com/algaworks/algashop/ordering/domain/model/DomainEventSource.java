package com.algaworks.algashop.ordering.domain.model;

import java.util.List;

//13.3. Implementando evento de Customer Registered - 50" Classes fontes de eventos

public interface DomainEventSource {
    List<Object> domainEvents();
    void clearDomainEvents();
}

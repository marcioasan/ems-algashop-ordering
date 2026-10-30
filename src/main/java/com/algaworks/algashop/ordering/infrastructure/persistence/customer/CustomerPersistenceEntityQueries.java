package com.algaworks.algashop.ordering.infrastructure.persistence.customer;

import com.algaworks.algashop.ordering.application.customer.query.CustomerOutput;

import java.util.Optional;
import java.util.UUID;

//15.3. Otimizando consulta de Customer com JPQL
public interface CustomerPersistenceEntityQueries {
    Optional<CustomerOutput> findByIdAsOutput(UUID customerId);
}

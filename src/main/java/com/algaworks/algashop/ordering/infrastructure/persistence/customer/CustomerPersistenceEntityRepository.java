package com.algaworks.algashop.ordering.infrastructure.persistence.customer;

//8.30. Desafio: Implemente persistência para Customer

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CustomerPersistenceEntityRepository extends JpaRepository<CustomerPersistenceEntity, UUID> {
    Optional<CustomerPersistenceEntity> findByEmail(String value);

    //8.36. Consultas ligadas a verificações
    boolean existsByEmailAndIdNot(String email, UUID customerId);
}

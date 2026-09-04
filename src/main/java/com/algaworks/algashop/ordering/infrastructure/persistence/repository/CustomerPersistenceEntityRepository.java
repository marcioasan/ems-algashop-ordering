package com.algaworks.algashop.ordering.infrastructure.persistence.repository;

//8.30. Desafio: Implemente persistência para Customer

import com.algaworks.algashop.ordering.infrastructure.persistence.entity.CustomerPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerPersistenceEntityRepository extends JpaRepository<CustomerPersistenceEntity, UUID> {
}

package com.algaworks.algashop.ordering.infrastructure.persistence.repository;

import com.algaworks.algashop.ordering.domain.model.entity.Order;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

//8.6. Implementando Persistence Model - 7'55"

public interface OrderPersistenceEntityRepository extends JpaRepository<OrderPersistenceEntity, Long> {

    //8.33. Consultas para listagens - 2'50"
    List<OrderPersistenceEntity> findByCustomer_IdAndPlacedAtBetween(
            UUID customerId,
            OffsetDateTime start,
            OffsetDateTime end
    );
}

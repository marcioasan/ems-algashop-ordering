package com.algaworks.algashop.ordering.infrastructure.persistence.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

//8.6. Implementando Persistence Model - 7'55"

public interface OrderPersistenceEntityRepository extends JpaRepository<OrderPersistenceEntity, Long> {

//8.34. Criando consultas com JPQL
    @Query("""
        SELECT o
        FROM OrderPersistenceEntity o
        WHERE o.customer.id = :customerId
        AND YEAR(o.placedAt) = :year
    """)
    List<OrderPersistenceEntity> placedByCustomerInYear(
            @Param("customerId") UUID customerId,
            @Param("year") Integer year);

    //8.35. Consultas para somas e contagens com filtros - 1'40"
    @Query("""
        SELECT COUNT(o)
        FROM OrderPersistenceEntity o
        WHERE o.customer.id = :customerId
        AND YEAR(o.placedAt) = :year
        AND o.paidAt IS NOT NULL
        AND o.canceledAt IS NULL
    """)
    long salesQuantityByCustomerInYear(
            @Param("customerId") UUID customerId,
            @Param("year") int year
    );

    //8.35. Consultas para somas e contagens com filtros - 3'30", 10'20" explica o COALESCE
    @Query("""
        SELECT COALESCE(SUM(o.totalAmount), 0)
        FROM OrderPersistenceEntity o
        WHERE o.customer.id = :customerId
        AND o.canceledAt IS NULL
        AND o.paidAt IS NOT NULL
    """)
    BigDecimal totalSoldForCustomer(@Param("customerId") UUID customerId);

    //8.33. Consultas para listagens - 2'50"
//    List<OrderPersistenceEntity> findByCustomer_IdAndPlacedAtBetween(
//            UUID customerId,
//            OffsetDateTime start,
//            OffsetDateTime end
//    );
}

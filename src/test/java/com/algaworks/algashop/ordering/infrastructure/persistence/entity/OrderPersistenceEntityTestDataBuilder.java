package com.algaworks.algashop.ordering.infrastructure.persistence.entity;

import com.algaworks.algashop.ordering.domain.model.utility.IdGenerator;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntity.OrderPersistenceEntityBuilder;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

//8.13. Disassembler: Conversor de Jakarta Persistence Entity para Domain Entity - 4'50"
public class OrderPersistenceEntityTestDataBuilder {

    private OrderPersistenceEntityTestDataBuilder() {
    }

    public static OrderPersistenceEntityBuilder existingOrder() { //8.13. Disassembler: Conversor de Jakarta Persistence Entity para Domain Entity - 6'
        return OrderPersistenceEntity.builder()
                .id(IdGenerator.generateTSID().toLong())
                .customerId(IdGenerator.generateTimeBasedUUID())
                .totalItems(2)
                .totalAmount(new BigDecimal(1000))
                .status("DRAFT")
                .paymentMethod("CREDIT_CARD")
                .placedAt(OffsetDateTime.now());
    }
}

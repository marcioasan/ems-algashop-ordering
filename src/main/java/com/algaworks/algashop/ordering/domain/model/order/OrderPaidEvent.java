package com.algaworks.algashop.ordering.domain.model.order;

import com.algaworks.algashop.ordering.domain.model.customer.CustomerId;

import java.time.OffsetDateTime;

//13.11. Desafio: Implemente os eventos de Order

public record OrderPaidEvent(OrderId orderId, CustomerId customerId, OffsetDateTime paidAt) {
}
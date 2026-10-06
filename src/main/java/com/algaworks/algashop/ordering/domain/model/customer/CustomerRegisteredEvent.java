package com.algaworks.algashop.ordering.domain.model.customer;

import java.time.OffsetDateTime;
import java.time.OffsetTime;

//13.3. Implementando evento de Customer Registered - 5'30"

public record CustomerRegisteredEvent(CustomerId customerId, OffsetDateTime registeredAt) {
}

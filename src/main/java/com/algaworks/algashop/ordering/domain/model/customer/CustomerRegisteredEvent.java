package com.algaworks.algashop.ordering.domain.model.customer;

import com.algaworks.algashop.ordering.domain.model.commons.Email;
import com.algaworks.algashop.ordering.domain.model.commons.FullName;

import java.time.OffsetDateTime;
import java.time.OffsetTime;

//13.3. Implementando evento de Customer Registered - 5'30"

//13.9. Enriquecendo eventos
public record CustomerRegisteredEvent(CustomerId customerId,
                                      OffsetDateTime registeredAt,
                                      FullName fullName,
                                      Email email) {
}

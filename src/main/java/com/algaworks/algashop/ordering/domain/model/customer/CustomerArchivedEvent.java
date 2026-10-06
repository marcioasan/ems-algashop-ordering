package com.algaworks.algashop.ordering.domain.model.customer;

import java.time.OffsetDateTime;

//13.4. Implementando o evento de Customer Archived ₣1 - 50"

public record CustomerArchivedEvent(CustomerId customerId, OffsetDateTime archivedAt) {
}

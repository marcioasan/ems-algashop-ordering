package com.algaworks.algashop.ordering.application.customer.query;

import java.util.UUID;

//14.5. Utilizando Specification no Checkout Service - 1'20"
public interface CustomerQueryService {
    CustomerOutput findById(UUID customerId);
}

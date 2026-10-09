package com.algaworks.algashop.ordering.domain.model.shoppingcart;

import com.algaworks.algashop.ordering.domain.model.customer.CustomerId;

import java.time.OffsetDateTime;

//13.14. Desafio: Implemento os eventos de Shopping Cart

public record ShoppingCartCreatedEvent(
    ShoppingCartId shoppingCartId,
    CustomerId customerId,
    OffsetDateTime createdAt
) {}

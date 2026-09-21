package com.algaworks.algashop.ordering.domain.model.shoppingcart;

//8.37. Desafio: Implemente persistência para Shopping Cart

import com.algaworks.algashop.ordering.domain.model.Repository;
import com.algaworks.algashop.ordering.domain.model.customer.CustomerId;

import java.util.Optional;

public interface ShoppingCarts extends Repository<ShoppingCart, ShoppingCartId> {

    Optional<ShoppingCart> ofCustomer(CustomerId customerId);
}

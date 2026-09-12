package com.algaworks.algashop.ordering.domain.model.repository;

//8.37. Desafio: Implemente persistência para Shopping Cart

import com.algaworks.algashop.ordering.domain.model.entity.ShoppingCart;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.CustomerId;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.ShoppingCartId;

import java.util.Optional;

public interface ShoppingCarts extends Repository<ShoppingCart, ShoppingCartId>{

    Optional<ShoppingCart> ofCustomer(CustomerId customerId);
}

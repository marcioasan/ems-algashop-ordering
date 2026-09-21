package com.algaworks.algashop.ordering.domain.model.shoppingcart;

import com.algaworks.algashop.ordering.domain.model.customer.CustomerAlreadyHaveShoppingCartException;
import com.algaworks.algashop.ordering.domain.model.customer.CustomerNotFoundException;
import com.algaworks.algashop.ordering.domain.model.customer.Customers;
import com.algaworks.algashop.ordering.domain.model.DomainService;
import com.algaworks.algashop.ordering.domain.model.customer.CustomerId;
import lombok.RequiredArgsConstructor;

//9.11. Desafio - Implemente o Domain Service para ShoppingCart

@DomainService
@RequiredArgsConstructor
public class ShoppingService {
	
	private final ShoppingCarts shoppingCarts;
	private final Customers customers;

	public ShoppingCart startShopping(CustomerId customerId) {
		if (!customers.exists(customerId)) {
			throw new CustomerNotFoundException();
		}

		if (shoppingCarts.ofCustomer(customerId).isPresent()) {
			throw new CustomerAlreadyHaveShoppingCartException();
		}

		return ShoppingCart.startShopping(customerId);
	}

}
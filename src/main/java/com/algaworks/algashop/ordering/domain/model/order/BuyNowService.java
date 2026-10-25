package com.algaworks.algashop.ordering.domain.model.order;

import com.algaworks.algashop.ordering.domain.model.DomainService;
import com.algaworks.algashop.ordering.domain.model.commons.Money;
import com.algaworks.algashop.ordering.domain.model.customer.Customer;
import com.algaworks.algashop.ordering.domain.model.customer.LoyaltyPoints;
import com.algaworks.algashop.ordering.domain.model.product.Product;
import com.algaworks.algashop.ordering.domain.model.commons.Quantity;
import com.algaworks.algashop.ordering.domain.model.customer.CustomerId;
import lombok.RequiredArgsConstructor;

import java.time.Year;

//9.10. Desafio - Implemente o Domain Service de BuyNow

@DomainService
@RequiredArgsConstructor
public class BuyNowService {

	private final CustomerHaveFreeShippingSpecification customerHaveFreeShippingSpecification;

	public Order buyNow(Product product,
						Customer customer, //14.2. Implementando regra para frete grátis - 30"
						Billing billing,
						Shipping shipping,
						Quantity quantity,
						PaymentMethod paymentMethod) {
		
		product.checkOutOfStock();

		Order order = Order.draft(customer.id());
		order.changeBilling(billing);

		order.changePaymentMethod(paymentMethod);
		order.addItem(product, quantity);

		//14.2. Implementando regra para frete grátis - 1'
		if (haveFreeShipping(customer)) {
			Shipping freeShipping = shipping.toBuilder().cost(Money.ZERO).build();
			order.changeShipping(freeShipping);
		} else {
			order.changeShipping(shipping);
		}

		order.place();

		return order;
	}

	private boolean haveFreeShipping(Customer customer) {
		return customerHaveFreeShippingSpecification.isSatisfiedBy(customer); //14.3. Implementando Specification parametrizável - 5'45"
	}
}
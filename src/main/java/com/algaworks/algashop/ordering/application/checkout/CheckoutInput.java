package com.algaworks.algashop.ordering.application.checkout;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

//12.14. Desafio - Application Service para Checkout

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CheckoutInput {
	private UUID shoppingCartId;
	private String paymentMethod;
	private ShippingInput shipping;
	private BillingData billing;
}
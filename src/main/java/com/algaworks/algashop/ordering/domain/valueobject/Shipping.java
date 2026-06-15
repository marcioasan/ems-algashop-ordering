package com.algaworks.algashop.ordering.domain.valueobject;

import lombok.Builder;

import java.time.LocalDate;
import java.util.Objects;

//6.12. Desafio: Implemente o Value Object de Money e Quantity

@Builder(toBuilder = true) //6.29. Refinando a linguagem onipresente da implementação - 3'40"
public record Shipping(Money cost, LocalDate expectedDate, Recipient recipient, Address address) { //6.29. Refinando a linguagem onipresente da implementação - 2'20"
	public Shipping {
		Objects.requireNonNull(address);
		Objects.requireNonNull(recipient);
		Objects.requireNonNull(cost);
		Objects.requireNonNull(expectedDate);
	}
}
package com.algaworks.algashop.ordering.domain.valueobject;

import lombok.Builder;

import java.util.Objects;

//6.12. Desafio: Implemente o Value Object de Money e Quantity

@Builder
public record BillingInfo(FullName fullName, Document document, Phone phone, Address address) {
	public BillingInfo {
		Objects.requireNonNull(fullName);
		Objects.requireNonNull(document);
		Objects.requireNonNull(phone);
		Objects.requireNonNull(address);
	}
}
package com.algaworks.algashop.ordering.domain.model.valueobject;

import com.algaworks.algashop.ordering.domain.model.validator.FieldValidations;

//6.12. Desafio: Implemente o Value Object de Money e Quantity
public record ProductName(String value) {

	public ProductName {
		FieldValidations.requiresNonBlank(value);
	}

	@Override
	public String toString() {
		return value;
	}

}
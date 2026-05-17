package com.algaworks.algashop.ordering.domain.valueobject;

import lombok.Builder;

import java.util.Objects;

//6.12. Desafio: Implemente o Value Object de Money e Quantity
//6.21. Implementando métodos para o preenchimento de uma Order - 13' - O record faz a comparação pelo valor das propriedades e não pela referência (valor em memória)
// *Explicação gerada pelo copilot -> ou seja, se os valores forem iguais, o record considera os objetos iguais mesmo que sejam referências diferentes. O record é imutável, ou seja, não possui setters e suas propriedades são final. O record é uma forma mais concisa de criar classes imutáveis e com comparação por valor, o que é ideal para Value Objects. O record também gera automaticamente os métodos equals(), hashCode() e toString() com base nas propriedades definidas no record.
@Builder
public record BillingInfo(FullName fullName, Document document, Phone phone, Address address) {
	public BillingInfo {
		Objects.requireNonNull(fullName);
		Objects.requireNonNull(document);
		Objects.requireNonNull(phone);
		Objects.requireNonNull(address);
	}
}
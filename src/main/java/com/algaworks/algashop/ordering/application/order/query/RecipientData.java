package com.algaworks.algashop.ordering.application.order.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//*** 15.7. Consulta paginada para Order com Criteria API - 2'48" - Essa classe foi movida para o pacote query.

//12.8. Implementando caso de uso compra instantânea - 3'25"

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecipientData {
    private String firstName;
    private String lastName;
    private String document;
    private String phone;
}

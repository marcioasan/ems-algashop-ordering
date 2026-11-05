package com.algaworks.algashop.ordering.application.order.query;

import com.algaworks.algashop.ordering.application.commons.AddressData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//*** 15.7. Consulta paginada para Order com Criteria API - 2'48" - Essa classe foi movida para o pacote query.

//12.8. Implementando caso de uso compra instantânea - 1'50"

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillingData {
    private String firstName;
    private String lastName;
    private String document;
    private String email;
    private String phone;
    private AddressData address;
}

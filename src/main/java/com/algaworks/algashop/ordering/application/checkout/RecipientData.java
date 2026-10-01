package com.algaworks.algashop.ordering.application.checkout;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

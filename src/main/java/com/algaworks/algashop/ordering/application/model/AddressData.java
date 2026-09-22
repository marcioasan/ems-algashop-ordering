package com.algaworks.algashop.ordering.application.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//12.2. Application Service para casos de uso de Customer - 2'50"

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressData {
    private String street;
    private String number;
    private String complement;
    private String neighborhood;
    private String city;
    private String state;
    private String zipCode;
}

package com.algaworks.algashop.ordering.application.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

//12.2. Application Service para casos de uso de Customer - 50"
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerInput {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String document;
    private LocalDate birthDate;
    private Boolean promotionNotificationsAllowed;
    private AddressData address;
}

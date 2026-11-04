package com.algaworks.algashop.ordering.application.order.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

//15.5. Modelo de leitura detalhado para Order

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerMinimalOutput {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String document;
    private String phone;
}

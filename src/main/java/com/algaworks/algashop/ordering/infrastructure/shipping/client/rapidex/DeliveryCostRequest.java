package com.algaworks.algashop.ordering.infrastructure.shipping.client.rapidex;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//10.5. Integração com Spring RestClient - 1'
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryCostRequest {
    private String originZipCode;
    private String destinationZipCode;
}

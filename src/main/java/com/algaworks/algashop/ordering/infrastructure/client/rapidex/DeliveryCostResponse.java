package com.algaworks.algashop.ordering.infrastructure.client.rapidex;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//10.5. Integração com Spring RestClient - 2'
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryCostResponse {
    private String deliveryCost;
    private Long estimatedDaysToDeliver;
}

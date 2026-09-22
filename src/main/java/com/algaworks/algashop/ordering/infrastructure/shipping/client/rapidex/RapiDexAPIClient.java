package com.algaworks.algashop.ordering.infrastructure.shipping.client.rapidex;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;
//10.5. Integração com Spring RestClient - 2'50"
public interface RapiDexAPIClient {

    @PostExchange("/api/delivery-cost")
    DeliveryCostResponse calculate(@RequestBody DeliveryCostRequest request);
}

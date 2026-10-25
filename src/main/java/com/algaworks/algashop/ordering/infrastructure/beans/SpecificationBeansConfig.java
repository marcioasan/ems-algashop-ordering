package com.algaworks.algashop.ordering.infrastructure.beans;

import com.algaworks.algashop.ordering.domain.model.order.CustomerHaveFreeShippingSpecification;
import com.algaworks.algashop.ordering.domain.model.order.Orders;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//14.3. Implementando Specification parametrizável - 4'
@Configuration
public class SpecificationBeansConfig {

    @Bean
    public CustomerHaveFreeShippingSpecification customerHaveFreeShippingSpecification(Orders orders) {
        return new CustomerHaveFreeShippingSpecification(
                orders,
                200,
                2L,
                2000
        );
    }

}

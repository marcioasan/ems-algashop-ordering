package com.algaworks.algashop.ordering.application.order.query;

//15.6. Implementando consulta detalhada para Order - 30"
public interface OrderQueryService {
    OrderDetailOutput findById(String id);
}

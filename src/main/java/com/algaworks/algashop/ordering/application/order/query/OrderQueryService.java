package com.algaworks.algashop.ordering.application.order.query;

import com.algaworks.algashop.ordering.application.utility.PageFilter;
import org.springframework.data.domain.Page;

//15.6. Implementando consulta detalhada para Order - 30"
public interface OrderQueryService {
    OrderDetailOutput findById(String id);

    //15.7. Consulta paginada para Order com Criteria API - 2'
    Page<OrderSummaryOutput> filter(PageFilter filter);
}

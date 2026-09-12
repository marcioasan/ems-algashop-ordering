package com.algaworks.algashop.ordering.domain.model.repository;

import com.algaworks.algashop.ordering.domain.model.entity.Order;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.CustomerId;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.OrderId;

import java.time.Year;
import java.util.List;

//8.3. Definindo um Repository no Domain Model - 5'
public interface Orders extends Repository<Order, OrderId> {

    //8.33. Consultas para listagens - 1'30"
    List<Order> placedByCustomerInYear(CustomerId customerId, Year year);
}

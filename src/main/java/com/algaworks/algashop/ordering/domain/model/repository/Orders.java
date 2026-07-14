package com.algaworks.algashop.ordering.domain.model.repository;

import com.algaworks.algashop.ordering.domain.model.entity.Order;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.OrderId;

//8.3. Definindo um Repository no Domain Model - 5'
public interface Orders extends Repository<Order, OrderId> {
}

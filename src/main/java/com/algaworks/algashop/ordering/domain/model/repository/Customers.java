package com.algaworks.algashop.ordering.domain.model.repository;

import com.algaworks.algashop.ordering.domain.model.entity.Customer;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.CustomerId;

//8.3. Definindo um Repository no Domain Model - 6'
public interface Customers extends Repository<Customer, CustomerId> {
}

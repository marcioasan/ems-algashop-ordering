package com.algaworks.algashop.ordering.domain.model.order;

import com.algaworks.algashop.ordering.domain.model.Repository;
import com.algaworks.algashop.ordering.domain.model.commons.Money;
import com.algaworks.algashop.ordering.domain.model.customer.CustomerId;

import java.time.Year;
import java.util.List;

//8.3. Definindo um Repository no Domain Model - 5'
public interface Orders extends Repository<Order, OrderId> {

    //8.33. Consultas para listagens - 1'30"
    List<Order> placedByCustomerInYear(CustomerId customerId, Year year);

    //8.35. Consultas para somas e contagens com filtros - 1'
    long salesQuantityByCustomerInYear(CustomerId customerId, Year year);
    Money totalSoldForCustomer(CustomerId customerId);
}

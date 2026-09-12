package com.algaworks.algashop.ordering.domain.model.repository;

import com.algaworks.algashop.ordering.domain.model.entity.Customer;
import com.algaworks.algashop.ordering.domain.model.valueobject.Email;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.CustomerId;

import java.util.Optional;

/** Interface do Repositório de Domínio **/

//8.3. Definindo um Repository no Domain Model - 6'
public interface Customers extends Repository<Customer, CustomerId> {

    //8.32. Consultas com filtros - 1'
    Optional<Customer> ofEmail(Email email);

    //8.36. Consultas ligadas a verificações
    boolean isEmailUnique(Email email, CustomerId exceptCustomerId);
}

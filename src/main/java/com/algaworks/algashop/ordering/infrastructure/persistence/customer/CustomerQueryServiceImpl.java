package com.algaworks.algashop.ordering.infrastructure.persistence.customer;

import com.algaworks.algashop.ordering.application.customer.query.CustomerOutput;
import com.algaworks.algashop.ordering.application.customer.query.CustomerQueryService;
import com.algaworks.algashop.ordering.application.utility.Mapper;
import com.algaworks.algashop.ordering.domain.model.customer.CustomerNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

//14.5. Utilizando Specification no Checkout Service - 2'20"
@Component
@RequiredArgsConstructor
public class CustomerQueryServiceImpl implements CustomerQueryService {

    private final CustomerPersistenceEntityRepository repository;

    @Override
    public CustomerOutput findById(UUID customerId) {
        return repository.findByIdAsOutput(customerId).orElseThrow(()-> new CustomerNotFoundException()); //15.3. Otimizando consulta de Customer com JPQL - 6'
    }
}

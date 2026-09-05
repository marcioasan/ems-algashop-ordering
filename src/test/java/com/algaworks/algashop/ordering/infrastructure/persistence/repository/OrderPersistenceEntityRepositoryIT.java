package com.algaworks.algashop.ordering.infrastructure.persistence.repository;

import com.algaworks.algashop.ordering.domain.model.entity.CustomerTestDataBuilder;
import com.algaworks.algashop.ordering.infrastructure.persistence.config.SpringDataAuditingConfig;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.CustomerPersistenceEntity;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.CustomerPersistenceEntityTestDataBuilder;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntity;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntityTestDataBuilder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.UUID;

//8.8. Isolando execução dos testes de integração - 9'40"
//8.9. Testando modelo de persistência com testes de integração - 1' - mostra como executar test ou integrationTest
//@SpringBootTest
//@Transactional //8.9. Testando modelo de persistência com testes de integração - 7'30"
@DataJpaTest  //8.10. Otimizando testes de persistência com @DataJpaTest - 1'
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) //8.10. Otimizando testes de persistência com @DataJpaTest - 4'30"
@Import(SpringDataAuditingConfig.class) //8.15. Implementando propriedades únicas para o modelo de persistência - 9'50"
class OrderPersistenceEntityRepositoryIT {

    private final OrderPersistenceEntityRepository orderPersistenceEntityRepository;
    private final CustomerPersistenceEntityRepository customerPersistenceEntityRepository; //8.31. Relacionando entidades de persistência - 20'30"

    //8.31. Relacionando entidades de persistência - 22'30"
    private CustomerPersistenceEntity customerPersistenceEntity;

    @Autowired
    public OrderPersistenceEntityRepositoryIT(OrderPersistenceEntityRepository orderPersistenceEntityRepository, CustomerPersistenceEntityRepository customerPersistenceEntityRepository) {
        this.orderPersistenceEntityRepository = orderPersistenceEntityRepository;
        this.customerPersistenceEntityRepository = customerPersistenceEntityRepository;
    }

    //8.31. Relacionando entidades de persistência - 21'
    @BeforeEach
    public void setup() {
        UUID customerId = CustomerTestDataBuilder.DEFAULT_CUSTOMER_ID.value();
        if(!customerPersistenceEntityRepository.existsById(customerId)) {
            customerPersistenceEntity = customerPersistenceEntityRepository.saveAndFlush(
                    CustomerPersistenceEntityTestDataBuilder.aCustomer().build()
            );
        }
    }

    //8.9. Testando modelo de persistência com testes de integração - 3'
    @Test
    public void shouldPersist() {

        OrderPersistenceEntity entity = OrderPersistenceEntityTestDataBuilder.existingOrder()
                .customer(customerPersistenceEntity) //8.31. Relacionando entidades de persistência - 23'30"
                .build(); //8.13. Disassembler: Conversor de Jakarta Persistence Entity para Domain Entity - 5'50"

        orderPersistenceEntityRepository.saveAndFlush(entity);
        Assertions.assertThat(orderPersistenceEntityRepository.existsById(entity.getId())).isTrue();

        OrderPersistenceEntity savedEntity = orderPersistenceEntityRepository.findById(entity.getId()).orElseThrow();

        Assertions.assertThat(savedEntity.getItems()).isNotEmpty();
    }

    //8.9. Testando modelo de persistência com testes de integração - 5'30"
    @Test
    public void shouldCount() {
        long ordersCount = orderPersistenceEntityRepository.count();
        Assertions.assertThat(ordersCount).isZero();
    }

    //8.15. Implementando propriedades únicas para o modelo de persistência - 8'
    @Test
    public void shouldSetAuditingValues() {
        OrderPersistenceEntity entity = OrderPersistenceEntityTestDataBuilder.existingOrder()
                .customer(customerPersistenceEntity)
                .build();
        entity = orderPersistenceEntityRepository.saveAndFlush(entity);

        Assertions.assertThat(entity.getCreatedByUserId()).isNotNull();

        Assertions.assertThat(entity.getLastModifiedAt()).isNotNull();
        Assertions.assertThat(entity.getLastModifiedByUserId()).isNotNull();
    }
}
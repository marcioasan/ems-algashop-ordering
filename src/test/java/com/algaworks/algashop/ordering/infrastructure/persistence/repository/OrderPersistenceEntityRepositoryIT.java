package com.algaworks.algashop.ordering.infrastructure.persistence.repository;

import com.algaworks.algashop.ordering.domain.model.utility.IdGenerator;
import com.algaworks.algashop.ordering.infrastructure.persistence.config.SpringDataAuditingConfig;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntity;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntityTestDataBuilder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

//8.8. Isolando execução dos testes de integração - 9'40"
//8.9. Testando modelo de persistência com testes de integração - 1' - mostra como executar test ou integrationTest
//@SpringBootTest
//@Transactional //8.9. Testando modelo de persistência com testes de integração - 7'30"
@DataJpaTest  //8.10. Otimizando testes de persistência com @DataJpaTest - 1'
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) //8.10. Otimizando testes de persistência com @DataJpaTest - 4'30"
@Import(SpringDataAuditingConfig.class) //8.15. Implementando propriedades únicas para o modelo de persistência - 9'50"
class OrderPersistenceEntityRepositoryIT {

    private final OrderPersistenceEntityRepository orderPersistenceEntityRepository;

    @Autowired
    public OrderPersistenceEntityRepositoryIT(OrderPersistenceEntityRepository orderPersistenceEntityRepository) {
        this.orderPersistenceEntityRepository = orderPersistenceEntityRepository;
    }

    //8.9. Testando modelo de persistência com testes de integração - 3'
    @Test
    public void shouldPersist() {
        long orderId = IdGenerator.generateTSID().toLong();
        OrderPersistenceEntity entity = OrderPersistenceEntityTestDataBuilder.existingOrder().build(); //8.13. Disassembler: Conversor de Jakarta Persistence Entity para Domain Entity - 5'50"

        orderPersistenceEntityRepository.saveAndFlush(entity);
        Assertions.assertThat(orderPersistenceEntityRepository.existsById(orderId)).isTrue();
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
        OrderPersistenceEntity entity = OrderPersistenceEntityTestDataBuilder.existingOrder().build();
        entity = orderPersistenceEntityRepository.saveAndFlush(entity);

        Assertions.assertThat(entity.getCreatedByUserId()).isNotNull();

        Assertions.assertThat(entity.getLastModifiedAt()).isNotNull();
        Assertions.assertThat(entity.getLastModifiedByUserId()).isNotNull();
    }
}
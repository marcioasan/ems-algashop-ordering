package com.algaworks.algashop.ordering.infrastructure.persistence.provider;

import com.algaworks.algashop.ordering.domain.model.entity.Order;
import com.algaworks.algashop.ordering.domain.model.repository.Orders;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.OrderId;
import com.algaworks.algashop.ordering.infrastructure.persistence.assembler.OrderPersistenceEntityAssembler;
import com.algaworks.algashop.ordering.infrastructure.persistence.disassembler.OrderPersistenceEntityDisassembler;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntity;
import com.algaworks.algashop.ordering.infrastructure.persistence.repository.OrderPersistenceEntityRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Optional;

//8.11. Persistindo um Aggregate - 3', 5'50"(@Component)
@Component
@RequiredArgsConstructor
public class OrdersPersistenceProvider implements Orders {

    private final OrderPersistenceEntityRepository persistenceRepository;
    private final OrderPersistenceEntityAssembler assembler; //8.12. Assembler: Conversor de Domain Entity para Jakarta Persistence Entity - 10'
    private final OrderPersistenceEntityDisassembler disassembler; //8.13. Disassembler: Conversor de Jakarta Persistence Entity para Domain Entity - 9'30"

    private final EntityManager entityManager;//8.19. Implementando Optimistic Lock - 10'

    @Override
    public Optional<Order> ofId(OrderId orderId) {
        Optional<OrderPersistenceEntity> possibleEntity = persistenceRepository.findById(orderId.value().toLong());
        return possibleEntity.map(disassembler::toDomainEntity);
    }

    @Override
    public boolean exists(OrderId orderId) {
        return false;
    }

    //8.16. Atualizando o estado de um Aggregate - 10'30"
    @Override
    public void add(Order aggregateRoot) {
        long orderId = aggregateRoot.id().value().toLong();

        persistenceRepository.findById(orderId)
                .ifPresentOrElse(
                        (persistenceEntity) -> {
                            update(aggregateRoot, persistenceEntity);
                        },
                        ()-> {
                            insert(aggregateRoot);
                        }
                );
    }

    private void update(Order aggregateRoot, OrderPersistenceEntity persistenceEntity) {
        persistenceEntity = assembler.merge(persistenceEntity, aggregateRoot);
        entityManager.detach(persistenceEntity);//8.19. Implementando Optimistic Lock - 9'30"
        persistenceEntity = persistenceRepository.saveAndFlush(persistenceEntity);
        updateVersion(aggregateRoot, persistenceEntity);
//        aggregateRoot.setVersion(persistenceEntity.getVersion()); //8.19. Implementando Optimistic Lock - 6'
    }

    private void insert(Order aggregateRoot) {
        OrderPersistenceEntity persistenceEntity = assembler.fromDomain(aggregateRoot);
        persistenceRepository.saveAndFlush(persistenceEntity);
        updateVersion(aggregateRoot, persistenceEntity);
//        aggregateRoot.setVersion(persistenceEntity.getVersion()); //8.19. Implementando Optimistic Lock - 6'
    }

    //8.20. Protegendo alterações indevidas de Version - 1'20"
    @SneakyThrows
    private void updateVersion(Order aggregateRoot, OrderPersistenceEntity persistenceEntity) {
        Field version = aggregateRoot.getClass().getDeclaredField("version");
        version.setAccessible(true);
        ReflectionUtils.setField(version, aggregateRoot, persistenceEntity.getVersion());
        version.setAccessible(false);
    }
//    @Override
//    public void add(Order aggregateRoot) {
//
//        OrderPersistenceEntity persistenceEntity = assembler.fromDomain(aggregateRoot); //8.12. Assembler: Conversor de Domain Entity para Jakarta Persistence Entity - 10'30"
//
//
//        /*var persistenceEntity = OrderPersistenceEntity.builder()
//                .id(aggregateRoot.id().value().toLong())
//                .customerId(aggregateRoot.customerId().value())
//                .build();*/
//        persistenceRepository.saveAndFlush(persistenceEntity);
//    }

    @Override
    public int count() {
        return 0;
    }
}

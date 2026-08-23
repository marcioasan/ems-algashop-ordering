package com.algaworks.algashop.ordering.domain.model.repository;

import com.algaworks.algashop.ordering.domain.model.entity.Order;
import com.algaworks.algashop.ordering.domain.model.entity.OrderStatus;
import com.algaworks.algashop.ordering.domain.model.entity.OrderTestDataBuilder;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.OrderId;
import com.algaworks.algashop.ordering.infrastructure.persistence.assembler.OrderPersistenceEntityAssembler;
import com.algaworks.algashop.ordering.infrastructure.persistence.disassembler.OrderPersistenceEntityDisassembler;
import com.algaworks.algashop.ordering.infrastructure.persistence.provider.OrdersPersistenceProvider;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Optional;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

//8.20. Protegendo alterações indevidas de Version - Conteúdo de apoio "Testando o Optimistic Locking"
@DataJpaTest
@Import({OrdersPersistenceProvider.class,
        OrderPersistenceEntityAssembler.class,
        OrderPersistenceEntityDisassembler.class})
class Orders_2_IT {

    private final Orders orders;
    private final TransactionTemplate newTransaction;

    @Autowired
    public Orders_2_IT(Orders orders, PlatformTransactionManager transactionManager) {
        this.orders = orders;
        this.newTransaction = new TransactionTemplate(transactionManager);
        this.newTransaction.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
    }

    @Test
    public void shouldNotAllowStaleUpdates() {
        // T0: insere o pedido em transação própria
        OrderId orderId = inNewTransaction(() -> {
            Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.PLACED).build();
            orders.add(order);
            return order.id();
        });

        Assertions.assertThatExceptionOfType(ObjectOptimisticLockingFailureException.class)
                .isThrownBy(() -> inNewTransaction(() -> {
                    // T1: carrega o pedido em sua própria transação
                    Order orderT1 = orders.ofId(orderId).orElseThrow();

                    // T2: em outra transação separada, salva primeiro
                    inNewTransaction(() -> {
                        Order orderT2 = orders.ofId(orderId).orElseThrow();
                        orderT2.markAsPaid();
                        orders.add(orderT2);
                    });

                    // T1 tenta salvar com versão obsoleta
                    orderT1.cancel();
                    orders.add(orderT1);
                }));

        // Verifica que a atualização de T2 prevaleceu
        Order savedOrder = orders.ofId(orderId).orElseThrow();
        Assertions.assertThat(savedOrder.canceledAt()).isNull();
        Assertions.assertThat(savedOrder.paidAt()).isNotNull();
    }

    private <T> T inNewTransaction(Supplier<T> callback) {
        return newTransaction.execute(status -> callback.get());
    }

    private void inNewTransaction(Runnable callback) {
        newTransaction.executeWithoutResult(status -> callback.run());
    }
}

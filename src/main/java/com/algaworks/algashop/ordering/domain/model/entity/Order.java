package com.algaworks.algashop.ordering.domain.model.entity;

import com.algaworks.algashop.ordering.domain.model.exception.*;
import com.algaworks.algashop.ordering.domain.model.valueobject.*;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.CustomerId;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.OrderId;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.OrderItemId;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

//6.14. Implementando Aggregate de Order

//8.3. Definindo um Repository no Domain Model - 1'10"
public class Order implements AggregateRoot<OrderId>{ //6.10. Modelagem de Aggregates - 4' - Order é um <<AggregateRoot>>, nada mais que uma Entity que controla as outras, ou seja, a raiz do agregado.

    private OrderId id;
    private CustomerId customerId;//6.14. Implementando Aggregate de Order - 2'

    private Money totalAmount;
    private Quantity totalItems; //Invariante - Deve ser sempre a soma exata dos itens contidos nele e o custo de envio - 6.7. Analisando os detalhes de Order

    private OffsetDateTime placedAt;
    private OffsetDateTime paidAt;
    private OffsetDateTime canceledAt;
    private OffsetDateTime readyAt;

    private Billing billing;
    private Shipping shipping;

    private OrderStatus status;
    private PaymentMethod paymentMethod;

    private Set<OrderItem> items;

    //8.19. Implementando Optimistic Lock - 1'50"
    private Long version;

    @Builder(builderClassName = "ExistingOrderBuilder", builderMethodName = "existing") //6.15. Implementando Factory Method e Builder em Order e Orderltem 1'
    public Order(OrderId id, Long version, CustomerId customerId,
                 Money totalAmount, Quantity totalItems,
                 OffsetDateTime placedAt, OffsetDateTime paidAt,
                 OffsetDateTime canceledAt, OffsetDateTime readyAt,
                 Billing billing, Shipping shipping,
                 OrderStatus status, PaymentMethod paymentMethod,
                 Set<OrderItem> items) {
        this.setId(id);
        this.setVersion(version);
        this.setCustomerId(customerId);
        this.setTotalAmount(totalAmount);
        this.setTotalItems(totalItems);
        this.setPlacedAt(placedAt);
        this.setPaidAt(paidAt);
        this.setCanceledAt(canceledAt);
        this.setReadyAt(readyAt);
        this.setBilling(billing);
        this.setShipping(shipping);
        this.setStatus(status);
        this.setPaymentMethod(paymentMethod);
        this.setItems(items);
    }

    //6.15. Implementando Factory Method e Builder em Order e Orderltem 1'40"
    public static Order draft(CustomerId customerId) {
        return new Order(
                new OrderId(),
                null,
                customerId,
                Money.ZERO,
                Quantity.ZERO,
                null,
                null,
                null,
                null,
                null,
                null,
                OrderStatus.DRAFT,
                null,
                new HashSet<>()
        );
    }

    //6.16. Adicionando Orderltem em um Order - 1' - esse é um métod de negócio que altera o estado de um Order.
    //6.27. Implementando Value Object de Product - 1'25"
    public void addItem(Product product, Quantity quantity) {

        Objects.requireNonNull(product);
        Objects.requireNonNull(quantity);

        verifyIfChangeable();

        product.checkOutOfStock();

        OrderItem orderItem = OrderItem.brandNew()
                .orderId(this.id())
                .quantity(quantity)
                .product(product)
                .build();

        if (this.items == null) {
            this.items = new HashSet<>();
        }

        this.items.add(orderItem);

        this.recalculateTotals(); //6.18. Propriedades calculadas
    }

    //6.20. Usando regras para o controle de alteração de status - 40"
    //6.22. Implementando regras de negócio para garantir invariantes
    public void place() {

        //6.24. Aprimorando Exceptions com Factory Method - 8'5"
        this.verifyIfCanChangeToPlaced();

        this.changeStatus(OrderStatus.PLACED);//6.22. Implementando regras de negócio para garantir invariantes - CONTEÚDO DE APOIO -> Opte por invocar o método que faz a alteração de status, antes de qualquer outro método que realiza alterações de estado. Isso irá garantir que o Aggregate só seja alterado caso a transição de estado seja válida.
        this.setPlacedAt(OffsetDateTime.now());
    }

    //6.23. Implementando o padrão TestDataBuilder em Order - 16'30"
    public void markAsPaid() {
        this.setPaidAt(OffsetDateTime.now());
        this.changeStatus(OrderStatus.PAID);
    }

    //6.34. Desafio: Implemente o método para marcar um Order como ready
    public void markAsReady() {
        this.changeStatus(OrderStatus.READY);
        this.setReadyAt(OffsetDateTime.now());
    }

    //6.21. Implementando métodos para o preenchimento de uma Order - 30"
    public void changePaymentMethod(PaymentMethod paymentMethod) {
        Objects.requireNonNull(paymentMethod);
        verifyIfChangeable();
        this.setPaymentMethod(paymentMethod);
    }

    public void changeBilling(Billing billing) {
        Objects.requireNonNull(billing);
        verifyIfChangeable();
        this.setBilling(billing);
    }

    //6.21. Implementando métodos para o preenchimento de uma Order - 2'30"
    public void changeShipping(Shipping newShipping) { //6.29. Refinando a linguagem onipresente da implementação - 7'35"
        Objects.requireNonNull(newShipping);

        verifyIfChangeable();

        if (newShipping.expectedDate().isBefore(LocalDate.now())) {
            throw new OrderInvalidShippingDeliveryDateException(this.id());
        }

        this.setShipping(newShipping);
        this.recalculateTotals();
    }

    //6.25. Alterando quantidade de um item - 1'
    public void changeItemQuantity(OrderItemId orderItemId, Quantity quantity) {
        Objects.requireNonNull(orderItemId);
        Objects.requireNonNull(quantity);

        verifyIfChangeable();

        OrderItem orderItem = this.findOrderItem(orderItemId);
        orderItem.changeQuantity(quantity);

        this.recalculateTotals();
    }

    //6.33. Desafio: Remoção de itens de um Order
    public void removeItem(OrderItemId orderItemId) {
        Objects.requireNonNull(orderItemId);
        verifyIfChangeable();

        OrderItem orderItem = findOrderItem(orderItemId);
        this.items.remove(orderItem);

        recalculateTotals();
    }

    //6.20. Usando regras para o controle de alteração de status - 6'
    public boolean isDraft() {
        return OrderStatus.DRAFT.equals(this.status());
    }

    public boolean isPlaced() {
        return OrderStatus.PLACED.equals(this.status());
    }

    public boolean isPaid() {
        return OrderStatus.PAID.equals(this.status());
    }
    public boolean isReady() {
        return OrderStatus.READY.equals(this.status());
    }

    public boolean isCanceled() {
        return OrderStatus.CANCELED.equals(this.status());
    }

    public OrderId id() {
        return id;
    }

    public CustomerId customerId() {
        return customerId;
    }

    public Money totalAmount() {
        return totalAmount;
    }

    public Quantity totalItems() {
        return totalItems;
    }

    public OffsetDateTime placedAt() {
        return placedAt;
    }

    public OffsetDateTime paidAt() {
        return paidAt;
    }

    public OffsetDateTime canceledAt() {
        return canceledAt;
    }

    public OffsetDateTime readyAt() {
        return readyAt;
    }

    public Billing billing() {
        return billing;
    }

    public Shipping shipping() {
        return shipping;
    }

    public OrderStatus status() {
        return status;
    }

    public PaymentMethod paymentMethod() {
        return paymentMethod;
    }

    public Set<OrderItem> items() {
        return Collections.unmodifiableSet(this.items); //6.17. Protegendo Collections
    }

    //6.18. Propriedades calculadas
    private void recalculateTotals() {
        BigDecimal totalItemsAmount = this.items().stream().map(i -> i.totalAmount().value())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Integer totalItemsQuantity = this.items().stream().map(i -> i.quantity().value())
                .reduce(0, Integer::sum);

        BigDecimal shippingCost;
        //6.29. Refinando a linguagem onipresente da implementação - 6'40"
        if(this.shipping() == null) {
            shippingCost = BigDecimal.ZERO;
        } else {
            shippingCost = this.shipping().cost().value();
        }

        BigDecimal totalAmount = totalItemsAmount.add(shippingCost);

        this.setTotalAmount(new Money(totalAmount));
        this.setTotalItems(new Quantity(totalItemsQuantity));
    }

    //6.20. Usando regras para o controle de alteração de status - 1'50"
    private void changeStatus(OrderStatus newStatus) {
        Objects.requireNonNull(newStatus);
        if (this.status().canNotChangeTo(newStatus)) {
            throw new OrderStatusCannotBeChangedException(this.id(), this.status(), newStatus);
        }
        this.setStatus(newStatus);
    }

    private void verifyIfCanChangeToPlaced() {
        if (this.shipping() == null) {
            throw OrderCannotBePlacedException.noShippingInfo(this.id());
        }
        if (this.billing() == null) {
            throw OrderCannotBePlacedException.noBillingInfo(this.id());
        }
        if (this.paymentMethod() == null) {
            throw OrderCannotBePlacedException.noPaymentMethod(this.id());
        }
        if (this.items() == null || this.items().isEmpty()) {
            throw OrderCannotBePlacedException.noItems(this.id());
        }
    }

    //6.25. Alterando quantidade de um item - 2'40"
    private OrderItem findOrderItem(OrderItemId orderItemId) {
        Objects.requireNonNull(orderItemId);
        return this.items().stream()
                .filter(i -> i.id().equals(orderItemId))
                .findFirst()
                .orElseThrow(()-> new OrderDoesNotContainOrderItemException(this.id(), orderItemId));
    }

    //6.35. Desafio: Cancelamento de um Order
    public void cancel() {
        this.setCanceledAt(OffsetDateTime.now());
        this.changeStatus(OrderStatus.CANCELED);
    }

    //6.32. Desafio: Bloqueando edição de um Order
    private void verifyIfChangeable(){
        if(!this.isDraft()){
            throw new OrderCannotBeEditedException(this.id(), this.status());
        }
    }

    private void setId(OrderId id) {
        Objects.requireNonNull(id);
        this.id = id;
    }

    //8.19. Implementando Optimistic Lock - 4'
    public Long version() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    private void setCustomerId(CustomerId customerId) {
        Objects.requireNonNull(customerId);
        this.customerId = customerId;
    }

    private void setTotalAmount(Money totalAmount) {
        Objects.requireNonNull(totalAmount);
        this.totalAmount = totalAmount;
    }

    private void setTotalItems(Quantity totalItems) {
        Objects.requireNonNull(totalItems);
        this.totalItems = totalItems;
    }

    private void setPlacedAt(OffsetDateTime placedAt) {
        this.placedAt = placedAt;
    }

    private void setPaidAt(OffsetDateTime paidAt) {
        this.paidAt = paidAt;
    }

    private void setCanceledAt(OffsetDateTime canceledAt) {
        this.canceledAt = canceledAt;
    }

    private void setReadyAt(OffsetDateTime readyAt) {
        this.readyAt = readyAt;
    }

    private void setBilling(Billing billing) {
        this.billing = billing;
    }

    private void setShipping(Shipping shipping) {
        this.shipping = shipping;
    }

    private void setStatus(OrderStatus status) {
        Objects.requireNonNull(status);
        this.status = status;
    }

    private void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    private void setItems(Set<OrderItem> items) {
        Objects.requireNonNull(items);
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

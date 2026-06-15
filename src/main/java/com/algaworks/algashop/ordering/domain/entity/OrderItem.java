package com.algaworks.algashop.ordering.domain.entity;

import com.algaworks.algashop.ordering.domain.valueobject.Money;
import com.algaworks.algashop.ordering.domain.valueobject.Product;
import com.algaworks.algashop.ordering.domain.valueobject.ProductName;
import com.algaworks.algashop.ordering.domain.valueobject.Quantity;
import com.algaworks.algashop.ordering.domain.valueobject.id.OrderId;
import com.algaworks.algashop.ordering.domain.valueobject.id.OrderItemId;
import com.algaworks.algashop.ordering.domain.valueobject.id.ProductId;
import lombok.Builder;

import java.util.Objects;

//6.14. Implementando Aggregate de Order - 4'40"

public class OrderItem {
    
    private OrderItemId id;
    private OrderId orderId;
    
    private ProductId productId;
    private ProductName productName;
    
    private Money price;
    private Quantity quantity;
    
    private Money totalAmount;

    @Builder(builderClassName = "ExistingOrderItemBuilder", builderMethodName = "existing") //6.15. Implementando Factory Method e Builder em Order e Orderltem 4'10"
    public OrderItem(OrderItemId id, OrderId orderId,
                     ProductId productId, ProductName productName,
                     Money price, Quantity quantity,
                     Money totalAmount) {
        this.setId(id);
        this.setOrderId(orderId);
        this.setProductId(productId);
        this.setProductName(productName);
        this.setPrice(price);
        this.setQuantity(quantity);
        this.setTotalAmount(totalAmount);
    }

    @Builder(builderClassName = "BrandNewOrderItemBuilder", builderMethodName = "brandNew") //6.15. Implementando Factory Method e Builder em Order e Orderltem 4'40"
    private static OrderItem createBrandNew(OrderId orderId,
                                            Product product,
                                            Quantity quantity) { //6.27. Implementando Value Object de Product - 2'20"
        Objects.requireNonNull(product);
        Objects.requireNonNull(orderId);
        Objects.requireNonNull(quantity);

        //6.18. Propriedades calculadas
        OrderItem orderItem = new OrderItem(
                new OrderItemId(),
                orderId,
                product.id(),//6.27. Implementando Value Object de Product - 2'20"
                product.name(),
                product.price(),
                quantity,
                Money.ZERO
        );

        orderItem.recalculateTotals();
        return orderItem;
    }

    //6.25. Alterando quantidade de um item - 6'30"
    void changeQuantity(Quantity quantity) { //6.10. Modelagem de Aggregates - 3' Esse método só pode ser acessado pelo Root Order, então ficará com o package private
        Objects.requireNonNull(quantity);
        this.setQuantity(quantity);
        this.recalculateTotals();
    }

    public OrderItemId id() {
        return id;
    }

    public OrderId orderId() {
        return orderId;
    }

    public ProductId productId() {
        return productId;
    }

    public ProductName productName() {
        return productName;
    }

    public Money price() {
        return price;
    }

    public Quantity quantity() {
        return quantity;
    }

    public Money totalAmount() {
        return totalAmount;
    }

    private void recalculateTotals() {
        this.setTotalAmount(this.price().multiply(this.quantity()));
    }

    private void setId(OrderItemId id) {
        Objects.requireNonNull(id);
        this.id = id;
    }

    private void setOrderId(OrderId orderId) {
        Objects.requireNonNull(orderId);
        this.orderId = orderId;
    }

    private void setProductId(ProductId productId) {
        Objects.requireNonNull(productId);
        this.productId = productId;
    }

    private void setProductName(ProductName productName) {
        Objects.requireNonNull(productName);
        this.productName = productName;
    }

    private void setPrice(Money price) {
        Objects.requireNonNull(price);
        this.price = price;
    }

    private void setQuantity(Quantity quantity) {
        Objects.requireNonNull(quantity);
        this.quantity = quantity;
    }

    private void setTotalAmount(Money totalAmount) {
        Objects.requireNonNull(totalAmount);
        this.totalAmount = totalAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return Objects.equals(id, orderItem.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

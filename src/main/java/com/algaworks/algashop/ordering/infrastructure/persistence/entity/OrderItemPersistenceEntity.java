package com.algaworks.algashop.ordering.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

//8.25. Persistindo Entities em cascata - 1'40"

@Entity
@Table(name = "order_item")
@Data
@ToString(of= "id")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemPersistenceEntity {
    @Id
    @EqualsAndHashCode.Include
    private Long id;
    private UUID productId;
    private String productName;
    private BigDecimal price;
    private Integer quantity;
    private BigDecimal totalAmount;

    //8.25. Persistindo Entities em cascata - 5'35"
    @JoinColumn
    @ManyToOne(optional = false)
    private OrderPersistenceEntity order;

    public Long getOrderId() {
        if (getOrder() == null) {
            return null;
        }

        return getOrder().getId();
    }
}

package com.algaworks.algashop.ordering.infrastructure.persistence.entity;

import com.algaworks.algashop.ordering.infrastructure.persistence.embeddable.BillingEmbeddable;
import com.algaworks.algashop.ordering.infrastructure.persistence.embeddable.ShippingEmbeddable;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

//8.6. Implementando Persistence Model - 1'20"

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(of = "id")
@Table(name = "\"order\"")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@EntityListeners(AuditingEntityListener.class) //8.15. Implementando propriedades únicas para o modelo de persistência - 7' - Ativa os listeners de auditoria
public class OrderPersistenceEntity {
    @Id
    @EqualsAndHashCode.Include
    private Long id;
    private UUID customerId;

    private BigDecimal totalAmount;
    private Integer totalItems;
    private String status;
    private String paymentMethod;

    private OffsetDateTime placedAt;
    private OffsetDateTime paidAt;
    private OffsetDateTime canceledAt;
    private OffsetDateTime readyAt;

    //8.15. Implementando propriedades únicas para o modelo de persistência - 1'10"
    @CreatedBy
    private UUID createdByUserId;
    @LastModifiedDate
    private OffsetDateTime lastModifiedAt;
    @LastModifiedBy
    private UUID lastModifiedByUserId;

    //8.19. Implementando Optimistic Lock - 20"
    @Version
    private Long version;

    //8.22. Integrando Embeddable e Entity - 10"
    @Embedded
    private BillingEmbeddable billing;

    @Embedded
    private ShippingEmbeddable shipping;
}

package com.algaworks.algashop.ordering.infrastructure.persistence.customer;

import com.algaworks.algashop.ordering.infrastructure.persistence.commons.AddressEmbeddable;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.UUID;

/** Entidade de Persistência **/

//8.30. Desafio: Implemente persistência para Customer

@Entity
@Getter
@Setter
@ToString(of = "id")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false) //13.5. Implementando infraestrutura para publicação de eventos - 2' - deixa explícito que não é pra chamar EqualsAndHashCode do AbstractAggregateRoot, que é a superclasse
@NoArgsConstructor
@Table(name = "\"customer\"")
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class CustomerPersistenceEntity extends AbstractAggregateRoot<CustomerPersistenceEntity> { //13.5. Implementando infraestrutura para publicação de eventos - 40" - Classe que ajuda a trabalhar com eventos de domínio para o Spring Data

    /** Campos equivalentes aos atributos do aggregate Customer **/
    @Id
    @EqualsAndHashCode.Include
    private UUID id; //5.25. Refatorando as entidades para usar Value Objects - 30"
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String email;
    private String phone;
    private String document;
    private Boolean promotionNotificationsAllowed;
    private Boolean archived;
    private OffsetDateTime registeredAt;
    private OffsetDateTime archivedAt;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "street", column = @Column(name = "address_street")),
            @AttributeOverride(name = "number", column = @Column(name = "address_number")),
            @AttributeOverride(name = "complement", column = @Column(name = "address_complement")),
            @AttributeOverride(name = "neighborhood", column = @Column(name = "address_neighborhood")),
            @AttributeOverride(name = "city", column = @Column(name = "address_city")),
            @AttributeOverride(name = "state", column = @Column(name = "address_state")),
            @AttributeOverride(name = "zipCode", column = @Column(name = "address_zipCode"))
    })
    private AddressEmbeddable address;
    private Integer loyaltyPoints;

    /** Campos de auditoria **/
    @CreatedBy
    private UUID createdByUserId;

    @LastModifiedBy
    private UUID lastModifiedByUserId;

    @LastModifiedDate
    private OffsetDateTime lastModifiedAt;

    /** Campo para controle de concorrência otimista **/ //8.19. Implementando Optimistic Lock - 20"
    @Version
    private Long version;

    //13.5. Implementando infraestrutura para publicação de eventos - 2'30" - métodos para expor os eventos de domínio e adicionar eventos de domínio, que serão usados no CustomerRepositoryImpl.java
    public Collection<Object> getEvents() {
        return super.domainEvents();
    }

    public void addEvents(Collection<Object> events) {
        if (events != null) {
            for (Object event : events) {
                this.registerEvent(event);
            }
        }
    }
}

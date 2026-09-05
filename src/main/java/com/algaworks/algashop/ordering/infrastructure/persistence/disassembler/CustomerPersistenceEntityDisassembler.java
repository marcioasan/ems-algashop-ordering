package com.algaworks.algashop.ordering.infrastructure.persistence.disassembler;

//8.30. Desafio: Implemente persistência para Customer

import com.algaworks.algashop.ordering.domain.model.entity.Customer;
import com.algaworks.algashop.ordering.domain.model.valueobject.*;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.CustomerId;
import com.algaworks.algashop.ordering.infrastructure.persistence.embeddable.AddressEmbeddable;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.CustomerPersistenceEntity;

import java.util.UUID;

public class CustomerPersistenceEntityDisassembler {

    public Customer  toDomainEntity(CustomerPersistenceEntity persistenceEntity) {

       return Customer.existing()
                .id(new CustomerId(UUID.fromString(persistenceEntity.getId().toString())))
                .fullName(new FullName(persistenceEntity.getFirstName(), persistenceEntity.getLastName()))
                .birthDate(persistenceEntity.getBirthDate() != null ? new BirthDate(persistenceEntity.getBirthDate()) : null)
                .email(new Email(persistenceEntity.getEmail()))
                .phone(new Phone(persistenceEntity.getPhone()))
                .document(new Document(persistenceEntity.getDocument()))
                .loyaltyPoints(new LoyaltyPoints(persistenceEntity.getLoyaltyPoints()))
                .promotionNotificationsAllowed(persistenceEntity.getPromotionNotificationsAllowed())
                .archived(persistenceEntity.getArchived())
                .registeredAt(persistenceEntity.getRegisteredAt())
                .archivedAt(persistenceEntity.getArchivedAt())
                .address(toAddressValueObject(persistenceEntity.getAddress()))
                .version(persistenceEntity.getVersion())
                .build();
    }

    private Address toAddressValueObject(AddressEmbeddable address) {
        return Address.builder()
                .street(address.getStreet())
                .number(address.getNumber())
                .complement(address.getComplement())
                .neighborhood(address.getNeighborhood())
                .city(address.getCity())
                .state(address.getState())
                .zipCode(new ZipCode(address.getZipCode()))
                .build();
    }
}

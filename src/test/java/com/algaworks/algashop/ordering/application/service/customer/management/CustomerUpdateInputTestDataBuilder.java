package com.algaworks.algashop.ordering.application.service.customer.management;

import com.algaworks.algashop.ordering.application.commons.AddressData;
import com.algaworks.algashop.ordering.application.customer.management.CustomerUpdateInput;

//12.7. Implementando caso de uso de atualização de Customer - 10'50"
public class CustomerUpdateInputTestDataBuilder {

    public static CustomerUpdateInput.CustomerUpdateInputBuilder aCustomerUpdate() {
        return CustomerUpdateInput.builder()
                .firstName("Matt")
                .lastName("Damon")
                .phone("123-321-1112")
                .promotionNotificationsAllowed(true)
                .address(AddressData.builder()
                        .street("Amphitheatre Parkway")
                        .number("1600")
                        .complement("")
                        .neighborhood("Mountain View")
                        .city("Mountain View")
                        .state("California")
                        .zipCode("94043")
                        .build());

    }

}

package com.algaworks.algashop.ordering.application.service.customer.management;

import com.algaworks.algashop.ordering.application.customer.management.CustomerManagementApplicationService;
import com.algaworks.algashop.ordering.application.commons.AddressData;
import com.algaworks.algashop.ordering.application.customer.management.CustomerInput;
import com.algaworks.algashop.ordering.application.customer.management.CustomerOutput;
import com.algaworks.algashop.ordering.application.customer.management.CustomerUpdateInput;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

//12.2. Application Service para casos de uso de Customer - 9'10"

@SpringBootTest
@Transactional //12.7. Implementando caso de uso de atualização de Customer - 13'50" - Anotação para um teste não afetar o outro.
class CustomerManagementApplicationServiceIT {

    @Autowired
    private CustomerManagementApplicationService customerManagementApplicationService;
    //12.3. Consulta de dados - 6'40"
    //12.7. Implementando caso de uso de atualização de Customer - 6'20"
    @Test
    public void shouldRegister() {
        CustomerInput input = CustomerInputTestDataBuilder.aCustomer().build();

        UUID customerId = customerManagementApplicationService.create(input);
        Assertions.assertThat(customerId).isNotNull();

        CustomerOutput customerOutput = customerManagementApplicationService.findById(customerId);

        //12.7. Implementando caso de uso de atualização de Customer - 8'
        Assertions.assertThat(customerOutput)
                .extracting(
                        CustomerOutput::getId,
                        CustomerOutput::getFirstName,
                        CustomerOutput::getLastName,
                        CustomerOutput::getEmail,
                        CustomerOutput::getBirthDate
                ).containsExactly(
                        customerId,
                        "John",
                        "Doe",
                        "johndoe@email.com",
                        LocalDate.of(1991, 7,5)
                );

        Assertions.assertThat(customerOutput.getRegisteredAt()).isNotNull();
    }

    //12.7. Implementando caso de uso de atualização de Customer - 10'
    @Test
    public void shouldUpdate() {
        CustomerInput input = CustomerInputTestDataBuilder.aCustomer().build();
        CustomerUpdateInput updateInput = CustomerUpdateInputTestDataBuilder.aCustomerUpdate().build();

        UUID customerId = customerManagementApplicationService.create(input);
        Assertions.assertThat(customerId).isNotNull();

        customerManagementApplicationService.update(customerId, updateInput);

        CustomerOutput customerOutput = customerManagementApplicationService.findById(customerId);

        Assertions.assertThat(customerOutput)
                .extracting(
                        CustomerOutput::getId,
                        CustomerOutput::getFirstName,
                        CustomerOutput::getLastName,
                        CustomerOutput::getEmail,
                        CustomerOutput::getBirthDate
                ).containsExactly(
                        customerId,
                        "Matt",
                        "Damon",
                        "johndoe@email.com",
                        LocalDate.of(1991, 7,5)
                );

        Assertions.assertThat(customerOutput.getRegisteredAt()).isNotNull();
    }
}
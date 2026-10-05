package com.algaworks.algashop.ordering.application.customer.management;

import com.algaworks.algashop.ordering.domain.model.customer.CustomerArchivedException;
import com.algaworks.algashop.ordering.domain.model.customer.CustomerEmailIsInUseException;
import com.algaworks.algashop.ordering.domain.model.customer.CustomerId;
import com.algaworks.algashop.ordering.domain.model.customer.CustomerNotFoundException;
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

    //12.9. Desafio - Implemente o arquivamento de Customer
    @Test
    public void shouldArchiveCustomer() {
        CustomerInput input = CustomerInputTestDataBuilder.aCustomer().build();
        UUID customerId = customerManagementApplicationService.create(input);
        Assertions.assertThat(customerId).isNotNull();

        customerManagementApplicationService.archive(customerId);

        CustomerOutput archivedCustomer = customerManagementApplicationService.findById(customerId);

        Assertions.assertThat(archivedCustomer)
                .isNotNull()
                .extracting(
                        CustomerOutput::getFirstName,
                        CustomerOutput::getLastName,
                        CustomerOutput::getPhone,
                        CustomerOutput::getDocument,
                        CustomerOutput::getBirthDate,
                        CustomerOutput::getPromotionNotificationsAllowed
                ).containsExactly(
                        "Anonymous",
                        "Anonymous",
                        "000-000-0000",
                        "000-00-0000",
                        null,
                        false
                );

        Assertions.assertThat(archivedCustomer.getEmail()).endsWith("@anonymous.com");
        Assertions.assertThat(archivedCustomer.getArchived()).isTrue();
        Assertions.assertThat(archivedCustomer.getArchivedAt()).isNotNull();

        Assertions.assertThat(archivedCustomer.getAddress()).isNotNull();
        Assertions.assertThat(archivedCustomer.getAddress().getNumber()).isNotNull().isEqualTo("Anonymized");
        Assertions.assertThat(archivedCustomer.getAddress().getComplement()).isNull();
    }

    @Test
    public void shouldThrowCustomerNotFoundExceptionWhenArchivingNonExistingCustomer() {
        UUID nonExistingId = UUID.randomUUID();

        Assertions.assertThatExceptionOfType(CustomerNotFoundException.class)
                .isThrownBy(() -> customerManagementApplicationService.archive(nonExistingId));
    }

    @Test
    public void shouldThrowCustomerArchivedExceptionWhenArchivingAlreadyArchivedCustomer() {
        CustomerInput input = CustomerInputTestDataBuilder.aCustomer().build();
        UUID customerId = customerManagementApplicationService.create(input);
        Assertions.assertThat(customerId).isNotNull();

        customerManagementApplicationService.archive(customerId);

        Assertions.assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(() -> customerManagementApplicationService.archive(customerId));
    }

    //12.10. Desafio - Implemente alteração de email para Customer - resolução de um aluno - https://app.algaworks.com/forum/topicos/92325/resolucao-desafio-12-10
    @Test
    void shouldChangeEmail() {
        String expectedEmail = "new.email@example.com";

        CustomerInput customerInput = CustomerInputTestDataBuilder.aCustomer().build();
        UUID customerId = customerManagementApplicationService.create(customerInput);
        customerManagementApplicationService.changeEmail(customerId, expectedEmail);

        CustomerOutput customerOutput = customerManagementApplicationService.findById(customerId);

        Assertions.assertThat(customerOutput).isNotNull();
        Assertions.assertThat(customerOutput.getEmail())
                .isNotBlank()
                .isEqualTo(expectedEmail);
    }

    @Test
    void givenANonExistentCustomer_whenChangeEmail_shouldThrowException() {
        Assertions.assertThatExceptionOfType(CustomerNotFoundException.class)
                .isThrownBy(() -> customerManagementApplicationService.changeEmail(
                        new CustomerId().value(),
                        "new.email@example.com"));
    }

    @Test
    void givenAnArchivedCustomer_whenChangeEmail_shouldThrowException() {
        CustomerInput customerInput = CustomerInputTestDataBuilder.aCustomer().build();
        UUID customerId = customerManagementApplicationService.create(customerInput);
        customerManagementApplicationService.archive(customerId);

        Assertions.assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(() -> customerManagementApplicationService.changeEmail(customerId, "new.email@example.com"));
    }

    @Test
    void givenAnInvalidEmail_whenChangeEmail_shouldThrowException() {
        CustomerInput customerInput = CustomerInputTestDataBuilder.aCustomer().build();
        UUID customerId = customerManagementApplicationService.create(customerInput);

        String invalidEmail = "email.com";

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> customerManagementApplicationService.changeEmail(customerId, invalidEmail));
    }

    @Test
    void givenAnAlreadyUsedEmail_whenChangeEmail_shouldThrowException() {
        CustomerInput customer1 = CustomerInputTestDataBuilder.aCustomer()
                .email("customer1@example.com")
                .build();
        UUID customerId1 = customerManagementApplicationService.create(customer1);

        CustomerInput customer2 = CustomerInputTestDataBuilder.aCustomer()
                .email("customer2@example.com")
                .build();
        customerManagementApplicationService.create(customer2);

        Assertions.assertThatExceptionOfType(CustomerEmailIsInUseException.class)
                .isThrownBy(() -> customerManagementApplicationService.changeEmail(customerId1, customer2.getEmail()));
    }
}
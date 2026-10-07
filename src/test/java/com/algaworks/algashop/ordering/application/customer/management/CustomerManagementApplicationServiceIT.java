package com.algaworks.algashop.ordering.application.customer.management;

import com.algaworks.algashop.ordering.application.customer.notification.CustomerNotificationService;
import com.algaworks.algashop.ordering.domain.model.customer.*;
import com.algaworks.algashop.ordering.infrastructure.listener.customer.CustomerEventListener;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

//12.2. Application Service para casos de uso de Customer - 9'10"

@SpringBootTest
@Transactional //12.7. Implementando caso de uso de atualização de Customer - 13'50" - Anotação para um teste não afetar o outro.
class CustomerManagementApplicationServiceIT {

    @Autowired
    private CustomerManagementApplicationService customerManagementApplicationService;

    //13.7. Garantindo escuta de eventos com Mockito Spy - 30"
    @MockitoSpyBean
    private CustomerEventListener customerEventListener;

    @MockitoSpyBean
    private CustomerNotificationService customerNotificationService;

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

        //13.7. Garantindo escuta de eventos com Mockito Spy -
        Mockito.verify(customerEventListener)
                .listen(Mockito.any(CustomerRegisteredEvent.class));

        Mockito.verify(customerEventListener, Mockito.never())
                .listen(Mockito.any(CustomerArchivedEvent.class));

        Mockito.verify(customerNotificationService)
                .notifyNewRegistration(Mockito.any(CustomerNotificationService.NotifyNewRegistrationInput.class));

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
    void shouldChangeEmail2() {
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

    //12.10. Desafio - Implemente alteração de email para Customer - resolução do Instrutor Alex após meu questionamento no suporte
    @Test
    public void shouldChangeEmail1() {
        CustomerInput input = CustomerInputTestDataBuilder.aCustomer().build();
        UUID customerId = customerManagementApplicationService.create(input);
        String newEmail = "john.doe@newemail.com";

        customerManagementApplicationService.changeEmail(customerId, newEmail);

        CustomerOutput customerOutput = customerManagementApplicationService.findById(customerId);
        Assertions.assertThat(customerOutput.getEmail()).isEqualTo(newEmail);
    }

    @Test
    public void shouldThrowCustomerNotFoundExceptionWhenChangingEmailOfNonExistingCustomer() {
        UUID nonExistingId = UUID.randomUUID();

        Assertions.assertThatExceptionOfType(CustomerNotFoundException.class)
                .isThrownBy(() -> customerManagementApplicationService.changeEmail(nonExistingId, "new@email.com"));
    }

    @Test
    public void shouldThrowCustomerArchivedExceptionWhenChangingEmailOfArchivedCustomer() {
        CustomerInput input = CustomerInputTestDataBuilder.aCustomer().build();
        UUID customerId = customerManagementApplicationService.create(input);
        customerManagementApplicationService.archive(customerId);

        Assertions.assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(() -> customerManagementApplicationService.changeEmail(customerId, "new@email.com"));
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenChangingEmailToInvalidFormat() {
        CustomerInput input = CustomerInputTestDataBuilder.aCustomer().build();
        UUID customerId = customerManagementApplicationService.create(input);

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> customerManagementApplicationService.changeEmail(customerId, "email-invalido"));
    }

    @Test
    public void shouldThrowCustomerEmailIsInUseExceptionWhenChangingEmailToExistingEmail() {
        CustomerInput firstInput = CustomerInputTestDataBuilder.aCustomer().build();
        CustomerInput secondInput = CustomerInputTestDataBuilder.aCustomer()
                .email("janedoe@email.com")
                .build();
        UUID firstCustomerId = customerManagementApplicationService.create(firstInput);
        customerManagementApplicationService.create(secondInput);

        Assertions.assertThatExceptionOfType(CustomerEmailIsInUseException.class)
                .isThrownBy(() -> customerManagementApplicationService.changeEmail(firstCustomerId, secondInput.getEmail()));
    }

}
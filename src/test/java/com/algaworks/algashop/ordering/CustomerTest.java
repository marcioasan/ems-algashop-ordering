package com.algaworks.algashop.ordering;

import com.algaworks.algashop.ordering.domain.entity.Customer;
import com.algaworks.algashop.ordering.domain.exception.CustomerArchivedException;
import com.algaworks.algashop.ordering.domain.utility.IdGenerator;
import com.algaworks.algashop.ordering.domain.valueobject.CustomerId;
import com.algaworks.algashop.ordering.domain.valueobject.FullName;
import com.algaworks.algashop.ordering.domain.valueobject.LoyaltyPoints;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomerTest {

    @Test
    public void testingCustomer() {
        Customer customer = new Customer(
                new CustomerId(),
                new FullName("John", "Doe"),
                LocalDate.of(1991, 7,5),
                "jhon.doe@email.com",
                "478-256-2504",
                "255-08-0578",
                true,
                OffsetDateTime.now()
        );

        System.out.println(customer.id());
        System.out.println(IdGenerator.generateTimeBasedUUID());

        customer.addLoyaltyPoints(new LoyaltyPoints(10));
    }

    @Test
    void given_invalidEmail_whenTryUpdatedCustomerEmail_shouldGenerateException() {
        Customer customer = new Customer(
                new CustomerId(),
                new FullName("Anonymous", "Anonymous"),
                LocalDate.of(1991, 7, 5),
                "john.doe@gmail.com",
                "478-256-2504",
                "255-08-0578",
                false,
                OffsetDateTime.now()
        );

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(()-> {
                    customer.changeEmail("invalid");
                });

//        customer.changeEmail("invalid");
    }

    //5.14. Dando adeus aos getters do JavaBean
    @Test
    void given_unarchivedCustomer_whenArchive_shouldAnonymize() {
        Customer customer = new Customer(
                new CustomerId(),
                new FullName("John", "Doe"),
                LocalDate.of(1991, 7, 5),
                "john.doe@gmail.com",
                "478-256-2504",
                "255-08-0578",
                false,
                OffsetDateTime.now()
        );

        customer.archive();

        Assertions.assertWith(customer,
                c -> assertThat(c.fullName()).isEqualTo(new FullName("Anonymous", "Anonymous")),
                c -> assertThat(c.email()).isNotEqualTo("john.doe@gmail.com"),
                c -> assertThat(c.phone()).isEqualTo("000-000-0000"),
                c -> assertThat(c.document()).isEqualTo("000-00-0000"),
                c -> assertThat(c.birthDate()).isNull(),
                c -> assertThat(c.isPromotionNotificationsAllowed()).isFalse() //5.19. Exceções para regras de negócio - 9'50"
        );

    }

    //5.19. Exceções para regras de negócio - 3'30"
    @Test
    void given_archivedCustomer_whenTryToUpdate_shouldGenerateException() {
        Customer customer = new Customer(
                new CustomerId(),
                new FullName("Anonymous", "Anonymous"),
                null,
                "anonymous@anonymous.com",
                "000-000-0000",
                "000-00-0000",
                false,
                true,
                OffsetDateTime.now(),
                OffsetDateTime.now(),
                new LoyaltyPoints(10)
        );

        Assertions.assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(customer::archive);

        Assertions.assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(()-> customer.changeEmail("email@gmail.com"));

        Assertions.assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(()-> customer.changePhone("123-123-1111"));

        Assertions.assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(customer::enablePromotionNotifications); //5.25. Refatorando as entidades para usar Value Objects - 7'

        Assertions.assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(customer::disablePromotionNotifications);
    }

    @Test
    void given_brandNewCustomer_whenAddLoyaltyPoints_shouldSumPoints() {
        Customer customer = new Customer(
                new CustomerId(),
                new FullName("John", "Doe"),
                LocalDate.of(1991, 7, 5),
                "john.doe@gmail.com",
                "478-256-2504",
                "255-08-0578",
                false,
                OffsetDateTime.now()
        );

        customer.addLoyaltyPoints(new LoyaltyPoints(10));
        customer.addLoyaltyPoints(new LoyaltyPoints(20));

        Assertions.assertThat(customer.loyaltyPoints()).isEqualTo(new LoyaltyPoints(30));
    }

    @Test
    void given_brandNewCustomer_whenAddInvalidLoyaltyPoints_shouldGenerateException() {
        Customer customer = new Customer(
                new CustomerId(),
                new FullName("John", "Doe"),
                LocalDate.of(1991, 7, 5),
                "john.doe@gmail.com",
                "478-256-2504",
                "255-08-0578",
                false,
                OffsetDateTime.now()
        );

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(()-> customer.addLoyaltyPoints(new LoyaltyPoints(0)));

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(()-> customer.addLoyaltyPoints(new LoyaltyPoints(-10)));
    }
}

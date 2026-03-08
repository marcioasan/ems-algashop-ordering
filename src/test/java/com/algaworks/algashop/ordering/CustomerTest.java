package com.algaworks.algashop.ordering;

import com.algaworks.algashop.ordering.domain.entity.Customer;
import com.algaworks.algashop.ordering.domain.utility.IdGenerator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

public class CustomerTest {

    @Test
    public void testingCustomer() {
        Customer customer = new Customer(
                IdGenerator.generateTimeBasedUUID(),
                "Jhon Doe",
                LocalDate.of(1991, 7,5),
                "jhon.doe@email.com",
                "478-256-2504",
                "255-08-0578",
                true,
                OffsetDateTime.now()
        );

        System.out.println(customer.id());
        System.out.println(IdGenerator.generateTimeBasedUUID());

        customer.addLoayltyPoints(10);
    }

    @Test
    void given_invalidEmail_whenTryUpdatedCustomerEmail_shouldGenerateException() {
        Customer customer = new Customer(
                IdGenerator.generateTimeBasedUUID(),
                "John Doe",
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

}

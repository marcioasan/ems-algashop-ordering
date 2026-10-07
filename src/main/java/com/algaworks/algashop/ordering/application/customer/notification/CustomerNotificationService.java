package com.algaworks.algashop.ordering.application.customer.notification;

import java.util.UUID;

//13.8. Reagindo a eventos - No conteúdo de apoio o nome da interface é alterado para CustomerNotificationService

public interface CustomerNotificationService {
    void notifyNewRegistration(NotifyNewRegistrationInput input);

    //13.9. Enriquecendo eventos - 1'30"
    record NotifyNewRegistrationInput(UUID customerId, String firstName, String email) { }
}

package com.algaworks.algashop.ordering.application.customer.notification;

import java.util.UUID;

//13.8. Reagindo a eventos - No conteúdo de apoio o nome da interface é alterado para CustomerNotificationApplicationService

public interface CustomerNotificationApplicationService {
    void notifyNewRegistration(UUID customerId);
}

package com.algaworks.algashop.ordering.infrastructure.notification.customer;

import com.algaworks.algashop.ordering.application.customer.notification.CustomerNotificationApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

//13.8. Reagindo a eventos

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerNotificationApplicationServiceFakeImpl implements CustomerNotificationApplicationService {

    @Override
    public void notifyNewRegistration(NotifyNewRegistrationInput input) { //13.9. Enriquecendo eventos - 2'25"

        log.info("Welcome {}", input.firstName());
        log.info("User your email to access your account {}", input.email());
    }
}

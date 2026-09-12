package com.algaworks.algashop.ordering.domain.model.exception;

//5.19. Exceções para regras de negócio
public class DomainException extends RuntimeException {

    //9.3. Desenvolvendo um Domain Service - 7'20"
    public DomainException() {
    }

    public DomainException(Throwable cause) {
        super(cause);
    }

    public DomainException(String message, Throwable cause) {
        super(message, cause);
    }

    public DomainException(String message) {
        super(message);
    }
}

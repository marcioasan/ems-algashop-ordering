package com.algaworks.algashop.ordering.domain.exception;

//5.19. Exceções para regras de negócio
public class DomainException extends RuntimeException {

    public DomainException(String message, Throwable cause) {
        super(message, cause);
    }

    public DomainException(String message) {
        super(message);
    }
}

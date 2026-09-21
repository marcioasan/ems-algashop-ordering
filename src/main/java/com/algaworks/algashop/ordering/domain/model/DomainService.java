package com.algaworks.algashop.ordering.domain.model;

import java.lang.annotation.*;

//9.8. Usar Annotations do Spring ou não
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DomainService {
}

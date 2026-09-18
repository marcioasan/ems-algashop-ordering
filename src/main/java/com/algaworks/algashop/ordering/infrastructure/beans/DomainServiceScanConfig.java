package com.algaworks.algashop.ordering.infrastructure.beans;

import com.algaworks.algashop.ordering.domain.model.utility.DomainService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

//9.8. Usar Annotations do Spring ou não - 3'
@Configuration
@ComponentScan(
    basePackages = "com.algaworks.algashop.ordering.domain.model",
    includeFilters = @ComponentScan.Filter( //faz com que o Spring só registre os beans anotados com @DomainService
            type = FilterType.ANNOTATION,
            classes = DomainService.class
    )
)
public class DomainServiceScanConfig {
}

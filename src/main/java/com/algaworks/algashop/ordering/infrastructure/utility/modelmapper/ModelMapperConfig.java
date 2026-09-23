package com.algaworks.algashop.ordering.infrastructure.utility.modelmapper;

import com.algaworks.algashop.ordering.application.customer.management.CustomerOutput;
import com.algaworks.algashop.ordering.application.utility.Mapper;
import com.algaworks.algashop.ordering.domain.model.commons.FullName;
import com.algaworks.algashop.ordering.domain.model.customer.BirthDate;
import com.algaworks.algashop.ordering.domain.model.customer.Customer;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.convention.NamingConventions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

//12.5. Mapeadores automáticos com ModelMapper - 3', 6'
//12.6. Customizando mapeamento de propriedades

/** BOA EXPLICAÇÃO SOBRE O USO DA ARQUITETURA
 https://app.algaworks.com/forum/topicos/92608/criacao-da-classe-de-configuracao
 **/

@Configuration
public class ModelMapperConfig {

    private static final Converter<FullName, String> fullNameToFirstNameConverter =
            mappingContext -> {
                FullName fullName = mappingContext.getSource();
                if (fullName == null) {
                    return null;
                }
                return fullName.firstName();
            };

    private static final Converter<FullName, String> fullNameToLastNameConverter =
            mappingContext -> {
                FullName fullName = mappingContext.getSource();
                if (fullName == null) {
                    return null;
                }
                return fullName.lastName();
            };

    private static final Converter<BirthDate, LocalDate> birthDateToLocalDateConverter =
            mappingContext -> {
                BirthDate birthDate = mappingContext.getSource();
                if (birthDate == null) {
                    return null;
                }
                return birthDate.value();
            };

    @Bean
    public Mapper mapper() {
        ModelMapper modelMapper = new ModelMapper();
        configuration(modelMapper);
        return modelMapper::map;
    }

    private void configuration(ModelMapper modelMapper) {
        modelMapper.getConfiguration()
                .setSourceNamingConvention(NamingConventions.NONE)
                .setDestinationNamingConvention(NamingConventions.NONE)
                .setMatchingStrategy(MatchingStrategies.STRICT);

        modelMapper.createTypeMap(Customer.class, CustomerOutput.class)
                .addMappings(mapping ->
                        mapping.using(fullNameToFirstNameConverter)
                                .map(Customer::fullName, CustomerOutput::setFirstName))
                .addMappings(mapping ->
                        mapping.using(fullNameToLastNameConverter)
                                .map(Customer::fullName, CustomerOutput::setLastName))
                .addMappings(mapping ->
                        mapping.using(birthDateToLocalDateConverter)
                                .map(Customer::birthDate, CustomerOutput::setBirthDate));
    }
}

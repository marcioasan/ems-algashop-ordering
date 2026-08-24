package com.algaworks.algashop.ordering.infrastructure.persistence.embeddable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//8.21. Definindo propriedades compostas usando @Embbeded - 8'44"
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class RecipientEmbeddable {
    private String firstName;
    private String lastName;
    private String document;
    private String phone;
}

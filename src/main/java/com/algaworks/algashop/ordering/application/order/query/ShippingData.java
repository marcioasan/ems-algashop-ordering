package com.algaworks.algashop.ordering.application.order.query;

import com.algaworks.algashop.ordering.application.commons.AddressData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

//15.5. Modelo de leitura detalhado para Order

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShippingData {
    private BigDecimal cost;
    private LocalDate expectedDate;
    private RecipientData recipient;
    private AddressData address;
}

package com.algaworks.algashop.ordering.domain.model.order.shipping;

import com.algaworks.algashop.ordering.domain.model.commons.Address;

//10.3. Domain Service para buscar endereço AlgaShop
public interface OriginAddressService {
    Address originAddress();
}

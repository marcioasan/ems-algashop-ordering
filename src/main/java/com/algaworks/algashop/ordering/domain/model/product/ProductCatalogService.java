package com.algaworks.algashop.ordering.domain.model.product;

import java.util.Optional;

//9.6. Domain Service para consulta de Product
public interface ProductCatalogService {
    Optional<Product> ofId(ProductId productId);
}

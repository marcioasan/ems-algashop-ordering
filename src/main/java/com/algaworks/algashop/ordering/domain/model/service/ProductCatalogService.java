package com.algaworks.algashop.ordering.domain.model.service;

import com.algaworks.algashop.ordering.domain.model.valueobject.Product;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.ProductId;

import java.util.Optional;

//9.6. Domain Service para consulta de Product
public interface ProductCatalogService {
    Optional<Product> ofId(ProductId productId);
}

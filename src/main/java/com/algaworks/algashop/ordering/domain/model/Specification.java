package com.algaworks.algashop.ordering.domain.model;

//14.3. Implementando Specification parametrizável
public interface Specification<T> {
    boolean isSatisfiedBy(T t);
}

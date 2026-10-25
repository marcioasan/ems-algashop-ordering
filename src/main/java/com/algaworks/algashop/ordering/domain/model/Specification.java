package com.algaworks.algashop.ordering.domain.model;

//14.3. Implementando Specification parametrizável
public interface Specification<T> {
    boolean isSatisfiedBy(T t);

    //14.4. Implementando Specification composta
    default Specification<T> and(Specification<T> other) {
        return t -> this.isSatisfiedBy(t) && other.isSatisfiedBy(t);
    }

    default Specification<T> or(Specification<T> other) {
        return t -> this.isSatisfiedBy(t) || other.isSatisfiedBy(t);
    }

    default Specification<T> not() {
        return t -> !this.isSatisfiedBy(t);
    }

    default Specification<T> andNot(Specification<T> other) {
        return t -> this.isSatisfiedBy(t) && !other.isSatisfiedBy(t);
    }
}

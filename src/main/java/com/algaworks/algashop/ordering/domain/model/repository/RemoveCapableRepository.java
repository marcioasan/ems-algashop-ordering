package com.algaworks.algashop.ordering.domain.model.repository;

import com.algaworks.algashop.ordering.domain.model.entity.AggregateRoot;
//8.3. Definindo um Repository no Domain Model - 3'20"
public interface RemoveCapableRepository<T extends AggregateRoot<ID>, ID>  extends  Repository<T,ID> {
    void remove(T t);
    void remove(ID id);
}

package com.algaworks.algashop.ordering.application.utility;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

//15.8. Definindo filtros e ordenação

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class SortablePageFilter<T> extends PageFilter {
    private T sortByProperty;
    private Sort.Direction sortDirection;

    //15.9. Filtrando Order por Customer - 5'30"
    public SortablePageFilter(int size, int page) {
        super(size, page);
    }

    public abstract T getSortByPropertyOrDefault();
    public abstract Sort.Direction getSortDirectionOrDefault();
}

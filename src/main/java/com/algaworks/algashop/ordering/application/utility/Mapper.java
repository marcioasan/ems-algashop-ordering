package com.algaworks.algashop.ordering.application.utility;

//12.5. Mapeadores automáticos com ModelMapper - 2'

public interface Mapper {
    <T> T convert(Object object, Class<T> destinationType);
}

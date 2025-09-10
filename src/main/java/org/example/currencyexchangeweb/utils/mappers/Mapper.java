package org.example.currencyexchangeweb.utils.mappers;

public interface Mapper <E, R, D>{
    D convertToDto(E entity);
    E convertToEntity(R dto);
}

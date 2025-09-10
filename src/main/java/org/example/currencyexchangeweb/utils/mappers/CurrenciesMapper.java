package org.example.currencyexchangeweb.utils.mappers;

import org.example.currencyexchangeweb.dto.request.RequestCurrencyDTO;
import org.example.currencyexchangeweb.dto.response.ResponseCurrencyDTO;
import org.example.currencyexchangeweb.entities.Currency;

public final class CurrenciesMapper implements Mapper<Currency, RequestCurrencyDTO, ResponseCurrencyDTO> {

    private static final CurrenciesMapper INSTANCE = new CurrenciesMapper();

    private CurrenciesMapper(){};

    public static CurrenciesMapper getInstance(){
        return INSTANCE;
    }

    @Override
    public ResponseCurrencyDTO convertToDto(Currency entity) {
        return new ResponseCurrencyDTO(
                entity.getCode(),
                entity.getName(),
                entity.getSign()
        );
    }

    @Override
    public Currency convertToEntity(RequestCurrencyDTO dto) {
        var entity = new Currency();

        entity.setCode(dto.code());
        entity.setName(dto.name());
        entity.setSign(dto.sign());

        return entity;
    }
}

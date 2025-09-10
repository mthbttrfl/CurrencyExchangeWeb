package org.example.currencyexchangeweb.utils.mappers;

import org.example.currencyexchangeweb.dto.request.RequestExchangeRateDTO;
import org.example.currencyexchangeweb.dto.response.ResponseExchangeRateDTO;
import org.example.currencyexchangeweb.entities.Currency;
import org.example.currencyexchangeweb.entities.ExchangeRate;

public final class ExchangeRatesMapper implements Mapper<ExchangeRate, RequestExchangeRateDTO, ResponseExchangeRateDTO> {

    private static final ExchangeRatesMapper INSTANCE = new ExchangeRatesMapper();

    private final CurrenciesMapper mapper = CurrenciesMapper.getInstance();

    private ExchangeRatesMapper(){};

    public static ExchangeRatesMapper getInstance(){
        return INSTANCE;
    }

    @Override
    public ResponseExchangeRateDTO convertToDto(ExchangeRate entity) {
        return new ResponseExchangeRateDTO(
                mapper.convertToDto(entity.getBaseCurrency()),
                mapper.convertToDto(entity.getTargetCurrency()),
                entity.getRate()
        );
    }

    @Override
    public ExchangeRate convertToEntity(RequestExchangeRateDTO dto) {
        var entity = new ExchangeRate();

        entity.setBaseCurrency(new Currency(0 , dto.baseCode(), null, null));
        entity.setTargetCurrency(new Currency(0 , dto.targetCode(), null, null));
        entity.setRate(dto.rate());

        return entity;
    }
}

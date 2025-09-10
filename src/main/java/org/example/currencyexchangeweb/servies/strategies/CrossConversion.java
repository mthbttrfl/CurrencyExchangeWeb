package org.example.currencyexchangeweb.servies.strategies;

import org.example.currencyexchangeweb.dto.request.RequestExchangeDTO;
import org.example.currencyexchangeweb.dto.request.RequestExchangeRateDTO;
import org.example.currencyexchangeweb.dto.response.ResponseExchangeDTO;
import org.example.currencyexchangeweb.dto.response.ResponseExchangeRateDTO;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.NoSuchElementException;
import java.util.Optional;

public final class CrossConversion extends StrategiesConversion<RequestExchangeDTO, ResponseExchangeDTO> {

    private final static StrategiesConversion<RequestExchangeDTO, ResponseExchangeDTO> INSTANCE = new CrossConversion();
    private final static String USD = "USD";

    private CrossConversion(){}

    public static StrategiesConversion<RequestExchangeDTO, ResponseExchangeDTO> getInstance(){
        return INSTANCE;
    }

    @Override
    public Optional<ResponseExchangeDTO> converting(RequestExchangeDTO parameter) {
        try{
            BigDecimal amount = parameter.amount();

            RequestExchangeRateDTO baseUSD = new RequestExchangeRateDTO(
                    USD,
                    parameter.exchangeRate().baseCode(),
                    parameter.exchangeRate().rate()
            );

            RequestExchangeRateDTO targetUSD = new RequestExchangeRateDTO(
                    USD,
                    parameter.exchangeRate().targetCode(),
                    parameter.exchangeRate().rate()
            );

            ResponseExchangeRateDTO byCodeBase = serviceExchangeRates.findByCode(baseUSD);
            ResponseExchangeRateDTO byCodeTarget = serviceExchangeRates.findByCode(targetUSD);

            BigDecimal crossRate = byCodeTarget.rate().divide(byCodeBase.rate(), 6, RoundingMode.HALF_UP);

            BigDecimal convertedAmount = amount.multiply(crossRate).setScale(2, RoundingMode.HALF_UP);

            ResponseExchangeRateDTO response = new ResponseExchangeRateDTO(
                    byCodeBase.targetCurrency(),
                    byCodeTarget.targetCurrency(),
                    crossRate
            );

            return Optional.of(new ResponseExchangeDTO(response, amount, convertedAmount));
        }catch (NoSuchElementException ex){
            return Optional.empty();
        }
    }
}

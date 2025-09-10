package org.example.currencyexchangeweb.servies.strategies;

import org.example.currencyexchangeweb.dto.request.RequestExchangeDTO;
import org.example.currencyexchangeweb.dto.request.RequestExchangeRateDTO;
import org.example.currencyexchangeweb.dto.response.ResponseExchangeDTO;
import org.example.currencyexchangeweb.dto.response.ResponseExchangeRateDTO;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.NoSuchElementException;
import java.util.Optional;

public final class ReverseConversion extends StrategiesConversion<RequestExchangeDTO, ResponseExchangeDTO> {

    private final static StrategiesConversion<RequestExchangeDTO, ResponseExchangeDTO> INSTANCE = new ReverseConversion();

    private ReverseConversion(){};

    public static StrategiesConversion<RequestExchangeDTO, ResponseExchangeDTO> getInstance(){
        return INSTANCE;
    }

    @Override
    public Optional<ResponseExchangeDTO> converting(RequestExchangeDTO parameter) {
        try{
            BigDecimal amount = parameter.amount();

            RequestExchangeRateDTO reverseExchangeRate = new RequestExchangeRateDTO(
                    parameter.exchangeRate().targetCode(),
                    parameter.exchangeRate().baseCode(),
                    parameter.exchangeRate().rate()
            );

            ResponseExchangeRateDTO byCode = serviceExchangeRates.findByCode(new RequestExchangeRateDTO(
                    reverseExchangeRate.baseCode(),
                    reverseExchangeRate.targetCode(),
                    reverseExchangeRate.rate())
            );

            BigDecimal reverseRate = BigDecimal.ONE.divide(byCode.rate(), 6, RoundingMode.HALF_UP);

            BigDecimal convertedAmount = amount.multiply(reverseRate).setScale(2, RoundingMode.HALF_UP);

            return Optional.of(new ResponseExchangeDTO(new ResponseExchangeRateDTO(
                    byCode.targetCurrency(),
                    byCode.baseCurrency(),
                    reverseRate),
                    amount, convertedAmount));

        }catch (NoSuchElementException ex){
            return Optional.empty();
        }
    }
}

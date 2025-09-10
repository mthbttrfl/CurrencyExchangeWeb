package org.example.currencyexchangeweb.servies.strategies;

import org.example.currencyexchangeweb.dto.request.RequestExchangeDTO;
import org.example.currencyexchangeweb.dto.request.RequestExchangeRateDTO;
import org.example.currencyexchangeweb.dto.response.ResponseExchangeDTO;
import org.example.currencyexchangeweb.dto.response.ResponseExchangeRateDTO;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.NoSuchElementException;
import java.util.Optional;

public final class DefaultConversion extends StrategiesConversion<RequestExchangeDTO, ResponseExchangeDTO> {

    private final static StrategiesConversion<RequestExchangeDTO, ResponseExchangeDTO> INSTANCE = new DefaultConversion();

    private DefaultConversion(){}

    public static StrategiesConversion<RequestExchangeDTO, ResponseExchangeDTO> getInstance(){
        return INSTANCE;
    }

    @Override
    public Optional<ResponseExchangeDTO> converting(RequestExchangeDTO parameter) {
        try {
            BigDecimal amount = parameter.amount();

            RequestExchangeRateDTO request = new RequestExchangeRateDTO(
                    parameter.exchangeRate().baseCode(),
                    parameter.exchangeRate().targetCode(),
                    parameter.exchangeRate().rate()
            );

            ResponseExchangeRateDTO byCode = serviceExchangeRates.findByCode(request);

            BigDecimal convertedAmount = amount.multiply(byCode.rate()).setScale(2, RoundingMode.HALF_UP);

            return Optional.of(new ResponseExchangeDTO(byCode, amount, convertedAmount));

        } catch (NoSuchElementException ex) {
            return Optional.empty();
        }
    }
}
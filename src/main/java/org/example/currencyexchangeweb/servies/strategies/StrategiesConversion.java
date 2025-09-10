package org.example.currencyexchangeweb.servies.strategies;

import org.example.currencyexchangeweb.dto.request.RequestExchangeRateDTO;
import org.example.currencyexchangeweb.dto.response.ResponseExchangeRateDTO;
import org.example.currencyexchangeweb.servies.ExchangeRatesService;
import org.example.currencyexchangeweb.servies.interfacies.Service;

import java.util.Optional;

public abstract class StrategiesConversion<R, D>{

    protected final Service<RequestExchangeRateDTO, ResponseExchangeRateDTO> serviceExchangeRates = ExchangeRatesService.getInstance();

    public abstract Optional<D> converting(R parameter);
}

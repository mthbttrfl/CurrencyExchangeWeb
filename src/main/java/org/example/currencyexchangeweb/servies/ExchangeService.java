package org.example.currencyexchangeweb.servies;

import org.example.currencyexchangeweb.dto.request.RequestExchangeDTO;
import org.example.currencyexchangeweb.dto.response.ResponseExchangeDTO;
import org.example.currencyexchangeweb.servies.interfacies.ServiceExchange;
import org.example.currencyexchangeweb.servies.strategies.CrossConversion;
import org.example.currencyexchangeweb.servies.strategies.DefaultConversion;
import org.example.currencyexchangeweb.servies.strategies.ReverseConversion;
import org.example.currencyexchangeweb.servies.strategies.StrategiesConversion;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public final class ExchangeService implements ServiceExchange<RequestExchangeDTO, ResponseExchangeDTO> {

    private final static ExchangeService INSTANCE = new ExchangeService();
    
    private final List<StrategiesConversion<RequestExchangeDTO, ResponseExchangeDTO>> strategies = List.of(
            DefaultConversion.getInstance(),
            ReverseConversion.getInstance(),
            CrossConversion.getInstance());

    private ExchangeService(){}

    public static ExchangeService getInstance(){
        return INSTANCE;
    }

    @Override
    public ResponseExchangeDTO exchange(RequestExchangeDTO requestDto) {
        for(StrategiesConversion<RequestExchangeDTO, ResponseExchangeDTO> strategy : strategies){
            Optional<ResponseExchangeDTO> converting = strategy.converting(requestDto);
            if(converting.isPresent()){
                return converting.get();
            }
        }
        throw new NoSuchElementException();
    }
}
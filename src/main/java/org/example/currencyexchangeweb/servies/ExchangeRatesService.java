package org.example.currencyexchangeweb.servies;

import org.example.currencyexchangeweb.dao.repositories.RepositoryExchangeRatesDAO;
import org.example.currencyexchangeweb.dto.request.RequestExchangeRateDTO;
import org.example.currencyexchangeweb.dto.response.ResponseExchangeRateDTO;
import org.example.currencyexchangeweb.entities.ExchangeRate;
import org.example.currencyexchangeweb.servies.interfacies.ServiceExchangeRates;
import org.example.currencyexchangeweb.utils.mappers.ExchangeRatesMapper;
import org.example.currencyexchangeweb.utils.mappers.Mapper;

import java.util.List;

public final class ExchangeRatesService implements ServiceExchangeRates<RequestExchangeRateDTO, ResponseExchangeRateDTO> {

    private final static ExchangeRatesService INSTANCE = new ExchangeRatesService();

    private final RepositoryExchangeRatesDAO dao = RepositoryExchangeRatesDAO.getInstance();
    private final Mapper<ExchangeRate, RequestExchangeRateDTO, ResponseExchangeRateDTO> mapper = ExchangeRatesMapper.getInstance();

    private ExchangeRatesService(){};

    public static ExchangeRatesService getInstance(){
        return INSTANCE;
    }

    @Override
    public List<ResponseExchangeRateDTO> findAll() {
        return dao.findAll().stream().map(mapper::convertToDto).toList();
    }

    @Override
    public ResponseExchangeRateDTO save(RequestExchangeRateDTO requestDto) {
        dao.save(mapper.convertToEntity(requestDto));
        return this.findByCode(requestDto);
    }

    @Override
    public ResponseExchangeRateDTO findByCode(RequestExchangeRateDTO requestDto) {
        return mapper.convertToDto(dao.findByCodes(mapper.convertToEntity(requestDto)).get());
    }

    @Override
    public ResponseExchangeRateDTO update(RequestExchangeRateDTO requestDto) {
        dao.update(mapper.convertToEntity(requestDto));
        return this.findByCode(requestDto);
    }
}
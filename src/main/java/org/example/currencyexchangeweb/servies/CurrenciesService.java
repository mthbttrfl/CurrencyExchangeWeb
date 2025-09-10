package org.example.currencyexchangeweb.servies;

import org.example.currencyexchangeweb.dao.repositories.RepositoryCurrenciesDAO;
import org.example.currencyexchangeweb.dto.request.RequestCurrencyDTO;
import org.example.currencyexchangeweb.dto.response.ResponseCurrencyDTO;
import org.example.currencyexchangeweb.entities.Currency;
import org.example.currencyexchangeweb.servies.interfacies.ServiceCurrencies;
import org.example.currencyexchangeweb.utils.mappers.CurrenciesMapper;
import org.example.currencyexchangeweb.utils.mappers.Mapper;

import java.util.List;

public final class CurrenciesService implements ServiceCurrencies<RequestCurrencyDTO, ResponseCurrencyDTO> {

    private static final CurrenciesService INSTANCE = new CurrenciesService();

    private final RepositoryCurrenciesDAO dao = RepositoryCurrenciesDAO.getInstance();
    private final Mapper<Currency, RequestCurrencyDTO, ResponseCurrencyDTO> mapper = CurrenciesMapper.getInstance();

    private CurrenciesService(){};

    public static CurrenciesService getInstance(){
        return INSTANCE;
    }

    @Override
    public List<ResponseCurrencyDTO> findAll(){
        return dao.findAll().stream().map(mapper::convertToDto).toList();
    }

    @Override
    public ResponseCurrencyDTO save(RequestCurrencyDTO dto) {
        dao.save(mapper.convertToEntity(dto));
        return this.findByCode(dto);
    }

    @Override
    public ResponseCurrencyDTO findByCode(RequestCurrencyDTO dto){
        return mapper.convertToDto(dao.findByCode(mapper.convertToEntity(dto)).get());
    }
}

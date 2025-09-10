package org.example.currencyexchangeweb.servies;

import org.example.currencyexchangeweb.dao.repositories.RepositoryExchangeRatesDAO;
import org.example.currencyexchangeweb.dto.CurrencyDTO;
import org.example.currencyexchangeweb.dto.ExchangeRateDTO;
import org.example.currencyexchangeweb.servies.interfacies.AdminService;

import java.util.List;

public final class AdminExchangeRatesService implements AdminService<Long, ExchangeRateDTO> {

    private static final AdminService<Long, ExchangeRateDTO> INSTANCE = new AdminExchangeRatesService();

    private final RepositoryExchangeRatesDAO dao = RepositoryExchangeRatesDAO.getInstance();

    private AdminExchangeRatesService(){}

    public static AdminService<Long, ExchangeRateDTO> getInstance(){
        return INSTANCE;
    }

    @Override
    public List<ExchangeRateDTO> findAll() {
        return dao.findAll().stream().map(e -> new ExchangeRateDTO(
                e.getId(),
                new CurrencyDTO(
                        e.getBaseCurrency().getId(),
                        e.getBaseCurrency().getCode(),
                        e.getBaseCurrency().getName(),
                        e.getBaseCurrency().getSign()
                ),
                new CurrencyDTO(
                        e.getTargetCurrency().getId(),
                        e.getTargetCurrency().getCode(),
                        e.getTargetCurrency().getName(),
                        e.getTargetCurrency().getSign()
                ),
                e.getRate()
        )).toList();
    }

    @Override
    public boolean delete(Long key) {
        return dao.delete(key);
    }
}

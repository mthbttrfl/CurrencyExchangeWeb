package org.example.currencyexchangeweb.servies;

import org.example.currencyexchangeweb.dao.repositories.RepositoryCurrenciesDAO;
import org.example.currencyexchangeweb.dto.CurrencyDTO;
import org.example.currencyexchangeweb.servies.interfacies.AdminService;

import java.util.List;

public final class AdminCurrenciesService implements AdminService<Long, CurrencyDTO> {

    private static final AdminService<Long, CurrencyDTO> INSTANCE = new AdminCurrenciesService();

    private final RepositoryCurrenciesDAO dao = RepositoryCurrenciesDAO.getInstance();

    private AdminCurrenciesService(){}

    public static AdminService<Long, CurrencyDTO> getInstance(){
        return INSTANCE;
    }

    @Override
    public List<CurrencyDTO> findAll() {
        return dao.findAll().stream().map(c -> new CurrencyDTO(
                c.getId(),
                c.getCode(),
                c.getName(),
                c.getSign())
        ).toList();
    }

    @Override
    public boolean delete(Long key) {
        return dao.delete(key);
    }
}

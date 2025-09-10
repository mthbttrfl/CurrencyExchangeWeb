package org.example.currencyexchangeweb.dao.repositories;


import org.example.currencyexchangeweb.dao.caches.Cache;
import org.example.currencyexchangeweb.dao.caches.CacheCurrency;
import org.example.currencyexchangeweb.entities.Currency;
import org.example.currencyexchangeweb.exeptions.DaoException;
import org.example.currencyexchangeweb.utils.ConnectionManager;
import org.example.currencyexchangeweb.utils.builders.Builder;
import org.example.currencyexchangeweb.utils.builders.CurrencyBuilder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class RepositoryCurrenciesDAO implements CurrenciesDAO {

    private final static RepositoryCurrenciesDAO INSTANCE = new RepositoryCurrenciesDAO();

    private final static Cache<Currency, Long> CACHE = CacheCurrency.getInstance();
    private final static Builder<Currency> BUILDER = CurrencyBuilder.getInstance();

    private final static String SELECT_ALL = "SELECT ID, CODE, NAME, SIGN FROM CURRENCIES";
    private final static String SELECT_BY_ID = "SELECT ID, CODE, NAME, SIGN FROM CURRENCIES WHERE ID = ?";
    private final static String SELECT_BY_CODE = "SELECT ID, CODE, NAME, SIGN FROM CURRENCIES WHERE CODE = ?";
    private final static String DELETE_BY_ID = "DELETE FROM CURRENCIES WHERE ID = ?";
    private final static String INSERT = "INSERT INTO CURRENCIES (CODE, NAME, SIGN) VALUES (?, ?, ?)";
    private final static String UPDATE = "UPDATE CURRENCIES SET CODE = ?, NAME = ?, SIGN = ? WHERE ID = ?";

    private RepositoryCurrenciesDAO(){}

    public static RepositoryCurrenciesDAO getInstance(){
        return INSTANCE;
    }

    @Override
    public List<Currency> findAll() {
        if (CACHE.isLoaded()){
            return CACHE.getAll();
        }
        try(var connection = ConnectionManager.open();
            var statement = connection.prepareStatement(SELECT_ALL)){
            List<Currency> currencies = new ArrayList<>();

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                currencies.add(BUILDER.build(resultSet));
            }

            if (!currencies.isEmpty()){
                CACHE.updateCache(currencies);
            }
            return currencies;
        }catch (SQLException ex){
            throw new DaoException(ex);
        }
    }

    @Override
    public Optional<Currency> findById(Long id) {
        if (CACHE.isLoaded()){
            Optional<Currency> byId = CACHE.getById(id);
            if(byId.isPresent()){
                return byId;
            }
        }
        try(var connection = ConnectionManager.open();
            var statement = connection.prepareStatement(SELECT_BY_ID)) {
            Optional<Currency> currency = Optional.empty();

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()){
                currency = Optional.of(BUILDER.build(resultSet));
            }

            currency.ifPresent(CACHE::updateCache);
            return currency;
        }catch (SQLException ex){
            throw new DaoException(ex);
        }
    }

    @Override
    public Optional<Currency> findByCode(Currency currency) {
        if(CACHE.isLoaded()){
            Optional<Currency> byCode = CACHE.getByCode(currency);
            if(byCode.isPresent()){
                return byCode;
            }
        }
        try(var connection = ConnectionManager.open();
            var statement = connection.prepareStatement(SELECT_BY_CODE)){
            Optional<Currency> entity = Optional.empty();

            statement.setString(1, currency.getCode());
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                entity = Optional.of(BUILDER.build(resultSet));
            }

            entity.ifPresent(CACHE::updateCache);
            return entity;
        }catch (SQLException ex){
            throw new DaoException(ex);
        }
    }

    @Override
    public boolean delete(Long id) {
        try(var connection = ConnectionManager.open();
            var statement = connection.prepareStatement(DELETE_BY_ID)) {
            statement.setLong(1, id);

            int count = statement.executeUpdate();
            if (count > 0){
                CACHE.removeFromCacheById(id);
                return true;
            }
            return false;
        }catch (SQLException ex){
            throw new DaoException(ex);
        }
    }

    @Override
    public void save(Currency currency) {
        try(var connection = ConnectionManager.open();
            var statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)){
            statement.setString(1, currency.getCode());
            statement.setString(2, currency.getName());
            statement.setString(3, currency.getSign());

            int count = statement.executeUpdate();
            if (count > 0){
                ResultSet generatedKey = statement.getGeneratedKeys();
                if (generatedKey.next()){
                    long id = generatedKey.getLong(1);
                    currency.setId(id);
                    CACHE.updateCache(currency);
                }
            }
        }catch (SQLException ex){
            throw new DaoException(ex);
        }
    }

    @Override
    public void update(Currency currency) {
        try(var connection = ConnectionManager.open();
            var statement = connection.prepareStatement(UPDATE)){
            statement.setString(1, currency.getCode());
            statement.setString(2, currency.getName());
            statement.setString(3, currency.getSign());
            statement.setLong(4, currency.getId());

            int count = statement.executeUpdate();
            if (count > 0){
                CACHE.removeFromCacheByCode(currency);
                CACHE.updateCache(currency);
            }
        }catch (SQLException ex){
            throw new DaoException(ex);
        }
    }
}
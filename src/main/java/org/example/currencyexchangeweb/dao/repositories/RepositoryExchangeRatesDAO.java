package org.example.currencyexchangeweb.dao.repositories;

import org.example.currencyexchangeweb.dao.caches.Cache;
import org.example.currencyexchangeweb.dao.caches.CacheExchangeRates;
import org.example.currencyexchangeweb.entities.ExchangeRate;
import org.example.currencyexchangeweb.exeptions.DaoException;
import org.example.currencyexchangeweb.utils.ConnectionManager;
import org.example.currencyexchangeweb.utils.builders.Builder;
import org.example.currencyexchangeweb.utils.builders.ExchangeRateBuilder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class RepositoryExchangeRatesDAO implements ExchangeRatesDAO {

    private final static RepositoryExchangeRatesDAO INSTANCE = new RepositoryExchangeRatesDAO();

    private final static Cache<ExchangeRate, Long> CACHE = CacheExchangeRates.getInstance();
    private final static Builder<ExchangeRate> BUILDER = ExchangeRateBuilder.getInstance();

    private final static String SELECT_ALL = """
            SELECT e.ID,
                cb.ID, cb.CODE, cb.NAME, cb.SIGN,
                ct.ID, ct.CODE, ct.NAME, ct.SIGN,
                e.RATE
            FROM exchangerates e
                JOIN currencies cb ON e.basecurrencyid = cb.id
                JOIN currencies ct ON e.targetcurrencyid = ct.id;
    """;
    private final static String SELECT_BY_ID = """
            SELECT e.ID,
                cb.ID, cb.CODE, cb.NAME, cb.SIGN,
                ct.ID, ct.CODE, ct.NAME, ct.SIGN,
                e.RATE
            FROM exchangerates e
                JOIN currencies cb ON e.basecurrencyid = cb.id
                JOIN currencies ct ON e.targetcurrencyid = ct.id
            WHERE e.ID = ?;
    """;
    private final static String SELECT_BY_BASE_CODE_AND_TARGET_CODE = """
            SELECT e.ID,
                cb.id, cb.CODE, cb.NAME, cb.SIGN,
                ct.ID, ct.CODE, ct.NAME, ct.SIGN,
                e.RATE
            FROM exchangerates e
                JOIN currencies cb ON e.basecurrencyid = cb.id
                JOIN currencies ct ON e.targetcurrencyid = ct.id
            WHERE cb.CODE = ? AND  ct.CODE = ?;
    """;
    private final static String DELETE_BY_ID = "DELETE FROM EXCHANGERATES WHERE ID = ?";
    private final static String INSERT = """
        INSERT INTO EXCHANGERATES(BASECURRENCYID, TARGETCURRENCYID, RATE)
            VALUES (
                (SELECT ID FROM currencies WHERE CODE = ?),
                (SELECT ID FROM currencies WHERE CODE = ?),
                ?);
    """;
    private final static String UPDATE = """
        UPDATE EXCHANGERATES SET RATE = ?
            WHERE ID =
                (SELECT e.ID FROM exchangerates e
                    JOIN currencies cb ON e.basecurrencyid = cb.id
                    JOIN currencies ct ON e.targetcurrencyid = ct.id
                WHERE cb.CODE = ? AND ct.CODE = ?)
        RETURNING id;
    """;

    private RepositoryExchangeRatesDAO(){}

    public static RepositoryExchangeRatesDAO getInstance(){
        return INSTANCE;
    }

    @Override
    public List<ExchangeRate> findAll() {
        if(CACHE.isLoaded()){
            return CACHE.getAll();
        }
        try(var connection = ConnectionManager.open();
            var statement = connection.prepareStatement(SELECT_ALL)) {
            List<ExchangeRate> exchangeRates = new ArrayList<>();
            var resultSet = statement.executeQuery();
            while (resultSet.next()){
                exchangeRates.add(BUILDER.build(resultSet));
            }

            if(!exchangeRates.isEmpty()){
                CACHE.updateCache(exchangeRates);
            }
            return exchangeRates;
        }catch (SQLException ex){
            throw new DaoException(ex);
        }
    }

    @Override
    public Optional<ExchangeRate> findById(Long id) {
        if(CACHE.isLoaded()){
            Optional<ExchangeRate> byId = CACHE.getById(id);
            if(byId.isPresent()){
                return byId;
            }
        }
        try(var connection = ConnectionManager.open();
            var statement = connection.prepareStatement(SELECT_BY_ID)) {
            Optional<ExchangeRate> exchangeRate = Optional.empty();

            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                exchangeRate = Optional.of(BUILDER.build(resultSet));
            }

            exchangeRate.ifPresent(CACHE::updateCache);

            return exchangeRate;
        }catch (SQLException ex){
            throw new DaoException(ex);
        }
    }

    @Override
    public Optional<ExchangeRate> findByCodes(ExchangeRate entity) {
        if(CACHE.isLoaded()){
            Optional<ExchangeRate> byCode = CACHE.getByCode(entity);
            if(byCode.isPresent()){
                return byCode;
            }
        }
        try(var connection = ConnectionManager.open();
            var statement = connection.prepareStatement(SELECT_BY_BASE_CODE_AND_TARGET_CODE)){
            Optional<ExchangeRate> exchangeRate = Optional.empty();

            statement.setString(1, entity.getBaseCurrency().getCode());
            statement.setString(2, entity.getTargetCurrency().getCode());

            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                exchangeRate = Optional.of(BUILDER.build(resultSet));
            }

            exchangeRate.ifPresent(CACHE::updateCache);

            return exchangeRate;
        }catch (SQLException ex){
            throw new DaoException(ex);
        }
    }

    @Override
    public boolean delete(Long id) {
        try(var connection = ConnectionManager.open();
            var statement = connection.prepareStatement(DELETE_BY_ID)){
            statement.setLong(1, id);

            int count = statement.executeUpdate();
            if(count > 0){
                CACHE.removeFromCacheById(id);
                return true;
            }
            return false;
        }catch (SQLException ex){
            throw new DaoException(ex);
        }
    }

    @Override
    public void save(ExchangeRate exchangeRate) {
        try(var connection = ConnectionManager.open();
            var statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)){
            statement.setString(1, exchangeRate.getBaseCurrency().getCode());
            statement.setString(2, exchangeRate.getTargetCurrency().getCode());
            statement.setBigDecimal(3, exchangeRate.getRate());

            statement.executeUpdate();
        }catch (SQLException ex){
            throw new DaoException(ex);
        }
    }

    @Override
    public void update(ExchangeRate exchangeRate) {
        try(var connection = ConnectionManager.open();
            var statement = connection.prepareStatement(UPDATE, Statement.RETURN_GENERATED_KEYS)){
            statement.setBigDecimal(1, exchangeRate.getRate());
            statement.setString(2, exchangeRate.getBaseCurrency().getCode());
            statement.setString(3, exchangeRate.getTargetCurrency().getCode());

            int count = statement.executeUpdate();
            if(count > 0){
                ResultSet generatedKey = statement.getGeneratedKeys();
                if(generatedKey.next()){
                    long id = generatedKey.getLong(1);
                    CACHE.removeFromCacheById(id);
                }
            }
        }catch (SQLException ex){
            throw new DaoException(ex);
        }
    }
}
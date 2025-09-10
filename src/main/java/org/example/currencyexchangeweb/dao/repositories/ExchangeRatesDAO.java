package org.example.currencyexchangeweb.dao.repositories;

import org.example.currencyexchangeweb.entities.ExchangeRate;

import java.util.Optional;

public interface ExchangeRatesDAO extends CrudDAO<Long, ExchangeRate> {
    Optional<ExchangeRate> findByCodes(ExchangeRate entity);
}

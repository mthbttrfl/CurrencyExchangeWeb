package org.example.currencyexchangeweb.dao.repositories;

import org.example.currencyexchangeweb.entities.Currency;

import java.util.Optional;

public interface CurrenciesDAO extends CrudDAO<Long, Currency> {
    Optional<Currency> findByCode(Currency entity);
}

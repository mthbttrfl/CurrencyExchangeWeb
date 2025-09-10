package org.example.currencyexchangeweb.utils.builders;

import org.example.currencyexchangeweb.entities.Currency;
import org.example.currencyexchangeweb.entities.ExchangeRate;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class ExchangeRateBuilder implements Builder<ExchangeRate>{

    private static final ExchangeRateBuilder INSTANCE = new ExchangeRateBuilder();

    private ExchangeRateBuilder() {}

    public static ExchangeRateBuilder getInstance() {
        return INSTANCE;
    }

    @Override
    public ExchangeRate build(ResultSet rs) throws SQLException {
        long idExchangeRate = rs.getLong(1);

        long idBase = rs.getLong(2);
        String codeBase = rs.getString(3);
        String nameBase = rs.getString(4);
        String signBase = rs.getString(5);

        Currency base = new Currency(idBase, codeBase, nameBase, signBase);

        long idTarget = rs.getLong(6);
        String codeTarget = rs.getString(7);
        String nameTarget = rs.getString(8);
        String signTarget = rs.getString(9);

        Currency target = new Currency(idTarget, codeTarget, nameTarget, signTarget);

        BigDecimal rate = rs.getBigDecimal(10);

        return new ExchangeRate(idExchangeRate, base, target, rate);
    }
}

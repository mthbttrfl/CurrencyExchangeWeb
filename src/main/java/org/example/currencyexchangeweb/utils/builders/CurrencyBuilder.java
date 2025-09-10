package org.example.currencyexchangeweb.utils.builders;

import org.example.currencyexchangeweb.entities.Currency;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class CurrencyBuilder implements Builder<Currency> {

    private static final Builder<Currency> INSTANCE = new CurrencyBuilder();

    private CurrencyBuilder(){}

    public static Builder<Currency> getInstance(){
        return INSTANCE;
    }

    @Override
    public Currency build(ResultSet rs) throws SQLException {
        long id = rs.getLong(1);
        String code = rs.getString(2);
        String name = rs.getString(3);
        String sign = rs.getString(4);

        return new Currency(id, code, name, sign);
    }
}

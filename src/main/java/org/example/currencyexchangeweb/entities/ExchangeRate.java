package org.example.currencyexchangeweb.entities;

import java.math.BigDecimal;



public class ExchangeRate {
    private Long id;
    private Currency baseCurrencyId;
    private Currency targetCurrencyId;
    private BigDecimal rate;

    public ExchangeRate(Long id, Currency baseCurrencyId, Currency targetCurrencyId, BigDecimal rate) {
        this.id = id;
        this.baseCurrencyId = baseCurrencyId;
        this.targetCurrencyId = targetCurrencyId;
        this.rate = rate;
    }

    public ExchangeRate() {}

    public void setId(Long id) {
        this.id = id;
    }

    public void setBaseCurrency(Currency baseCurrency) {
        this.baseCurrencyId = baseCurrency;
    }

    public void setTargetCurrency(Currency targetCurrency) {
        this.targetCurrencyId = targetCurrency;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public Long getId() {
        return id;
    }

    public Currency getBaseCurrency() {
        return baseCurrencyId;
    }

    public Currency getTargetCurrency() {
        return targetCurrencyId;
    }

    public BigDecimal getRate() {
        return rate;
    }

    @Override
    public String toString() {
        return "id =" + id +
                ", baseCurrencyId =" + baseCurrencyId +
                ", targetCurrencyId =" + targetCurrencyId +
                ", rate =" + rate;
    }
}

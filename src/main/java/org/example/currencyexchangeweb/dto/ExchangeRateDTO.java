package org.example.currencyexchangeweb.dto;

import java.math.BigDecimal;

public record ExchangeRateDTO(long id, CurrencyDTO baseCurrency, CurrencyDTO targetCurrency, BigDecimal rate){}
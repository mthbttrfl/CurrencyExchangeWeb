package org.example.currencyexchangeweb.dto.response;

import java.math.BigDecimal;

public record ResponseExchangeRateDTO(ResponseCurrencyDTO baseCurrency, ResponseCurrencyDTO targetCurrency, BigDecimal rate) {}

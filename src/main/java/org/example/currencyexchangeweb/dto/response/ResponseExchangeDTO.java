package org.example.currencyexchangeweb.dto.response;

import java.math.BigDecimal;

public record ResponseExchangeDTO(ResponseExchangeRateDTO exchangeRate, BigDecimal amount, BigDecimal convertedAmount) {}
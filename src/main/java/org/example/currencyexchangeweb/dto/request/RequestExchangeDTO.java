package org.example.currencyexchangeweb.dto.request;

import java.math.BigDecimal;

public record RequestExchangeDTO(RequestExchangeRateDTO exchangeRate, BigDecimal amount) {}
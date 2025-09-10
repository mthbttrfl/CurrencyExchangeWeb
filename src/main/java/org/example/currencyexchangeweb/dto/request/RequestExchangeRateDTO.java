package org.example.currencyexchangeweb.dto.request;

import java.math.BigDecimal;

public final class RequestExchangeRateDTO {
    private final static BigDecimal STUB = BigDecimal.ONE; // необходимо для прохождения валидации на пустоты

    private final String baseCode;
    private final String targetCode;
    private final BigDecimal rate;

    public RequestExchangeRateDTO(String baseCode, String targetCode, BigDecimal rate) {
        this.baseCode = baseCode;
        this.targetCode = targetCode;
        this.rate = rate;
    }

    public RequestExchangeRateDTO(String baseCode, String targetCode) {
        this(baseCode, targetCode, STUB);
    }

    public String baseCode() {
        return baseCode;
    }

    public String targetCode() {
        return targetCode;
    }

    public BigDecimal rate() {
        return rate;
    }
}
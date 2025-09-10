package org.example.currencyexchangeweb.dto.request;

public final class RequestCurrencyDTO {
    private final static String STUB = "AAA"; // необходимо для прохождения валидации на пустоты

    private final String code;
    private final String name;
    private final String sign;

    public RequestCurrencyDTO(String code, String name, String sign) {
        this.code = code;
        this.name = name;
        this.sign = sign;
    }

    public RequestCurrencyDTO(String code) {
        this(code, STUB, STUB);
    }

    public String code() {
        return code;
    }

    public String name() {
        return name;
    }

    public String sign() {
        return sign;
    }
}
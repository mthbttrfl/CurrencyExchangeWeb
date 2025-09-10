package org.example.currencyexchangeweb.servies.interfacies;

public interface ServiceExchangeRates <R, D> extends Service<R, D> {
    D update(R requestDto);
}

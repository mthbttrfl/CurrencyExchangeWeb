package org.example.currencyexchangeweb.servies.interfacies;

public interface ServiceExchange <R, D>{
    D exchange(R requestDto);
}

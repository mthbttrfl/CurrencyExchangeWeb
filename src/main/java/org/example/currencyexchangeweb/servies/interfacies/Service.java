package org.example.currencyexchangeweb.servies.interfacies;

import java.util.List;

public interface Service<R, D>{
    List<D> findAll();
    D save(R requestDto);
    D findByCode(R requestDto);
}

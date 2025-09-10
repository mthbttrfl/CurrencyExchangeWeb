package org.example.currencyexchangeweb.servies.interfacies;

import java.util.List;

public interface AdminService <K, D>{
    List<D> findAll();
    boolean delete(K key);
}

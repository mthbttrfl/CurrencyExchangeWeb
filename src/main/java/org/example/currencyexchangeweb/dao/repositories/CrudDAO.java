package org.example.currencyexchangeweb.dao.repositories;

import java.util.List;
import java.util.Optional;

public interface CrudDAO<K, E>{
    List<E> findAll();
    Optional<E> findById(K key);
    boolean delete(K key);
    void save(E entity);
    void update(E entity);
}

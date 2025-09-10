package org.example.currencyexchangeweb.dao.caches;

import java.util.List;
import java.util.Optional;

public interface Cache<E, K>{
    List<E> getAll();
    Optional<E> getByCode(E entity);
    Optional<E> getById(K key);
    boolean isLoaded();
    void updateCache(List<E> entities);
    void updateCache(E entity);
    void removeFromCacheByCode(E entity);
    void removeFromCacheById(K key);
}

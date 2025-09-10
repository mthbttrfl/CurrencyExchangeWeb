package org.example.currencyexchangeweb.dao.caches;

import org.example.currencyexchangeweb.entities.Currency;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public final class CacheCurrency implements Cache<Currency, Long> {

    private static final Cache<Currency, Long> INSTANCE = new CacheCurrency();

    private static final Map<Long, Currency> CACHE_BY_ID = new ConcurrentHashMap<>();
    private static final Map<String, Currency> CACHE_BY_CODE = new ConcurrentHashMap<>();

    private static volatile boolean cacheLoaded = false;

    private CacheCurrency(){}

    public static Cache<Currency, Long> getInstance(){
        return INSTANCE;
    }

    @Override
    public List<Currency> getAll() {
        return List.copyOf(CACHE_BY_CODE.values());
    }

    @Override
    public Optional<Currency> getByCode(Currency entity) {
        if(CACHE_BY_CODE.containsKey(entity.getCode())){
            return Optional.of(CACHE_BY_CODE.get(entity.getCode()));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Currency> getById(Long key) {
        if (CACHE_BY_ID.containsKey(key)){
            return Optional.of(CACHE_BY_ID.get(key));
        }
        return Optional.empty();
    }

    @Override
    public boolean isLoaded() {
        return cacheLoaded;
    }

    @Override
    public void updateCache(List<Currency> entities) {
        entities.forEach(this::updateCache);
        cacheLoaded = true;
    }

    @Override
    public void updateCache(Currency entity) {
        CACHE_BY_ID.put(entity.getId(), entity);
        CACHE_BY_CODE.put(entity.getCode(), entity);
    }

    @Override
    public void removeFromCacheById(Long key) {
        if (CACHE_BY_ID.containsKey(key)){
            Currency currency = CACHE_BY_ID.get(key);
            CACHE_BY_ID.remove(key);
            if(CACHE_BY_CODE.containsKey(currency.getCode())){
                CACHE_BY_CODE.remove(currency.getCode());
            }
        }
    }

    @Override
    public void removeFromCacheByCode(Currency entity) {
        if (CACHE_BY_ID.containsKey(entity.getId())){
            Currency currency = CACHE_BY_ID.get(entity.getId());
            CACHE_BY_ID.remove(entity.getId());
            if(CACHE_BY_CODE.containsKey(currency.getCode())){
                CACHE_BY_CODE.remove(currency.getCode());
            }
        }
    }
}

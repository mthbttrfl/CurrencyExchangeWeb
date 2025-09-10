package org.example.currencyexchangeweb.dao.caches;

import org.example.currencyexchangeweb.entities.ExchangeRate;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public final class CacheExchangeRates implements Cache<ExchangeRate, Long> {

    private static final Cache<ExchangeRate, Long> INSTANCE = new CacheExchangeRates();

    private static final Map<String, ExchangeRate> CACHE_BY_CODES = new ConcurrentHashMap<>();
    private static final Map<Long, ExchangeRate> CACHE_BY_ID = new ConcurrentHashMap<>();

    private static volatile boolean cacheLoaded = false;

    private CacheExchangeRates(){}

    public static Cache<ExchangeRate, Long> getInstance(){
        return INSTANCE;
    }

    @Override
    public List<ExchangeRate> getAll() {
        return List.copyOf(CACHE_BY_CODES.values());
    }

    @Override
    public Optional<ExchangeRate> getByCode(ExchangeRate entity) {
        String key = buildKeyByCodes(entity);
        if(CACHE_BY_CODES.containsKey(key)){
            return Optional.of(CACHE_BY_CODES.get(key));
        }
        return Optional.empty();
    }

    @Override
    public Optional<ExchangeRate> getById(Long key) {
        if(CACHE_BY_ID.containsKey(key)){
            return Optional.of(CACHE_BY_ID.get(key));
        }
        return Optional.empty();
    }

    @Override
    public boolean isLoaded() {
        return cacheLoaded;
    }

    @Override
    public void updateCache(List<ExchangeRate> entities) {
        entities.forEach(this::updateCache);
        cacheLoaded = true;
    }

    @Override
    public void updateCache(ExchangeRate entity) {
        CACHE_BY_ID.put(entity.getId(), entity);
        CACHE_BY_CODES.put(buildKeyByCodes(entity), entity);
    }

    @Override
    public void removeFromCacheById(Long key) {
        if (CACHE_BY_ID.containsKey(key)){
            String keyCodes = buildKeyByCodes(CACHE_BY_ID.get(key));
            CACHE_BY_ID.remove(key);
            if (CACHE_BY_CODES.containsKey(keyCodes)){
                CACHE_BY_CODES.remove(keyCodes);
            }
        }
    }

    @Override
    public void removeFromCacheByCode(ExchangeRate entity) {
        String keyCodes = buildKeyByCodes(entity);
        if(CACHE_BY_CODES.containsKey(keyCodes)){
            Long keyId = CACHE_BY_CODES.get(keyCodes).getId();
            CACHE_BY_CODES.remove(keyCodes);
            if (CACHE_BY_ID.containsKey(keyId)){
                CACHE_BY_ID.remove(keyId);
            }
        }
    }

    private String buildKeyByCodes(ExchangeRate entity){
        return entity.getBaseCurrency().getCode() + entity.getTargetCurrency().getCode();
    }
}

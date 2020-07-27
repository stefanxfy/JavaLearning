package com.stefan.test;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Favorites {
    private Map<Class<?>, Object> favorites = new HashMap<>();
    public <T> void put(Class<T> type, T instance) {
        favorites.put(type, instance);
        Objects.requireNonNull(type);
    }
    public <T> T get(Class<T> type) {
        return type.cast(favorites.get(type));
    }

    public <T> void putBatch(Class<T> type, T ... instances) {
        favorites.put(type, instances);
    }

}

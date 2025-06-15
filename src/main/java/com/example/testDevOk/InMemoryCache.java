
// src/main/java/com/example/InMemoryCache.java (Một implementation)
package com.example.testDevOk;
import com.example.interfaces.ICache;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryCache<K, V> implements ICache<K, V> {
    private final Map<K, V> store = new HashMap<>();
    @Override public void put(K key, V value) { store.put(key, value); }
    @Override public Optional<V> get(K key) { return Optional.ofNullable(store.get(key)); }
    @Override public void clear(K key) { store.remove(key); }
}
// src/main/java/com/example/Cache.java (Interface)
package com.example.interfaces;
import java.util.Optional;

public interface ICache<K, V> {
    void put(K key, V value);
    Optional<V> get(K key);
    void clear(K key);
}

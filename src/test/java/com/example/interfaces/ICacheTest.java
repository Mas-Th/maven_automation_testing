// src/test/java/com/example/CacheTest.java (Interface Test)
package com.example.interfaces;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Kiểm tra hợp đồng của Cache")
public interface ICacheTest<K, V> {

    // Phương thức trừu tượng mà các lớp test cụ thể phải implement
    // để cung cấp phiên bản cache cần test
    ICache<K, V> createCache();

    K getKey();
    V getValue();

    @Test
    @DisplayName("Lưu và lấy lại giá trị thành công")
    default void testPutAndGet() {
        ICache<K, V> cache = createCache();
        cache.put(getKey(), getValue());

        assertTrue(cache.get(getKey()).isPresent());
        assertEquals(getValue(), cache.get(getKey()).get());
    }

    @Test
    @DisplayName("Xóa một giá trị khỏi cache")
    default void testClear() {
        ICache<K, V> cache = createCache();
        cache.put(getKey(), getValue());
        assertTrue(cache.get(getKey()).isPresent());

        cache.clear(getKey());
        assertFalse(cache.get(getKey()).isPresent());
    }
}


// src/test/java/com/example/InMemoryCacheTest.java (Test cho implementation cụ thể)
package com.example.testDevOk;

import com.example.interfaces.ICache;
import com.example.interfaces.ICacheTest;

// Lớp này chỉ cần implements interface test và cung cấp dữ liệu
class InMemoryCacheTest implements ICacheTest<String, String> {
    @Override
    public ICache<String, String> createCache() {
        return new InMemoryCache<>();
    }

    @Override
    public String getKey() { return "test-key"; }

    @Override
    public String getValue() { return "test-value"; }
}
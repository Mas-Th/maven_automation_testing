package com.web.product;



// ✅ Đây là file độc lập: ProductEntity.java
public record ProductEntity(Long id, String name, String code) {
    public  Long getId() {
        return id;
    }

    public Object orElseThrow(Object o) {
        return o;
    }
}


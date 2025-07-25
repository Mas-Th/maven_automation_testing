package com.web.order;

public record OrderEntity(Long id, String username, String code, int idProduct) {
    public Long getId() {
        return id;
    }
}



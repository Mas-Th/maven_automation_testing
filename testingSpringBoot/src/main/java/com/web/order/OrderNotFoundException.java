package com.web.order;

import com.web.exception.EntityNotFoundException;

public class OrderNotFoundException extends EntityNotFoundException {
    public OrderNotFoundException(Long id) {
        super("Order", id);
    }
}
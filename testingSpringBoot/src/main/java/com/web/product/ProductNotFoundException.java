package com.web.product;

import com.web.exception.EntityNotFoundException;

public class ProductNotFoundException extends EntityNotFoundException {
    public ProductNotFoundException(Long id) {
        super("Product", id);
    }
}

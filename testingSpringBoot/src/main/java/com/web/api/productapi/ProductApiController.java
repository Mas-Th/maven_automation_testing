package com.web.api.productapi;


import com.web.product.ProductEntity;
import com.web.product.ProductNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/products")
public class ProductApiController {
    List<ProductEntity> products = List.of(
            new ProductEntity(1L, "iPhone", "IP1223"),
            new ProductEntity(2L, "ipad", "IP143"),
            new ProductEntity(3L, "ss", "IP1233"),
            new ProductEntity(4L, "mac", "IP124")

    );
    //  GET /products
    @GetMapping
    public List<ProductEntity> getProducts() {

        return products.isEmpty() ? Collections.emptyList() : products;
    }

    // ID product: GET /product/{id}
    @GetMapping("/{id}")
    public Object productById(@PathVariable Long id) {

        return products.stream()
                .filter(u -> Objects.equals(u.getId(), id))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException(id));
    }


}

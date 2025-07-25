package com.web.product;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
public class ProductController {
    @GetMapping
    public String products(Model model) {
        model.addAttribute("a");
        return "products/index";

    }

    @GetMapping("/detail")
    public String productsDetail(Model model) {
        model.addAttribute("san pham 1");
        return "products/detail";

    }

    @GetMapping("/detail/{id}")
    public String productsDetailId(Model model, @PathVariable Long id  ) {
        var product = new ProductEntity(id, "phone", "123ss");
        model.addAttribute("product", product);
        return "products/detail";

    }

    // crete product
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Object addProduct(@Validated @RequestBody ProductEntity product) {
        return "Ok" + product;
    }

    @PutMapping
    public Object updateProduct(@RequestBody ProductEntity product) {
        return product;
    }

    @PatchMapping
    public Object patchProduct(@RequestBody ProductEntity product) {
        return product;
    }

    @DeleteMapping("/{id}")
    public Object deleteProduct(@PathVariable Long id) {

        return id;
    }

}

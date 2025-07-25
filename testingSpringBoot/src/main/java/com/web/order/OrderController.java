package com.web.order;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;


@Controller
@RequestMapping("/orders")
public class OrderController {
    List<OrderEntity> orders = List.of(
            new OrderEntity(1L, "iPhone", "IP1223",10),
            new OrderEntity(2L, "ipad", "IP143",11),
            new OrderEntity(3L, "ss", "IP1233",12),
            new OrderEntity(4L, "mac", "IP124",13)

    );


    @GetMapping
    public String order(Model model) {
        model.addAttribute("orders", orders);
        return "orders/index";
    }


    @GetMapping("/{id}")
    public Object orderById(Model model, @PathVariable Long id) {
        var order = orders.stream()
                .filter(u -> Objects.equals(u.getId(), id))
                .findFirst()
                .orElseThrow(() -> new OrderNotFoundException(id));
        model.addAttribute("order", order);
        return "orders/detail";
    }
    // crete order
    @PostMapping
    public Object addOrder(@Validated @RequestBody OrderEntity order) {
        return order;
    }

    @PutMapping
    public Object updateOrder(@RequestBody OrderEntity order) {
        return order;
    }


    @PatchMapping
    public Object patchPOrder(@RequestBody OrderEntity order) {
        return order;
    }

    @DeleteMapping("/{id}")
    public Object deleteOrder(@PathVariable Long id) {
        return "Ok" + id;
    }
}

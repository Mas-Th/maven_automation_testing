package com.web.api.orderapi;

import com.web.order.OrderEntity;
import com.web.order.OrderNotFoundException;
import org.springframework.web.bind.annotation.*;



import java.util.Collections;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/orders")
public class OrderApiController {

    List<OrderEntity> orders = List.of(
            new OrderEntity(1L, "iPhone", "IP1223",10),
            new OrderEntity(2L, "ipad", "IP143",11),
            new OrderEntity(3L, "ss", "IP1233",12),
            new OrderEntity(4L, "mac", "IP124",13)

    );
    //  GET /orders
    @GetMapping
    public List<OrderEntity> orders() {
        return orders.isEmpty() ? Collections.emptyList() : orders;
    }

}

package com.swiggy.order.controllers;

import com.swiggy.order.models.requests.OrderRequest;
import com.swiggy.order.models.responses.OrderResponse;
import com.swiggy.order.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @PostMapping("")
    ResponseEntity<String> create(@RequestBody OrderRequest orderRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.create(orderRequest.getRestaurantId(), orderRequest.getItemIds()));
    }

    @GetMapping("/{id}")
    ResponseEntity<OrderResponse> fetch(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.fetch(id));
    }
}

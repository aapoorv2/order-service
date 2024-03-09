package com.swiggy.order.controllers;

import com.swiggy.order.exceptions.OrderNotFoundException;
import com.swiggy.order.models.requests.OrderRequest;
import com.swiggy.order.models.responses.OrderResponse;
import com.swiggy.order.services.OrderService;
import io.grpc.StatusRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @PostMapping("")
    ResponseEntity<String> create(@RequestBody OrderRequest orderRequest) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(orderService.create(orderRequest.getRestaurantId(), orderRequest.getItemIds()));
        } catch (StatusRuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("")
    ResponseEntity<String> deliver(@RequestBody Long id) {
        return ResponseEntity.ok().body(orderService.deliver(id));
    }
    @GetMapping("/{id}")
    ResponseEntity<?> fetch(@PathVariable Long id) {
        try {
            OrderResponse response = orderService.fetch(id);
            return ResponseEntity.ok().body(response);
        } catch (OrderNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("")
    ResponseEntity<List<OrderResponse>> fetchAll() {
        return ResponseEntity.ok().body(orderService.fetchAll());
    }
}

package com.swiggy.order.services;

import com.swiggy.order.entities.Order;
import com.swiggy.order.models.dto.ItemDTO;
import com.swiggy.order.models.responses.OrderResponse;
import com.swiggy.order.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import com.swiggy.order.exceptions.OrderNotFoundException;

@Service
public class OrderService {

    @Autowired
    private CatalogService catalogService;

    @Autowired
    private OrderRepository orderRepository;

    public String create(Long restaurantId, List<Long> itemIds) {

        List<ItemDTO> items = itemIds.stream()
                .map(itemId -> catalogService.getItemById(restaurantId, itemId))
                .collect(Collectors.toList());

        Order order = new Order(items);
        orderRepository.save(order);
        return "Created an order with id:" + order.getId();
    }

    public OrderResponse fetch(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException("Order with id " + id + " not found"));
        return new OrderResponse(id, order.getItems(), order.getTotalPrice(), order.getStatus());
    }
}

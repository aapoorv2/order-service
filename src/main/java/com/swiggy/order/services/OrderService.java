package com.swiggy.order.services;

import com.swiggy.order.entities.Order;
import com.swiggy.order.entities.User;
import com.swiggy.order.exceptions.UserNotFoundException;
import com.swiggy.order.models.dto.ItemDTO;
import com.swiggy.order.models.responses.OrderResponse;
import com.swiggy.order.repositories.OrderRepository;
import com.swiggy.order.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.swiggy.order.exceptions.OrderNotFoundException;

@Service
public class OrderService {

    @Autowired
    private CatalogService catalogService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;


    public String create(Long restaurantId, List<Long> itemIds) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow();
        List<ItemDTO> items = itemIds.stream()
                .map(itemId -> catalogService.getItemById(restaurantId, itemId))
                .collect(Collectors.toList());

        Order order = new Order(user, items);
        orderRepository.save(order);
        order.assignAgent();
        orderRepository.save(order);
        return "Created an order with id: " + order.getId();
    }

    public OrderResponse fetch(Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found"));
        Order order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException("Order with id " + id + " not found"));
        if (order.getUser() != user) {
            throw new OrderNotFoundException("Order with id " + id + " not found");
        }
        return new OrderResponse(id, order.getItems(), order.getTotalPrice(), order.getStatus());
    }
    public List<OrderResponse> fetchAll() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found"));
        List<Order> orders = orderRepository.findByUser(user);
        List<OrderResponse> responses = new ArrayList<>();
        for (Order order : orders) {
            responses.add(new OrderResponse(order.getId(), order.getItems(), order.getTotalPrice(), order.getStatus()));
        }
        return responses;
    }

    public String deliver(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException("Order with id " + id + " not found"));
        order.deliver();
        orderRepository.save(order);
        return "Successfully delivered the order!";
    }
}

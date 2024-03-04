package com.swiggy.order.repositories;

import com.swiggy.order.entities.Order;
import com.swiggy.order.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);
}

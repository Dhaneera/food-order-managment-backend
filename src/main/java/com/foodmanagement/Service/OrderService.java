package com.foodmanagement.Service;

import com.foodmanagement.Entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface OrderService {
    Order placeOrder(Order order);
    Page<Order>getAllOrders(Pageable pageable);
    Optional<Order> getOrderById(Long id);
}

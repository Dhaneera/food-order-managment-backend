package com.foodmanagement.Service;

import com.foodmanagement.Entity.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface OrderService {
    Orders placeOrder(Orders order);
    Page<Orders>getAllOrders(Pageable pageable);
    Optional<Orders> getOrderById(UUID id);
    Page<Orders> getOrdersByStatus(String status, Pageable pageable);
}

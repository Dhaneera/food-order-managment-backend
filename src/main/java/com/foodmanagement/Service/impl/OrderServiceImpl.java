package com.foodmanagement.Service.impl;

import com.foodmanagement.Entity.Orders;
import com.foodmanagement.Repository.OrderRepository;
import com.foodmanagement.Service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private  final OrderRepository orderRepository;

    @Override
    public Orders placeOrder(Orders order) {
        order.setId(generateUUID());
        return orderRepository.save(order);
    }

    @Override
    public Page<Orders> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    @Override
    public Optional<Orders> getOrderById(UUID id) {
        return orderRepository.findById(id);
    }

    @Override
    public Page<Orders> getOrdersByStatus(String status, Pageable pageable) {
        return orderRepository.findByStatus(status, pageable);
    }

    private UUID generateUUID() {
        return java.util.UUID.randomUUID(); // Generates a String-based ID
    }

}

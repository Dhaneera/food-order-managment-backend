package com.foodmanagement.Service.impl;

import com.foodmanagement.Entity.Order;
import com.foodmanagement.Repository.OrderRepository;
import com.foodmanagement.Service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private  final OrderRepository orderRepository;

    @Override
    public Order placeOrder(Order order) {
        order.setId(generateUUID());
        return orderRepository.save(order);
    }



    @Override
    public Page<Order> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    @Override
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    private String generateUUID() {
        return java.util.UUID.randomUUID().toString(); // Generates a String-based ID
    }

}

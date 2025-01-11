package com.foodmanagement.Service.impl;

import com.foodmanagement.Entity.Meal;
import com.foodmanagement.Entity.Orders;
import com.foodmanagement.Repository.MealRepository;
import com.foodmanagement.Repository.OrderRepository;
import com.foodmanagement.Service.OrderService;
import com.foodmanagement.dto.MealDto;
import com.foodmanagement.dto.OrdersDto;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final MealRepository mealRepository;

    @Override
    public Meal placeOrder(OrdersDto order) {
        final UUID id=generateUUID();

        Orders orders = new Orders();
        Orders savedOrder= null;
        orders.setId(id);
        orders.setName(order.getName());
        orders.setPrice(order.getPrice());
        orders.setOrderedAt(order.getOrderedAt());
        orders.setCreatedAt(order.getCreatedAt());
        orders.setStatus(order.getStatus());
        orders.setCreatedBy(order.getCreatedBy());
        try {
            savedOrder = orderRepository.save(orders);
        }catch (Exception exception){
//            return new ResponseEntity<?>(exception.getMessage()+"order could not be placed", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(Objects.nonNull(savedOrder)){
            try {
                HashMap<String, MealDto> meals = order.getMeals();
                meals.forEach((key, value) -> {
                    Meal meal = new Meal();
                    meal.setId(0);
                    meal.setType(key);
                    meal.setOrderId(id);
                    meal.setStatus(value.getStatus());
                    meal.setCount(value.getCount());
                    value.setId(0);
                    mealRepository.save(meal);
                });

            }catch (DataAccessException exception){
                orderRepository.deleteById(id);
            }
        }
        return null;
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

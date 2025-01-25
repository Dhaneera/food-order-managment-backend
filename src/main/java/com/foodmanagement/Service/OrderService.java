package com.foodmanagement.Service;

import com.foodmanagement.Entity.Meal;
import com.foodmanagement.Entity.Orders;
import com.foodmanagement.dto.OrdersDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface OrderService {
    Meal placeOrder(OrdersDto order);
    Page<Orders>getAllOrders(Pageable pageable);
    Optional<Orders> getOrderById(String id);
    Page<Orders> getOrdersByStatus(String status, Pageable pageable);
     Page<Orders>getOrdersCreatedBy(String CreatedBy, Pageable pageable);
     int getCountOrderByType(String orderAt , String type);

}

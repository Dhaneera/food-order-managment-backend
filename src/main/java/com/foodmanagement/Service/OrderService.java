package com.foodmanagement.Service;

import com.foodmanagement.Entity.Orders;
import com.foodmanagement.dto.OrdersDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public interface OrderService {
    List<String> placeOrder(OrdersDto order);
    Page<Orders>getAllOrders(Pageable pageable);
    Optional<Orders> getOrderById(String id);
    Page<Orders> getOrdersByStatus(String status, Pageable pageable);
     Page<Orders>getOrdersCreatedBy(String CreatedBy, Pageable pageable);
     HashMap<String, Integer> getCountOrderByType(String orderAt , String type);

}

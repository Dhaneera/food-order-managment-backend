package com.foodmanagement.Service.impl;

import com.foodmanagement.Entity.Meal;
import com.foodmanagement.Entity.Orders;
import com.foodmanagement.Repository.MealRepository;
import com.foodmanagement.Repository.NativeOrderRepository;
import com.foodmanagement.Repository.OrderRepository;
import com.foodmanagement.Service.OrderService;
import com.foodmanagement.dto.MealDto;
import com.foodmanagement.dto.OrdersDto;
import com.foodmanagement.dto.PaginatedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.*;

import static com.foodmanagement.util.RandomIdGenerator.createOrderId;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final MealRepository mealRepository;
    private final NativeOrderRepository nativeOrderRepository;


    @Override
    public List<String> placeOrder(OrdersDto order) {
        if(ObjectUtils.isEmpty(order.getCreatedBy())){
            return null;
        }
        Map<String,String> mapOfIds = createOrderId();
        order.setId(mapOfIds.get("orderId"));
        List<String> listOfStrings = new ArrayList<>();


        Orders orders = new Orders();
        Orders savedOrder= null;
        orders.setId(order.getId());
        System.out.println(orders);
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
                    if(key.equalsIgnoreCase("breakfast")) {
                        meal.setId(mapOfIds.get("mealBreakfast") + value.getCount());
                    } else if (key.equalsIgnoreCase("lunch")) {
                        meal.setId(mapOfIds.get("mealLunch") + value.getCount());
                    } else if (key.equalsIgnoreCase("dinner")) {
                        meal.setId(mapOfIds.get("mealDinner") + value.getCount());
                    }
                    meal.setType(key);
                    meal.setOrderId(orders.getId());
                    meal.setStatus(value.getStatus());
                    meal.setCount(value.getCount());
                    value.setId(0);
                    if(meal.getCount()>0) {
                        mealRepository.save(meal);
                        listOfStrings.add(meal.getId());
                    }
                });

            }catch (DataAccessException exception){
                orderRepository.deleteById(mapOfIds.get("orderId"));
            }
        }


        return listOfStrings ;
    }

    @Override
    public Page<Orders> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    @Override
    public Optional<Orders> getOrderById(String id) {
        return orderRepository.findById(id);
    }

    @Override
    public Page<Orders> getOrdersByStatus(String status, Pageable pageable) {
        return orderRepository.findByStatus(status, pageable);
    }

    @Override
    public PaginatedResponse<?> getOrdersCreatedBy(String createdBy, Pageable pageable){
        return nativeOrderRepository.getAllOrders(createdBy,pageable.getPageNumber());

    }

    @Override
    public HashMap<String, Integer> getCountOrderByType(String orderAt, String type) {
        int effectedRowCount=orderRepository.findRowCountByTypeAndOrderedAt(orderAt, type);
        int pendingRowCount=orderRepository.findRowCountByTypeAndOrderedAtWhichIsPending(orderAt,type);
        HashMap<String,Integer> map = new HashMap<>();
        map.put("total",effectedRowCount);
        map.put("pending",pendingRowCount);
        return map;
    }

    @Override
    public boolean changeOrderStatus(String localDate) {
       orderRepository.changeOrderStatusByDate(localDate);
       return true;
    }


    private String generateMealId(String orderId, int count) {
        String countFormatted = String.format("%03d", count);
        return orderId + "BR" + countFormatted; // Concatenate Order ID, BR, and Count
    }


}

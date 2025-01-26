package com.foodmanagement.Controller;

import com.foodmanagement.Entity.Meal;
import com.foodmanagement.Entity.Orders;
import com.foodmanagement.Service.OrderService;
import com.foodmanagement.dto.OrdersDto;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@CrossOrigin
public class OrderController {

    private final OrderService orderService;


    @PostMapping("/create")
    public ResponseEntity placeOrder(@RequestBody OrdersDto order) {
        List<String> list = orderService.placeOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(list);
    }

    @GetMapping("/cookies")
    public String getOrders(HttpServletRequest request) {
        // Access cookies
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                System.out.println("Cookie Name: " + cookie.getName() + ", Value: " + cookie.getValue());
            }
        } else {
            System.out.println("No cookies found in the request.");
        }
        return "Orders API is working!";
    }

    @GetMapping
    public ResponseEntity<Page<Orders>>getAllOrders(Pageable pageable) {
        Page<Orders> orders = orderService.getAllOrders(pageable);
        return ResponseEntity.ok(orders);
    }

    // Endpoint to retrieve an order by its ID
    @GetMapping("/id/{id}")
    public ResponseEntity<Orders> getOrderById(@PathVariable String id) {
        Optional<Orders> order = orderService.getOrderById(id);
        return order.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    @GetMapping("/{status}")
    public ResponseEntity<Page<Orders>> getOrdersByStatus(@PathVariable String status, Pageable pageable) {
        Page<Orders> orders = orderService.getOrdersByStatus(status, pageable);
        return ResponseEntity.ok(orders);
    }
    @GetMapping("/createdBy/{createdBy}")
    public ResponseEntity<Page<Orders>> getOrdersCreatedBy(@PathVariable String createdBy, Pageable pageable) {
        Page<Orders> orders = orderService.getOrdersCreatedBy(createdBy, pageable);
        return ResponseEntity.ok(orders);
    }
    @GetMapping("/count/{orderAt}/{type}")
    public ResponseEntity<HashMap<String,Integer>> getOrdersCount(@PathVariable String orderAt, @PathVariable String type){

        HashMap<String,Integer> map =orderService.getCountOrderByType(orderAt,type);
        return ResponseEntity.ok(map);
    }
}
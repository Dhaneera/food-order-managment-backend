package com.foodmanagement.Controller;

import com.foodmanagement.Entity.Orders;
import com.foodmanagement.Service.OrderService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;



    @PostMapping("/create")
    public ResponseEntity<Orders> placeOrder(@RequestBody Orders order) {
        Orders createdOrder = orderService.placeOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
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
    public ResponseEntity<Page<Orders>> getAllOrders(Pageable pageable) {
        Page<Orders> orders = orderService.getAllOrders(pageable);
        return ResponseEntity.ok(orders);
    }

    // Endpoint to retrieve an order by its ID
    @GetMapping("/{id}")
    public ResponseEntity<Orders> getOrderById(@PathVariable Long id) {
        Optional<Orders> order = orderService.getOrderById(id);
        return order.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
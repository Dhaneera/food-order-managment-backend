package com.foodmanagement.Controller;

import com.foodmanagement.Entity.Meal;
import com.foodmanagement.Entity.Orders;
import com.foodmanagement.Entity.User;
import com.foodmanagement.Repository.MealRepository;
import com.foodmanagement.Repository.OrderRepository;
import com.foodmanagement.Repository.UsersRepository;
import com.foodmanagement.Service.impl.ImageStoreServiceImpl;
import com.foodmanagement.dto.UserByMealDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/meal")
@RequiredArgsConstructor
public class MealController {

    private final MealRepository mealRepository;
    private final OrderRepository orderRepository;
    private final UsersRepository usersRepository;
    private final ImageStoreServiceImpl imageStoreController;

    @GetMapping("/{id}")
    public ResponseEntity getMealById(@PathVariable String id){
        Optional<Orders> order;
        Optional<User> user;
        Optional<Meal> byId = mealRepository.findById(id);
        if(byId.isPresent()) {
            order=orderRepository.findById(byId.get().getOrderId());
            if(order.isPresent()) {
                user=usersRepository.findByUsername(order.get().getCreatedBy());
                if(user.isPresent()) {
                    return new ResponseEntity(UserByMealDto.builder()
                            .mealId(byId.get().getId())
                            .name(user.get().getName())
                            .contactNo(user.get().getUsername())
                            .quantity(String.valueOf(byId.get().getCount()))
                            .image(user.get().getId()).build()
                            ,HttpStatus.OK);
                }

            }
            return ResponseEntity.ok(byId.get());
        }
        return new ResponseEntity<>("Meal not found for the id"+id, HttpStatus.NOT_FOUND);
    }


}

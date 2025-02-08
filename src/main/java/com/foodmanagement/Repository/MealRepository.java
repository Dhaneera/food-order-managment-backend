package com.foodmanagement.Repository;


import com.foodmanagement.Entity.Meal;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MealRepository extends CrudRepository<Meal,String> {
    @Transactional
    @Modifying
    @Query("UPDATE Meal m SET m.status = :status WHERE m.id = :id")
    int updateMealStatusById(String id, String status);

    @Query("SELECT m FROM Meal m WHERE m.orderId = :orderId")
    List<Meal> findMealByOrderId(@Param("orderId") String orderId);
}

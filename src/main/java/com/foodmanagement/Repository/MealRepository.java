package com.foodmanagement.Repository;


import com.foodmanagement.Entity.Meal;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface MealRepository extends CrudRepository<Meal,String> {
    @Transactional
    @Modifying
    @Query("UPDATE Meal m SET m.status = :status WHERE m.id = :id")
    int updateMealStatusById(String id, String status);
}

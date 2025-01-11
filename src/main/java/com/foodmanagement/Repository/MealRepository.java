package com.foodmanagement.Repository;


import com.foodmanagement.Entity.Meal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MealRepository extends JpaRepository<Meal,Long> {
}

package com.foodmanagement.Repository;


import com.foodmanagement.Entity.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface MealRepository extends CrudRepository<Meal,Long> {
}

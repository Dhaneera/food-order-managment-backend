package com.foodmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserByMealDto {
    private String mealId;
    private String name;
    private String contactNo;
    private String quantity;
    private String image;
}

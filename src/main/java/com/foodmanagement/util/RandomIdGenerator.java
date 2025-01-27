package com.foodmanagement.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RandomIdGenerator {

    public static Map<String,String> createOrderId(){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
            LocalDateTime localDate = LocalDateTime.now();
            String dateTime=localDate.format(formatter);
        Random random = new Random();
        int randomVal=random.nextInt(10000);
        HashMap<String,String> map = new HashMap<>();
        map.put("orderId",dateTime+randomVal);
        map.put("mealBreakfast","BR"+randomVal);
        map.put("mealLunch","LN"+randomVal);
        map.put("mealDinner","DN"+randomVal);
        return map;
    }
}

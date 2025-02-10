package com.foodmanagement.Service.impl;

import com.foodmanagement.Entity.Orders;
import com.foodmanagement.Repository.OrderRepository;
import com.foodmanagement.Service.PaymentService;
import com.foodmanagement.dto.CommonResponse;
import com.foodmanagement.dto.PaymentGetDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final OrderRepository orderRepository;
    @Override
    public CommonResponse getPaymentsForPeriod(String createdBy, String startDate, String endDate) {
        List<Orders> allByOrderedAtBetween = orderRepository.findAllByOrderedAtBetween(startDate, endDate, createdBy);
        return  new CommonResponse<>(0000, "Success", allByOrderedAtBetween);
    }
}

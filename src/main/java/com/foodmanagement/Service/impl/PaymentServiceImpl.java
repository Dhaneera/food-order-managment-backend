package com.foodmanagement.Service.impl;

import com.foodmanagement.Entity.Orders;
import com.foodmanagement.Repository.OrderRepository;
import com.foodmanagement.Service.PaymentService;
import com.foodmanagement.dto.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final OrderRepository orderRepository;
    @Override
    public CommonResponse getPaymentsForPeriod(String createdBy, String startDate, String endDate) {
        List<Map> allByOrderedAtBetween = orderRepository.findAllByOrderedAtBetween(startDate, endDate, createdBy);
        return  new CommonResponse<>(0000, "Success", allByOrderedAtBetween);
    }

    @Override
    public CommonResponse getPaymentsForSalaryDeductionAll(String startDate, String endDate) {
        List<Map> allOrderedAtBetween = orderRepository.findAllByOrderedAt(startDate,endDate);
        return new CommonResponse(0000,"Success",allOrderedAtBetween);
    }
}

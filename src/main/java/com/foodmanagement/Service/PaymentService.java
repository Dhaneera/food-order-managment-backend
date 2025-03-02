package com.foodmanagement.Service;

import com.foodmanagement.dto.CommonResponse;
import com.foodmanagement.dto.PaymentGetDto;

import java.time.LocalDate;

public interface PaymentService {
    CommonResponse getPaymentsForPeriod(String createdBy, String startDate, String endDate);

    CommonResponse getPaymentsForSalaryDeductionAll(String startDate, String endDate);
}

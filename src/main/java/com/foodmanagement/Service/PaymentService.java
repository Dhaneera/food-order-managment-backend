package com.foodmanagement.Service;

import com.foodmanagement.dto.CommonResponse;
import com.foodmanagement.dto.PaymentGetDto;

public interface PaymentService {
    CommonResponse getPaymentsForPeriod(PaymentGetDto paymentGetDto);
}

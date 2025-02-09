package com.foodmanagement.Controller;

import com.foodmanagement.Service.PaymentService;
import com.foodmanagement.dto.CommonResponse;
import com.foodmanagement.dto.PaymentGetDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/get-all-for-period")
    public CommonResponse getAllPaymentsOfEmployeesForPeriod(@RequestBody PaymentGetDto paymentGetDto){
        return paymentService.getPaymentsForPeriod(paymentGetDto);
    }
}

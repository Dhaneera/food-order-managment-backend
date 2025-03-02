package com.foodmanagement.Controller;

import com.foodmanagement.Service.PaymentService;
import com.foodmanagement.dto.CommonResponse;
import com.foodmanagement.dto.PaymentGetDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import static java.rmi.server.LogStream.log;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private static final Logger log = LoggerFactory.getLogger(PaymentController.class);
    private final PaymentService paymentService;

    @GetMapping("/get-all-for-period/{createdBy}/{startDate}/{endDate}")
    public CommonResponse getAllPaymentsOfEmployeesForPeriod(@PathVariable String createdBy, @PathVariable String startDate,@PathVariable String endDate ){
        log("getAllPaymentsOfEmployeesForPeriod");
        return paymentService.getPaymentsForPeriod(createdBy, startDate, endDate);

    }
    @GetMapping("/get-all-for-period/{startDate}/{endDate}")
    public CommonResponse getAllPaymentsOfEmployees(@PathVariable String startDate,@PathVariable String endDate ){
        log("getAllPaymentsOfEmployeesForPeriod");
        return paymentService.getPaymentsForSalaryDeductionAll(startDate, endDate);

    }
}

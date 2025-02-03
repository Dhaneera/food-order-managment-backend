package com.foodmanagement.schedular;

import com.foodmanagement.Service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static com.foodmanagement.util.DateTimeUtil.getCurrentDateTime;

@Slf4j
@EnableAsync
@Component
@RequiredArgsConstructor
public class CompleteOrdersCronJob {
    private final OrderService orderService;

    @Scheduled(cron = "59 59 23 * * *")
    public void completeOrders() {
        log.info("Complete  ");
        boolean isUpdatedStatus=orderService.changeOrderStatus(getCurrentDateTime());
        log.info(String.valueOf(isUpdatedStatus));
    }
}

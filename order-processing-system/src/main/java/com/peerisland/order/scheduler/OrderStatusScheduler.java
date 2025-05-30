package com.peerisland.order.scheduler;

import com.peerisland.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderStatusScheduler {
    private final OrderService orderService;

    // Runs every 5 minutes
    @Scheduled(fixedRate = 5 * 60 * 1000)
    public void updatePendingOrders() {
        orderService.updatePendingOrdersToProcessing();
    }
} 
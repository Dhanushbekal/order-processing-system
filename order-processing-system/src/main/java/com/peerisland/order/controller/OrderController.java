package com.peerisland.order.controller;

import com.peerisland.order.model.*;
import com.peerisland.order.service.OrderService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody CreateOrderRequest request) {
        List<OrderItem> items = request.getItems().stream()
                .map(i -> OrderItem.builder()
                        .productName(i.getProductName())
                        .quantity(i.getQuantity())
                        .price(i.getPrice())
                        .build())
                .collect(Collectors.toList());
        Order order = orderService.createOrder(request.getCustomerName(), items);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable Long id) {
        return orderService.getOrder(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Order> getAllOrders(@RequestParam(required = false) OrderStatus status) {
        return orderService.getAllOrders(Optional.ofNullable(status));
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<Order> cancelOrder(@PathVariable Long id) {
        return orderService.cancelOrder(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long id, @RequestBody UpdateStatusRequest request) {
        return orderService.updateOrderStatus(id, request.getStatus())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @Data
    public static class CreateOrderRequest {
        private String customerName;
        private List<OrderItemRequest> items;
    }

    @Data
    public static class OrderItemRequest {
        private String productName;
        private int quantity;
        private double price;
    }

    @Data
    public static class UpdateStatusRequest {
        private OrderStatus status;
    }
} 
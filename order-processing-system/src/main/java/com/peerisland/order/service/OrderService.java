package com.peerisland.order.service;

import com.peerisland.order.model.*;
import com.peerisland.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    @Transactional
    public Order createOrder(String customerName, List<OrderItem> items) {
        Order order = Order.builder()
                .customerName(customerName)
                .status(OrderStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .items(items)
                .build();
        if (items != null) {
            items.forEach(item -> item.setOrder(order));
        }
        return orderRepository.save(order);
    }

    public Optional<Order> getOrder(Long orderId) {
        return orderRepository.findById(orderId);
    }

    public List<Order> getAllOrders(Optional<OrderStatus> status) {
        return status.map(orderRepository::findByStatus)
                .orElseGet(orderRepository::findAll);
    }

    @Transactional
    public Optional<Order> cancelOrder(Long orderId) {
        Optional<Order> orderOpt = orderRepository.findById(orderId);
        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();
            if (order.getStatus() == OrderStatus.PENDING) {
                order.setStatus(OrderStatus.CANCELED);
                order.setUpdatedAt(LocalDateTime.now());
                return Optional.of(orderRepository.save(order));
            }
        }
        return Optional.empty();
    }

    @Transactional
    public Optional<Order> updateOrderStatus(Long orderId, OrderStatus status) {
        Optional<Order> orderOpt = orderRepository.findById(orderId);
        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();
            order.setStatus(status);
            order.setUpdatedAt(LocalDateTime.now());
            return Optional.of(orderRepository.save(order));
        }
        return Optional.empty();
    }

    @Transactional
    public void updatePendingOrdersToProcessing() {
        List<Order> pendingOrders = orderRepository.findByStatus(OrderStatus.PENDING);
        for (Order order : pendingOrders) {
            order.setStatus(OrderStatus.PROCESSING);
            order.setUpdatedAt(LocalDateTime.now());
            orderRepository.save(order);
        }
    }
} 
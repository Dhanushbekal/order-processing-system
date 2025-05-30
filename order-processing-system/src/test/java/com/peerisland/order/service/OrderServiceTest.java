package com.peerisland.order.service;

import com.peerisland.order.model.*;
import com.peerisland.order.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateOrder() {
        OrderItem item = OrderItem.builder().productName("Test Product").quantity(2).price(10.0).build();
        Order order = Order.builder().id(1L).customerName("John").status(OrderStatus.PENDING).items(Collections.singletonList(item)).build();
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        Order created = orderService.createOrder("John", Collections.singletonList(item));
        assertEquals("John", created.getCustomerName());
        assertEquals(OrderStatus.PENDING, created.getStatus());
        assertEquals(1, created.getItems().size());
    }

    @Test
    void testGetOrder() {
        Order order = Order.builder().id(1L).customerName("John").status(OrderStatus.PENDING).build();
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        Optional<Order> found = orderService.getOrder(1L);
        assertTrue(found.isPresent());
        assertEquals("John", found.get().getCustomerName());
    }

    @Test
    void testCancelOrder() {
        Order order = Order.builder().id(1L).customerName("John").status(OrderStatus.PENDING).build();
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        Optional<Order> canceled = orderService.cancelOrder(1L);
        assertTrue(canceled.isPresent());
        assertEquals(OrderStatus.CANCELED, canceled.get().getStatus());
    }
} 
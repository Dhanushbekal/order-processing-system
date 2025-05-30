package com.peerisland.order.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.peerisland.order.model.OrderStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateAndGetOrder() throws Exception {
        OrderController.CreateOrderRequest request = new OrderController.CreateOrderRequest();
        request.setCustomerName("Alice");
        OrderController.OrderItemRequest item = new OrderController.OrderItemRequest();
        item.setProductName("Book");
        item.setQuantity(1);
        item.setPrice(15.0);
        request.setItems(Collections.singletonList(item));

        // Create order
        String response = mockMvc.perform(post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerName").value("Alice"))
                .andReturn().getResponse().getContentAsString();

        Long orderId = objectMapper.readTree(response).get("id").asLong();

        // Get order
        mockMvc.perform(get("/api/orders/" + orderId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerName").value("Alice"))
                .andExpect(jsonPath("$.status").value(OrderStatus.PENDING.name()));
    }
} 
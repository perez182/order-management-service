package com.nova.order.management.service.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nova.order.management.service.api.dto.request.OrderRequest;
import com.nova.order.management.service.api.dto.response.OrderManagementResponse;
import com.nova.order.management.service.service.OrderManagementService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/order-management")
@AllArgsConstructor
@Slf4j
public class OrderManagementController {

    private final OrderManagementService orderManagementService;

    @PostMapping
    public ResponseEntity<OrderManagementResponse> createOrder(@Valid @RequestBody OrderRequest request) {
        log.info("Received request to create order for Customer ID: {}", request.getCustomerId());
        
        OrderManagementResponse response = orderManagementService.createOrder(request);
        
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
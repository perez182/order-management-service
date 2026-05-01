package com.nova.order.management.service.service.Impl;

import org.springframework.stereotype.Service;

import com.nova.order.management.service.api.dto.request.OrderRequest;
import com.nova.order.management.service.api.dto.response.CustomerResponse;
import com.nova.order.management.service.api.dto.response.OrderManagementResponse;
import com.nova.order.management.service.api.dto.response.OrderResponse;
import com.nova.order.management.service.exception.exceptions.BadRequestException;
import com.nova.order.management.service.exception.exceptions.ResourceNotFoundException;
import com.nova.order.management.service.exception.exceptions.ServiceUnavailableException;
import com.nova.order.management.service.infrastructure.client.CustomerClient;
import com.nova.order.management.service.infrastructure.client.OrderClient;
import com.nova.order.management.service.service.OrderManagementService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class OrderManagementServiceImpl implements OrderManagementService {

    private final CustomerClient customerClient;
    private final OrderClient orderClient;

    @Override
    @CircuitBreaker(name = "customerServiceCB", fallbackMethod = "fallbackCreateOrder")
    public OrderManagementResponse createOrder(OrderRequest request) {
        log.info("Starting order orchestration for customer ID: {}", request.getCustomerId());

     
        CustomerResponse customer = customerClient.getById(request.getCustomerId());

        OrderResponse order = orderClient.create(request);

        log.info("Order successfully created with ID: {}", order.getId());

        return OrderManagementResponse.builder()
                .order(order)
                .customer(customer)
                .build();
    }

    
    public OrderManagementResponse fallbackCreateOrder(OrderRequest request, Throwable t) {
        
        if (t instanceof ResourceNotFoundException || t instanceof BadRequestException) {
            log.warn("Business exception caught in fallback, rethrowing: {}", t.getMessage());
            throw (RuntimeException) t;
        }

        log.error("Circuit breaker fallback activated for customer {}: {}", 
                request.getCustomerId(), t.getMessage());
        
        throw new ServiceUnavailableException(
            "We're sorry, the ordering service is temporarily unavailable."
        );
    }
}
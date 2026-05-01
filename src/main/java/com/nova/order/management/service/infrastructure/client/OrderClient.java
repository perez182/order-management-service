package com.nova.order.management.service.infrastructure.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.nova.order.management.service.api.dto.request.OrderRequest;
import com.nova.order.management.service.api.dto.response.OrderResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrderClient {

    private final RestTemplate restTemplate;

    @Value("${services.order.url}")
    private String orderUrl;

    public OrderResponse create(OrderRequest request) {
        return restTemplate.postForObject(orderUrl, request, OrderResponse.class);
    }
}
package com.nova.order.management.service.infrastructure.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.nova.order.management.service.api.dto.response.CustomerResponse;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class CustomerClient {

    private final RestTemplate restTemplate;

    @Value("${services.customer.url}")
    private String customerUrl;

    public CustomerResponse getById(Long id) {
        return restTemplate.getForObject(customerUrl + "/" + id, CustomerResponse.class);
    }
}
package com.nova.order.management.service.infrastructure.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nova.order.management.service.api.dto.request.OrderRequest;
import com.nova.order.management.service.api.dto.response.OrderResponse;
import com.nova.order.management.service.exception.exceptions.BadRequestException;
import com.nova.order.management.service.exception.exceptions.ResourceNotFoundException;
import com.nova.order.management.service.exception.exceptions.ServiceUnavailableException;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@Slf4j
public class OrderClient {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${services.order.url}")
    private String orderUrl;

    public OrderResponse create(OrderRequest request) {
        try {
            return restTemplate.postForObject(orderUrl, request, OrderResponse.class);
        } catch (HttpStatusCodeException e) {
            
            Object errorDetails = parseErrorBody(e);

            if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                log.warn("Invalid order request sent to System API: {}", e.getMessage());
                throw new BadRequestException("Order Validation Failed", errorDetails);
            }

            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                log.warn("Order endpoint or related resource not found: {}", orderUrl);
                throw new ResourceNotFoundException("Order Resource Not Found", errorDetails);
            }

            log.error("System error calling Order Service: {}", e.getMessage());
            throw new ServiceUnavailableException("The order service is currently unavailable");
        }
    }

    private Object parseErrorBody(HttpStatusCodeException e) {
        try {
            return objectMapper.readValue(e.getResponseBodyAsString(), Object.class);
        } catch (Exception parseException) {
            log.error("Failed to parse error response from Order Service");
            return e.getResponseBodyAsString();
        }
    }
}
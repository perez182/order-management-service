package com.nova.order.management.service.infrastructure.client;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nova.order.management.service.api.dto.response.CustomerResponse;
import com.nova.order.management.service.exception.exceptions.BadRequestException;
import com.nova.order.management.service.exception.exceptions.ResourceNotFoundException;
import com.nova.order.management.service.exception.exceptions.ServiceUnavailableException;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@AllArgsConstructor
public class CustomerClient {

    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper; 

    @Value("${services.customer.url}")
    private String customerUrl;

    public CustomerResponse getById(Long id) {
        try {
            return restTemplate.getForObject(customerUrl + "/" + id, CustomerResponse.class);
        } catch (HttpStatusCodeException e) {
         
            Object errorDetails = parseErrorBody(e);

            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                log.warn("Customer not found for ID: {}", id);
                throw new ResourceNotFoundException("Customer Not Found", errorDetails);
            } 
            
            log.error("System error calling Customer Service: {}", e.getMessage());
            throw new ServiceUnavailableException("Customer service is temporarily unavailable");
        }
    }

    private Object parseErrorBody(HttpStatusCodeException e) {
        try {
            return objectMapper.readValue(e.getResponseBodyAsString(), Object.class);
        } catch (Exception parseException) {
            log.error("Failed to parse error response body from Customer Service");
            return e.getResponseBodyAsString(); 
        }
    }
}
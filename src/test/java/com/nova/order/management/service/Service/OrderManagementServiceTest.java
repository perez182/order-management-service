package com.nova.order.management.service.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nova.order.management.service.api.dto.request.OrderRequest;
import com.nova.order.management.service.api.dto.response.CustomerResponse;
import com.nova.order.management.service.api.dto.response.OrderResponse;
import com.nova.order.management.service.exception.exceptions.ResourceNotFoundException;
import com.nova.order.management.service.exception.exceptions.ServiceUnavailableException;
import com.nova.order.management.service.infrastructure.client.CustomerClient;
import com.nova.order.management.service.infrastructure.client.OrderClient;
import com.nova.order.management.service.service.Impl.OrderManagementServiceImpl;

@ExtendWith(MockitoExtension.class)
class OrderManagementServiceTest {

    @Mock CustomerClient customerClient;
    @Mock OrderClient orderClient;
    @InjectMocks OrderManagementServiceImpl service;

    @Test
    void shouldCreateOrderSuccessfully() {
        var req = new OrderRequest(1L, "Laptop",1,12900.99);
        //1L, "John", "john@test.com"
        var resC = CustomerResponse.builder()
        .id(1L)
        .firstName("John")
        .lastName("Smith")
        .email("john@test.com")
        .phone("5558976983")
        .address("Otavalo 56")
        .isActive(true)
        .build();

        ;
        var resO = OrderResponse.builder()
        .id(99L)
        .product("Laptop")
        .customerId(1L)
        .quantity(1)
        .build();
        

        when(customerClient.getById(1L)).thenReturn(resC);
        when(orderClient.create(req)).thenReturn(resO);

        var result = service.createOrder(req);

        assertNotNull(result);
        assertEquals("John", result.getCustomer().getFirstName());
        verify(customerClient).getById(1L);
    }

    @Test
    void shouldRethrowWhenBusinessError() {
        var req = new OrderRequest(2L, "Laptop",1,12900.99);
        var ex = new ResourceNotFoundException("Not Found","");
        assertThrows(ResourceNotFoundException.class, () -> 
            service.fallbackCreateOrder(req, ex));
    }

    @Test
    void shouldThrowUnavailableOnTechnicalError() {
        var req = new OrderRequest(3L, "Laptop",1,12900.99);
        assertThrows(ServiceUnavailableException.class, () -> 
            service.fallbackCreateOrder(req, new RuntimeException()));
    }
}
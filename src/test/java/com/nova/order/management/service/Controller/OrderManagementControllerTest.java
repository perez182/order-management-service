package com.nova.order.management.service.Controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import com.nova.order.management.service.api.controller.OrderManagementController;
import com.nova.order.management.service.api.dto.request.OrderRequest;
import com.nova.order.management.service.api.dto.response.OrderManagementResponse;
import com.nova.order.management.service.service.OrderManagementService;

@ExtendWith(MockitoExtension.class)
class OrderManagementControllerTest {

    @Mock OrderManagementService service;
    @InjectMocks OrderManagementController controller;

    @Test
    void createOrderReturnStatusCreated() {
        var req = new OrderRequest(1L, "Laptop",1,12900.99);
        when(service.createOrder(any())).thenReturn(OrderManagementResponse.builder().build());

        var res = controller.createOrder(req);

        assertEquals(HttpStatus.CREATED, res.getStatusCode());
        verify(service).createOrder(req);
    }
}
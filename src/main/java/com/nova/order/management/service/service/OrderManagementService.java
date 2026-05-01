package com.nova.order.management.service.service;

import com.nova.order.management.service.api.dto.request.OrderRequest;
import com.nova.order.management.service.api.dto.response.OrderManagementResponse;

public interface OrderManagementService {
    OrderManagementResponse createOrder(OrderRequest request);
}
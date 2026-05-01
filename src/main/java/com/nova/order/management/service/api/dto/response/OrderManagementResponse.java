package com.nova.order.management.service.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderManagementResponse {
    private OrderResponse order;
    private CustomerResponse customer;
}
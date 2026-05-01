package com.nova.order.management.service.api.dto.response;


import java.time.LocalDateTime;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderResponse {
    private Long id;
    private Long customerId;
    private String product;
    private Integer quantity;
    private Double price;
    private String status;
    private LocalDateTime createdAt;
}
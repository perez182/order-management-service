package com.nova.order.management.service.api.dto.request;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderRequest {
    @NotNull(message = "customerId is required")
    private Long customerId;

    @NotBlank(message = "product is required")
    private String product;

    @Min(1)
    private Integer quantity;

    @Positive
    private Double price;
    
}
package com.nova.order.management.service.api.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerResponse {

    private Long id;

    private String firstName;
    private String middleName;
    private String lastName;
    private String secondLastName;
    

    private String email; 
    
    private String phone;
    private String address; 

    private LocalDateTime createdAt;
    
    private Boolean isActive; 
}
package com.nova.order.management.service.exception.exceptions;

import lombok.Data;

@Data
public class ResourceNotFoundException extends RuntimeException {
    public Object details;

    public ResourceNotFoundException (String message,Object details) {
        super(message);
        this.details=details;
    }
}

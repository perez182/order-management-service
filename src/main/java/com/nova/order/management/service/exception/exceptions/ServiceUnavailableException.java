package com.nova.order.management.service.exception.exceptions;

import lombok.Data;

@Data
public class ServiceUnavailableException extends RuntimeException {
    public Object details;

     public ServiceUnavailableException(String message) {
        super(message);
        this.details=message;
    }

    public ServiceUnavailableException(String message,Object details) {
        super(message);
        this.details=message;
    }
}

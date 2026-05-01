package com.nova.order.management.service.exception.exceptions;

import lombok.Data;

@Data
public class BadRequestException extends RuntimeException{
    public Object details;

      public BadRequestException(String message,Object details) {
         super(message);
         this.details=details;
    }
}

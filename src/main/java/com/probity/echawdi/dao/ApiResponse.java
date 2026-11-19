package com.probity.echawdi.dao;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponse<T> {
    private int status;
    private String message;
    private T data;
    private boolean success;
    private Integer updatedCount;

    
    
    
}
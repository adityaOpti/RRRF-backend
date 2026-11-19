package com.probity.echawdi.dao;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponseDecryptExternal<T> {
	
	
	 private String status;
	    private String message;
	    private T data;

	    public ApiResponseDecryptExternal() {}

	    public ApiResponseDecryptExternal(String status, String message, T data) {
	        this.status = status;
	        this.message = message;
	        this.data = data;
	    }


}

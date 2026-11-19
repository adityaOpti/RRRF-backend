package com.probity.echawdi.exception;

public class VasuliDataException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	
    public VasuliDataException(String message) {
        super(message);
    }

    
    public VasuliDataException(String message, Throwable cause) {
    	super(message, cause);
    }
    



}
package com.probity.echawdi.exception;

public class MutationDataException extends RuntimeException {
    public MutationDataException(String message) {
        super(message);
    }

    public MutationDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
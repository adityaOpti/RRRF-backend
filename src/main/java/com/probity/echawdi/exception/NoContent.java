package com.probity.echawdi.exception;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NO_CONTENT)
public class NoContent extends RuntimeException{
   
	private static final long serialVersionUID = 1L;

    public NoContent(String messageKey, MessageSource messageSource, Locale locale, Object...args) {
    	super(messageSource.getMessage(messageKey, args, locale));
    }
    
    public NoContent(String message) {
		super(message);
	}
}
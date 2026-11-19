/**
 * 
 */
package com.probity.echawdi.exception;

import java.util.Locale;

import org.springframework.context.MessageSource;

import com.probity.echawdi.config.DatabaseContextHolder;

/**
 * @author Bhagyashri Lolage
 *
 */

public class ExchangeException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	 public ExchangeException(String messageKey, MessageSource messageSource, Locale locale, Object...args) {
	    	super(messageSource.getMessage(messageKey, args, locale));
	    	DatabaseContextHolder.restoreCtx();
	    }
	 
	public ExchangeException(String message) {
		super(message);
	}
}
/**
 * 
 */
package com.probity.echawdi.exception;


/**
 * @author Bhagyashri Lolage
 *
 */
public class FieldValidationException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public FieldValidationException(String message) {
		super(message);
	}
}
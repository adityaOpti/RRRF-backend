/**
 * 
 */
package com.probity.echawdi.exception;

/**
 * @author Bhagyashri Lolage
 *
 */

public class BadCredentialsException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public BadCredentialsException(String message) {
		super(message);
	}
}
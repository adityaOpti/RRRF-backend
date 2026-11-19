package com.probity.echawdi.exception;

public class BadRequest extends RuntimeException {

	/**
	 * @author Bhagyashri Lolage
	 *
	 */

	private static final long serialVersionUID = 1L;

	public BadRequest(String message) {
		super(message);
	}
}

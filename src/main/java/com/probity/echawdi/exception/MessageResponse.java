/**
 * 
 */
package com.probity.echawdi.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Bhagyashri Lolage
 *
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageResponse {
	public MessageResponse(String message) {}
	private String message;
	private String status;
}
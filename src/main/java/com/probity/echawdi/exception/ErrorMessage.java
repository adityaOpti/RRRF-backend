package com.probity.echawdi.exception;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Bhagyashri Lolage
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorMessage {
  private int statusCode;
  private Date timestamp;
  private String message;
  private String description;
}
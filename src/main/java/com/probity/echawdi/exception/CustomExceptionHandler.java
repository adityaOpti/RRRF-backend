/**
 * 
 */
package com.probity.echawdi.exception;


import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpClientErrorException.BadRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.probity.echawdi.dao.ApiResponse;

/**
 * @author Bhagyashri Lolage
 *
 */

@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler{
	@Autowired
	private MessageSource messageSource;

	@ExceptionHandler(FieldValidationException.class)    //request parameter exception
	public final ResponseEntity<MessageResponse> handleUserNotFoundException(FieldValidationException ex, WebRequest request,Locale locale) {
		MessageResponse messageResponse = MessageResponse.builder().message(messageSource.getMessage(ex.getLocalizedMessage(), null, locale) ).status("FAILURE").build(); 
		return new ResponseEntity<>(messageResponse, HttpStatus.BAD_REQUEST);
	}
	
	
//	@Override
//	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
//			HttpHeaders headers, HttpStatus status, WebRequest request) {
//		ex.getBindingResult().getFieldErrors();
//		return super.handleMethodArgumentNotValid(ex, headers, status, request);
//	}
//
	
	
	  @Override
	    protected ResponseEntity<Object> handleMethodArgumentNotValid(
	        MethodArgumentNotValidException ex, HttpHeaders headers, 
	        HttpStatus status, WebRequest request) {

	        Map<String, Object> body = new LinkedHashMap<>();
	        body.put("timestamp", LocalDate.now());
	        body.put("status", status.value());

	        List<String> errors = ex.getBindingResult()
	                .getFieldErrors()
	                .stream()
	                .map(x -> x.getDefaultMessage())
	                .collect(Collectors.toList());

	        body.put("errors", errors);

	        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	    }

	
	
	@ExceptionHandler(ResourceNotFoundException.class)   // data exception
	public final ResponseEntity<MessageResponse> handleUserNotFoundException(ResourceNotFoundException ex, WebRequest request,Locale locale) {
		try {
			if(messageSource.getMessage(ex.getLocalizedMessage(), null, locale) != null) {
				MessageResponse messageResponse = MessageResponse.builder().message(messageSource.getMessage(ex.getLocalizedMessage(), null, locale) ).status("FAILURE").build(); 
				return new ResponseEntity<>(messageResponse, HttpStatus.NOT_FOUND);
			}
			else {
				MessageResponse messageResponse = MessageResponse.builder().message(ex.getLocalizedMessage()).status("FAILURE").build(); 
				return new ResponseEntity<>(messageResponse, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			MessageResponse messageResponse = MessageResponse.builder().message(ex.getLocalizedMessage()).status("FAILURE").build(); 
			return new ResponseEntity<>(messageResponse, HttpStatus.NOT_FOUND);
		}
	}

	@ExceptionHandler(NoContent.class)   // No data exception
	public final ResponseEntity<MessageResponse> handleUserNotFoundException(NoContent ex, WebRequest request,Locale locale) {
		try {
			if(messageSource.getMessage(ex.getLocalizedMessage(), null, locale) != null) {
				MessageResponse messageResponse = MessageResponse.builder().message(messageSource.getMessage(ex.getLocalizedMessage(), null, locale) ).status("FAILURE").build(); 
				return new ResponseEntity<>(messageResponse, HttpStatus.NO_CONTENT);
			}
			else {
				MessageResponse messageResponse = MessageResponse.builder().message(ex.getLocalizedMessage()).status("FAILURE").build(); 
				return new ResponseEntity<>(messageResponse, HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			MessageResponse messageResponse = MessageResponse.builder().message(ex.getLocalizedMessage()).status("FAILURE").build(); 
			return new ResponseEntity<>(messageResponse, HttpStatus.NO_CONTENT);
		}
	}

	@ExceptionHandler(ExchangeException.class)  //db exception
	public final ResponseEntity<MessageResponse> handleUserNotFoundException(ExchangeException ex, WebRequest request, Locale locale) {
		MessageResponse messageResponse = MessageResponse.builder().message(messageSource.getMessage(ex.getLocalizedMessage(), null, locale) ).status("FAILURE").build();
		return new ResponseEntity<>(messageResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(BadRequest.class)  // incomplete request argument exception
	public final ResponseEntity<MessageResponse> handleUserNotFoundException(BadRequest ex, WebRequest request, Locale locale) {
		MessageResponse messageResponse = MessageResponse.builder().message(messageSource.getMessage(ex.getLocalizedMessage(), null, locale) ).status("FAILURE").build();
		return new ResponseEntity<>(messageResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(BadCredentialsException.class)   // Invalid User Name or Password
	public final ResponseEntity<MessageResponse> handleUserNotFoundException(BadCredentialsException ex, WebRequest request) {
		MessageResponse messageResponse = MessageResponse.builder().message(ex.getLocalizedMessage()).status("FAILURE").build();
		return new ResponseEntity<>(messageResponse, HttpStatus.FORBIDDEN);
	}
	
	
	@ExceptionHandler(VasuliDataException.class)   // Error at vasuli Method
	public final ResponseEntity<MessageResponse> handleUserNotFoundException(VasuliDataException ex, WebRequest request) {
		MessageResponse messageResponse = MessageResponse.builder().message(ex.getLocalizedMessage()).status("FAILURE").build();
		return new ResponseEntity<>(messageResponse, HttpStatus.FORBIDDEN);
	}
	
	
	
	//for gram adarsh takta
			@ExceptionHandler(DuplicateRecordException.class)
			public final ResponseEntity<MessageResponse> handleDuplicateRecordException(DuplicateRecordException ex, WebRequest request, Locale locale) {
			    MessageResponse messageResponse = MessageResponse.builder()
			        .message(messageSource.getMessage(ex.getLocalizedMessage(), null, locale))
			        .status("FAILURE")
			        .build();
			    return new ResponseEntity<>(messageResponse, HttpStatus.CONFLICT); // 409 Conflict
			}
			
			
			@ExceptionHandler(HttpClientErrorException.class)
			public ResponseEntity<String> handleHttpClientError(HttpClientErrorException ex) {
			    return ResponseEntity
			            .status(ex.getStatusCode())
			            .body(ex.getResponseBodyAsString());
			}

	
			
	//for inspection talathi remark Api (Talathi module inspection report regarding)
			@ExceptionHandler(DuplicateRemarkException.class)
		    public ResponseEntity<ApiResponse<Object>> handleDuplicateRemark(DuplicateRemarkException ex) {

		        ApiResponse<Object> response = ApiResponse.builder()
		                .status(HttpStatus.BAD_REQUEST.value())
		                .message(ex.getMessage())
		                .data(null)
		                .build();

		        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		    }
//			//http method not found for point 3.11 sequrity audit 
//			  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
//			  public ResponseEntity<?> handleMethodNotAllowed(
//			          HttpRequestMethodNotSupportedException ex) {
//
//			      Map<String, Object> response = new HashMap<>();
//			      response.put("status", "ERROR");
//			      response.put("message", "HTTP method not allowed");
//
//			      return new ResponseEntity<>(response,
//			              HttpStatus.METHOD_NOT_ALLOWED);
//			  }
			
}
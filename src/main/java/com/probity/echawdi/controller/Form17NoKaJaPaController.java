package com.probity.echawdi.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.probity.echawdi.dao.Form17NoKaJaPaDao;
import com.probity.echawdi.exception.ExchangeException;
import com.probity.echawdi.exception.MessageResponse;
import com.probity.echawdi.payload.Response;
import com.probity.echawdi.service.Form17NoKaJaPaService;

@RestController
@RequestMapping("/api/form17NoKaJaPa")
public class Form17NoKaJaPaController {

	@Autowired
	private Form17NoKaJaPaService form17NoKaJaPaService;
	@Autowired
	private MessageSource messageSource;

	@GetMapping("/getForm17NoKaJaPaData")
	@ResponseStatus(HttpStatus.OK)
	public Response getAllForm17(
			HttpServletRequest request,
			@RequestParam String cCode,
			@RequestParam String khataNo,
			@RequestParam String revenueYear,
			Locale locale){
		return  form17NoKaJaPaService.getAllForm17KaJaPaData(request,cCode,khataNo,revenueYear,locale);
	}

	@GetMapping(value = "/getForm17NoKaJaPaDataById")
	@ResponseStatus(HttpStatus.OK)
	public Form17NoKaJaPaDao  getForm17ById(@RequestParam long id,HttpServletRequest request,Locale locale){
		return form17NoKaJaPaService.getForm17ById(id,request,locale);
	}

	@PostMapping("/saveForm17NoKaJaPa")
	@ResponseStatus(HttpStatus.CREATED)
	public MessageResponse createForm17(@Valid  @RequestBody Form17NoKaJaPaDao form17NoKaJaPaDao,HttpServletRequest request,Locale locale){
		return form17NoKaJaPaService.save(form17NoKaJaPaDao,request,locale);
	}

	@PostMapping("/editForm17NoKaJaPa")
	@ResponseStatus(HttpStatus.OK)
	public MessageResponse updateForm17(@Valid @RequestBody Form17NoKaJaPaDao form17NoKaJaPaDao,HttpServletRequest request, Locale locale){
		try {
			form17NoKaJaPaService.updateForm17NoKaJaPa(form17NoKaJaPaDao, request,locale);
			return MessageResponse.builder().status("SUCCESS").message(
					messageSource.getMessage("error.form.successmessage", null, locale)).build();
		} 
		catch (Exception ex) {
			throw new ExchangeException("error.form.editrecord");
		}
	}

	@DeleteMapping("/discardForm17NoKaJaPa")
	@ResponseStatus(HttpStatus.IM_USED)
	public MessageResponse deleteForm17(@RequestParam long id,HttpServletRequest request,Locale locale){
		try {
			form17NoKaJaPaService.deleteForm17NoKaJaPaById(id, request,locale);
			return MessageResponse.builder().status("SUCCESS").message(
					messageSource.getMessage("error.form.deletemessage", null, locale)).build();	
		}
		catch (Exception ex) {
			throw new ExchangeException("error.form.deleterecord");
		}
	}
	
	@GetMapping("/getReportForm17NoKaJaPa")
	@ResponseStatus(HttpStatus.OK)
	public Response getForm17NoKaJaPaReport(		
			@RequestParam String cCode, Locale locale,HttpServletRequest request){
		return  form17NoKaJaPaService.getForm17NoKaJaPaReport(cCode, locale, request); 
	}

	//for kajapa 2024 nov  08
		@GetMapping("/getDetailByKhataNoKaJaPa")
	    Response getDetailByKhataNo(@RequestParam String cCode,@RequestParam String khataNo,HttpServletRequest request,Locale locale){
	    	return form17NoKaJaPaService.getDetailBykhataNoKajapa(cCode,khataNo,request,locale);
	    }
		
	
		//Rate API
		/*@GetMapping("/getrate")
		@ResponseStatus(HttpStatus.OK)
		public ResponseEntity<Object> getrate(		
				@RequestParam String cCode, @RequestParam String pin,Locale locale,HttpServletRequest request){
			
			BigDecimal rate =  form17NoKaJaPaService.getrate(cCode, pin,locale, request); 
			if (rate != null) {
	        	Map<String, Object> response = new HashMap<>();
	            response.put("rate", rate);
	            return ResponseEntity.ok(response);
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(" Data Not Found ");
	        }
		}*/
		
		
		@GetMapping("/getrate")
		public ResponseEntity<Object> getrate(
				@RequestParam String cCode,
				@RequestParam String pin,
				Locale locale,
				HttpServletRequest request) {
			
			Map<String, Object> response = new HashMap<>();
			
			try {
				BigDecimal rate = form17NoKaJaPaService.getrate(cCode, pin, locale, request);

				if (rate != null) {
					response.put("rate", rate);
				} else {
					response.put("message", "Mapanapa rate is not found");
					response.put("rate", 0);
				}

			} catch (Exception e) {
				response.put("message", "An error occurred");
			}
			
			return ResponseEntity.ok(response);
		}


}
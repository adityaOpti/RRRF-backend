package com.probity.echawdi.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.probity.echawdi.dao.Form3Dao;
import com.probity.echawdi.exception.ExchangeException;
import com.probity.echawdi.exception.MessageResponse;
import com.probity.echawdi.payload.Response;
import com.probity.echawdi.service.Form3Service;

@RestController
@RequestMapping("/api/form3")
public class Form3Controller {
	@Autowired
	private Form3Service form3Service;
	
	@Autowired
	private MessageSource messageSource;

	@GetMapping("/getForm3DataN")
	@ResponseStatus(HttpStatus.OK)
	public Response getAllForm3(
			@RequestParam String cCode, @RequestParam String pin,
			HttpServletRequest request,
			Locale locale) {
		return form3Service.getForm3cDataN(cCode, pin,request,locale);
	}

//	@PostMapping("/saveForm3")
//	@ResponseStatus(HttpStatus.CREATED)
//	public MessageResponse createForm3(@Valid @RequestBody Form3Dao form3Dao, HttpServletRequest request,Locale locale) {
//		return form3Service.save(form3Dao,request,locale);
//	}

	@PutMapping("/editForm3")
	@ResponseStatus(HttpStatus.OK)
	public MessageResponse updateForm3(@Valid @RequestBody Form3Dao form3Dao,HttpServletRequest request, Locale locale) {
		try {
			form3Service.updateForm3(form3Dao,request,locale);
			return MessageResponse.builder().status("SUCCESS").message(
    				messageSource.getMessage("error.form.successmessage", null, locale)).build();
		} 
	   	catch (Exception ex) {
	         throw new ExchangeException("error.form.editrecord");
   	    }
	}

	@DeleteMapping("/discardForm3")
	@ResponseStatus(HttpStatus.IM_USED)
	public MessageResponse deleteForm3(@RequestParam long id,HttpServletRequest request, Locale locale) {
		try {
			form3Service.deleteForm3ById(id,request,locale);
			  return MessageResponse.builder().status("SUCCESS").message(
        				messageSource.getMessage("error.form.deletemessage", null, locale)).build();	
    		}
        	catch (Exception ex) {
     	         throw new ExchangeException("error.form.deleterecord");
     	}

	}

	@GetMapping("/getForm3Report")
	@ResponseStatus(HttpStatus.OK)
	public Response getForm3ReportDetails(
			@RequestParam String cCode, 
			HttpServletRequest request,
			Locale locale) {
		return form3Service.getForm3ReportDetails(cCode,request,locale);
	}

	@GetMapping("/getForm3AbstractReport")
	@ResponseStatus(HttpStatus.OK)
	public Response getForm3ReportAbstractDetails(
			@RequestParam("cCode") String cCode, HttpServletRequest request,Locale locale) {
		return form3Service.getForm3AbstractReportDetails(cCode, request, locale);
	}
//	@GetMapping("/getForm3EferfarDetials")
//	@ResponseStatus(HttpStatus.OK)
//	public List<Form3Dao> getForm3EferfarDetials(
//			@RequestParam String cCode, 
//			@RequestParam String pin,
//			@RequestParam String hissaNo, 
//			HttpServletRequest request,
//			Locale locale) {
//		return form3Service.getForm3EferfarDetials(cCode, pin, hissaNo,request,locale);
//	}

}
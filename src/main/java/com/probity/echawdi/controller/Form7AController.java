package com.probity.echawdi.controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.probity.echawdi.dao.Form7ADao;
import com.probity.echawdi.exception.ExchangeException;
import com.probity.echawdi.exception.MessageResponse;
import com.probity.echawdi.payload.Response;
import com.probity.echawdi.service.Form7AService;

/**
 * @author Bhagyashri Lolage
 *
 */
@RestController
@RequestMapping("/api/form7a")
public class Form7AController {

	@Autowired
	private Form7AService form7AService;

	@Autowired
	MessageSource messageSource;

	@GetMapping("/getForm7AData")
	@ResponseStatus(HttpStatus.OK)
	public Response getAllForm7A(
			@RequestParam String cCode,
			@RequestParam String pin,
			HttpServletRequest request,
			Locale locale){
		return form7AService.getAllForm7AData(cCode,pin,request,locale);
	}


	@PostMapping("/saveForm7A")
	@ResponseStatus(HttpStatus.CREATED)
	public MessageResponse createForm7A(@Valid @RequestBody Form7ADao form7ADao,HttpServletRequest request,Locale locale){
		return form7AService.save(form7ADao,request,locale);
	}


	@PutMapping("/editForm7A")
	@ResponseStatus(HttpStatus.OK)
	public MessageResponse updateForm7A(@Valid @RequestBody Form7ADao form7ADao,HttpServletRequest request,Locale locale){
		try {
			form7AService.updateForm7A(form7ADao,request,locale);
			return MessageResponse.builder().status("SUCCESS").message(
					messageSource.getMessage("error.form.successmessage", null, locale)).build();
		}
		catch (Exception ex) {
			throw new ExchangeException("error.form.editrecord");
		}
	}


	@DeleteMapping("/discardForm7A")
	@ResponseStatus(HttpStatus.IM_USED)
	public MessageResponse deleteForm7A(@RequestParam  long id,HttpServletRequest request,Locale locale){
		try {
			form7AService.deleteForm7AById(id,request,locale);
			return MessageResponse.builder().status("SUCCESS").message(
					messageSource.getMessage("error.form.deletemessage", null, locale)).build();	
		}
		catch (Exception ex) {
			throw new ExchangeException("error.form.deleterecord");
		}   
	}

	@GetMapping("/getForm7AReport")
	@ResponseStatus(HttpStatus.OK)
	public Response getAllForm7AReportdetails(
			@RequestParam String cCode,
			HttpServletRequest request,
			Locale locale) {
		return form7AService.getForm7AReportDetails(cCode,request,locale);
	}



	@GetMapping("/getForm7AEferfarDetials")    //form7
	@ResponseStatus(HttpStatus.OK)
	public List<Form7ADao> getForm7AEferfarDetials(
			@RequestParam String cCode,
			@RequestParam String pin,
			@RequestParam String hissaNo,
			HttpServletRequest request,
			Locale locale) {
		return form7AService.getForm7AEferfarDetials(cCode,pin,hissaNo,request,locale);
	}


	@GetMapping("/getForm7AEferfarKhataDetials")    //form7
	@ResponseStatus(HttpStatus.OK)
	public List<Form7ADao> getForm7AEferfarKhataDetials(
			@RequestParam String cCode,
			@RequestParam String pin,
			@RequestParam String hissaNo,
			@RequestParam String khataNo,
			HttpServletRequest request,
			Locale locale) {
		return form7AService.getForm7AEferfarKhataDetials(cCode,pin,hissaNo,khataNo,request,locale);
	}

}

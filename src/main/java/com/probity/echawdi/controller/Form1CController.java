/**

 * This Controller is Describes data persistence and retrieval of echawdi form 1c


 *  It Includes CRUD OPERATIONS ON echawdi form 1c
 */
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.probity.echawdi.dao.Form1CDao;
import com.probity.echawdi.exception.ExchangeException;
import com.probity.echawdi.exception.MessageResponse;
import com.probity.echawdi.payload.Response;
import com.probity.echawdi.service.Form1CService;
/**
 * @author Rushikeh Durgude
 *
 */
@RestController
@RequestMapping("/api/form1c")
public class Form1CController {

	@Autowired
	private Form1CService form1CService;

	@Autowired
	private MessageSource messageSource;

	@GetMapping("/getForm1CData")
	@ResponseStatus(HttpStatus.OK)
	public Response getAllForm1C(
			@RequestParam String cCode,
			@RequestParam String pin,
			@RequestParam Integer clause,
			 HttpServletRequest request,
			Locale locale){
		return form1CService.getAllForm1CData(cCode,pin,clause,request,locale);
	}

//	@GetMapping(value = "/getForm1CDataById")
//	@ResponseStatus(HttpStatus.OK)
//	public Form1CDao  getForm1CCById(@RequestParam long id,Locale locale){
//		return form1CService.getForm1CById(id, locale);
//	}

//	@PostMapping("/saveForm1C")
//	@ResponseStatus(HttpStatus.CREATED)
//	public MessageResponse createForm1C(@Valid @RequestBody Form1CDao form1CDao,Locale locale){
//		return form1CService.save(form1CDao, locale);
//	}

	@PutMapping("/editForm1C")
	@ResponseStatus(HttpStatus.OK)
	public MessageResponse updateForm1C(@Valid @RequestBody Form1CDao form1CDao,HttpServletRequest request,Locale locale){
		try {
			form1CService.updateForm1C(form1CDao,request,locale);
			return MessageResponse.builder().status("SUCCESS").message(
					messageSource.getMessage("error.form.successmessage", null, locale)).build();
		}
		catch (Exception ex) {
			throw new ExchangeException("error.form.editrecord");
		}
	}

	@DeleteMapping("/discardForm1C")
	@ResponseStatus(HttpStatus.IM_USED)
	public MessageResponse deleteForm1C(@RequestParam long id,HttpServletRequest request,Locale locale){
		try {
			form1CService.deleteForm1CById(id,request,locale);
			return MessageResponse.builder().status("SUCCESS").message(
					messageSource.getMessage("error.form.deletemessage", null, locale)).build();	
		}
		catch (Exception ex) {
			throw new ExchangeException("error.form.deleterecord");
		}
	}

	@GetMapping("/getForm1CReport")  // form1C
	@ResponseStatus(HttpStatus.OK)
	public Response getForm1CReport(
			@RequestParam ("cCode")String cCode,HttpServletRequest request,Locale locale) {
		return form1CService.getForm1CReport(cCode,request,locale);
	}

//	@GetMapping("/getForm1CeFerfarData")
//	@ResponseStatus(HttpStatus.OK)
//	public Response getForm1BAssesment(@RequestParam Integer districtCode , @RequestParam Integer talukaCode , @RequestParam String cCode,Locale locale) {
//		return form1CService.getForm1CeFerfarData(districtCode,talukaCode,cCode, locale);
//	}
	
	@GetMapping("/getClauses")  // form1C
	@ResponseStatus(HttpStatus.OK)
	public List<Form1CDao> getClauses(HttpServletRequest request) {
		return form1CService.getClauses(request);
	}
}
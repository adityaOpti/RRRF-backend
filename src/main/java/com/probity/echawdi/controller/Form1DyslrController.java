/**
 * This Controller is Describes data persistence and retrieval of echawdi form 1


 *  It Includes CRUD OPERATIONS ON echawdi form 1
 */
package com.probity.echawdi.controller;

import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.probity.echawdi.dao.Form1AbstractDyslrDao;
import com.probity.echawdi.dao.Form1DyslrDao;
import com.probity.echawdi.exception.ExchangeException;
import com.probity.echawdi.exception.MessageResponse;
import com.probity.echawdi.payload.Response;
import com.probity.echawdi.service.Form1DyslrService;



@RestController
@PreAuthorize("hasRole('ROLE_DYSLR')")
@RequestMapping("/api/form1Dyslr")
public class Form1DyslrController {

	@Autowired
	private Form1DyslrService form1DyslrService;

	@Autowired
	private MessageSource messageSource;





	@PostMapping("/editForm1Dyslr")
	@ResponseStatus(HttpStatus.OK)
	public MessageResponse updateForm1( @Valid @RequestBody Form1DyslrDao form1DyslrDao,HttpServletRequest request,Locale locale){
		try {
			form1DyslrService.updateForm1(form1DyslrDao,request,locale);
			return MessageResponse.builder().status("SUCCESS").message(
					messageSource.getMessage("error.form.successmessage", null, locale)).build();
		}
		catch (Exception ex) {
			throw new ExchangeException("error.form.editrecord");
		}
	}
	
	@PostMapping("/saveForm1Dyslr")
	@ResponseStatus(HttpStatus.OK)
	public MessageResponse saveForm1Dyslr( @Valid @RequestBody Form1DyslrDao form1DyslrDao,HttpServletRequest request,Locale locale){
		try {
			form1DyslrService.saveForm1Dyslr(form1DyslrDao,request,locale);
			return MessageResponse.builder().status("SUCCESS").message(
					messageSource.getMessage("error.form.successmessage", null, locale)).build();
		}
		catch (Exception ex) {
			throw new ExchangeException("error.form.editrecord");
		}
	}

	@PostMapping("/discardForm1Dyslr")
	@ResponseStatus(HttpStatus.IM_USED)
	public MessageResponse deleteForm1(@Valid @RequestBody Form1DyslrDao form1DyslrDao,Locale locale,HttpServletRequest request){
		try {
			form1DyslrService.deleteForm1ById(form1DyslrDao,locale,request);
			return MessageResponse.builder().status("SUCCESS").message(
					messageSource.getMessage("error.form.deletemessage", null, locale)).build();	
		}
		catch (Exception ex) {
			throw new ExchangeException("error.form.deleterecord");
		}
	}
	
	@GetMapping("/getForm1DyslrReport")
	public Response getForm1DyslrReportDetails(@RequestParam Integer districtCode,
			@RequestParam Integer talukaCode,
			@RequestParam ("cCode") String cCode,Locale locale,HttpServletRequest request)  {
		return form1DyslrService.getForm1DyslrReportDetails(districtCode,talukaCode,cCode, locale,request);
	}

	@GetMapping("/getFormDyslrSaveEntries")
	@ResponseStatus(HttpStatus.OK)
	public Response getFormDyslrSaveEntries(
			@RequestParam String cCode,
			HttpServletRequest request,
			Locale locale){
		return form1DyslrService.getFormDyslrSaveEntries(cCode,request,locale);
	}
	
	@GetMapping("/getFormDyslrDeleteEntries")
	@ResponseStatus(HttpStatus.OK)
	public Response getFormDyslrDeleteEntries(
			@RequestParam String cCode,
			HttpServletRequest request,
			Locale locale){
		return form1DyslrService.getFormDyslrDeleteEntries(cCode,request,locale);
	}
	
	
//form 1  DYSLR Akarband Durursti
	@GetMapping("/getForm1DylsrData")
	@ResponseStatus(HttpStatus.OK)
	public Response getAllForm1(
			@RequestParam Integer districtCode,
			@RequestParam Integer talukaCode,
			@RequestParam String cCode,
			@RequestParam String pin,
			Locale locale,HttpServletRequest request){
		return form1DyslrService.getAllForm1DyslrData(districtCode,talukaCode,cCode,pin,locale,request);
	}
	
	@PostMapping("/editForm1DyslrAkarband")
	@ResponseStatus(HttpStatus.OK)
	public MessageResponse updateForm1Dyslr( @Valid @RequestBody Form1DyslrDao form1DyslrDao,HttpServletRequest request,Locale locale){
		try {
			form1DyslrService.updateForm1Dyslr(form1DyslrDao,request,locale);
			return MessageResponse.builder().status("SUCCESS").message(
					messageSource.getMessage("error.form.successmessage", null, locale)).build();
		}
		catch (Exception ex) {
			throw new ExchangeException("error.form.editrecord");
		}
	}
	//08/07/2026 new added api for dyslr by shubham
	@GetMapping("/getDyslrForm1ReportFooter")
	public  List<Form1AbstractDyslrDao> getDyslrForm1FooterDetails(@RequestParam Integer districtCode,
			@RequestParam Integer talukaCode,
			@RequestParam ("cCode") String cCode,Locale locale,HttpServletRequest request)  {
		return form1DyslrService.getDyslrForm1FooterDetails(districtCode,talukaCode,cCode, locale,request);
	}
	
	//Dyslr Report///
	@GetMapping("/getForm1AbstractReportDyslr")
	public Form1AbstractDyslrDao getForm1AbstractReportDyslr(@RequestParam Integer districtCode,@RequestParam Integer talukaCode,@RequestParam String cCode,Locale locale,HttpServletRequest request) {
		return form1DyslrService.getForm1AbstractDyslrReport(districtCode,talukaCode,cCode,locale,request);
	}

	//Dyslr  Report///
					@GetMapping("/getnewForm1AbstractReportDyslr")
					public Form1DyslrDao getNewForm1DyslrReport(@RequestParam Integer districtCode,@RequestParam Integer talukaCode,@RequestParam String cCode,Locale locale,HttpServletRequest request) {
						return form1DyslrService.getNewForm1DyslrReport(districtCode,talukaCode,cCode,locale,request);
					}



}
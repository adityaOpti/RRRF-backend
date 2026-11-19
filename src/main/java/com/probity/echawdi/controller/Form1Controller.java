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

import com.probity.echawdi.dao.Form1AbstractDao;
import com.probity.echawdi.dao.Form1Dao;
import com.probity.echawdi.dao.Form1HeaderDao;
import com.probity.echawdi.dao.NewForm1DyslrDao;
import com.probity.echawdi.exception.ExchangeException;
import com.probity.echawdi.exception.MessageResponse;
import com.probity.echawdi.payload.Response;
import com.probity.echawdi.service.Form1Service;

/**
 * @author Rushikesh Durgude
 *
 */

@RestController
@PreAuthorize("hasRole('ROLE_DYSLR')")
@RequestMapping("/api/form1")
public class Form1Controller {

	@Autowired
	private Form1Service form1Service;

	@Autowired
	private MessageSource messageSource;


	@GetMapping("/getForm1Data")
	
	@ResponseStatus(HttpStatus.OK)
	public Response getAllForm1(
			@RequestParam Integer districtCode,
			@RequestParam Integer talukaCode,
			@RequestParam String cCode,
			@RequestParam String pin,
			Locale locale,HttpServletRequest request){
		return form1Service.getAllForm1Data(districtCode,talukaCode,cCode,pin,locale,request);
	}

	@GetMapping(value = "/getForm1DataById")
	@ResponseStatus(HttpStatus.OK)
	public Form1Dao  getForm1ById(@RequestParam long id,Locale locale,HttpServletRequest request){
		return form1Service.getForm1ById(id,locale,request);
	}


	@PostMapping("/editForm1")
	@ResponseStatus(HttpStatus.OK)
	public MessageResponse updateForm1( @Valid @RequestBody Form1Dao form1Dao,HttpServletRequest request,Locale locale){
		try {
			form1Service.updateForm1(form1Dao,request,locale);
			return MessageResponse.builder().status("SUCCESS").message(
					messageSource.getMessage("error.form.successmessage", null, locale)).build();
		}
		catch (Exception ex) {
			throw new ExchangeException("error.form.editrecord");
		}
	}

	@DeleteMapping("/discardForm1")
	@ResponseStatus(HttpStatus.IM_USED)
	public MessageResponse deleteForm1(@RequestParam long id,Locale locale,HttpServletRequest request){
		try {
			form1Service.deleteForm1ById(id, locale,request);
			return MessageResponse.builder().status("SUCCESS").message(
					messageSource.getMessage("error.form.deletemessage", null, locale)).build();	
		}
		catch (Exception ex) {
			throw new ExchangeException("error.form.deleterecord");
		}


	}

	@GetMapping("/getForm1Report")
	public Response getForm1ReportDetails(@RequestParam Integer districtCode,
			@RequestParam Integer talukaCode,
			@RequestParam ("cCode") String cCode,Locale locale,HttpServletRequest request)  {
		return form1Service.getForm1ReportDetails(districtCode,talukaCode,cCode, locale,request);
	}
	
	@GetMapping("/getForm1ReportFooter")
	public  List<Form1AbstractDao> getForm1FooterDetails(@RequestParam Integer districtCode,
			@RequestParam Integer talukaCode,
			@RequestParam ("cCode") String cCode,Locale locale,HttpServletRequest request)  {
		return form1Service.getForm1FooterDetails(districtCode,talukaCode,cCode, locale,request);
	}

	@GetMapping(value = "/getForm1HeaderDetails")
	@ResponseStatus(HttpStatus.OK)
	public List<Form1HeaderDao> getForm1headerdetails(@RequestParam Integer districtCode,@RequestParam Integer talukaCode,@RequestParam String cCode,Locale locale,HttpServletRequest request){
		return form1Service.getForm1headerdetails(districtCode,talukaCode,cCode,locale,request);
	}


	@GetMapping("/getForm1ODCReport")
	public List<Form1Dao> getForm1ODCReportDetails(@RequestParam String cCode,Locale locale,HttpServletRequest request)  {
		return form1Service.getForm1ODCReportDetails(cCode, locale,request);
	}
	@PreAuthorize("hasAnyAuthority('ROLE_TALATHI','ROLE_TAH')")
	@GetMapping("/form1OdcDiff")
	@ResponseStatus(HttpStatus.OK)
	public List<Form1Dao> form1OdcDiff(@RequestParam String cCode,Locale locale,HttpServletRequest request){
		return form1Service.form1OdcDiff(cCode,locale,request);
	}
	
//new api form1 Dsylr mis
@GetMapping("/form1OdcDyslrDiff")
	@ResponseStatus(HttpStatus.OK)
	public List<NewForm1DyslrDao> form1OdcDyslrDiff(@RequestParam String cCode,Locale locale,HttpServletRequest request){
		return form1Service.form1OdcDyslrDiff(cCode,locale,request);
	}

}
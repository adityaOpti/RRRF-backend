/**

 * This Controller is Describes data persistence and retrieval of echawdi form 1a
 *  It Includes CRUD OPERATIONS ON echawdi form 1a
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.probity.echawdi.dao.Form1ADao;
import com.probity.echawdi.dao.Form7Dao;
import com.probity.echawdi.exception.ExchangeException;
import com.probity.echawdi.exception.MessageResponse;
import com.probity.echawdi.payload.Response;
import com.probity.echawdi.service.Form1AService;
import com.probity.echawdi.service.Form7Service;

/**
 * @author Bhagyashri Lolage
 *
 */

@RestController
@RequestMapping("/api/form1a")
public class Form1AController {
	
	@Autowired
	private Form1AService form1AService;
	@Autowired
	private Form7Service form7Service;
	@Autowired
	private MessageSource messageSource;

	@GetMapping("/getForm1AArea")    //form7
   	@ResponseStatus(HttpStatus.OK)
    public List<Form7Dao> getForm1AArea(@RequestParam String cCode,
    									@RequestParam String pin,
    									@RequestParam String hissaNo,
    									HttpServletRequest request,
    									Locale locale) {
       	 return form7Service.getForm1AArea(request,cCode,pin,hissaNo, locale);
    }
    
	@GetMapping("/getForm1AData")
	@ResponseStatus(HttpStatus.OK)
	public Response getAllForm1As(@RequestParam String cCode,
//			@RequestParam String pin,
			HttpServletRequest request,Locale locale ) {
	 return form1AService.getAllForm1AData(cCode,request,locale);
		
	}
	
	@GetMapping("/getForm1AReport")  // form1A
	@ResponseStatus(HttpStatus.OK)
	public Response getForm1AReport(@RequestParam ("cCode")String cCode,HttpServletRequest request,Locale locale) {
		return form1AService.getForm1AReport(cCode,request, locale);
	}
	
	@GetMapping("/getForm1ADataById")
	@ResponseStatus(HttpStatus.OK)
	public Form1ADao  getForm1AById(@RequestParam long id,HttpServletRequest request,Locale locale){
		return form1AService.getForm1AById(id,request,locale);	
	}

    @PostMapping("/saveForm1A")
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponse createForm1A(@Valid @RequestBody Form1ADao form1ADao,HttpServletRequest request,Locale locale){
		return form1AService.save(form1ADao,request, locale);
    }

    @PutMapping("/editForm1A")
    @ResponseStatus(HttpStatus.OK)
    public MessageResponse updateForm1A(@Valid @RequestBody Form1ADao form1ADao,HttpServletRequest request,Locale locale){
    	try {
    		form1AService.updateForm1A(form1ADao,request, locale);
    		return MessageResponse.builder().status("SUCCESS").message(
    				messageSource.getMessage("error.form.successmessage", null, locale)).build();
    	}
      	catch (Exception ex) {
	         throw new ExchangeException("error.form.editrecord");
	}
    }

    @DeleteMapping("/discardForm1A")
    @ResponseStatus(HttpStatus.IM_USED)
    public MessageResponse deleteForm1A(@RequestParam long id,HttpServletRequest request,Locale locale){
    	try {
    		   form1AService.deleteForm1AById(id,request,locale);
    		return MessageResponse.builder().status("SUCCESS").message(
    				messageSource.getMessage("error.form.deletemessage", null, locale)).build();	
		}
    	catch (Exception ex) {
 	         throw new ExchangeException("error.form.deleterecord");
    	}
    }
}
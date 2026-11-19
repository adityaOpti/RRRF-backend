/**

 * This Controller is Describes data persistence and retrieval of echawdi form 1b
 *  It Includes CRUD OPERATIONS ON echawdi form 1b
 */
package com.probity.echawdi.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.probity.echawdi.dao.Form1BDao;
import com.probity.echawdi.exception.ExchangeException;
import com.probity.echawdi.exception.MessageResponse;
import com.probity.echawdi.payload.Response;
import com.probity.echawdi.service.Form1BService;

/**
 * @author Bhagyashri Lolage
 *
 */

@RestController
@RequestMapping("/api/form1b")
public class Form1BController {
	
	@Autowired
	private Form1BService form1BService;
	
	@Autowired
	private MessageSource messageSource;

	@GetMapping("/getForm1BData")
	@ResponseStatus(HttpStatus.OK)
	public Response getAllForm1B(
			@RequestParam String cCode,
			@RequestParam String pin,
            HttpServletRequest request,
            Locale locale){
	 return form1BService.getAllForm1BData(cCode,pin,request,locale);
    }
	
	@GetMapping("/getForm1BDataById")
	@ResponseStatus(HttpStatus.OK)
	public Form1BDao  getForm1BById(@RequestParam long id,HttpServletRequest request,Locale locale){
		return form1BService.getForm1BById(id, request,locale);
	}

//    @PostMapping("/saveForm1B")
//    @ResponseStatus(HttpStatus.CREATED)
//    public MessageResponse createForm1B(@Valid @RequestBody Form1BDao form1BDao,HttpServletRequest request,Locale locale){
//     	return form1BService.save(form1BDao,request,locale);
//    }

    @PostMapping("/editForm1B")
    @ResponseStatus(HttpStatus.OK)
    public MessageResponse updateForm1B(@Valid @RequestBody Form1BDao form1BDao,HttpServletRequest request, Locale locale){
    		try {
    			form1BService.updateForm1B(form1BDao, request,locale);
    	  		return MessageResponse.builder().status("SUCCESS").message(
        				messageSource.getMessage("error.form.successmessage", null, locale)).build();
        	}
          	catch (Exception ex) {
    	         throw new ExchangeException("error.form.editrecord");
    	}
    }

    @DeleteMapping("/discardForm1B")
    @ResponseStatus(HttpStatus.IM_USED)
    public MessageResponse deleteForm1B(@RequestParam long id,HttpServletRequest request,Locale locale){
    	try {
    		form1BService.deleteForm1BById(id,request, locale);
			return MessageResponse.builder().status("SUCCESS").message(
					messageSource.getMessage("error.form.deletemessage", null, locale)).build();	
		}
		catch (Exception ex) {
			 throw new ExchangeException("error.form.deleterecord");
	}

    }
//    @GetMapping("/getFormsAssesment")
// 	@ResponseStatus(HttpStatus.OK)
//    public Response getForm1BAssesment(@RequestParam String cCode,@RequestParam String pin,@RequestParam String hissaNo,HttpServletRequest request,Locale locale) {
//     	 return form1BService.getForm1BAssesment(cCode,pin,hissaNo,request,locale);
//    }

    @GetMapping("/getForm1BReport")
	@ResponseStatus(HttpStatus.OK)
	public Response getForm1BReport(@RequestParam ("cCode")String cCode,HttpServletRequest request,Locale locale) {
   	 return form1BService.getForm1BReport(cCode,request,locale);
    }
}
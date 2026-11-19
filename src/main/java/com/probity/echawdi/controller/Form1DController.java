/**

 * This Controller is Describes data persistence and retrieval of echawdi form 1d
 *  It Includes CRUD OPERATIONS ON echawdi form 1d
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

import com.probity.echawdi.dao.Form1DDao;
import com.probity.echawdi.exception.ExchangeException;
import com.probity.echawdi.exception.MessageResponse;
import com.probity.echawdi.payload.Response;
import com.probity.echawdi.service.Form1DService;

/**
 * @author Bhagyashri Lolage
 *
 */

@RestController
@RequestMapping("/api/form1d")
public class Form1DController {
	
	@Autowired
	private Form1DService form1DService;
	
	@Autowired
	private MessageSource messageSource;
	
	@GetMapping("/getForm1DData")
	@ResponseStatus(HttpStatus.OK)
	public Response getAllForm1Ds(@RequestParam String cCode,
								  @RequestParam String pin,
						          HttpServletRequest request,
								  Locale locale){
        return form1DService.getAllForm1DData(cCode,pin,request,locale);
    }
	
	@GetMapping("/getForm1DDataById")
	@ResponseStatus(HttpStatus.OK)
	public Form1DDao  getForm1DById(@RequestParam long id,
									HttpServletRequest request,
									Locale locale){
		return form1DService.getForm1DById(id,request,locale);
	}

    @PostMapping("/saveForm1D")
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponse createForm1D(@Valid @RequestBody Form1DDao form1DDao,
    									HttpServletRequest request,
    									Locale locale){
        return form1DService.save(form1DDao,request,locale);
    }

    @PostMapping("/editForm1D")
    @ResponseStatus(HttpStatus.OK)
    public MessageResponse updateForm1D(@Valid @RequestBody Form1DDao form1DDao,
    									HttpServletRequest request,
    									Locale locale){
    		try {
    			form1DService.updateForm1D(form1DDao,request,locale);
    	 		return MessageResponse.builder().status("SUCCESS").message(
        				messageSource.getMessage("error.form.successmessage", null, locale)).build();
        	}
          	catch (Exception ex) {
    	         throw new ExchangeException("error.form.editrecord");
    	}
    }

    @DeleteMapping("/discardForm1D")
    @ResponseStatus(HttpStatus.IM_USED)
    public MessageResponse deleteForm1D(@RequestParam long id ,
    									HttpServletRequest request,
    									Locale locale){
    	 try {
    		 form1DService.deleteForm1DById(id,request,locale);
    			return MessageResponse.builder().status("SUCCESS").message(
						messageSource.getMessage("error.form.deletemessage", null, locale)).build();	
			}
			catch (Exception ex) {
				 throw new ExchangeException("error.form.deleterecord");
		}

    }
    
//	@GetMapping("/getForm1DArea")
//	@ResponseStatus(HttpStatus.OK)
//	public List<Form1DDao> getForm1DArea(@RequestParam String cCode,
//										 @RequestParam String pin,
//										 @RequestParam String hissaNo,
//										 HttpServletRequest request,Locale locale) {
//	     return form1DService.getForm1DArea(cCode,pin,hissaNo,request,locale);
//	}
    
    @GetMapping("/getForm1DReport")
	@ResponseStatus(HttpStatus.OK)
	public Response getForm1DReport(@RequestParam String cCode,
									HttpServletRequest request,
									Locale locale) {
   	 return form1DService.getForm1DReport(cCode,request,locale);
    }
}
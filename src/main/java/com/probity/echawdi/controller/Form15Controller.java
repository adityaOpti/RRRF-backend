/**
 * 
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.probity.echawdi.dao.Form15Dao;
import com.probity.echawdi.exception.ExchangeException;
import com.probity.echawdi.exception.MessageResponse;
import com.probity.echawdi.payload.Response;
import com.probity.echawdi.service.Form15Service;

/**
 * @author Bhagyashri Lolage
 *
 */

@RestController
@RequestMapping("/api/form15")
public class Form15Controller {
	
	@Autowired
	private Form15Service form15Service;
	
	@Autowired
	private MessageSource messageSource;
	
	
	@GetMapping("/getForm15Data")
	@ResponseStatus(HttpStatus.OK)
	public Response getAllForm15(
			@RequestParam String sajjaCode,
//			@RequestParam String userId,
            HttpServletRequest request,Locale locale){
		    return  form15Service.getAllForm15Data(sajjaCode,request,locale);
	}
	
	@PostMapping("/saveForm15")
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponse createForm15(@Valid  @RequestBody Form15Dao form15Dao,HttpServletRequest request,Locale locale){
		return form15Service.save(form15Dao,request,locale);
	}


    @PutMapping("/editForm15")
    @ResponseStatus(HttpStatus.OK)
    public MessageResponse updateForm15(@Valid @RequestBody Form15Dao form15Dao,HttpServletRequest request, Locale locale){
    		try {
    			form15Service.updateForm15(form15Dao,request, locale);
    			return MessageResponse.builder().status("SUCCESS").message(
        				messageSource.getMessage("error.form.successmessage", null, locale)).build();
    		} 
    	   	catch (Exception ex) {
   	         throw new ExchangeException("error.form.editrecord");
       	}
    }
    
    @DeleteMapping("/discardForm15")
    @ResponseStatus(HttpStatus.IM_USED)
    public MessageResponse deleteForm15(@RequestParam long id,HttpServletRequest request,Locale locale){
    	   try {
    		   form15Service.deleteForm15ById(id,request, locale);
    		   return MessageResponse.builder().status("SUCCESS").message(
       				messageSource.getMessage("error.form.deletemessage", null, locale)).build();	
   		}
       	catch (Exception ex) {
    	         throw new ExchangeException("error.form.deleterecord");
    	}
    }
}
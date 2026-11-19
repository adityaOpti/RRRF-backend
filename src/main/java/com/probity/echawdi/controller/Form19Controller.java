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

import com.probity.echawdi.dao.Form19Dao;
import com.probity.echawdi.exception.ExchangeException;
import com.probity.echawdi.exception.MessageResponse;
import com.probity.echawdi.payload.Response;
import com.probity.echawdi.service.Form19Service;

/**
 * @author Rushikesh Durgude
 *
 */
@RestController
@RequestMapping("/api/form19")
public class Form19Controller {

	
	@Autowired
	private Form19Service form19Service;
	
	@Autowired
	private MessageSource messageSource;

	@GetMapping("/getForm19Data")
	@ResponseStatus(HttpStatus.OK)
	public Response getAllForm19(
			@RequestParam String userId,
			@RequestParam String sajjaCode,
            HttpServletRequest request,
            Locale locale){
			return form19Service.getAllForm19Data(userId,sajjaCode,request,locale);
		}
	
	
	@PostMapping("/saveForm19")
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponse createForm19(@Valid  @RequestBody Form19Dao form19Dao,HttpServletRequest request,Locale locale){
		return form19Service.save(form19Dao,request,locale);
	}

    @PutMapping("/editForm19")
    @ResponseStatus(HttpStatus.OK)
    public MessageResponse updateForm19(@Valid @RequestBody Form19Dao form19Dao,HttpServletRequest request, Locale locale){
    		try {
    			form19Service.updateForm19(form19Dao,request, locale);
    			return MessageResponse.builder().status("SUCCESS").message(
        				messageSource.getMessage("error.form.successmessage", null, locale)).build();
    		} 
    	   	catch (Exception ex) {
   	         throw new ExchangeException("error.form.editrecord");
       	}
    }
    
    @DeleteMapping("/discardForm19")
    @ResponseStatus(HttpStatus.IM_USED)
    public MessageResponse deleteForm19(@RequestParam long id,HttpServletRequest request,Locale locale){
    	   try {
    		   form19Service.deleteForm19ById(id, request,locale);
    		   return MessageResponse.builder().status("SUCCESS").message(
       				messageSource.getMessage("error.form.deletemessage", null, locale)).build();	
   		}
       	catch (Exception ex) {
    	         throw new ExchangeException("error.form.deleterecord");
    	}
    }


}

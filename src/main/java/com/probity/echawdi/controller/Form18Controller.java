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

import com.probity.echawdi.dao.Form18Dao;
import com.probity.echawdi.exception.ExchangeException;
import com.probity.echawdi.exception.MessageResponse;
import com.probity.echawdi.payload.Response;
import com.probity.echawdi.service.Form18Service;
import com.probity.echawdi.utils.CommonConstants;

/**
 * @author Rushikesh Durgude
 *
 */
@RestController
@RequestMapping("/api/form18")
public class Form18Controller {

	
	@Autowired
	private Form18Service form18Service;
	
	@Autowired
	private MessageSource messageSource;

	@GetMapping("/getForm18Data")
	@ResponseStatus(HttpStatus.OK)
	public Response getAllForm18(
			@RequestParam Integer districtCode,
			@RequestParam Integer talukaCode,
			@RequestParam String cCode,
            @RequestParam(value = "pageNo", defaultValue = CommonConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = CommonConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = CommonConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = CommonConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
            ,HttpServletRequest request,Locale locale){
		    return  form18Service.getAllForm18Data(districtCode,talukaCode,cCode,pageNo, pageSize, sortBy, sortDir,request,locale);
	}
	
	@PostMapping("/saveForm18")
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponse createForm18(@Valid  @RequestBody Form18Dao form18Dao,HttpServletRequest request,Locale locale){
		return form18Service.save(form18Dao,request,locale);
	}
	
    @PutMapping("/editForm18")
    @ResponseStatus(HttpStatus.OK)
    public MessageResponse updateForm18(@Valid @RequestBody Form18Dao form18Dao, HttpServletRequest request,Locale locale){
    		try {
    			form18Service.updateForm18(form18Dao,request, locale);
    			return MessageResponse.builder().status("SUCCESS").message(
        				messageSource.getMessage("error.form.successmessage", null, locale)).build();
    		} 
    	   	catch (Exception ex) {
   	         throw new ExchangeException("error.form.editrecord");
       	}
    }
    
    @DeleteMapping("/discardForm18")
    @ResponseStatus(HttpStatus.IM_USED)
    public MessageResponse deleteForm18(@RequestParam long id,HttpServletRequest request,Locale locale){
    	   try {
    		   form18Service.deleteForm18ById(id,request, locale);
    		   return MessageResponse.builder().status("SUCCESS").message(
       				messageSource.getMessage("error.form.deletemessage", null, locale)).build();	
   		}
       	catch (Exception ex) {
    	         throw new ExchangeException("error.form.deleterecord");
    	}
    }
}
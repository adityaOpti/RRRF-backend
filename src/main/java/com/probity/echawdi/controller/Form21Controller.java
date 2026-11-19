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

import com.probity.echawdi.dao.Form21Dao;
import com.probity.echawdi.exception.ExchangeException;
import com.probity.echawdi.exception.MessageResponse;
import com.probity.echawdi.payload.Response;
import com.probity.echawdi.service.Form21Service;
import com.probity.echawdi.utils.CommonConstants;

/**
 * @author Rushikesh Durgude
 *
 */
@RestController
@RequestMapping("/api/form21")
public class Form21Controller {

	
	@Autowired
	private Form21Service form21Service;
	
	@Autowired
	private MessageSource messageSource;

	@GetMapping("/getForm21Data")
	@ResponseStatus(HttpStatus.OK)
	public Response getAllForm21(
			@RequestParam String userId,
            @RequestParam(value = "pageNo", defaultValue = CommonConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = CommonConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = CommonConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = CommonConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir,
            HttpServletRequest request,
            Locale locale){
		    return  form21Service.getAllForm21Data(userId,pageNo, pageSize, sortBy, sortDir,request,locale);
	}
	
	@PostMapping("/saveForm21")
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponse createForm21(@Valid  @RequestBody Form21Dao form21Dao,HttpServletRequest request,Locale locale){
		return form21Service.save(form21Dao,request,locale);
	}

    @PutMapping("/editForm21")
    @ResponseStatus(HttpStatus.OK)
    public MessageResponse updateForm21(@Valid @RequestBody Form21Dao form21Dao,HttpServletRequest request, Locale locale){
    		try {
    			form21Service.updateForm21(form21Dao,request, locale);
    			return MessageResponse.builder().status("SUCCESS").message(
        				messageSource.getMessage("error.form.successmessage", null, locale)).build();
    		} 
    	   	catch (Exception ex) {
   	         throw new ExchangeException("error.form.editrecord");
       	}
    }
    
    @DeleteMapping("/discardForm21")
    @ResponseStatus(HttpStatus.IM_USED)
    public MessageResponse deleteForm21(@RequestParam long id,HttpServletRequest request,Locale locale){
    	   try {
    		   form21Service.deleteForm21ById(id,request, locale);
    		   return MessageResponse.builder().status("SUCCESS").message(
       				messageSource.getMessage("error.form.deletemessage", null, locale)).build();	
   		}
       	catch (Exception ex) {
    	         throw new ExchangeException("error.form.deleterecord");
    	}
    }
}

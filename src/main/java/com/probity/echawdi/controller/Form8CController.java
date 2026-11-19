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

import com.probity.echawdi.dao.Form8CDao;
import com.probity.echawdi.exception.ExchangeException;
import com.probity.echawdi.exception.MessageResponse;
import com.probity.echawdi.payload.Response;
import com.probity.echawdi.service.Form8CService;
import com.probity.echawdi.utils.CommonConstants;

/**
 * @author Bhagyashri Lolage
 *
 */
@RestController
@RequestMapping("/api/form8c")
public class Form8CController {
	
	@Autowired
	private Form8CService form8CService;
	
	@Autowired
	private MessageSource messageSource;
	
	
	@GetMapping("/getForm8CData")
	@ResponseStatus(HttpStatus.OK)
	public Response getAllForm8C(
			@RequestParam Integer districtCode,
			@RequestParam Integer talukaCode,
			@RequestParam String cCode,
            @RequestParam(value = "pageNo", defaultValue = CommonConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = CommonConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = CommonConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = CommonConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
            ,HttpServletRequest request,Locale locale){
		    return  form8CService.getAllForm8CData(districtCode,talukaCode,cCode,pageNo, pageSize, sortBy, sortDir,request,locale);
	}
	
	@PostMapping("/saveForm8C")
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponse createForm8C(@Valid  @RequestBody Form8CDao Form8CDao,HttpServletRequest request,Locale locale){
		return form8CService.save(Form8CDao,request,locale);
	}


    @PutMapping("/editForm8C")
    @ResponseStatus(HttpStatus.OK)
    public MessageResponse updateForm8C(@Valid @RequestBody Form8CDao Form8CDao,HttpServletRequest request, Locale locale){
    		try {
    			form8CService.updateForm8C(Form8CDao,request, locale);
    			return MessageResponse.builder().status("SUCCESS").message(
        				messageSource.getMessage("error.form.successmessage", null, locale)).build();
    		} 
    	   	catch (Exception ex) {
   	         throw new ExchangeException("error.form.editrecord");
       	}
    }
    
    @DeleteMapping("/discardForm8C")
    @ResponseStatus(HttpStatus.IM_USED)
    public MessageResponse deleteForm8C(@RequestParam long id,HttpServletRequest request,Locale locale){
    	   try {
    		   form8CService.deleteForm8CById(id, request,locale);
    		   return MessageResponse.builder().status("SUCCESS").message(
       				messageSource.getMessage("error.form.deletemessage", null, locale)).build();	
   		}
       	catch (Exception ex) {
    	         throw new ExchangeException("error.form.deleterecord");
    	}
    }
}
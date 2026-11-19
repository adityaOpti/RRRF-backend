
/**
 * 
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

import com.probity.echawdi.dao.Form14Dao;
import com.probity.echawdi.exception.ExchangeException;
import com.probity.echawdi.exception.MessageResponse;
import com.probity.echawdi.payload.Response;
import com.probity.echawdi.service.Form14Service;

/**
 * @author Bhagyashri Lolage
 *
 */
@RestController
@RequestMapping("/api/form14")
public class Form14Controller {
	
	@Autowired
	private Form14Service form14Service;
	
	@Autowired
	private MessageSource messageSource;

	@GetMapping("/getForm14Data")
	@ResponseStatus(HttpStatus.OK)
	public Response getAllForm14(HttpServletRequest request, @RequestParam String cCode,Locale locale){
		    return  form14Service.getAllForm14Data(request,cCode,locale);
	}
	
	@GetMapping("/getForm14GhoshwaraReport")
	@ResponseStatus(HttpStatus.OK)
	public Response getForm14GhoshwaraReport(HttpServletRequest request, @RequestParam String cCode,Locale locale){
		    return  form14Service.getForm14GhoshwaraReport(request,cCode,locale);
	}
	
	@PostMapping("/saveForm14")
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponse createForm14(HttpServletRequest request, @Valid  @RequestBody Form14Dao form14Dao,Locale locale){
		return form14Service.save(request,form14Dao,locale);
	}
	
	 @PutMapping("/editForm14")
	    @ResponseStatus(HttpStatus.OK)
	    public MessageResponse updateForm14(HttpServletRequest request,@Valid  @RequestBody Form14Dao form14Dao, Locale locale){
	    		try {
	    			form14Service.updateForm14(request,form14Dao, locale);
	    			return MessageResponse.builder().status("SUCCESS").message(
	        				messageSource.getMessage("error.form.successmessage", null, locale)).build();
	    		} 
	    	   	catch (Exception ex) {
	   	         throw new ExchangeException("error.form.editrecord");
	       	}
	    }
	    
	    @DeleteMapping("/discardForm14")
	    @ResponseStatus(HttpStatus.IM_USED)
	    public MessageResponse deleteForm14(HttpServletRequest request,@RequestParam long id,Locale locale){
	    	   try {
	    		   form14Service.deleteForm14ById(request,id, locale);
	    		   return MessageResponse.builder().status("SUCCESS").message(
	       				messageSource.getMessage("error.form.deletemessage", null, locale)).build();	
	   		}
	       	catch (Exception ex) {
	    	         throw new ExchangeException("error.form.deleterecord");
	    	}
	    }
	    
	    @GetMapping("/getForm14EferfarDetails")   
	   	@ResponseStatus(HttpStatus.OK)
	    public List<Form14Dao> getForm14EferfarDetials(HttpServletRequest request,@RequestParam String cCode,@RequestParam String pin,@RequestParam String hissaNo,Locale locale) {
	       	 return form14Service.getForm14EferfarDetials(request,cCode,pin,hissaNo,locale);
	    }
	    
	  
	    @GetMapping("/getForm14Report")
		@ResponseStatus(HttpStatus.OK)
		public Response getAllForm14report(
				HttpServletRequest request,	@RequestParam String districtCode,
				@RequestParam String talukaCode,
				@RequestParam String cCode , Locale locale){
			    return  form14Service.getAllForm14ReportDetails(request,cCode,locale);
		}
	    
	    
}
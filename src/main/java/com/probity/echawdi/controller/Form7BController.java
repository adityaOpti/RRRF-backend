
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

import com.probity.echawdi.dao.Form7BDao;
import com.probity.echawdi.exception.ExchangeException;
import com.probity.echawdi.exception.MessageResponse;
import com.probity.echawdi.payload.Response;
import com.probity.echawdi.service.Form7BService;

/**
 * @author Bhagyashri Lolage
 *
 */
@RestController
@RequestMapping("/api/form7b")
public class Form7BController {
	
	@Autowired
	private Form7BService form7BService;
	
	@Autowired
	MessageSource messageSource;
	
	@GetMapping("/getForm7BData")
	@ResponseStatus(HttpStatus.OK)
	public Response getAllForm7B(
				@RequestParam String cCode,
				@RequestParam String pin,
	            Locale locale,
	            HttpServletRequest request){
	        return form7BService.getAllForm7BData(cCode,pin,locale,request);
	    }
	
	    @PostMapping("/saveForm7B")
	    @ResponseStatus(HttpStatus.CREATED)
	    public MessageResponse createForm7B(@Valid @RequestBody Form7BDao form7BDao,Locale locale,HttpServletRequest request){
	        return form7BService.save(form7BDao,locale,request);
	    }
	 
	
	    @PutMapping("/editForm7B")
	    @ResponseStatus(HttpStatus.OK)
	    public MessageResponse updateForm7B(@Valid @RequestBody Form7BDao form7BDao,Locale locale,HttpServletRequest request){
	    		try {
	    			form7BService.updateForm7B(form7BDao,locale,request);
	    			return MessageResponse.builder().status("SUCCESS").message(
	        				messageSource.getMessage("error.form.successmessage", null, locale)).build();
	        	}
	          	catch (Exception ex) {
	    	         throw new ExchangeException("error.form.editrecord");
	    	}
	    }

	    @DeleteMapping("/discardForm7B")
	    @ResponseStatus(HttpStatus.IM_USED)
	    public MessageResponse deleteForm7B(@RequestParam long id,Locale locale,HttpServletRequest request){
	    	 try {
	    		 form7BService.deleteForm7BById(id,locale,request);
	    		 return MessageResponse.builder().status("SUCCESS").message(
							messageSource.getMessage("error.form.deletemessage", null, locale)).build();	
				}
				catch (Exception ex) {
					 throw new ExchangeException("error.form.deleterecord");
		}
	    
    }	
		@GetMapping("/getForm7BReport")
		@ResponseStatus(HttpStatus.OK)
		public Response getAllForm7BReport(
					@RequestParam String cCode,
		            Locale locale,
		            HttpServletRequest request){
		        return form7BService.getAllForm7BReportDetails(cCode,locale,request);
		    }
		

		@GetMapping("/getKhataNodetails")
		@ResponseStatus(HttpStatus.OK)
		public Response getKhataNodetails( @RequestParam String cCode,@RequestParam String pin,@RequestParam String hissaNo, Locale locale,HttpServletRequest request) {
			return form7BService.getKhataNodetails(cCode,pin,hissaNo,locale,request);
		}
}

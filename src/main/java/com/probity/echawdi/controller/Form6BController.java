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

import com.probity.echawdi.dao.Form6BDao;
import com.probity.echawdi.exception.ExchangeException;
import com.probity.echawdi.exception.MessageResponse;
import com.probity.echawdi.payload.Response;
import com.probity.echawdi.service.Form6BService;

@RequestMapping("/api/form6B")
@RestController
public class Form6BController {

	@Autowired
	private Form6BService form6BService;
	
	
	
	@Autowired
	private MessageSource messageSource;
	
	@GetMapping("/getForm6BData")
	@ResponseStatus(HttpStatus.OK)
	public Response getAllForm6B(
			@RequestParam String cCode,
			@RequestParam Integer entryVillageForm6,
			Locale locale,
			HttpServletRequest request
			){
		    return  form6BService.getAllForm6BData(cCode,entryVillageForm6,locale,request);
	}
	
	
		@PostMapping("/saveForm6B")
	    @ResponseStatus(HttpStatus.CREATED)
	    public MessageResponse createForm6B(@Valid @RequestBody Form6BDao form6BDao,Locale locale,HttpServletRequest request){
	        return form6BService.save(form6BDao,locale,request);
	    }
	 
	
		@PutMapping("/editForm6B")
	    @ResponseStatus(HttpStatus.OK)
	    public MessageResponse updateForm6B(@Valid @RequestBody Form6BDao form6BDao,Locale locale,HttpServletRequest request){
	    		try {
	    			form6BService.updateForm6B(form6BDao,locale,request);
	    			return MessageResponse.builder().status("SUCCESS").message(
	        				messageSource.getMessage("error.form.successmessage", null, locale)).build();
	        	}
	          	catch (Exception ex) {
	    	         throw new ExchangeException("error.form.editrecord");
	    	}
	    }
	    
	    @DeleteMapping("/discardForm6B")
	    @ResponseStatus(HttpStatus.IM_USED)
	    public MessageResponse deleteForm6B(@RequestParam  long id,Locale locale,HttpServletRequest request){
	    	try {
	    		form6BService.deleteForm6BById(id,locale,request);
	    		return MessageResponse.builder().status("SUCCESS").message(
						messageSource.getMessage("error.form.deletemessage", null, locale)).build();	
			}
			catch (Exception ex) {
				 throw new ExchangeException("error.form.deleterecord");
		}
	    }
	    
		@GetMapping("/getForm6BReport")
		@ResponseStatus(HttpStatus.OK)
		public Response getAllForm6B(
					@RequestParam String cCode,
		            Locale locale,HttpServletRequest request){
		        return form6BService.getAllForm6BReportDetails(cCode,locale,request);
		    }
		
		  
	    @GetMapping("/getForm6BEferfarDetails")    
	   	@ResponseStatus(HttpStatus.OK)
	    public List<Form6BDao> getForm6BEferfarDetails(
				@RequestParam String cCode,@RequestParam Integer entryVillageForm6,
				HttpServletRequest request,
				Locale locale) {
	       	 return form6BService.getForm6BEferfarDetails(cCode,entryVillageForm6,request,locale);
	    }
	    
	    
	    @GetMapping("/getSankirnReceiptDetails")    
	   	@ResponseStatus(HttpStatus.OK)
	    public List<Form6BDao> getSankirnReceiptDetails(
				@RequestParam String cCode,@RequestParam String khataNo,HttpServletRequest request,Locale locale) {
	       	 return form6BService.getSankirnReceiptDetails(cCode,khataNo,request,locale);
	    }
	 
}
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

import com.probity.echawdi.dao.OneTimeEntryDao;
import com.probity.echawdi.exception.ExchangeException;
import com.probity.echawdi.exception.MessageResponse;
import com.probity.echawdi.payload.Response;
import com.probity.echawdi.service.OneTimeEntryService;

/**
 * @author Bhagyashri Lolage/Rushikesh Durgude
 *
 */

@RestController
@RequestMapping("/api/oneTimeEntry")
public class OneTimeEntryController {
	
	@Autowired
	private OneTimeEntryService oneTimeEntryService;
	@Autowired
	private MessageSource messageSource;

	@GetMapping("/getOneTimeEntryData")
	@ResponseStatus(HttpStatus.OK)
	public Response getOneTimeEntry(
			@RequestParam String cCode,
			@RequestParam String khataNo,
            HttpServletRequest request,
            Locale locale){
        return oneTimeEntryService.getOneTimeEntryDataBycCode(cCode,khataNo,request,locale);
    }
	
	@GetMapping("/getOneTimeEntryDataById")
	@ResponseStatus(HttpStatus.OK)
	public OneTimeEntryDao  getOneTimeEntryById(
			@RequestParam long id,
			HttpServletRequest request,
			Locale locale
			){
		return oneTimeEntryService.getOneTimeEntryById(id,request, locale);
	}
	
    @PostMapping("/saveOneTimeEntry")
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponse createOneTimeEntry(
    		@Valid @RequestBody OneTimeEntryDao oneTimeEntryDao,
    		HttpServletRequest request,
    		Locale locale
    		){
    	return oneTimeEntryService.save(oneTimeEntryDao,request,locale);
    }
  
	@PutMapping("/editOneTimeEntry")
	@ResponseStatus(HttpStatus.OK)
	public MessageResponse updateOneTimeEntry(
			@Valid @RequestBody OneTimeEntryDao oneTimeEntryDao,
			HttpServletRequest request,
			Locale locale
			){
		 try {
			 oneTimeEntryService.updateOneTimeEntry(oneTimeEntryDao,request, locale);
		 		return MessageResponse.builder().status("SUCCESS").message(
	    				messageSource.getMessage("error.form.successmessage", null, locale)).build();
	    	}
	      	catch (Exception ex) {
		         throw new ExchangeException("error.form.editrecord");
		}
	}

    @DeleteMapping("/discardOneTimeEntry")
	@ResponseStatus(HttpStatus.IM_USED)
    public MessageResponse deleteOneTimeEntry(
    		@RequestParam long id,
    		HttpServletRequest request,
    		Locale locale
    		){
	   try {
		   oneTimeEntryService.deleteOneTimeEntryById(id, request,locale);
			return MessageResponse.builder().status("SUCCESS").message(
					messageSource.getMessage("error.form.deletemessage", null, locale)).build();	
		}
		catch (Exception ex) {
			 throw new ExchangeException("error.form.deleterecord");
		}
	}
    
    @GetMapping("/khataNumbers")
    @ResponseStatus(HttpStatus.OK)
    public List<Object> khataNumbers(
    		@RequestParam String cCode,
    		@RequestParam String revenueYear,
    		HttpServletRequest request
    		){
    	return oneTimeEntryService.khataNmbers(cCode,revenueYear,request);
    }
    
    @GetMapping("/getReportOneTimeEntryDetails")
	@ResponseStatus(HttpStatus.OK)
	public Response getOneTimeEntryReport(		
			@RequestParam String cCode,  Locale locale,HttpServletRequest request){
		return  oneTimeEntryService.getOneTimeEntryReportDtls(cCode, locale, request);
	}
}
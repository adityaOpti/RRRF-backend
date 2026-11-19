/**
 * This Controller is Describes data persistence and retrieval of echawdi form 1e


 *  It Includes CRUD OPERATIONS ON echawdi form 1e
 */
package com.probity.echawdi.controller;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

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
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.probity.echawdi.dao.Form1EDao;
import com.probity.echawdi.exception.ExchangeException;
import com.probity.echawdi.exception.MessageResponse;
import com.probity.echawdi.payload.Response;
import com.probity.echawdi.service.Form1EService;

/**
 * @author Rushikesh Durgude
 *
 */

@RestController
@RequestMapping("/api/form1e")
public class Form1EController {
	
	@Autowired
	private Form1EService form1EService;
	@Autowired
	private MessageSource messageSource;
	
	@PostMapping(value="/saveForm1E")
	@ResponseStatus(HttpStatus.CREATED)
	public MessageResponse Form1EDao( @RequestParam(name="file",required=false) MultipartFile file,@RequestParam("form1EDao") String form1EDao,HttpServletRequest request,Locale locale) throws JsonMappingException, JsonProcessingException {		
		return form1EService.saveForm1E(form1EDao,file,request,locale);
	}

    @PostMapping("/editForm1E")
    @ResponseStatus(HttpStatus.OK)
    public MessageResponse updateForm1E(@RequestBody Form1EDao form1EDao,HttpServletRequest request,Locale locale){
		try {
			form1EService.updateForm1E(form1EDao,request,locale);
	 		return MessageResponse.builder().status("SUCCESS").message(messageSource.getMessage("error.form.successmessage", null, locale)).build();
    	}
      	catch (Exception ex) {
	         throw new ExchangeException("error.form.editrecord");
	    }
    }

    @DeleteMapping("/discardForm1E")
    @ResponseStatus(HttpStatus.IM_USED)
    public MessageResponse deleteForm1E(@RequestParam  long id,HttpServletRequest request,Locale locale){
    	try {
    		form1EService.deleteForm1EById(id,request,locale);
    		return MessageResponse.builder().status("SUCCESS").message(messageSource.getMessage("error.form.deletemessage", null, locale)).build();	
		}
		catch (Exception ex) {
			 throw new ExchangeException("error.form.deleterecord");
	    }
    }
    
    @GetMapping("/getForm1EData")
	@ResponseStatus(HttpStatus.OK)
	public Response getAllForm1Es(
								  @RequestParam String cCode,@RequestParam String pin,HttpServletRequest request,Locale locale) {
    return form1EService.getAllForm1EData(cCode,pin,request,locale);
    }
	
	@GetMapping(value = "/getForm1EDataById")
	@ResponseStatus(HttpStatus.OK)
	public Form1EDao  getForm1EById(@RequestParam long id,HttpServletRequest request,Locale locale) throws IOException{
		return form1EService.getForm1EById(id, request,locale);
	}
    
    @GetMapping("/getForm1EReport")
	@ResponseStatus(HttpStatus.OK)
	public Response getForm1EReport(@RequestParam String cCode,HttpServletRequest request,Locale locale) throws IOException {
    	return form1EService.getForm1EReport(cCode,request,locale);
    }
}
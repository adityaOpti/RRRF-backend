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

import com.probity.echawdi.dao.Form6DDao;
import com.probity.echawdi.exception.ExchangeException;
import com.probity.echawdi.exception.MessageResponse;
import com.probity.echawdi.payload.Response;
import com.probity.echawdi.service.Form6DService;

@RequestMapping("/api/form6D")
@RestController
public class Form6DController {

	@Autowired
	private Form6DService form6DService;
	@Autowired
	private MessageSource messageSource;
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/getForm6DData")
	public Response getAllForm6D(
			    @RequestParam String cCode,@RequestParam String pin,
	            Locale locale,
	            HttpServletRequest request){
    return form6DService.getAllForm6DData(cCode,pin,locale,request);
    }

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/saveForm6D")
    public MessageResponse createForm6D(@Valid @RequestBody Form6DDao form6DDao,Locale locale,HttpServletRequest request){
    return form6DService.save(form6DDao,locale,request);
    }

	@ResponseStatus(HttpStatus.OK)
	@PutMapping("/editForm6D")
    public MessageResponse updateForm6D(@Valid @RequestBody Form6DDao form6DDao,Locale locale,HttpServletRequest request){
		try {
			form6DService.updateForm6D(form6DDao,locale,request);
			return MessageResponse.builder().status("SUCCESS").message(
    				messageSource.getMessage("error.form.successmessage", null, locale)).build();
    	}
      	catch (Exception ex) {
	         throw new ExchangeException("error.form.editrecord");
      	}
    }
    
	@ResponseStatus(HttpStatus.IM_USED)
    @DeleteMapping("/discardForm6D")
    public MessageResponse deleteForm6D(@RequestParam  long id,Locale locale,HttpServletRequest request){
    	try {
    		form6DService.deleteForm6DById(id,locale,request);
    		return MessageResponse.builder().status("SUCCESS").message(
					messageSource.getMessage("error.form.deletemessage", null, locale)).build();	
		}
		catch (Exception ex) {
			 throw new ExchangeException("error.form.deleterecord");
		}
    }
    
	@ResponseStatus(HttpStatus.OK)
    @GetMapping("/getForm6DReport")
	public Response getAllForm6D(@RequestParam String cCode,Locale locale,HttpServletRequest request){
		return form6DService.getAllForm6DReportDetails(cCode,locale,request);
    }
    
	@ResponseStatus(HttpStatus.OK)
    @GetMapping("/getForm6DEferfarDetails")    
    public List<Form6DDao> getForm6DEferfarDetials(@RequestParam String cCode,@RequestParam String pin,@RequestParam String hissaNo,Locale locale,HttpServletRequest request) {
        return form6DService.getForm6DEferfarDetials(cCode,pin,hissaNo,locale,request);
    }

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/getForm6DEferfarDetail")    
	public List<Form6DDao> getHissaNo(@RequestParam String cCode,@RequestParam String pin,Locale locale,HttpServletRequest request) {
		return form6DService.getHissaNo(cCode,pin,locale,request);
	}
}
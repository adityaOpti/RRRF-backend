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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.probity.echawdi.dao.Form2Dao;
import com.probity.echawdi.exception.ExchangeException;
import com.probity.echawdi.exception.MessageResponse;
import com.probity.echawdi.payload.Response;
import com.probity.echawdi.service.Form2Service;

/**
 * @author Rushikesh Durgude
 *
 */

@RestController
@RequestMapping("/api/form2")
public class Form2Controller {
	@Autowired
	private Form2Service form2Service;
	
	@Autowired
	private MessageSource messageSource;

	@GetMapping("/getForm2Data")
	@ResponseStatus(HttpStatus.OK)
	public Response getAllForm2(
			@RequestParam Integer districtCode,
			@RequestParam Integer talukaCode,
			@RequestParam String cCode,
			@RequestParam String pin,
            HttpServletRequest request,
            Locale locale){
        return form2Service.getAllForm2Data(cCode,pin,request,locale);
    }
	
	
//	@GetMapping("/getForm2NirankData")
//	@ResponseStatus(HttpStatus.OK)
//	public Response getNirankDataForm2(
//			@RequestParam String cCode,
//            HttpServletRequest request,
//            Locale locale){
//        return form2Service.getNirankDataForm2(cCode,request,locale);
//    }


    @PutMapping("/editForm2")
    @ResponseStatus(HttpStatus.OK)
    public MessageResponse updateForm2(@Valid @RequestBody Form2Dao form2Dao,HttpServletRequest request,Locale locale){
    	try {
    		form2Service.updateForm2(form2Dao,request,locale);
    		return MessageResponse.builder().status("SUCCESS").message(
    				messageSource.getMessage("error.form.successmessage", null, locale)).build();
		} 
	   	catch (Exception ex) {
	         throw new ExchangeException("error.form.editrecord");
   	    }
    }

    @DeleteMapping("/discardForm2")
    @ResponseStatus(HttpStatus.IM_USED)
    public MessageResponse deleteForm2(@RequestParam long id,HttpServletRequest request,Locale locale){
    	try {
    		   form2Service.deleteForm2ById(id,request,locale);
    		   return MessageResponse.builder().status("SUCCESS").message(
          				messageSource.getMessage("error.form.deletemessage", null, locale)).build();	
      		}
          	catch (Exception ex) {
       	         throw new ExchangeException("error.form.deleterecord");
       	}
    }
    
    @GetMapping("/getForm2Report")
    @ResponseStatus(HttpStatus.OK)
	public Response getForm2ReportDetails(@RequestParam String cCode,HttpServletRequest request,Locale locale)  {
	    		return form2Service.getForm2ReportDetails(cCode,request,locale);
	}
    
//    @GetMapping("/getFormsAssesment")
// 	@ResponseStatus(HttpStatus.OK)
//    public Response getForm1BAssesment(@RequestParam String cCode,@RequestParam String pin,@RequestParam String hissaNo,HttpServletRequest request,Locale locale) {
//     	 return form2Service.getForm2AreaAndAssesment(cCode,pin,hissaNo,request,locale);
//    }
}
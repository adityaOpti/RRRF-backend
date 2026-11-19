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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.probity.echawdi.dao.Form1AbstractDao;
import com.probity.echawdi.dao.Form1AbstractDyslrDao;
import com.probity.echawdi.exception.ExchangeException;
import com.probity.echawdi.exception.MessageResponse;
import com.probity.echawdi.service.Form1AbstractService;

/**
 * @author Bhagyashri Lolage
 *
 */

@RestController
@RequestMapping("/api/form1Abstract")
public class Form1AbstractController {

	@Autowired
	private Form1AbstractService form1AbstractService;
	@Autowired
	private MessageSource messageSource;

	@GetMapping("/getForm1AbstractData")
	@ResponseStatus(HttpStatus.OK)
	public Form1AbstractDao getAllForm1Abstract(
			@RequestParam String cCode,
			Locale locale
			,HttpServletRequest request
			){
		return  form1AbstractService.getForm1AbstractData(cCode,locale,request);
	}
	
	//****DYSLR****

	@GetMapping("/getForm1AbstractDataDyslr")
	@ResponseStatus(HttpStatus.OK)
	public Form1AbstractDyslrDao getAllForm1AbstractDyslrData(
			@RequestParam String cCode,
			Locale locale
			,HttpServletRequest request
			){
		return  form1AbstractService.getForm1AbstractDyslrData(cCode,locale,request);
	}


	@GetMapping("/getForm1AbstractReport")
	public Form1AbstractDao getForm1AbstractReport(@RequestParam Integer districtCode,@RequestParam Integer talukaCode,@RequestParam String cCode,Locale locale,HttpServletRequest request) {
		return form1AbstractService.getForm1AbstractReport(districtCode,talukaCode,cCode,locale,request);
	}
	
//	//Dyslr Report///
//	@GetMapping("/getForm1AbstractReportDyslr")
//	public Form1AbstractDyslrDao getForm1AbstractReportDyslr(@RequestParam Integer districtCode,@RequestParam Integer talukaCode,@RequestParam String cCode,Locale locale,HttpServletRequest request) {
//		return form1AbstractService.getForm1AbstractDyslrReport(districtCode,talukaCode,cCode,locale,request);
//	}
//

	@PostMapping("/editForm1Abstract")
	@ResponseStatus(HttpStatus.OK)
	public MessageResponse updateForm1Abstract(@Valid @RequestBody Form1AbstractDao form1AbstractDao, Locale locale,HttpServletRequest request){
		try {
			form1AbstractService.updateForm1Abstract(form1AbstractDao, locale,request);
			return MessageResponse.builder().status("SUCCESS").message(messageSource.getMessage("error.form.successmessage", null, locale)).build();
		} 
		catch (Exception ex) {
			ex.printStackTrace();
			throw new ExchangeException("error.form.editrecord");
		}
	}
	
	//****DYSLR****

	@PostMapping("/editForm1AbstractDyslr")
	@ResponseStatus(HttpStatus.OK)
	public MessageResponse updateForm1Abstract_dylsr(
	        @Valid @RequestBody Form1AbstractDyslrDao form1AbstractDyslrDao,
	        Locale locale,
	        HttpServletRequest request) {

	    try {

	        form1AbstractService.updateForm1AbstractDyslr(
	                form1AbstractDyslrDao,
	                locale,
	                request);

	        return MessageResponse.builder()
	                .status("SUCCESS")
	                .message(messageSource.getMessage(
	                        "error.form.successmessage",
	                        null,
	                        locale))
	                .build();

	    } catch (Exception ex) {

	        ex.printStackTrace();
	        throw new ExchangeException("error.form.editrecord");
	    }
	}

	@DeleteMapping("/discardForm1Abstract")
	@ResponseStatus(HttpStatus.IM_USED)
	public MessageResponse deleteForm1Abstract(@RequestParam long id,Locale locale,HttpServletRequest request){
		try {
			form1AbstractService.deleteForm1AbstractById(id, locale,request);
			return MessageResponse.builder().status("SUCCESS").message(messageSource.getMessage("error.form.deletemessage", null, locale)).build();	
		}
		catch (Exception ex) {
			throw new ExchangeException("error.form.deleterecord");
		}
	}
}

//@Autowired
//private Form1AbstractTypesService form1AbstractTypesService;

//@PostMapping("saveForm1Abstract")
//public  MessageResponse  saveForm1AbstractDetails(@RequestBody Form1AbstractDao form1AbstractDao ,Locale locale) {
//return form1AbstractService.save(form1AbstractDao,locale);
//}

//@GetMapping("/getForm1AbstractDetails")
//public List<Form1AbstractDao> getForm1AbstractDetails(@RequestParam Integer districtCode,@RequestParam Integer talukaCode,@RequestParam String cCode,Locale locale) {
//return form1AbstractService.getForm1AbstractDetails(districtCode,talukaCode,cCode,locale);
//}

//@GetMapping("/getLandTypeForm1AbstractDetails")
//public List<Form1AbstractTypesDao> getLandTypeForm1AbstractDetails() {
//	return form1AbstractTypesService.findAllByActiveFlag();
//}
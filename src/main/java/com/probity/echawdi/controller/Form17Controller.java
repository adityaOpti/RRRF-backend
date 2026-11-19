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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.probity.echawdi.dao.Form17Dao;
import com.probity.echawdi.exception.ExchangeException;
import com.probity.echawdi.exception.MessageResponse;
import com.probity.echawdi.exception.ResourceNotFoundException;
import com.probity.echawdi.payload.Response;
import com.probity.echawdi.service.Form17Service;

/**
 * @author Bhagyashri Lolage
 *
 */

@RestController
@RequestMapping("/api/form17")
public class Form17Controller {

	@Autowired
	private Form17Service form17Service;
	@Autowired
	private MessageSource messageSource;

	@GetMapping("/getForm17Data")
	@ResponseStatus(HttpStatus.OK)
	public Response getAllForm17(@RequestParam("khataNo") String khataNo, @RequestParam("cCode") String cCode,
			@RequestParam("revenueYear") String revenueYear, HttpServletRequest request, Locale locale) {
		return form17Service.getAllForm17Data(cCode, revenueYear, khataNo, request, locale);
	}

	@PostMapping("/saveForm17")
	@ResponseStatus(HttpStatus.CREATED)
	public MessageResponse createForm17(@Valid @RequestBody Form17Dao form17Dao, HttpServletRequest request,
			Locale locale) {

		return form17Service.save(form17Dao, request, locale);
	}

	@GetMapping(value = "/getForm17DataById")
	@ResponseStatus(HttpStatus.OK)
	public Form17Dao getForm17ById(@RequestParam long id, HttpServletRequest request, Locale locale) {
		return form17Service.getForm17ById(id, request, locale);
	}

	@PostMapping(value = "/getForm17DataByCCodeAndRevenueYearAndKhataNo")
	@ResponseStatus(HttpStatus.OK)
	public Form17Dao getForm17DataByinputs(@RequestBody Form17Dao form17Dao, HttpServletRequest request,
			Locale locale) {
		return form17Service.getForm17ByCCodeAndRevenueYearAndKhataNo(form17Dao, request, locale);
	}

	@PostMapping(value = "/getForm17DataByCCodeAndRevenueYearAndMakhtaKhataNo")
	@ResponseStatus(HttpStatus.OK)
	public Form17Dao getForm17DataByMakhtaKhataNo(@RequestBody Form17Dao form17Dao, HttpServletRequest request,
			Locale locale) {
		return form17Service.getForm17ByCCodeAndRevenueYearAndMakhtaKhataNo(form17Dao, request, locale);
	}

	@PostMapping("/editForm17")
	@ResponseStatus(HttpStatus.OK)
	public MessageResponse updateForm17(@Valid @RequestBody Form17Dao form17Dao, HttpServletRequest request,
			Locale locale) {
		try {
			form17Service.updateForm17(form17Dao, request, locale);
			return MessageResponse.builder().status("SUCCESS")
					.message(messageSource.getMessage("error.form.successmessage", null, locale)).build();
		} catch (Exception ex) {
			throw new ExchangeException("error.form.editrecord");
		}
	}

	@DeleteMapping("/discardForm17")
	@ResponseStatus(HttpStatus.IM_USED)
	public MessageResponse deleteForm17(@RequestParam long id, HttpServletRequest request, Locale locale) {
//	   try {
		form17Service.deleteForm17ById(id, request, locale);
		return MessageResponse.builder().status("SUCCESS")
				.message(messageSource.getMessage("error.form.deletemessage", null, locale)).build();
//   		}
//	   catch (ResourceNotFoundException resourceNotFoundException) {
//		   
//			throw new ResourceNotFoundException("error.form17.delete");
//			
//	   }

	}

	@GetMapping("/getForm17Report")
	@ResponseStatus(HttpStatus.OK)
	public Response getForm17ReportDetails(@RequestParam String revenueYear, @RequestParam String cCode,
			HttpServletRequest request, Locale locale) {
		return form17Service.getForm17ReportDetails(cCode, revenueYear, request, locale);
	}

	@GetMapping("/getDetailByKhataNo")
	Response getDetailByKhataNo(@RequestParam String cCode, @RequestParam String khataNo, HttpServletRequest request,
			Locale locale) {
		return form17Service.getDetailBykhataNo(cCode, khataNo, request, locale);
	}

	@PostMapping("/getReceiptDetailByMaktaKhataNoAndMaktakhataOwnerName")
	Response getReceiptDetailByMaktaKhataNoAndMaktakhataOwnerName(@RequestBody Form17Dao form17Dao,
			HttpServletRequest request, Locale locale) {
		return form17Service.getReceiptDetailByMaktaKhataNoAndMaktakhataOwnerName(form17Dao, request, locale);
	}

	@GetMapping("/khataNumbers")
	@ResponseStatus(HttpStatus.OK)
	public List<Object> khataNumbers(@RequestParam String cCode, @RequestParam String revenueYear,
			HttpServletRequest request) {
		return form17Service.khataNmbers(cCode, revenueYear, request);
	}

	@GetMapping("/makhtakhataNumbers")
	@ResponseStatus(HttpStatus.OK)
	public String makhtakhataNumbers(@RequestParam String cCode, @RequestParam String lgdCode,
			@RequestParam String revenueYear, HttpServletRequest request) {
		return form17Service.makhtakhataNumbers(cCode, lgdCode, revenueYear, request);
	}
}
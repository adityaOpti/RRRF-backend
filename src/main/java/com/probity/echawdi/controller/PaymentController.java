/**
 * 
 */
package com.probity.echawdi.controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.probity.echawdi.dao.LandRevenueDemandDao;
import com.probity.echawdi.exception.BadRequest;
import com.probity.echawdi.service.LandRevenueService;
import com.probity.echawdi.service.PaymentService;


/**
 * @author Rushikesh Durgude
 *
 */
@RestController
//@Controller
@RequestMapping("/auth/payment")
public class PaymentController {
	
	@Autowired
	private PaymentService paymentService	;
	@Autowired
	private LandRevenueService landRevenueService;

	@ResponseStatus(HttpStatus.ACCEPTED)
	@PostMapping("/getPaymentDetails")
	public List<LandRevenueDemandDao> getReceiptDetailsByKhataNo(@RequestBody LandRevenueDemandDao landRevenueDemandDao,HttpServletRequest request,Locale locale )  {
		return landRevenueService.getLandRevenueDemandDtl(landRevenueDemandDao,request,locale);
	}

	@GetMapping("/getDropDownsDistrict")
	public ResponseEntity<?> getDropDownsDistrict() {
		return paymentService.getDropDownsDistrict();
	}

	@GetMapping("/getDropDownsTaluka")
	public ResponseEntity<?> getDropDownsTaluka(@RequestParam String districtCode,HttpServletRequest request) {
		if(districtCode ==null || districtCode.isEmpty()) {
			throw new BadRequest("Please select District");
		}
		return paymentService.getDropDownsTaluka(districtCode);
	}
	
	@GetMapping("/getDropDownsVillage")
	public ResponseEntity<?> getDropDownsVillage(@RequestParam String districtCode,@RequestParam String talukaCode,HttpServletRequest request) {
		if((districtCode.isEmpty() && districtCode !=null ) && (talukaCode.isEmpty() && talukaCode !=null)) {
			throw new BadRequest("Please select District And Taluka");
		}else if(districtCode == null || districtCode.isEmpty()) {
			throw new BadRequest("Please select District");
		}else if(talukaCode == null || talukaCode.isEmpty()) {
			throw new BadRequest("Please Select Taluka");
		}else {
			return paymentService.getDropDownsVillage(districtCode,talukaCode);
		}
	}
}
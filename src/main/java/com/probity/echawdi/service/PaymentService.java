/**
 * 
 */
package com.probity.echawdi.service;


import org.springframework.http.ResponseEntity;


/**
 * @author Rushikesh Durgude
 *
 */
public interface PaymentService {

//	Response getpaymentServiceDetails(Integer districtCode, Integer talukaCode, String cCode, String khataNo, Locale locale);

	ResponseEntity<?> getDropDownsDistrict();
	
	ResponseEntity<?> getDropDownsTaluka(String districtCode );
	
	ResponseEntity<?> getDropDownsVillage(String districtCode ,String talukaCode);
}

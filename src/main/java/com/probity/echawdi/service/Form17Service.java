/**
 * 
 */
package com.probity.echawdi.service;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestBody;

import com.probity.echawdi.dao.Form17Dao;
import com.probity.echawdi.exception.MessageResponse;
import com.probity.echawdi.payload.Response;

/**
 * @author Bhagyashri Lolage
 *
 */

public interface Form17Service {
	
	MessageResponse save(Form17Dao form17Dao,HttpServletRequest request,Locale locale);
	
    Response getAllForm17Data(String cCode,String revenueYear,String khataNo,HttpServletRequest request,Locale locale);
    
	Form17Dao getForm17ById(long id,HttpServletRequest request,Locale locale);
	
	Form17Dao getForm17ByCCodeAndRevenueYearAndKhataNo(Form17Dao form17Dao,HttpServletRequest request,Locale locale); 
	
	void updateForm17(Form17Dao form17Dao,HttpServletRequest request,Locale locale);
	
	void deleteForm17ById(long id,HttpServletRequest request,Locale locale);

	Response getForm17ReportDetails(String cCode,String revenueYear,HttpServletRequest request,Locale locale);
	
	Response getReport4Details(String cCode,String revenueYear,HttpServletRequest request,Locale locale);
	
	Response getDetailBykhataNo(String cCode, String khataNo,HttpServletRequest request, Locale locale);

//	List<Form17Dao> getTotalAreaAndUom(Integer districtCode, Integer talukaCode, String cCode, String khataNo,String surveyHissaNo, Locale locale);
	
	List<Object> khataNmbers(String cCode,String revenueYear,HttpServletRequest request); // Ashish 

	String makhtakhataNumbers(String cCode, String lgdCode,String revenueYear,HttpServletRequest request);

	Form17Dao getForm17ByCCodeAndRevenueYearAndMakhtaKhataNo(Form17Dao form17Dao,HttpServletRequest request, Locale locale);

	Response getReceiptDetailByMaktaKhataNoAndMaktakhataOwnerName(Form17Dao form17Dao, HttpServletRequest request, Locale locale);

	Response getReport4GhoshwaraDetails(String cCode, String revenueYear, HttpServletRequest request, Locale locale);
}
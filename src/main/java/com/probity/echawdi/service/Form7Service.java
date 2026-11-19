/**
 * 
 */
package com.probity.echawdi.service;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import com.probity.echawdi.dao.Form7Dao;
import com.probity.echawdi.dao.Form7KhataDao;
import com.probity.echawdi.payload.Response;

/**
 * @author Bhagyashri Lolage 
 *
 */


public interface Form7Service {

	List<Form7Dao> findHissaNoByVillageAndSurveyNo(HttpServletRequest request,String cCode,String pin,Character marked,Locale locale);
	
	List<Form7Dao> getForm1Details(HttpServletRequest request,String cCode,String pin,String hissaNo,Locale locale);

//	List<Form7Dao> getForm1CDetails(Integer tenureCode,Integer tenureSubCode,Character marked,Locale locale);

	List<Form7Dao> getForm1AArea(HttpServletRequest request,String cCode, String pin, String hissaNo,Locale locale);
	
	Response getAllForm6AReportDetails(String districtCode, HttpServletRequest request, Locale locale);
	
	Form7KhataDao getKhataOwnerName(HttpServletRequest request,String khataNo,String cCode,Locale locale);
}
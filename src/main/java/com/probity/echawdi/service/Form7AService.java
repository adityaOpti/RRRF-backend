/**
 * 
 */
package com.probity.echawdi.service;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.probity.echawdi.dao.Form7ADao;
import com.probity.echawdi.exception.MessageResponse;
import com.probity.echawdi.payload.Response;

/**
 * @author Bhagyashri Lolage
 *
 */
public interface Form7AService {
	
	Response getAllForm7AData( String cCode, String pin,HttpServletRequest request,Locale locale);


	void updateForm7A(@Valid Form7ADao form7aDao,HttpServletRequest request,Locale locale);


	void deleteForm7AById(long id,HttpServletRequest request,Locale locale);


	MessageResponse save(@Valid Form7ADao form7aDao,HttpServletRequest request,Locale locale);


	List<Form7ADao> getForm7AEferfarDetials(String cCode, String pin, String hissaNo,HttpServletRequest request, Locale locale);


	Response getForm7AReportDetails(String cCode,HttpServletRequest request,Locale locale);


	List<Form7ADao> getForm7AEferfarKhataDetials(String cCode, String pin, String hissaNo, String khataNo,
			HttpServletRequest request, Locale locale);

}
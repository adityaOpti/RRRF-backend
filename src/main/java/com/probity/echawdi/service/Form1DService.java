/**
 * 
 */
package com.probity.echawdi.service;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import com.probity.echawdi.dao.Form1DDao;
import com.probity.echawdi.exception.MessageResponse;
import com.probity.echawdi.payload.Response;

/**
 * @author Bhagyashri Lolage
 *
 */

public interface  Form1DService {
	
	 MessageResponse save(Form1DDao form1DDao,HttpServletRequest request,Locale locale);
    
	 void updateForm1D(Form1DDao form1DDao,HttpServletRequest request,Locale locale);
	 
	 void deleteForm1DById(long id,HttpServletRequest request,Locale locale);

	 Response getAllForm1DData(String cCode, String pin,HttpServletRequest request,Locale locale);
		
 	 Form1DDao getForm1DById(long id,HttpServletRequest request,Locale locale);

	 Response getForm1DReport(String cCode,HttpServletRequest request,Locale locale);

//	 List<Form1DDao> getForm1DArea(String cCode, String pin, String hissaNo,HttpServletRequest request,Locale locale);
}
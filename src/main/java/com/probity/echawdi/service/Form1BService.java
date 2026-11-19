/**
 * 
 */
package com.probity.echawdi.service;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import com.probity.echawdi.dao.Form1BDao;
import com.probity.echawdi.payload.Response;

/**
 * @author Bhagyashri Lolage
 *
 */

public interface  Form1BService {
	
	// MessageResponse save(Form1BDao form1BDao,HttpServletRequest request,Locale locale);
    
     Response getAllForm1BData(String cCode, String pin,HttpServletRequest request,Locale locale);
		
 	 Form1BDao getForm1BById(long id,HttpServletRequest request,Locale locale);
 	
 	 void updateForm1B(Form1BDao form1BDao,HttpServletRequest request,Locale locale);

 	 void deleteForm1BById(long id,HttpServletRequest request,Locale locale);

//	 Response getForm1BAssesment(String cCode, String pin, String hissaNo,HttpServletRequest request,Locale locale);

	 Response getForm1BReport(String cCode,HttpServletRequest request,Locale locale);
}
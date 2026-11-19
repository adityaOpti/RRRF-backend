/**
 * 
 */
package com.probity.echawdi.service;


import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import com.probity.echawdi.dao.Form1ADao;
import com.probity.echawdi.exception.MessageResponse;
import com.probity.echawdi.payload.Response;

/**
 * @author Bhagyashri Lolage
 *
 */

public interface  Form1AService {
	
	 MessageResponse save(Form1ADao form1ADao,HttpServletRequest request,Locale locale);
    
	 void updateForm1A(Form1ADao form1ADao,HttpServletRequest request,Locale locale);
	 
	 void deleteForm1AById(long id,HttpServletRequest request,Locale locale);

	 Response getAllForm1AData(String cCode, HttpServletRequest request,Locale locale);
		
 	 Form1ADao getForm1AById(long id,HttpServletRequest request,Locale locale);

 	 Response getForm1AReport(String cCode,HttpServletRequest request,Locale locale);
}

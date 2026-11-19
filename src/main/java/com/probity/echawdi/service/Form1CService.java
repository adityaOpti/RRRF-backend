/**
 * 
 */
package com.probity.echawdi.service;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import com.probity.echawdi.dao.Form1CDao;
import com.probity.echawdi.payload.Response;

/**
 * @author Rushikesh Durgude
 *
 */
 
public interface  Form1CService {
	
	//MessageResponse save(Form1CDao form1CDao,Locale locale);

	void updateForm1C(Form1CDao form1CDao,HttpServletRequest request,Locale locale);
	
	void deleteForm1CById(long id,HttpServletRequest request,Locale locale);
	
//	Form1CDao getForm1CById(long id,Locale locale);
	
	Response getAllForm1CData(String cCode, String pin,Integer clause,HttpServletRequest request,Locale locale);


//	Response getForm1CeFerfarData(Integer districtCode, Integer talukaCode, String cCode, Locale locale);

	Response getForm1CReport(String cCode,HttpServletRequest request, Locale locale);
	
	List<Form1CDao> getClauses(HttpServletRequest request);


}

/**
 * 
 */
package com.probity.echawdi.service;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.probity.echawdi.dao.Form1AbstractDao;
import com.probity.echawdi.dao.Form1Dao;
import com.probity.echawdi.dao.Form1HeaderDao;
import com.probity.echawdi.dao.NewForm1DyslrDao;
import com.probity.echawdi.payload.Response;

/**
 * @author Ashish Dahale
 *
 */

public interface Form1Service {

	Form1Dao getForm1ById(long id,Locale locale,HttpServletRequest request);

	void updateForm1(@Valid Form1Dao form1Dao,HttpServletRequest request,Locale locale);

	void deleteForm1ById(long id,Locale locale,HttpServletRequest request);

	Response getForm1ReportDetails(Integer districtCode, Integer talukaCode, String cCode,Locale locale,HttpServletRequest request);

	 List<Form1AbstractDao> getForm1FooterDetails(Integer districtCode, Integer talukaCode, String cCode,Locale locale,HttpServletRequest request);
	
	Response getAllForm1Data(Integer districtCode, Integer talukaCode, String cCode, String pin,Locale locale,HttpServletRequest request);


	List<Form1HeaderDao> getForm1headerdetails(Integer districtCode, Integer talukaCode, String cCode,Locale locale,HttpServletRequest request);

	List<Form1Dao> getForm1ODCReportDetails(String cCode, Locale locale,HttpServletRequest request);

	List<Form1Dao> form1OdcDiff(String cCode,Locale locale,HttpServletRequest request);
	
	List<NewForm1DyslrDao> form1OdcDyslrDiff(String cCode, Locale locale, HttpServletRequest request);

}

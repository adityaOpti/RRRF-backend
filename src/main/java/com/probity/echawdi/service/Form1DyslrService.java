/**
 * 
 */
package com.probity.echawdi.service;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.probity.echawdi.dao.Form1AbstractDyslrDao;
import com.probity.echawdi.dao.Form1Dao;
import com.probity.echawdi.dao.Form1DyslrDao;
import com.probity.echawdi.payload.Response;



public interface Form1DyslrService {


	void updateForm1(@Valid Form1DyslrDao form1DyslrDao,HttpServletRequest request,Locale locale);

	void saveForm1Dyslr(@Valid Form1DyslrDao form1DyslrDao,HttpServletRequest request,Locale locale);
	
	void deleteForm1ById(Form1DyslrDao form1DyslrDao,Locale locale,HttpServletRequest request);

	Response getForm1DyslrReportDetails(Integer districtCode, Integer talukaCode, String cCode, Locale locale,
			HttpServletRequest request);

	Response getFormDyslrSaveEntries(String cCode, HttpServletRequest request, Locale locale);

	Response getFormDyslrDeleteEntries(String cCode, HttpServletRequest request, Locale locale);
	
	Response getAllForm1DyslrData(Integer districtCode, Integer talukaCode, String cCode, String pin,Locale locale,HttpServletRequest request);

	void updateForm1Dyslr(@Valid Form1DyslrDao form1DyslrDao, HttpServletRequest request, Locale locale);

	List<Form1AbstractDyslrDao> getDyslrForm1FooterDetails(Integer districtCode, Integer talukaCode, String cCode,
			Locale locale, HttpServletRequest request);

	Form1AbstractDyslrDao getForm1AbstractDyslrReport(Integer districtCode, Integer talukaCode, String cCode,
			Locale locale, HttpServletRequest request);

	Form1DyslrDao getNewForm1DyslrReport(Integer districtCode, Integer talukaCode, String cCode, Locale locale,
			HttpServletRequest request);

	List<Form1AbstractDyslrDao> getNewForm1DyslrReportM(Integer districtCode, Integer talukaCode, Locale locale,
			HttpServletRequest request);

	List<Form1AbstractDyslrDao> getForm1AbstractReportDyslrListModified(Integer districtCode, Integer talukaCode,
			Locale locale, HttpServletRequest request);

}

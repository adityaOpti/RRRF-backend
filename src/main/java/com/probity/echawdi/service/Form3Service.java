package com.probity.echawdi.service;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.probity.echawdi.dao.Form3Dao;
import com.probity.echawdi.payload.Response;

public interface Form3Service {

	Response getForm3cDataN(String cCode, String pin,HttpServletRequest request,Locale locale);

//	MessageResponse save(@Valid Form3Dao form3Dao,HttpServletRequest request,Locale locale);

	void updateForm3(@Valid Form3Dao form3Dao,HttpServletRequest request,Locale locale);

	void deleteForm3ById(long id,HttpServletRequest request,Locale locale);

	Response getForm3ReportDetails(String cCode,HttpServletRequest request,Locale locale);

	Response getForm3AbstractReportDetails(String cCode, HttpServletRequest request, Locale locale);

//	List<Form3Dao> getForm3EferfarDetials(String cCode, String pin, String hissaNo,HttpServletRequest request,Locale locale);
}

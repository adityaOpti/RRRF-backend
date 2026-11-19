/**
 * 
 */
package com.probity.echawdi.service;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.probity.echawdi.dao.Form2Dao;
import com.probity.echawdi.dao.NirankDao;
import com.probity.echawdi.exception.MessageResponse;
import com.probity.echawdi.payload.Response;

/**
 * @author ue
 *
 */
public interface Form2Service {

	Response getAllForm2Data(String cCode, String pin,HttpServletRequest request,Locale locale);


	void updateForm2(@Valid Form2Dao form2Dao,HttpServletRequest request,Locale locale);

	void deleteForm2ById(long id,HttpServletRequest request,Locale locale);

	Response getForm2ReportDetails(String cCode,HttpServletRequest request,Locale locale);

//	Response getNirankDataForm2(String cCode, HttpServletRequest request, Locale locale);
	
	//Response getForm2AreaAndAssesment(String cCode, String pin, String hissaNo,HttpServletRequest request,Locale locale);
}
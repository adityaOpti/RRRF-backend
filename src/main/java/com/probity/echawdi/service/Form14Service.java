
/**
 * 
 */
package com.probity.echawdi.service;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.probity.echawdi.dao.Form14Dao;
import com.probity.echawdi.exception.MessageResponse;
import com.probity.echawdi.payload.Response;

/**
 * @author Bhagyashri Lolage
 *
 */

@Service
public interface Form14Service {

	

	MessageResponse save(HttpServletRequest request,@Valid  Form14Dao form14Dao, Locale locale);

	void updateForm14(HttpServletRequest request,@Valid  Form14Dao form14Dao, Locale locale);

	void deleteForm14ById(HttpServletRequest request, long id, Locale locale);

	List<Form14Dao> getForm14EferfarDetials( HttpServletRequest request, String cCode,String pin, String hissaNo, Locale locale);

	Response getAllForm14ReportDetails(HttpServletRequest request, String cCode, Locale locale);

	Response getAllForm14Data(HttpServletRequest request, String cCode, Locale locale);
	
	Response getForm14GhoshwaraReport(HttpServletRequest request, String cCode, Locale locale);
}
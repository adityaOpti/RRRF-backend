/**
 * 
 */
package com.probity.echawdi.service;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import com.probity.echawdi.dao.Form7BDao;
import com.probity.echawdi.exception.MessageResponse;
import com.probity.echawdi.payload.Response;

/**
 * @author ue
 *
 */
public interface Form7BService {

	Response getAllForm7BData(String cCode, String pin,Locale locale,HttpServletRequest request);

	MessageResponse save( Form7BDao form7bDao,Locale locale,HttpServletRequest request);

    void updateForm7B( Form7BDao form7bDao,Locale locale,HttpServletRequest request) ;
		
    void deleteForm7BById(long id,Locale locale,HttpServletRequest request) ;

	Response getAllForm7BReportDetails(String cCode, Locale locale,HttpServletRequest request);

	Response getKhataNodetails(String cCode, String pin, String hissaNo,
			Locale locale,HttpServletRequest request);

}

/**
 * 
 */
package com.probity.echawdi.service;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import com.probity.echawdi.dao.Form19Dao;
import com.probity.echawdi.exception.MessageResponse;
import com.probity.echawdi.payload.Response;

/**
 * @author Rushikesh Durgude
 *
 */
public interface Form19Service {
	
	MessageResponse save(Form19Dao form19Dao,HttpServletRequest request,Locale locale);
	
    Response getAllForm19Data(String userId,String sajjaCode,HttpServletRequest request,Locale locale);
	
	void updateForm19(Form19Dao form19Dao,HttpServletRequest request,Locale locale);
	
	void deleteForm19ById(long id,HttpServletRequest request,Locale locale);


}

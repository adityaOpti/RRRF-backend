/**
 * 
 */
package com.probity.echawdi.service;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import com.probity.echawdi.dao.Form21Dao;
import com.probity.echawdi.exception.MessageResponse;
import com.probity.echawdi.payload.Response;

/**
 * @author Rushikesh Durgude
 *
 */
public interface Form21Service {
	MessageResponse save(Form21Dao form21Dao,HttpServletRequest request,Locale locale);
	
    Response getAllForm21Data( String userId,int pageNo, int pageSize, String sortBy, String sortDir,HttpServletRequest request,Locale locale);
	
	void updateForm21(Form21Dao form21Dao,HttpServletRequest request,Locale locale);
	
	void deleteForm21ById(long id,HttpServletRequest request,Locale locale);
}

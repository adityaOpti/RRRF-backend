/**
 * 
 */
package com.probity.echawdi.service;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import com.probity.echawdi.dao.Form18Dao;
import com.probity.echawdi.exception.MessageResponse;
import com.probity.echawdi.payload.Response;

/**
 * @author Rushikesh Durgude
 *
 */
public interface Form18Service {
	MessageResponse save(Form18Dao form18Dao,HttpServletRequest request,Locale locale);
	
    Response getAllForm18Data(Integer districtCode, Integer talukaCode, String cCode,  int pageNo, int pageSize, String sortBy, String sortDir,HttpServletRequest request,Locale locale);
    
	void updateForm18(Form18Dao form18Dao,HttpServletRequest request,Locale locale);
	
	void deleteForm18ById(long id,HttpServletRequest request,Locale locale);


}

/**
 * 
 */
package com.probity.echawdi.service;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.probity.echawdi.dao.OneTimeEntryDao;
import com.probity.echawdi.exception.MessageResponse;
import com.probity.echawdi.payload.Response;

/**
 * @author Bhagyashri Lolage/Rushikesh Durgude
 *
 */

public interface OneTimeEntryService {

	MessageResponse save(OneTimeEntryDao oneTimeEntryDao,HttpServletRequest request,Locale locale); 	//Rushikesh Durgude
	
	void updateOneTimeEntry(@Valid OneTimeEntryDao oneTimeEntryDao,HttpServletRequest request,Locale locale);	//Rushikesh Durgude
	
	void deleteOneTimeEntryById(Long id,HttpServletRequest request,Locale locale); //Rushikesh Durgude

	Response getOneTimeEntryDataBycCode(String cCode,String khataNo,HttpServletRequest request,Locale locale);  	//Bhagyashri Lolage
	
	OneTimeEntryDao getOneTimeEntryById(long id,HttpServletRequest request,Locale locale);   	//Bhagyashri Lolage
	
	List<Object> khataNmbers(String cCode,String revenueYear,HttpServletRequest request); // Ashish 
	
	Response getOneTimeEntryReportDtls(String cCode, Locale locale,HttpServletRequest request);
}
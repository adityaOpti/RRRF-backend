
/**
 * 
 */
package com.probity.echawdi.service;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.probity.echawdi.dao.NirankDao;
import com.probity.echawdi.exception.MessageResponse;
import com.probity.echawdi.payload.Response;

/**
 * @author Bhagyashri Lolage
 *
 */

@Service
public interface NirankService {

	MessageResponse save(NirankDao nirankDao, HttpServletRequest request, Locale locale);

	void editNirankandCompleted( NirankDao nirankDao, Locale locale, HttpServletRequest request);

	void deleteNirankAndCompleted(HttpServletRequest request, long id, Locale locale);

	Response getNirankAndCompleted(String cCode, String revenueYear, HttpServletRequest request, Locale locale);

	MessageResponse saveAll(Response requestBody, HttpServletRequest request, Locale locale);

	Response getNirnkForDashBoard(String districtCode, String talukaCode, HttpServletRequest request, Locale locale);

	

}
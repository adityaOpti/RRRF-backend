/**
 * 
 */
package com.probity.echawdi.service;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.probity.echawdi.dao.Form8CDao;
import com.probity.echawdi.exception.MessageResponse;
import com.probity.echawdi.payload.Response;

/**
 * @author Bhagyashri Lolage
 *
 */
@Service
public interface Form8CService {
	Response getAllForm8CData(Integer districtCode, Integer talukaCode, String cCode, int pageNo, int pageSize, String sortBy, String sortDir,
			HttpServletRequest request,Locale locale);

	MessageResponse save(@Valid Form8CDao form8cDao, HttpServletRequest request,Locale locale);


	void updateForm8C(@Valid Form8CDao form8cDao,HttpServletRequest request, Locale locale);

	void deleteForm8CById(long id,HttpServletRequest request, Locale locale);

}

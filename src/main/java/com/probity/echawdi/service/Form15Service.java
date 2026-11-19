/**
 * 
 */
package com.probity.echawdi.service;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.probity.echawdi.dao.Form15Dao;
import com.probity.echawdi.exception.MessageResponse;
import com.probity.echawdi.payload.Response;

/**
 * @author Bhagyashri Lolage
 *
 */
@Service
public interface Form15Service {

	Response getAllForm15Data(String sajjaCode,HttpServletRequest request,  Locale locale);

	MessageResponse save(@Valid Form15Dao form15Dao,HttpServletRequest request,  Locale locale);

	void updateForm15(@Valid Form15Dao form15Dao,HttpServletRequest request,  Locale locale);

	void deleteForm15ById(long id,HttpServletRequest request,  Locale locale);


}

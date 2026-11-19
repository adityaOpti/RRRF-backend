/**
 * 
 */
package com.probity.echawdi.service;


import java.io.IOException;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.probity.echawdi.dao.Form1EDao;
import com.probity.echawdi.exception.MessageResponse;
import com.probity.echawdi.payload.Response;

/**
 * @author Rushikesh Durgude
 *
 */
public interface  Form1EService {

	Response getAllForm1EData(String cCode, String pin,HttpServletRequest request,Locale locale);

	Form1EDao getForm1EById(long id,HttpServletRequest request,Locale locale) throws IOException;

	MessageResponse updateForm1E(Form1EDao form1EDao,HttpServletRequest request,Locale locale);

	void deleteForm1EById(long id,HttpServletRequest request,Locale locale);

	MessageResponse saveForm1E(String form1EDao,MultipartFile file,HttpServletRequest request,Locale locale) throws JsonMappingException, JsonProcessingException;

	Response getForm1EReport(String cCode,HttpServletRequest request,Locale locale) throws IOException;
}
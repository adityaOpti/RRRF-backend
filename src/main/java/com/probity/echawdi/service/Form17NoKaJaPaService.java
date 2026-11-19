package com.probity.echawdi.service;

import java.math.BigDecimal;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.probity.echawdi.dao.Form17NoKaJaPaDao;
import com.probity.echawdi.exception.MessageResponse;
import com.probity.echawdi.payload.Response;

@Service
public interface Form17NoKaJaPaService {

	Response getAllForm17KaJaPaData(HttpServletRequest request, String cCode,String khataNo,String revenueYear,Locale locale);

	MessageResponse save(@Valid Form17NoKaJaPaDao form17NoKaJaPaDao,HttpServletRequest request, Locale locale);

	void deleteForm17NoKaJaPaById(long id,HttpServletRequest request, Locale locale);

	void updateForm17NoKaJaPa(@Valid Form17NoKaJaPaDao form17NoKaJaPaDao,HttpServletRequest request, Locale locale);
	
	Form17NoKaJaPaDao getForm17ById(long id,HttpServletRequest request,Locale locale);
	
	Response getForm17NoKaJaPaReport(String cCode, Locale locale,HttpServletRequest request);   

	Response getDetailBykhataNoKajapa(String cCode, String khataNo, HttpServletRequest request, Locale locale); 
	
	BigDecimal getrate(String cCode, String pin, Locale locale, HttpServletRequest request);   


}

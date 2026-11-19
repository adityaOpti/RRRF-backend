package com.probity.echawdi.service;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.probity.echawdi.dao.Form6DDao;
import com.probity.echawdi.exception.MessageResponse;
import com.probity.echawdi.payload.Response;

public interface Form6DService {

	Response getAllForm6DData(String cCode, String pin,Locale locale, HttpServletRequest request);

	MessageResponse save(@Valid Form6DDao form6DDao,Locale locale, HttpServletRequest request);

	void updateForm6D(@Valid Form6DDao form6DDao,Locale locale, HttpServletRequest request);

	void deleteForm6DById(long id, Locale locale, HttpServletRequest request);

	Response getAllForm6DReportDetails(String cCode,Locale locale, HttpServletRequest request);

	List<Form6DDao> getForm6DEferfarDetials(String cCode,String pin,String hissaNo,Locale locale, HttpServletRequest request);

	List<Form6DDao> getHissaNo(String cCode, String pin,Locale locale, HttpServletRequest request);
}
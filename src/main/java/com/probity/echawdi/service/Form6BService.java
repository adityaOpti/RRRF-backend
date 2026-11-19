package com.probity.echawdi.service;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.probity.echawdi.dao.Form6BDao;
import com.probity.echawdi.exception.MessageResponse;
import com.probity.echawdi.payload.Response;

public interface Form6BService {


	MessageResponse save(Form6BDao form3Dao, Locale locale, HttpServletRequest request);

	void updateForm6B(@Valid Form6BDao form3Dao, Locale locale, HttpServletRequest request);

	void deleteForm6BById(long id, Locale locale, HttpServletRequest request);

	Response getAllForm6BReportDetails(String cCode,Locale locale, HttpServletRequest request);

	List<Form6BDao> getForm6BEferfarDetails(String cCode,Integer mutNo,
			HttpServletRequest request,
			Locale locale);

	Response getAllForm6BData(String cCode, Integer entryVillageForm6,Locale locale,HttpServletRequest request);

	List<Form6BDao> getSankirnReceiptDetails(String cCode, String khataNo, HttpServletRequest request, Locale locale);
}

package com.probity.echawdi.service;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import com.probity.echawdi.dao.AdditionalLandRevenueDao;
import com.probity.echawdi.payload.Response;

public interface AdditionalLandRevenueService {

	Response getAddLandRevenueDetails(String cCode, String khataNo,HttpServletRequest request, Locale locale);

	Response save(AdditionalLandRevenueDao additionalLandRevenueDao, HttpServletRequest request,Locale locale);

	Response getAddLandRevenueRecords(String cCode, String revenueYear,HttpServletRequest request,Locale locale);

	Response deleteMergeDtlByParentId(long id,HttpServletRequest request, Locale locale);

	Response saveAll(List<AdditionalLandRevenueDao> addlLst,HttpServletRequest request, Locale locale);

	Response getAddLandRevenueReportDtls(String cCode, String revenueYear, HttpServletRequest request, Locale locale);
	
}

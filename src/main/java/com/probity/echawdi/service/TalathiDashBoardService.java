package com.probity.echawdi.service;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.probity.echawdi.dao.TalathiDashBoardDao;
import com.probity.echawdi.exception.MessageResponse;
import com.probity.echawdi.payload.Response;

public interface TalathiDashBoardService {

	Response getDashBoardDetails(HttpServletRequest request,String cCode,String revenueYear);

	Response getDashBoardKhataNoDetails(HttpServletRequest request, String cCode,String talukaCode);
	
	Response getDashboardLrDataUpdated(HttpServletRequest request,String cCode,String revenueYear);

	Response getVillageTarget(HttpServletRequest request,String cCode,String revenueYear,Locale locale);
	
	MessageResponse save(TalathiDashBoardDao talathiDashBoardDao,HttpServletRequest request, Locale locale);

	void updateTalathiDashboard(@Valid TalathiDashBoardDao talathiDashBoardDao, Locale locale, HttpServletRequest request);

	
}

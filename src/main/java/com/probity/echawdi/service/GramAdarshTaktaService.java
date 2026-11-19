package com.probity.echawdi.service;


import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.probity.echawdi.dao.GramAdarshTaktaDTO;
import com.probity.echawdi.dao.GramAdarshaTaktaSaveDTO;


@Service
public interface GramAdarshTaktaService {
	
	GramAdarshTaktaDTO getKhatedarCountAndBelowArea(String ccode,
	         String revenueYear, HttpServletRequest request,Locale locale);
	
	GramAdarshTaktaDTO getAkrushakAndPothkhrabAreaByccode(String ccode,
	         HttpServletRequest request,Locale locale);
	
	
	GramAdarshTaktaDTO getAllTaxCalculationBycCode(String ccode,
	        /* String revenueYear,*/ HttpServletRequest request,Locale locale);
	
	 GramAdarshTaktaDTO getForestAndGaothanKshetraByCcode(String cCode, 
			 HttpServletRequest request, Locale locale);

	 GramAdarshTaktaDTO getBindumalaAndDumalaAreasBycCode(String ccode,
	          HttpServletRequest request,Locale locale);
	 
	 GramAdarshTaktaDTO getJalsinchanWellCount(String cCode, 
			 HttpServletRequest request, Locale locale);
	 
	 GramAdarshTaktaDTO getMahasulMafAndKamakariJaminiData(String cCode, 
			 HttpServletRequest request, Locale locale);
	 
	 
	 
	 GramAdarshaTaktaSaveDTO save(GramAdarshaTaktaSaveDTO gramAdarshaTaktaDTO,HttpServletRequest request,
		     Locale locale);
	 
	
	 //just add for the getApi
	 List<GramAdarshaTaktaSaveDTO> getByCcodeAndRevenueYear(String ccode);

	GramAdarshTaktaDTO getkuranAreaAndBagayatArea(String cCode, HttpServletRequest request, Locale locale);

}


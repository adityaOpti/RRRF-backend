/**
 * 
 */
package com.probity.echawdi.service;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.probity.echawdi.dao.MVillageDao;
import com.probity.echawdi.dao.VillageDao;
import com.probity.echawdi.dao.VillageVasuliRecordsDao;
import com.probity.echawdi.payload.Response;

/**
 * @author Ashish Dahale
 *
 */
@Service
public interface VillageService {

	public List<VillageDao> getVillageListByUserName(String username,HttpServletRequest request,Locale locale);

	public List<MVillageDao> getVillageRecords(String districtCode,String talukaCode,Locale locale, HttpServletRequest request);

	public List<Response> getDropDownsDistrict(HttpServletRequest request, Locale locale);

	public List<Response> getDropDownsTaluka(String districtCode, HttpServletRequest request, Locale locale);

	public List<Response> getDropDownsVillage(String districtCode, String talukaCode, HttpServletRequest request, Locale locale);

	//for vasuli report in mis
	public List<VillageVasuliRecordsDao> getVillageVasuliRecords(String districtCode, String talukaCode, String revenueYear, Locale locale, HttpServletRequest request );

}

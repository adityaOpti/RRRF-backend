package com.probity.echawdi.controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.probity.echawdi.dao.MVillageDao;
import com.probity.echawdi.dao.VillageVasuliRecordsDao;
import com.probity.echawdi.payload.Response;
import com.probity.echawdi.service.VillageService;

@CrossOrigin
@RestController
@RequestMapping("/portal/m_village")
public class MVillageController {
	
	
	@Autowired
	VillageService villageService;
	

	@GetMapping("/getvillagelist")
	@ResponseStatus(HttpStatus.OK)
	public List<MVillageDao> getVillageList(@RequestParam String districtCode,@RequestParam String talukaCode,Locale locale,HttpServletRequest request) {
		return villageService.getVillageRecords(districtCode,talukaCode,locale, request);
	}
	
	@GetMapping("/getDropDownsDistrict")
	public List<Response> getDropDownsDistrict(Locale locale,HttpServletRequest request) {
		return villageService.getDropDownsDistrict(request,locale);
	}
	@GetMapping("/getDropDownsTaluka")
	public List<Response> getDropDownsTaluka(@RequestParam String districtCode,Locale locale,HttpServletRequest request) {
		return villageService.getDropDownsTaluka(districtCode,request,locale);
	}
	
	@GetMapping("/getDropDownsVillage")
	public List<Response> getDropDownsVillage(@RequestParam String districtCode,@RequestParam String talukaCode,Locale locale,HttpServletRequest request) {
			return villageService.getDropDownsVillage(districtCode,talukaCode,request,locale);
	}
	
	
	//for mis vasuli report
	@GetMapping("/getvillageVasuliList")
	@ResponseStatus(HttpStatus.OK)
	public List<VillageVasuliRecordsDao> getVillageVasuliList(@RequestParam String districtCode,
			@RequestParam String talukaCode,
			@RequestParam String revenueYear,
			Locale locale,
			HttpServletRequest request) {
		return villageService.getVillageVasuliRecords(districtCode,talukaCode,revenueYear,locale, request);
	}
	
}
package com.probity.echawdi.controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.probity.echawdi.dao.CombinedChallanLandRevenueViewDao;
import com.probity.echawdi.dao.LandRevenueDemandDao;
import com.probity.echawdi.payload.Response;
import com.probity.echawdi.service.Form17Service;
import com.probity.echawdi.service.Form7Service;
import com.probity.echawdi.service.LandRevenueService;
import com.probity.echawdi.utils.CommonConstants;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

	@Autowired
	private Form17Service form17Service;

	@Autowired
	private LandRevenueService landRevenueService;

	@Autowired
	private Form7Service form7Service;

	@GetMapping("/getForm4Report")
	@ResponseStatus(HttpStatus.OK)
	public Response getForm4ReportDetails(
			@RequestParam ("revenueYear") String revenueYear,
			@RequestParam ("districtCode") Integer districtCode,
			@RequestParam ("talukaCode") Integer talukaCode,
			@RequestParam ("cCode") String cCode,
			HttpServletRequest request,
			Locale locale)  {
		return form17Service.getReport4Details(cCode,revenueYear,request,locale);
	}

	@GetMapping("/getForm4GhoshwaraReport")
	@ResponseStatus(HttpStatus.OK)
	public Response getForm4GhoshwaraReportDetails(
			@RequestParam ("cCode") String cCode,
			@RequestParam ("revenueYear") String revenueYear,
			HttpServletRequest request,
			Locale locale)  {
		return form17Service.getReport4GhoshwaraDetails(cCode,revenueYear,request,locale);
	}
	
	
	@PostMapping("/landRevenueForm8B")
	@ResponseStatus(HttpStatus.OK)
	public List<LandRevenueDemandDao>  getForm8B(@RequestParam  String cCode,
			@RequestParam  String revenueYear,
			@RequestParam  Integer districtCode,
			@RequestParam  Integer  talukaCode,
			HttpServletRequest request,
			Locale locale )  {
		return landRevenueService.getForm8BData(cCode,revenueYear,districtCode,talukaCode,request,locale);
	}

	@GetMapping("/getForm6AReport")
	@ResponseStatus(HttpStatus.OK)
	public Response getAllForm6A(
			@RequestParam String districtCode,
			@RequestParam String talukaCode,
			@RequestParam String cCode,
			HttpServletRequest request,
			Locale locale){
		return form7Service.getAllForm6AReportDetails(cCode,request,locale);
	}

//	@GetMapping("/getForm9BReport")
//	@ResponseStatus(HttpStatus.OK)
//	public List<LandRevenueDemandDao> getAllForm9B(
//			@RequestParam Integer districtCode,
//			@RequestParam Integer talukaCode,
//			@RequestParam String cCode,
//			HttpServletRequest request,
//			Locale locale){
//		return landRevenueService.getAllForm9BReportDetails(districtCode,talukaCode,cCode,request,locale);
//	}
	
	@GetMapping("/getForm9BReport")
	@ResponseStatus(HttpStatus.OK)
	public List<LandRevenueDemandDao> getForm9BReport(
	        @RequestParam Integer districtCode,
	        @RequestParam Integer talukaCode,
	        @RequestParam String cCode,
	        @RequestParam(required = false) String revenueYear, 
	        HttpServletRequest request,
	        Locale locale) {
		
		System.out.println("I am into controller 9B-------------");
	    
	    return landRevenueService.getAllForm9BReportDetails(
	            districtCode, talukaCode, cCode, revenueYear, request, locale);
	}

//	@GetMapping("/getForm9BReportPre")
//	@ResponseStatus(HttpStatus.OK)
//	public List<LandRevenueDemandDao> getAllForm9BPre(
//			@RequestParam Integer districtCode,
//			@RequestParam Integer talukaCode,
//			@RequestParam String cCode,
//			@RequestParam String revenueYear,
//			HttpServletRequest request,
//			Locale locale){
//		System.out.println("------------------------------->"+revenueYear);
//		return landRevenueService.getAllForm9BReportDetailsPre(districtCode,talukaCode,cCode,revenueYear,request,locale);
//	}
	
	
	@GetMapping("/getForm8DReport")
	@ResponseStatus(HttpStatus.OK)
	public Response getAllForm8D(
			@RequestParam Integer districtCode,
			@RequestParam Integer talukaCode,
			@RequestParam String cCode,
			@RequestParam  String revenueYear,
			HttpServletRequest request,
			Locale locale){
		if(revenueYear.equals("2025-26")) {
		//return landRevenueService.getAllForm8DReportDetails(districtCode,talukaCode,cCode,revenueYear,request,locale);
			
		return landRevenueService.getAllForm8DReportDetails(districtCode,talukaCode,cCode,revenueYear,request,locale);
			
	}else {
		return landRevenueService.getAllForm8DReportDetailsPre(districtCode,talukaCode,cCode,revenueYear,request,locale);

	}
		}
	
	
	
	
	@PostMapping("/landRevenueForm8BView")
	@ResponseStatus(HttpStatus.OK)
	public List<CombinedChallanLandRevenueViewDao>  getForm8BView(@RequestParam  String cCode,
			@RequestParam  String revenueYear,
			@RequestParam  Integer districtCode,
			@RequestParam  Integer  talukaCode,
			HttpServletRequest request,
			Locale locale )  {
		return landRevenueService.getForm8BDataView(cCode,revenueYear,districtCode,talukaCode,request,locale);
	}
	
	
	//8b 2023-24 controller
	
		@PostMapping("/landRevenueForm8BViewPre")
		@ResponseStatus(HttpStatus.OK)
		public List<CombinedChallanLandRevenueViewDao> getForm8BViewPre(@RequestParam String cCode,
				@RequestParam String revenueYear, @RequestParam Integer districtCode, @RequestParam Integer talukaCode,
				HttpServletRequest request, Locale locale) {
			return landRevenueService.getForm8BDataViewPre(cCode, revenueYear, districtCode, talukaCode, request, locale);
		}
		
	
	/*----------------------------------- 8B & 8K ReportView ---------------------------------     */
	@PostMapping("/landRevenueForm8B8kView")	
	@ResponseStatus(HttpStatus.OK)
	public List<CombinedChallanLandRevenueViewDao>  getForm8B8kView(@RequestParam  String cCode,
			@RequestParam  String revenueYear,
			@RequestParam  Integer districtCode,
			@RequestParam  Integer  talukaCode,
			@RequestParam  Integer  dropdownVal,
			HttpServletRequest request,
			Locale locale )  {
		if(revenueYear.equals(CommonConstants.currentYear)) {
		return landRevenueService.getForm8B8kDataView(cCode,revenueYear,districtCode,talukaCode , dropdownVal,request,locale);
	}
		else{
			return landRevenueService.getForm8B8kDataViewPre(cCode,revenueYear,districtCode,talukaCode , dropdownVal,request,locale);

		}
		}
	
	/*----------------------------------- 8B & 8K ReportView End ---------------------------------     */
	
	//old API
	@GetMapping("/getEduCessTax")
	@ResponseStatus(HttpStatus.OK)
	public Response getRojgarHamiTax(
			@RequestParam Integer districtCode, 
			@RequestParam Integer talukaCode,
			@RequestParam String cCode,
			@RequestParam String revenueYear,
			HttpServletRequest request, Locale locale){
		
		return landRevenueService.getListOfRojgarHamiTax(districtCode, talukaCode, cCode,revenueYear, request, locale);
		
	}
	
	//new Api for  rojgar hamikar 
		@GetMapping("/getRojgarHamiTax")
			@ResponseStatus(HttpStatus.OK)
			public List<LandRevenueDemandDao>  getRojgarHamiTax(@RequestParam  String cCode,
					@RequestParam  String revenueYear,
					@RequestParam  Integer districtCode,
					@RequestParam  Integer  talukaCode,
					HttpServletRequest request,
					Locale locale )  {
				
				return landRevenueService.getListOfRojgarHamiTaxx(districtCode, talukaCode, cCode,revenueYear, request, locale);

			}
		
		@GetMapping("/getEduCessTaxx")
		@ResponseStatus(HttpStatus.OK)
		public List<LandRevenueDemandDao>  getEduCessTax(@RequestParam  String cCode,
				@RequestParam  String revenueYear,
				@RequestParam  Integer districtCode,
				@RequestParam  Integer  talukaCode,
				HttpServletRequest request,
				Locale locale )  {
			
			return landRevenueService.getEduCessTaxx(districtCode, talukaCode, cCode,revenueYear, request, locale);

		
		}

}
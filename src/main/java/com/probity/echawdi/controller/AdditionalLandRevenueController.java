package com.probity.echawdi.controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.probity.echawdi.dao.AdditionalLandRevenueDao;
import com.probity.echawdi.exception.ExchangeException;
import com.probity.echawdi.payload.Response;
import com.probity.echawdi.service.AdditionalLandRevenueService;

@RestController
@RequestMapping("/api/additionalLandRevenue")
public class AdditionalLandRevenueController {

	@Autowired
	private AdditionalLandRevenueService additionalLandRevenueService;
	
	
	@GetMapping("/getAdditionalLandRevenueDetails")
	@ResponseStatus(HttpStatus.OK)
	public Response getAddLandRevenueDetails(		
			@RequestParam String cCode,String khataNo,HttpServletRequest request,Locale locale){
		return  additionalLandRevenueService.getAddLandRevenueDetails(cCode,khataNo,request,locale);
	}
	
	
	@PostMapping("/saveAdditionalLandRevenue")
    @ResponseStatus(HttpStatus.CREATED)
    public Response saveAdditionalLR(@RequestBody AdditionalLandRevenueDao additionalLandRevenueDao,HttpServletRequest request,Locale locale){
		return additionalLandRevenueService.save(additionalLandRevenueDao,request,locale);
	}
	
	@GetMapping("/getAdditionalLandRevenueTableRecords")
	@ResponseStatus(HttpStatus.OK)
	public Response getAddLandRevenueRecords(		
			@RequestParam String cCode,String revenueYear,HttpServletRequest request,Locale locale){
		return  additionalLandRevenueService.getAddLandRevenueRecords(cCode,revenueYear,request,locale);
	}
	
	
	@GetMapping("/ReportGetAdditionalLandRevenue")
	@ResponseStatus(HttpStatus.OK)
	public Response reportGetLandRevenueRecords(		
			@RequestParam String cCode,String revenueYear,HttpServletRequest request,Locale locale){
		return  additionalLandRevenueService.getAddLandRevenueReportDtls(cCode,revenueYear,request,locale);
	}

	 @DeleteMapping("/discardAdditionalLandRevenue")
	    @ResponseStatus(HttpStatus.IM_USED)
	    public Response deleteAddLRDtl(@RequestParam long id,HttpServletRequest request,Locale locale){
		   try {
			   return additionalLandRevenueService.deleteMergeDtlByParentId(id,request, locale);
	   		}
	       	catch (Exception ex) {
	    	         throw new ExchangeException("error.form.deleterecord");
	    	}
	    }
	
	 
	 @PostMapping("/confirmAdditionalLandRevenueDemand")
	    @ResponseStatus(HttpStatus.CREATED)
	    public Response confirmAddLrDemnad(@RequestBody List<AdditionalLandRevenueDao> addlLst,HttpServletRequest request,Locale locale){
			return additionalLandRevenueService.saveAll(addlLst,request,locale);
		}
}

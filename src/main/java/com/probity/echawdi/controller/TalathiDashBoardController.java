package com.probity.echawdi.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.probity.echawdi.config.WithDatabase;
import com.probity.echawdi.dao.Form6BDao;
import com.probity.echawdi.dao.TalathiDashBoardDao;
import com.probity.echawdi.exception.ExchangeException;
import com.probity.echawdi.exception.MessageResponse;
import com.probity.echawdi.payload.Response;
import com.probity.echawdi.service.TalathiDashBoardService;
import com.probity.echawdi.utils.DataSourceType;

@RestController
@RequestMapping("/api/restservice")
public class TalathiDashBoardController {
	@Autowired 
	private TalathiDashBoardService dashBoardService;
	
	@Autowired
	private MessageSource messageSource;
	
	@GetMapping("/getDashBoardTotalDemandDetails")
	@ResponseStatus(HttpStatus.OK)
	public Response getDashBoardTotalDemandDetails(HttpServletRequest request,@RequestParam String cCode,@RequestParam String revenueYear) {
		return dashBoardService.getDashBoardDetails(request,cCode,revenueYear);
	}

	@GetMapping("/getDashBoardKhataNoDetails")
	@ResponseStatus(HttpStatus.OK)
	public Response getDashBoardKhataNoDetails(HttpServletRequest request,@RequestParam String cCode ,@RequestParam String talukaCode) {
		return dashBoardService.getDashBoardKhataNoDetails(request,cCode, talukaCode);
	}
	
	
	@GetMapping("/getDashboardLrDataUpdated")
	@ResponseStatus(HttpStatus.OK)
	public Response getDashboardLrDataUpdated(HttpServletRequest request,@RequestParam String cCode,@RequestParam String revenueYear) {
			
		return dashBoardService.getDashboardLrDataUpdated(request,cCode,revenueYear);
	}
	
	
	@WithDatabase(DataSourceType.echmst)
	@PostMapping("/saveTalathiTarget")
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponse saveTalathiTarget(@RequestBody TalathiDashBoardDao talathiDashBoardDao, HttpServletRequest request, Locale locale){
		return dashBoardService.save(talathiDashBoardDao,request,locale);
	}
	
	@WithDatabase(DataSourceType.echmst)
	@PutMapping("/editTalathiTarget")
    @ResponseStatus(HttpStatus.OK)
    public MessageResponse editTalathiTarget(@Valid @RequestBody TalathiDashBoardDao talathiDashBoardDao,Locale locale,HttpServletRequest request){
    		try {
    			dashBoardService.updateTalathiDashboard(talathiDashBoardDao,locale,request);
    			return MessageResponse.builder().status("SUCCESS").message(
        				messageSource.getMessage("error.form.successmessage", null, locale)).build();
        	}
          	catch (Exception ex) {
    	         throw new ExchangeException("error.form.editrecord");
    	}
    }
	
	/*
	 * old api 22 Aug 2025 /
	 * @WithDatabase(DataSourceType.echmst)
	@GetMapping("/getVillageTarget")
	@ResponseStatus(HttpStatus.OK)
	public Response getVillageTarget(HttpServletRequest request,@RequestParam String cCode,@RequestParam String revenueYear,Locale locale) {
		return dashBoardService.getVillageTarget(request,cCode,revenueYear,locale);
	}*/
	
	@WithDatabase(DataSourceType.echmst)
	@GetMapping("/getVillageTarget")
	@ResponseStatus(HttpStatus.OK)
	public Response getVillageTarget(HttpServletRequest request,@RequestParam String cCode,@RequestParam String revenueYear,Locale locale) {
		return dashBoardService.getVillageTarget(request,cCode,revenueYear,locale);
	}

	
}
package com.probity.echawdi.controller;

import java.io.IOException;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.probity.echawdi.dao.ChallanDtlDao;
import com.probity.echawdi.dao.LandRevenueDemandDao;
import com.probity.echawdi.dao.LandRevenueDemandListDao;
import com.probity.echawdi.dao.OneTimeDataEntyRevenueYearCheckDTO;
import com.probity.echawdi.dao.VillageDropdownResponse;
import com.probity.echawdi.entity.ChallanDtl;
import com.probity.echawdi.entity.District;
import com.probity.echawdi.entity.Division;
import com.probity.echawdi.entity.MVillageMapper;
import com.probity.echawdi.entity.Taluka;
import com.probity.echawdi.exception.ExchangeException;
import com.probity.echawdi.exception.MessageResponse;
import com.probity.echawdi.payload.Response;
import com.probity.echawdi.repository.ChallanRepository;
import com.probity.echawdi.service.LandRevenueService;
import com.probity.echawdi.utils.CommonConstants;

/**
 * 
 * @author SANDEEP DUBEY
 *
 */

@RestController
//@PreAuthorize("hasRole('ROLE_TALATHI')")
@PreAuthorize("hasRole('ROLE_TALATHI') or hasRole('ROLE_COLLECTOR')")
@RequestMapping("/api/landRevenue")
public class LandRevenueController {

	@Autowired
	LandRevenueService landRevenueService;

	@Autowired
	ChallanRepository challanRepository;

	@Autowired
	private MessageSource messageSource;

	@ResponseStatus(HttpStatus.ACCEPTED)
	@PostMapping("/generateRevenue")
	public List<LandRevenueDemandDao> generateLandRevenueDemand(@RequestBody LandRevenueDemandDao landRevenueDemandDao,
			HttpServletRequest request, Locale locale) {
		return landRevenueService.generateRevenue(landRevenueDemandDao, request, locale);
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/persistLRDemand")
	public MessageResponse persistLRDemand(@RequestBody LandRevenueDemandListDao landRevenueDemandListDao,
			HttpServletRequest request, Locale locale) {
		return landRevenueService.persistLRDemand(landRevenueDemandListDao, request, locale);
	}

	@ResponseStatus(HttpStatus.ACCEPTED)
	@PostMapping("/getLRDemandBycCodeAndStatus")
	public List<LandRevenueDemandDao> getLRDemandBycCodeAndStatus(
			@RequestBody LandRevenueDemandDao landRevenueDemandDao, HttpServletRequest request, Locale locale) {
		return landRevenueService.getLRDemandBycCodeAndStatus(landRevenueDemandDao, request, locale);
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PatchMapping("/updateLRDemand")
	public MessageResponse editLRDemandById(@RequestBody LandRevenueDemandDao landRevenueDemandDao,
			HttpServletRequest request, Locale locale) {
		return landRevenueService.editLRDemandById(landRevenueDemandDao, request, locale);
	}

	@DeleteMapping("/deleteLRDemand")
	@ResponseStatus(HttpStatus.IM_USED)
	public MessageResponse deleteLRDemandById(HttpServletRequest request, @RequestParam long id, Locale locale) {
		try {
			landRevenueService.deleteLRDemandById(id, request, locale);
			return MessageResponse.builder().status("SUCCESS")
					.message(messageSource.getMessage("error.form.deletemessage", null, locale)).build();
		} catch (Exception ex) {
			throw new ExchangeException("error.form.deleterecord");
		}
	}

	@ResponseStatus(HttpStatus.ACCEPTED)
	@PostMapping("/getLandRevenueDemandDtl")
	public List<LandRevenueDemandDao> getLandRevenueDemandDtl(@RequestBody LandRevenueDemandDao landRevenueDemandDao,
			HttpServletRequest request, Locale locale) {
		return landRevenueService.getLandRevenueDemandDtl(landRevenueDemandDao, request, locale);
	}

	@ResponseStatus(HttpStatus.OK)
	@PatchMapping("/lrReceiptMoneyFromCustomer")
	public LandRevenueDemandDao lrReceiptMoneyFromCustomer(
			@Valid @RequestBody LandRevenueDemandDao landRevenueDemandDao, BindingResult bindingResult,
			HttpServletRequest request, Locale locale) {
		return landRevenueService.lrReceiptMoneyFromCustomer(landRevenueDemandDao, bindingResult, request, locale);
	}

	@ResponseStatus(HttpStatus.ACCEPTED)
	@PostMapping("/getReceiptDetailsByKhataNo")
	public LandRevenueDemandDao getReceiptDetailsByKhataNo(@RequestBody LandRevenueDemandDao landRevenueDemandDao,
			HttpServletRequest request, Locale locale) {
		return landRevenueService.getReceiptDetailsByKhataNo(landRevenueDemandDao, request, locale);
	}

	@ResponseStatus(HttpStatus.ACCEPTED)
	@PostMapping("/getReceiptDetailsByMakhtaKhataNo")
	public LandRevenueDemandDao getReceiptDetailsByMakhtaKhataNo(@RequestBody LandRevenueDemandDao landRevenueDemandDao,
			HttpServletRequest request, Locale locale) {
		//if (landRevenueDemandDao.getRevenueYear().equals("2025-26")) {
			return landRevenueService.getReceiptDetailsByMakhtaKhataNo(landRevenueDemandDao, request, locale);
		//} else {
		//	return landRevenueService.getReceiptDetailsByMakhtaKhataNoPre(landRevenueDemandDao, request, locale);

		//}
	}

	@ResponseStatus(HttpStatus.ACCEPTED)
	@PostMapping("/getAllReceiptsByVillage")
	public List<LandRevenueDemandDao> getAllReceiptsByVillage(@RequestBody LandRevenueDemandDao landRevenueDemandDao,
			HttpServletRequest request, Locale locale) {
		
		if (landRevenueDemandDao.getRevenueYear().equals(CommonConstants.currentYear)) {
			return landRevenueService.getAllReceiptsByVillage(landRevenueDemandDao, request, locale);
		}else if (landRevenueDemandDao.getRevenueYear().equals("2025-26")) {
			return landRevenueService.getAllReceiptsByVillagePre(landRevenueDemandDao, request, locale);
		} else if(landRevenueDemandDao.getRevenueYear().equals("2024-25")){
			
			return landRevenueService.getAllReceiptsByVillagePre2024(landRevenueDemandDao, request, locale);
		}else{
			
			return landRevenueService.getAllReceiptsByVillagePre2023(landRevenueDemandDao, request, locale);

		}
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/persistLandRevenueChallan")
	public ChallanDtlDao persistLandRevenueChallan(@RequestBody ChallanDtlDao challanDtlDao, HttpServletRequest request,
			Locale locale) {
		return landRevenueService.persistLandRevenueChallan(challanDtlDao, request, locale);
	}

	@ResponseStatus(HttpStatus.ACCEPTED)
	@PostMapping("/getChallanDetails")
	public List<ChallanDtlDao> getChallanDetails(@RequestBody ChallanDtlDao challanDtlDao, HttpServletRequest request,
			Locale locale) {
		return landRevenueService.getChallanDetails(challanDtlDao, request, locale);
	}

	@ResponseStatus(HttpStatus.ACCEPTED)
	@PostMapping("/getChallanDetailsAll")
	public Response getChallanDetailsAll(@RequestBody ChallanDtlDao challanDtlDao, HttpServletRequest request,
			Locale locale) {
		if (challanDtlDao.getRevenueYear().equals(CommonConstants.currentYear)) {
			return landRevenueService.getChallanDetailsAll(challanDtlDao, request, locale);
		} else {
			return landRevenueService.getChallanDetailsAllPre(challanDtlDao, request, locale);
		}
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PutMapping(value = "/updateChallanDetails", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public MessageResponse updateChallanDetails(@RequestParam(name = "file", required = false) MultipartFile file,
			@RequestParam(name = "file0045", required = false) MultipartFile file0045, @RequestParam("id") Long id,
			@Valid @RequestParam("bankAddress") String bankAddress, @Valid @RequestParam("bankName") String bankName,
			@RequestParam("bankReceiptNumber") String bankReceiptNumber,
			@RequestParam("bankReceiptNumber0045") String bankReceiptNumber0045, HttpServletRequest request,
			Locale locale,
			String ccode, 
			String talukaCode,
			String districtCode,
			String revenueYear) {
		return landRevenueService.updateChallanDetails(file,
				file0045,
				id,
				bankAddress,
				bankName,
				bankReceiptNumber,
				bankReceiptNumber0045,
				request,
				locale,
				ccode,
				talukaCode,
				districtCode,
				revenueYear);
	}

	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/download")
	public ChallanDtl getFile(@RequestBody ChallanDtlDao challanDtlDao,
			HttpServletRequest request, Locale locale,
			String revenueYear,
			String districtCode,
			String talukaCode,
			String cCode)
			throws EmptyStackException, IOException {
		return landRevenueService.download(challanDtlDao, request, locale,
				revenueYear,districtCode,talukaCode,cCode);
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/download0045")
	public ResponseEntity<Object> getFile0045(@RequestParam Long id, HttpServletRequest request, Locale locale)
			throws EmptyStackException, IOException {
		return landRevenueService.download0045(id, request, locale);
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PostMapping("/discard")
	public MessageResponse deleteAttachment(@RequestParam Long id, HttpServletRequest request, Locale locale) {
		return landRevenueService.deleteAttachment(id, request, locale);
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/persistSankirn")
	public LandRevenueDemandDao persistSankirn(@RequestBody LandRevenueDemandDao landRevenueDemandDao,
			HttpServletRequest request, Locale locale) {
		return landRevenueService.persistSankirn(landRevenueDemandDao, request, locale);
	}

	@ResponseStatus(HttpStatus.ACCEPTED)
	@PreAuthorize("hasAnyAuthority('ROLE_TALATHI','ROLE_TAH')")
	@GetMapping("/getLandRevenueDemandDetails")
	public List<LandRevenueDemandDao> getLandRevenueDemandDetails(@RequestParam String revenueYear,
			@RequestParam Integer districtCode, @RequestParam Integer talukaCode, @RequestParam String cCode,
			HttpServletRequest request, Locale locale) {
		
	    
		return landRevenueService.getLandRevenueDemandDetails(revenueYear, districtCode, talukaCode, cCode, request,
				locale);
	}

	@ResponseStatus(HttpStatus.ACCEPTED)
	@GetMapping("/getKaJaPaRate")
	public Response getAkrushakRateforRural(@RequestParam String cCode, HttpServletRequest request, Locale locale) {
		return landRevenueService.getAkrushakRateforRural(cCode, request, locale);
	}

	@ResponseStatus(HttpStatus.ACCEPTED)
	@PostMapping("/getLrDtlByKhataNoAndKhataOwnerName")
	public Response getLrDtlByKhataNoAndKhataOwnerName(@RequestBody LandRevenueDemandDao landRevenueDemandDao,
			HttpServletRequest request, Locale locale) {
		if (landRevenueDemandDao.getRevenueYear().equals(CommonConstants.currentYear)) {
			return landRevenueService.getLrDtlByKhataNoAndKhataOwnerName(landRevenueDemandDao, request, locale);
		} else {
			return landRevenueService.getLrDtlByKhataNoAndKhataOwnerNamePre(landRevenueDemandDao, request, locale);

		}
	}
	
	//For checking PreviousRevenueYear --One Time Data Entry.
    @GetMapping("/checkPreviousYear")
    public ResponseEntity<OneTimeDataEntyRevenueYearCheckDTO> checkRevenueYear(@RequestParam String ccode, 
    		HttpServletRequest request,
			Locale locale ) {
    	OneTimeDataEntyRevenueYearCheckDTO response = landRevenueService.checkRevenueYear(ccode,request,locale);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/getDropDownsVillage")
	public VillageDropdownResponse getDropDownsVillage(@RequestParam String districtCode,@RequestParam String talukaCode) {
			return landRevenueService.getDropDownsVillage(districtCode,talukaCode);
	}
    
  //For 7B Signup 04-11-2026
    
    @GetMapping("/getDropDownsVillage7B")
	public List<MVillageMapper> getDropDownsVillage7B(@RequestParam String districtCode,@RequestParam String talukaCode) {
			return landRevenueService.getDropDownsVillage7B(districtCode,talukaCode);
	}

	@GetMapping("/getDropDownsDivision")
	public List<Division> getDropDownsDivision() {
		return landRevenueService.getDropDownsDivision();
	}
	
	
	@GetMapping("/getDropDownsDistrict")
	public List<District> getDropDownsDistrict(@RequestParam String divisionCode) {
		return landRevenueService.getDropDownsDistrict(divisionCode);
	}

	@GetMapping("/getDropDownsTaluka")
	public List<Taluka> getDropDownsTaluka(@RequestParam String districtCode) {
		return landRevenueService.getDropDownsTaluka(districtCode);
	}



	
	
}
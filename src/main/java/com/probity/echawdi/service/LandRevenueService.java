package com.probity.echawdi.service;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import com.probity.echawdi.dao.ChallanDtlDao;
import com.probity.echawdi.dao.CombinedChallanLandRevenueViewDao;
import com.probity.echawdi.dao.LandRevenueDemandDao;
import com.probity.echawdi.dao.LandRevenueDemandListDao;
import com.probity.echawdi.dao.OneTimeDataEntyRevenueYearCheckDTO;
import com.probity.echawdi.dao.VillageDropdownResponse;
import com.probity.echawdi.entity.ChallanDtl;
import com.probity.echawdi.entity.District;
import com.probity.echawdi.entity.Division;
import com.probity.echawdi.entity.MVillageMapper;
import com.probity.echawdi.entity.Taluka;
import com.probity.echawdi.exception.MessageResponse;
import com.probity.echawdi.payload.Response;

public interface LandRevenueService {

	List<LandRevenueDemandDao> generateRevenue(LandRevenueDemandDao landRevenueDemandDao, HttpServletRequest request,
			Locale locale);

	List<LandRevenueDemandDao> getLandRevenueDemandDtl(LandRevenueDemandDao landRevenueDemandDao,
			HttpServletRequest request, Locale locale);

	LandRevenueDemandDao lrReceiptMoneyFromCustomer(LandRevenueDemandDao landRevenueDemandDao,
			BindingResult bindingResult, HttpServletRequest request, Locale locale);

	LandRevenueDemandDao getReceiptDetailsByKhataNo(LandRevenueDemandDao landRevenueDemandDao,
			HttpServletRequest request, Locale locale);

	List<LandRevenueDemandDao> getAllReceiptsByVillage(LandRevenueDemandDao landRevenueDemandDao,
			HttpServletRequest request, Locale locale);

	ChallanDtlDao persistLandRevenueChallan(ChallanDtlDao challanDtlDao, HttpServletRequest request, Locale locale);

	List<ChallanDtlDao> getChallanDetails(ChallanDtlDao challanDtlDao, HttpServletRequest request, Locale locale);

	Response getChallanDetailsAll(ChallanDtlDao challanDtlDao, HttpServletRequest request, Locale locale);

	List<LandRevenueDemandDao> getForm8BData(String cCode, String revenueYear, Integer districtCode, Integer talukaCode,
			HttpServletRequest request, Locale locale);

	MessageResponse persistLRDemand(LandRevenueDemandListDao landRevenueDemandListDao, HttpServletRequest request,
			Locale locale);

	List<LandRevenueDemandDao> getLRDemandBycCodeAndStatus(LandRevenueDemandDao landRevenueDemandDao,
			HttpServletRequest request, Locale locale);

	MessageResponse editLRDemandById(LandRevenueDemandDao landRevenueDemandDao, HttpServletRequest request,
			Locale locale);

	MessageResponse deleteAttachment(Long id, HttpServletRequest request, Locale locale);

	MessageResponse updateChallanDetails(MultipartFile file,
			MultipartFile file0045, 
			Long id, 
			@Valid String bankAddress,
			@Valid String bankName, 
			String bankReceiptNumber,
			String bankReceiptNumber0045,
			HttpServletRequest request,
			Locale locale,
			String ccode,
			String talukaCode,
			String districtCode,
			String revenueYear);

	ChallanDtl download(ChallanDtlDao challanDtlDao,
			HttpServletRequest request,
			Locale locale,
			String revenueYear,
			String districtCode,
			String talukaCode,
			String cCode) throws IOException;

	LandRevenueDemandDao persistSankirn(LandRevenueDemandDao landRevenueDemandDao, HttpServletRequest request,
			Locale locale);

	List<LandRevenueDemandDao> getLandRevenueDemandDetails(String revenueYear, Integer districtCode, Integer talukaCode,
			String cCode, HttpServletRequest request, Locale locale);

	Response getAkrushakRateforRural(String cCode, HttpServletRequest request, Locale locale);

//	List<LandRevenueDemandDao> getAllForm9BReportDetails(Integer districtCode, Integer talukaCode, String cCode,
//			HttpServletRequest request, Locale locale);

	
	public List<LandRevenueDemandDao> getAllForm9BReportDetails(Integer districtCode, Integer talukaCode, String cCode,String revenueYear,
			HttpServletRequest request, Locale locale);

	Response getAllForm8DReportDetails(Integer districtCode, Integer talukaCode, String cCode, String revenueYear,
			HttpServletRequest request, Locale locale);
	
	
	LandRevenueDemandDao getReceiptDetailsByMakhtaKhataNo(LandRevenueDemandDao landRevenueDemandDao,
			HttpServletRequest request, Locale locale);

	Response getLrDtlByKhataNoAndKhataOwnerName(LandRevenueDemandDao landRevenueDemandDao, HttpServletRequest request,
			Locale locale);

	ResponseEntity<Object> download0045(Long id, HttpServletRequest request, Locale locale) throws IOException;

	void deleteLRDemandById(Long id, HttpServletRequest request, Locale locale);

	public List<CombinedChallanLandRevenueViewDao> getForm8BDataView(String cCode, String revenueYear,
			Integer districtCode, Integer talukaCode, HttpServletRequest request, Locale locale);

	public List<CombinedChallanLandRevenueViewDao> getForm8BDataViewPre(String cCode, String revenueYear,
			Integer districtCode, Integer talukaCode, HttpServletRequest request, Locale locale);

	/*
	 * *************************** 8B and 8K View - Service ***********************
	 */

	public List<CombinedChallanLandRevenueViewDao> getForm8B8kDataView(String cCode, String revenueYear,
			Integer districtCode, Integer talukaCode, Integer dropdownVal, HttpServletRequest request, Locale locale);

	/*
	 * ***************************End - 8B and 8K View - ServiceEnd
	 * ***********************
	 */

//	Response getform3surveyno(String cCode, String revenueYear,HttpServletRequest request,Locale locale);
//
//	Response getform2surveyno(String cCode, String revenueYear,HttpServletRequest request, Locale locale);

	// below are for pre years data
//	LandRevenueDemandDao getReceiptDetailsByMakhtaKhataNoPre(LandRevenueDemandDao landRevenueDemandDao,
//			HttpServletRequest request, Locale locale);
	
	//I am here
	List<LandRevenueDemandDao> getAllReceiptsByVillagePre2023(LandRevenueDemandDao landRevenueDemandDao,
			HttpServletRequest request, Locale locale);
	
	

	List<LandRevenueDemandDao> getAllReceiptsByVillagePre2024(LandRevenueDemandDao landRevenueDemandDao,
			HttpServletRequest request, Locale locale);
	
	//new creted for 2025-26
	List<LandRevenueDemandDao> getAllReceiptsByVillagePre(LandRevenueDemandDao landRevenueDemandDao,
			HttpServletRequest request, Locale locale);
	
	
	
	
	


	Response getChallanDetailsAllPre(ChallanDtlDao challanDtlDao, HttpServletRequest request, Locale locale);

	Response getLrDtlByKhataNoAndKhataOwnerNamePre(LandRevenueDemandDao landRevenueDemandDao,
			HttpServletRequest request, Locale locale);

	public List<CombinedChallanLandRevenueViewDao> getForm8B8kDataViewPre(String cCode, String revenueYear,
			Integer districtCode, Integer talukaCode, Integer dropdownVal, HttpServletRequest request, Locale locale);

	Response getAllForm8DReportDetailsPre(Integer districtCode, Integer talukaCode, String cCode, String revenueYear,
			HttpServletRequest request, Locale locale);

//	List<LandRevenueDemandDao> getAllForm9BReportDetailsPre(Integer districtCode, Integer talukaCode, String cCode,
//			String revenueYear, HttpServletRequest request, Locale locale);

	Response getListOfRojgarHamiTax(Integer districtCode, Integer talukaCode, String cCode, String revenueYear,
			HttpServletRequest request, Locale locale);
	
	
	//for OneTimeData Entry
	OneTimeDataEntyRevenueYearCheckDTO checkRevenueYear(String ccode,HttpServletRequest request, Locale locale);

	List<LandRevenueDemandDao> getListOfRojgarHamiTaxx(Integer districtCode, Integer talukaCode, String cCode,
			String revenueYear, HttpServletRequest request, Locale locale);

	List<LandRevenueDemandDao> getEduCessTaxx(Integer districtCode, Integer talukaCode, String cCode,
			String revenueYear, HttpServletRequest request, Locale locale);
	
	
	VillageDropdownResponse getDropDownsVillage(String districtCode ,String talukaCode);

	List<Taluka> getDropDownsTaluka(String districtCode );  
	
	List<District> getDropDownsDistrict(String divisionCode);
	List<MVillageMapper> getDropDownsVillage7B(String districtCode ,String talukaCode);
	List<Division> getDropDownsDivision();

		


}
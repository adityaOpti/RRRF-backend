package com.probity.echawdi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.probity.echawdi.entity.LandRevenueDemand2024;

public interface LandRevenueDemandRepo2024 extends JpaRepository<LandRevenueDemand2024, Long> {
	
	public List<LandRevenueDemand2024> findReceiptDetailsBycCodeAndIsMoneyepositedBankAndRevenueYearAndActiveFlag
	   (String cCode, Character isMoneyepositedBank, String revenueYear,Character activeFlag);
	
	
	
	public List<LandRevenueDemand2024> findReceiptDetailsBycCodeAndIsMoneyepositedBankAndRevenueYearAndModeOfPaymentAndActiveFlagAndChallanIdIsNull
	   (String cCode, Character isMoneyepositedBank, String revenueYear,String modeOfPayment,Character activeFlag);
	
}

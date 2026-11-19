package com.probity.echawdi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.probity.echawdi.entity.LandRevenueDemand2025;

public interface LandRevenueDemandRepo2025 extends JpaRepository<LandRevenueDemand2025, Long> {
	
	public List<LandRevenueDemand2025> findReceiptDetailsBycCodeAndIsMoneyepositedBankAndRevenueYearAndActiveFlag
	   (String cCode, Character isMoneyepositedBank, String revenueYear,Character activeFlag);
	
	
	
	public List<LandRevenueDemand2025> findReceiptDetailsBycCodeAndIsMoneyepositedBankAndRevenueYearAndModeOfPaymentAndActiveFlagAndChallanIdIsNull
	   (String cCode, Character isMoneyepositedBank, String revenueYear,String modeOfPayment,Character activeFlag);
	
}

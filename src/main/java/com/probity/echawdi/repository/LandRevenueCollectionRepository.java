///**
// * 
// */
//package com.probity.echawdi.repository;
//
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import com.probity.echawdi.entity.LandRevenueCollection;
//
///**
// * @author Sandeep Dubey
// *
// */
//@Repository
//public interface LandRevenueCollectionRepository extends JpaRepository<LandRevenueCollection, Long> {
//
//	public List<LandRevenueCollection> findReceiptDetailsBycCodeAndKhataNoAndRevenueYearAndActiveFlag(String cCode, String khataNo, String revenueYear, Character activeFlag);
//
//	public List<LandRevenueCollection> findReceiptDetailsBycCodeAndIsMoneyepositedBankAndRevenueYearAndActiveFlagAndChallanIdIsNull
//									   (String cCode, Character isMoneyepositedBank, String revenueYear, Character activeFlag);
//
//	public Optional<LandRevenueCollection> findBycCodeAndKhataNoAndActiveFlagAndRevenueYear(String cCode,String khataNo,Character activeFlag,String revenueYear);
//
//	public Optional<LandRevenueCollection> findBycCodeAndRevenueYearAndKhataNoAndActiveFlagAndLandReceiveForm17IdIsNull
//	(String cCode, String revenueYear,String khataNo, Character activeFlag);
//	
//	public Optional<LandRevenueCollection> findBycCodeAndRevenueYearAndKhataNoAndActiveFlagAndLandReceiveDemandIdIsNull
//	(String cCode, String revenueYear,String khataNo, Character activeFlag);
//	public Optional<LandRevenueCollection> findBycCodeAndKhataNoAndActiveFlag(String cCode, String khataNo,Character activeFlag);
//}
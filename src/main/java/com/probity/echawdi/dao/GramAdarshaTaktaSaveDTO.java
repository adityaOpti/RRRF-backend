package com.probity.echawdi.dao;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class GramAdarshaTaktaSaveDTO {
	
	
	    private Long id;
	    private Double version;
	    private Long createdUserId;
	    private LocalDateTime createDtTm;
	    private Long updateUserid;
	    private LocalDateTime updateDtTm;
	    private Integer districtCode;
	    private Integer talukaCode;
	    private String ccode;
	    private String revenueYear;
	    private Double villageTotalArea;
	    private Integer villagePopulation;
	    private Integer malePopulation;
	    private Integer femalePopulation;
	    private Integer otherPopulation;
	    private Integer khatedarCount;
	    private Double khatedarBhogwataAreaGrater;
	    private Double khatedarBhogwataAreaLess;
	    private Integer landlessFarmLaborers;
	    private Integer numberOfArtisans;
	    private Double bindumalaArea;
	    private Double dumalaArea;
	    private Double bindumalaDumalaTotalArea;
	    private Double kharipArea;
	    private Double rabbiArea;
	    private Double kharipRabbiTotalArea;
	    private Double akrushik;
	    private Double govPadikLandArea;
	    private Double padikLandArea;
	    private Double potkharabaArea;
	    private Double gavthanArea;
	    private Double forestLandArea;
	    private Double kuranArea;
	    private Double plargroundArea;
	    private Double mahsulmafOrKamakariLandArea;
	    private Double areaUnderEncroachment;
	    private Double agriTotalDemand;
	    private Double agriTotalNiyatDemand;
	    private Double agriTotalSankirnDemand;
	    private Double agriTotalDemandPrevious;
	    private Double agriTotalNiyatDemandPrevious;
	    private Double agriTotalSankirnDemandPrevious;
	    private Double akrushakTotalDemand;
	    private Double akrushakTotalDemandPrevious;
	    private Double educationalCessCurrent;
	    private Double educationalCessPrevious;
	    private Double employeeGuaranteeSchemeCurrent;
	    private Double employeeGuaranteeSchemePrevious;
	    private Double sankirnCurrent;
	    private Double sankirnPrevious;
	    private Double averageRainfall;
	    private Integer drinkingWaterResourceCount;
	    private Integer irrWellsUsed;
	    private Integer irrWellsUnused;
	    private Double bagayatArea;
	    private Double irrKalwaArea;
	    private Double irrBandhArea;
	    private Double irrWellMotaArea;
	    private Double irrWellOilEnginArea;
	    private Double irrWellElectricMotarArea;
	    private Double irrWellPumpSanchArea;
	    private Double irrWellArea;
	    private Double irrBudkiArea;
	    private Double otherIrrigation;
	    private Integer animalWithHorn;
	    private Integer animalWithoutHorn;
	    private Integer gramPanchayatOrGroupGramPanchayat;
	    private Integer judiciary;
	    private Integer panchayatCommittee;
	    private Integer nearestPoliceStation;
	    private Integer postalOffice;
	    private Integer primarySchool;
	    private Integer secondarySchool;
	    private Integer college;
	    private Integer publicHospital;
	    private Integer transport;
	    private Integer coOrganization;
	    private Integer coOperativeFactories;
	    private Integer femaleBoard;
	    private Integer youngBoard;
	    private Integer bhajaniBoard;
	    private Integer library;
	    private Integer nearestRailwayStation;
	    private Double railwayStationDistanceFromVillage;
	    private Integer chawadi;
	    private Integer rashanShopCount;
	    private String villageMarketDay;
	    private String carnivalJatraDay;
	    private Integer familyPlanningCentersCount;
	    private Integer familyPalannigMaleCount;
	    private Integer familyPalannigFemaleCount;
	    private String majorBusinessOfVillage;
	    private String sarpanchName;
	    private String policePatilName;
	    private Integer kotwalCount;
	    private String kotwalName;
	    private String presidentOfCoOperativeSociety;
	    private String talathiName;
	    private Character activeFlag;
	    
	  //newly added colomn
	    private Double	irrUgharanArea	;
		private Integer	irrKalwaCount	;
		private Integer	irrBandhCount	;
		private Integer	irrWellMotaCount;	
		private Integer	irrWellOilEnginCount;	
		private Integer	irrWellElectricMotarCount;	
		private Integer	irrWellPumpSanchCount	;
		private Integer	irrWellCount	;
		private Integer	irrBudkiCount	;
		private Integer	irrUgharanCount	;
		
		private Integer panipuravathaDrinkingWellCount;	
		private Integer panipuravathaDrinkingRiverCount;	
		private Integer panipuravathaDrinkingVindhanVihirCount;	
		private Integer panipuravathaDrinkingKalwaCount	;
		private Integer panipuravathaDrinkingTalawCount	;

		private Integer panipuravathaAgriWellCount;
		private Integer panipuravathaAgriRiverCount;	
		private Integer panipuravathaAgriVindhanVihirCount;
		private Integer panipuravathaAgriKalwaCount;
		private Integer panipuravathaAgriTalawCount;
		
		private Integer total_eseva_kendra;
		
		private Integer other_irrigation_count;
		
		private Long KhataCountBelow2023;
		
	    private Long KhataCountAbove2023;
		
		//ended here newly created colomns
	    
	    private List<VillageCropAreaDTO> cropAreas;
	


}

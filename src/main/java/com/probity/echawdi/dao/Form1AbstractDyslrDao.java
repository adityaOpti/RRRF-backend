package com.probity.echawdi.dao;


import java.math.BigDecimal;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Form1AbstractDyslrDao {
	
	public Long id;
	 
	public Integer districtCode;
	
	public Integer talukaCode;
	
	public String cCode;
	
	public BigDecimal tenureCode1;
	
	public BigDecimal tenureCode2;
	
	public BigDecimal tenureCode3;
	
	
	
	public BigDecimal nonOccupationGov;
	
	public BigDecimal RevenueOrLeaseholdLandUnderSpecialAgreement;
	
	public BigDecimal dumala;
	
	public BigDecimal unOccupied;
	
	public BigDecimal assignedForSpecialUse;
	
	public BigDecimal potKharab;
	
	public BigDecimal riversNalas;
	
	public BigDecimal nalas;
	
	public String activeFlag;
	public BigDecimal forest;
	
	public BigDecimal kuran;
	
	public BigDecimal freePastureCattleStand;
	
	public BigDecimal villageSite;
	
	public BigDecimal tank;
	
	public BigDecimal burialGround;
	
	public BigDecimal railways;
	
	public BigDecimal potKharabAssignedRoadsWaterCourses;
	
	public BigDecimal roadsAndPath;
	
	public BigDecimal pipeLineCanel;
	
	public BigDecimal CantonmentLandMilitaryCamp;
	
	public BigDecimal schools;
	
	public BigDecimal dharmshalas;
	
	public BigDecimal srNoForNonAgricultureUse;
	
	public BigDecimal potKharabArea;

	public BigDecimal potKharabAssessment;

	public BigDecimal nalaAreaH;

	public BigDecimal riverAreaH;
	 
	public BigDecimal form3NetCultiArea;

	public BigDecimal form3Assessment;

	public BigDecimal tenure1Area;
	
	public String tenure1Areaa;
	 
	public BigDecimal tenure1Assessment;

	public BigDecimal tenure2Area;
	
	public String tenure2Areaa;

	 
	public BigDecimal tenure2Assessment;
	 
	public BigDecimal tenure3Area;
	
	public String tenure3Areaa;
	 
	public BigDecimal tenure3Assessment;
	 
	public BigDecimal tenure4Area;
	public String tenure4Areaa;
	 
	public BigDecimal tenure4Assessment;
	
	

//	public Long landType;
//	
//	public String landTypeName;
//	
//	public String landTypeNameMarathi;
//	
//	public BigDecimal area;
//	
//	public String areaUnit;
//	
//	public BigDecimal assessment;
//	
//	public String remarks;
	
//	public String getcCode() {
//		return cCode;
//	}
//
//	public void setcCode(String cCode) {
//		this.cCode = cCode;
//	}
//
//	public Long getId() {
//		return id;
//	}
//
//	public void setId(Long id) {
//		this.id = id;
//	}
//
//	public Integer getDistrictCode() {
//		return districtCode;
//	}
//
//	public void setDistrictCode(Integer districtCode) {
//		this.districtCode = districtCode;
//	}
//
//	public Integer getTalukaCode() {
//		return talukaCode;
//	}
//
//	public void setTalukaCode(Integer talukaCode) {
//		this.talukaCode = talukaCode;
//	}
//
//	public BigDecimal getTenureCode1() {
//		return tenureCode1;
//	}
//
//	public void setTenureCode1(BigDecimal tenureCode1) {
//		this.tenureCode1 = tenureCode1;
//	}
//
//	public BigDecimal getTenureCode2() {
//		return tenureCode2;
//	}
//
//	public void setTenureCode2(BigDecimal tenureCode2) {
//		this.tenureCode2 = tenureCode2;
//	}
//
//	public BigDecimal getTenureCode3() {
//		return tenureCode3;
//	}
//
//	public void setTenureCode3(BigDecimal tenureCode3) {
//		this.tenureCode3 = tenureCode3;
//	}
//
//	public BigDecimal getNonOccupationGov() {
//		return nonOccupationGov;
//	}
//
//	public void setNonOccupationGov(BigDecimal nonOccupationGov) {
//		this.nonOccupationGov = nonOccupationGov;
//	}
//
//	public BigDecimal getRevenueOrLeaseholdLandUnderSpecialAgreement() {
//		return RevenueOrLeaseholdLandUnderSpecialAgreement;
//	}
//
//	public void setRevenueOrLeaseholdLandUnderSpecialAgreement(BigDecimal revenueOrLeaseholdLandUnderSpecialAgreement) {
//		RevenueOrLeaseholdLandUnderSpecialAgreement = revenueOrLeaseholdLandUnderSpecialAgreement;
//	}
//
//	public BigDecimal getDumala() {
//		return dumala;
//	}
//
//	public void setDumala(BigDecimal dumala) {
//		this.dumala = dumala;
//	}
//
//	public BigDecimal getUnOccupied() {
//		return unOccupied;
//	}
//
//	public void setUnOccupied(BigDecimal unOccupied) {
//		this.unOccupied = unOccupied;
//	}
//
//	public BigDecimal getAssignedForSpecialUse() {
//		return assignedForSpecialUse;
//	}
//
//	public void setAssignedForSpecialUse(BigDecimal assignedForSpecialUse) {
//		this.assignedForSpecialUse = assignedForSpecialUse;
//	}
//
//	public BigDecimal getPotKharab() {
//		return potKharab;
//	}
//
//	public void setPotKharab(BigDecimal potKharab) {
//		this.potKharab = potKharab;
//	}
//
//	public BigDecimal getRiversNalas() {
//		return riversNalas;
//	}
//
//	public void setRiversNalas(BigDecimal riversNalas) {
//		this.riversNalas = riversNalas;
//	}
//
//	
//
//	public String getActiveFlag() {
//		return activeFlag;
//	}
//
//	public void setActiveFlag(String activeFlag) {
//		this.activeFlag = activeFlag;
//	}
//
//	public BigDecimal getForest() {
//		return forest;
//	}
//
//	public void setForest(BigDecimal forest) {
//		this.forest = forest;
//	}
//
//	public BigDecimal getKuran() {
//		return kuran;
//	}
//
//	public void setKuran(BigDecimal kuran) {
//		this.kuran = kuran;
//	}
//
//	public BigDecimal getFreePastureCattleStand() {
//		return freePastureCattleStand;
//	}
//
//	public void setFreePastureCattleStand(BigDecimal freePastureCattleStand) {
//		this.freePastureCattleStand = freePastureCattleStand;
//	}
//
//	public BigDecimal getVillageSite() {
//		return villageSite;
//	}
//
//	public void setVillageSite(BigDecimal villageSite) {
//		this.villageSite = villageSite;
//	}
//
//	public BigDecimal getTank() {
//		return tank;
//	}
//
//	public void setTank(BigDecimal tank) {
//		this.tank = tank;
//	}
//
//	public BigDecimal getBurialGround() {
//		return burialGround;
//	}
//
//	public void setBurialGround(BigDecimal burialGround) {
//		this.burialGround = burialGround;
//	}
//
//	public BigDecimal getRailways() {
//		return railways;
//	}
//
//	public void setRailways(BigDecimal railways) {
//		this.railways = railways;
//	}
//
//	public BigDecimal getPotKharabAssignedRoadsWaterCourses() {
//		return potKharabAssignedRoadsWaterCourses;
//	}
//
//	public void setPotKharabAssignedRoadsWaterCourses(BigDecimal potKharabAssignedRoadsWaterCourses) {
//		this.potKharabAssignedRoadsWaterCourses = potKharabAssignedRoadsWaterCourses;
//	}
//
//	public BigDecimal getRoadsAndPath() {
//		return roadsAndPath;
//	}
//
//	public void setRoadsAndPath(BigDecimal roadsAndPath) {
//		this.roadsAndPath = roadsAndPath;
//	}
//
//	public BigDecimal getPipeLineCanel() {
//		return pipeLineCanel;
//	}
//
//	public void setPipeLineCanel(BigDecimal pipeLineCanel) {
//		this.pipeLineCanel = pipeLineCanel;
//	}
//
//	public BigDecimal getCantonmentLandMilitaryCamp() {
//		return CantonmentLandMilitaryCamp;
//	}
//
//	public void setCantonmentLandMilitaryCamp(BigDecimal cantonmentLandMilitaryCamp) {
//		CantonmentLandMilitaryCamp = cantonmentLandMilitaryCamp;
//	}
//
//	public BigDecimal getSchools() {
//		return schools;
//	}
//
//	public void setSchools(BigDecimal schools) {
//		this.schools = schools;
//	}
//
//	public BigDecimal getDharmshalas() {
//		return dharmshalas;
//	}
//
//	public void setDharmshalas(BigDecimal dharmshalas) {
//		this.dharmshalas = dharmshalas;
//	}
//
//	public BigDecimal getSrNoForNonAgricultureUse() {
//		return srNoForNonAgricultureUse;
//	}
//
//	public void setSrNoForNonAgricultureUse(BigDecimal srNoForNonAgricultureUse) {
//		this.srNoForNonAgricultureUse = srNoForNonAgricultureUse;
//	}
//
//	public BigDecimal getPotKharabArea() {
//		return potKharabArea;
//	}
//
//	public void setPotKharabArea(BigDecimal potKharabArea) {
//		this.potKharabArea = potKharabArea;
//	}
//
//	public BigDecimal getPotKharabAssessment() {
//		return potKharabAssessment;
//	}
//
//	public void setPotKharabAssessment(BigDecimal potKharabAssessment) {
//		this.potKharabAssessment = potKharabAssessment;
//	}
//
//	public BigDecimal getNalaAreaH() {
//		return nalaAreaH;
//	}
//
//	public void setNalaAreaH(BigDecimal nalaAreaH) {
//		this.nalaAreaH = nalaAreaH;
//	}
//
//	public BigDecimal getRiverAreaH() {
//		return riverAreaH;
//	}
//
//	public void setRiverAreaH(BigDecimal riverAreaH) {
//		this.riverAreaH = riverAreaH;
//	}
//
//	public BigDecimal getForm3NetCultiArea() {
//		return form3NetCultiArea;
//	}
//
//	public void setForm3NetCultiArea(BigDecimal form3NetCultiArea) {
//		this.form3NetCultiArea = form3NetCultiArea;
//	}
//
//	public BigDecimal getForm3Assessment() {
//		return form3Assessment;
//	}
//
//	public void setForm3Assessment(BigDecimal form3Assessment) {
//		this.form3Assessment = form3Assessment;
//	}
//
//	public BigDecimal getTenure1Area() {
//		return tenure1Area;
//	}
//
//	public void setTenure1Area(BigDecimal tenure1Area) {
//		this.tenure1Area = tenure1Area;
//	}
//
//	public BigDecimal getTenure1Assessment() {
//		return tenure1Assessment;
//	}
//
//	public void setTenure1Assessment(BigDecimal tenure1Assessment) {
//		this.tenure1Assessment = tenure1Assessment;
//	}
//
//	public BigDecimal getTenure2Area() {
//		return tenure2Area;
//	}
//
//	public void setTenure2Area(BigDecimal tenure2Area) {
//		this.tenure2Area = tenure2Area;
//	}
//
//	public BigDecimal getTenure2Assessment() {
//		return tenure2Assessment;
//	}
//
//	public void setTenure2Assessment(BigDecimal tenure2Assessment) {
//		this.tenure2Assessment = tenure2Assessment;
//	}
//
//	public BigDecimal getTenure3Area() {
//		return tenure3Area;
//	}
//
//	public void setTenure3Area(BigDecimal tenure3Area) {
//		this.tenure3Area = tenure3Area;
//	}
//
//	public BigDecimal getTenure3Assessment() {
//		return tenure3Assessment;
//	}
//
//	public void setTenure3Assessment(BigDecimal tenure3Assessment) {
//		this.tenure3Assessment = tenure3Assessment;
//	}
//
//	public BigDecimal getTenure4Area() {
//		return tenure4Area;
//	}
//
//	public void setTenure4Area(BigDecimal tenure4Area) {
//		this.tenure4Area = tenure4Area;
//	}
//
//	public BigDecimal getTenure4Assessment() {
//		return tenure4Assessment;
//	}
//
//	public void setTenure4Assessment(BigDecimal tenure4Assessment) {
//		this.tenure4Assessment = tenure4Assessment;
//	}
	
	
}
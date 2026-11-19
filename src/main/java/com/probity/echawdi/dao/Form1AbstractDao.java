/**
 * 
 */
package com.probity.echawdi.dao;

import java.math.BigDecimal;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Bhagyashri Lolage
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Form1AbstractDao {
	
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
	 
	public BigDecimal tenure1Assessment;

	public BigDecimal tenure2Area;
	 
	public BigDecimal tenure2Assessment;
	 
	public BigDecimal tenure3Area;
	 
	public BigDecimal tenure3Assessment;
	 
	public BigDecimal tenure4Area;
	 
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
	
	public String getcCode() {
		return cCode;
	}

	public void setcCode(String cCode) {
		this.cCode = cCode;
	}
}
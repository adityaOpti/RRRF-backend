/**
 * 
 */
package com.probity.echawdi.dao;


import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Rushikesh Durgude
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Form3Dao {

	public Long id;
	@NotNull(message = "{districtCode.notempty}")
	public Integer districtCode;
	@NotNull(message = "{talukaCode.notempty}")
	public Integer talukaCode;
	@NotNull(message = "{cCode.notempty}")
	public String cCode;
	public String khataNo;
	@NotNull(message = "{pin.notempty}")
	public String pin;
	public String pin1;
	public String pin2;
	public String pin3;
	public String pin4;
	public String pin5;
	public String pin6;
	public String pin7;
	public String pin8;
	public String classes;
	public String naArea;
	public String naAssessment;
	public String netCultiAreaH;
	public String assessment;
	public BigDecimal juniSalami;
	public String total;
	public String hissaNo;
	public String naAreaH;
	public String rate;	
	public BigDecimal netPending;
	public String echAssessment;
	@NotNull(message = "{kindAndHowLongContinuable.notempty}")
	public String kindAndHowLongContinuable;
	public Long runningNoEachClass ;
	//@NotNull(message = "{noInRegisterOfLands.notempty}")
	public String noInRegisterOfLands;
	//@NotNull(message = "{sanadNo.notempty}")
	public String sanadNo;
	public String remarks ;
	public String potKharabArea;
	public String totalArea;
	public String getcCode() {
		return cCode;
	}
	public void setcCode(String cCode) {
		this.cCode = cCode;
	}
	 public Integer getIntPin() {
			return StringUtils.isNumeric(pin) == true ? Integer.valueOf(pin): 0;
	}
}
//	    public String typeAndPeriod;

//	    public String currentNo;

//	    public String registerNoAndTypeEligibility;

//	    public String cultivableArea;

//		public String potKharabAre;

//	    public String TotalAreaofCultivable;

//	    public String cultivableAssessment;

//	    public String jamabandiRate;

//	    public String jamabandhiAssessment;

//	    public String dumaladarLosses;

//		public String tenure;

//	    public String assessment;  //form7area

//	    public String tenureName;

//	    public Character  activeFlag;
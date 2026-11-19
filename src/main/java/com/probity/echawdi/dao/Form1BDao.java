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
 * @author Bhagyashri Lolage
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Form1BDao {

	public Long id;

	@NotNull(message= "{districtCode.notempty}")
	public Integer districtCode;

	@NotNull(message= "{talukaCode.notempty}")
	public Integer talukaCode;

	@NotNull(message= "{cCode.notempty}")
	public String cCode;	

	public String khataNo;

	@NotNull(message= "{pin.notempty}")
	public String pin;	
	public String pin1;	
	public String pin2;	
	public String pin3;	
	public String pin4;	
	public String pin5;	
	public String pin6;	
	public String pin7;	
	public String pin8;

//	@NotNull(message = "{unassessedArea.notempty}")
	public BigDecimal unassessedArea;

	@NotNull(message = "{publicRightsOfWayAndEasements.notempty}")
	public String publicRightsOfWayAndEasements;
	
	
	public BigDecimal assessedArea;
	
	//@NotNull(message = "{remarks.notempty}")
	public String remarks;

	public String assessment;  //form7area 

	@NotNull(message="{hissaNo.notempty}")
	public String hissaNo;  //pin1 to 8

	public String totalAreaH;//form7

	public String totalAreaR;//form7area

	public String totalAreaSqm;//form7area

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

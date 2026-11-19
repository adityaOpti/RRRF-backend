package com.probity.echawdi.dao;


import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdditionalLandRevenueDao {

	public Long id;

	public String khataNo;
	
	public String surveyHissaNo;

	public String khataOwnerName;

	public BigDecimal area;

	public BigDecimal assessment;

	@NotNull(message="{cCode.notempty}")
	public String cCode;
	
	public Character activeFlag;
	
	List<AdditionalLandRevenueDao> additionalLandRevenueDao;
	
	public  String radiogroup;
		
	public String checkboxgroup;
	
	public String remarks;

	public String revenueYear;
	private Character applicableForDemand;

	
	public String getcCode() {
		return cCode;
	}

	public void setcCode(String cCode) {
		this.cCode = cCode;
	}
}
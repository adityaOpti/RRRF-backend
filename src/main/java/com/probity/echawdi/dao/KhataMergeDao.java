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
public class KhataMergeDao {

	public Long id;

	public String khataNo;
	
	//public String revenueYear="2024-25";
	
	public String revenueYear;
	
	public String servarthId;
	
	@NotNull(message="{cCode.notempty}")
	public String cCode;

	@NotNull(message = "{districtCode.notempty}")
	public Integer districtCode;

	@NotNull(message = "{talukaCode.notempty}")
	public Integer talukaCode;

	public List<KhataMergeDtlDao> khataMergeDtlLst;

	public Character activeFlag;
	
	public String mergeId;
	
	public String getcCode() {
		return cCode;
	}

	public void setcCode(String cCode) {
		this.cCode = cCode;
	}
	
	public String surveyHissaNo;

	public String khataOwnerName;

	public BigDecimal area;

	public BigDecimal assessment;

	public String uom;
	
	public String villageName;
}

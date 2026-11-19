package com.probity.echawdi.dao;

import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KhataMergeDtlDao {

	public Long id;

	public String khataNo;

	public String surveyHissaNo;

	public String khataOwnerName;

	public BigDecimal area;

	public BigDecimal assessment;

	public Long parentId;

	@NotNull(message="{cCode.notempty}")
	public String cCode;
	
	public String revenueYear;

	@NotNull(message = "{districtCode.notempty}")
	public Integer districtCode;

	@NotNull(message = "{talukaCode.notempty}")
	public Integer talukaCode;

	public String mergeId;
	
	public String childKhataNumbers;
	
	public Character isPresent;
	
	public Character activeFlag;

	public String uom;

	public String getcCode() {
		return cCode;
	}

	public void setcCode(String cCode) {
		this.cCode = cCode;
	}
}
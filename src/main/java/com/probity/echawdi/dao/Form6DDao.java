package com.probity.echawdi.dao;

import java.sql.Date;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Form6DDao {

public Long id;

	
	@NotNull(message = "{districtCode.notempty}")
	public Integer districtCode;
	
	@NotNull(message = "{talukaCode.notempty}")
	public Integer talukaCode;
	
	@NotNull(message = "{cCode.notempty}")
	public String cCode;
	
	@NotNull(message = "{pin.notempty}")
	public String pin;

	@NotNull(message = "{hissaNo.notempty}")
	public String hissaNo;
	
	@NotNull(message = "{natureOfChangeInMap.notempty}")
	public String natureOfChangeInMap;
	
	
	public String  byWhomDone;
   
	public String surveyNoOrSubDivisionNo;
	
	public Date dateOfChange;
	
	public String byWhomDoneAndDate;

    public String mutationEntryInVillageForm6;
    
    public String getcCode() {
		return cCode;
	}
	public void setcCode(String cCode) {
		this.cCode = cCode;
	}
}
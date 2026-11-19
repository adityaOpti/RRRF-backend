package com.probity.echawdi.dao;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Form6BDao {
	public Long id;
	 
	@NotNull(message = "{districtCode.notempty}")
	public Integer districtCode;
	 
	@NotNull(message = "{talukaCode.notempty}")
	public Integer talukaCode;
	
    @NotNull(message = "{cCode.notempty}")
    public String cCode;
    
//    @NotNull(message = "{pin.notempty}")
//    public String pin;
    
    public Integer entryVillageForm6;
    
    public String khataNo;
    
    public LocalDate dateOfDeath;
    
    public String reasonOfDeath ;
    
    public String nameOfAcquirerOfRight;
	
	@NotNull(message= "{orderOfTahsildarAsFine.notempty}")
    public String orderOfTahsildarAsFine;
	
	@NotNull(message = "{receiptNo.notempty}")
    public String  receiptNo;
	
	@NotNull(message = "{receiptDate.notempty}")
    public LocalDate receiptDate;
    
//    @NotNull(message = "{hissaNo.notempty}")
    public String hissaNo;
    
    
    public String getcCode() {
		return cCode;
	}
	public void setcCode(String cCode) {
		this.cCode = cCode;
	}
}
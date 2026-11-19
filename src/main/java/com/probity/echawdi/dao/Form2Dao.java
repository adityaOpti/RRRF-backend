/**
 * 
 */
package com.probity.echawdi.dao;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;

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
public class Form2Dao {
	
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
    public Character isFromEmpty;
    public Character isToEmpty;
    public Character isOccupancyPriceEmpty;
    public String part;
    
    @NotNull(message = "{purposeOfUse.notempty}")
    private String purposeOfUse ;
    
//    @NotNull(message = "{natureAndTermsOfNaGrant.notempty}")
//    public String natureAndTermsOfNaGrant;
    
   
    public String occupanyPrice;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date periodFrom ;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date periodTo;
    
    @NotNull(message = "{entryNumberInTalukaForm2.notempty}")
    public String entryNumberInTalukaForm2;
    
    public String  authority;
    
    public String authorityOrderNo;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date authorityDate;
    
   // @NotNull(message = "{nameOfFirstOccupant.notempty}")
    public String  nameOfFirstOccupant;
    
    //@NotNull(message = "{remarks.notempty}")
    public String  remarks;
    
//    public Character  activeFlag;
    
    public String naAreaH;
    
    @NotNull(message = "{hissaNo.notempty}")
    public String hissaNo;
    
    public String naAssessment;  //form7area 
    
    public String codeNo;
    
    public String description;
    
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

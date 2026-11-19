/**
 * 
 */
package com.probity.echawdi.dao;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.NotNull;

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
public class Form1EDao {

	public Long id;
	
	@NotNull(message = "{districtCode.notempty}")
	public Integer districtCode;
	
	@NotNull(message = "{talukaCode.notempty}")
	public Integer talukaCode;
	
    @NotNull(message="{cCode.notempty}")
    public String cCode;
	
    public String khataNo;
	
	@NotNull(message="{pin.notempty}")
    public String pin;
	
//    public String pin1;
//    public String pin2;
//    public String pin3;
//    public String pin4;
//    public String pin5;
//    public String pin6;
//    public String pin7;
//    public String pin8;
    
    @NotNull(message="{nameOfEncroacher.notempty}")
    public String nameOfEncroacher;
    
    public String areaOfEncroachedPath;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate dateOfEncroachment;
    
    @NotNull(message="{purposeOfEncroachmentLand.notempty}")
    public String purposeOfEncroachmentLand;
    
    public Integer  yearFromWhichRentRecoverable;
    
    public String yearOfEncroachmentNotedIfRemoved;
    
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate dateOfDecisionOnEncroachment;
    
    @NotNull(message="{hissaNo.notempty}")
    public String hissaNo;
    
    //@NotNull(message = "remarks.notempty")
	public String  remarks;
    
    public  BigDecimal areaOfEncroached;
    
    public BigDecimal totalArea;
	
    public String fileName;
    
    public byte[] attachment;
    
	public String getcCode() {
		return cCode;
	}
	  public void setcCode(String cCode) {
		this.cCode = cCode;
	}
}

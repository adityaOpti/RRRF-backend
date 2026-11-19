/**
 * 
 */
package com.probity.echawdi.dao;

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
public class Form1DDao {
	
	public Long id;
	
	@NotNull(message ="{districtCode.notempty}")
	public Integer districtCode;
	
	@NotNull(message ="{talukaCode.notempty}")
    public Integer talukaCode;
    
    @NotNull(message ="{cCode.notempty}")
	public String cCode;
    
	public String khataNo;
    
    @NotNull(message ="{pin.notempty}")
	public String pin;
	
    @NotNull(message ="{remarks.notempty}")
	public String  remarks;
    
    @NotNull(message ="{rules}")
	public String  rules;
    
	public String assessment;  //form7area 
    
    @NotNull(message="{hissaNo.notempty}")
	public String hissaNo;  //pin1 to 8
    
	public String totalAreaH;//total_area in hecter form7a
	
	public String irrigatedArea;//irrigated Area form7a bagayatAreaH 
	
	public String unirrigatedArea;//un-irrigated Area form7a jirayatAreaH
	
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

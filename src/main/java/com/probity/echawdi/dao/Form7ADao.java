/**
 * 
 */
package com.probity.echawdi.dao;

import javax.validation.constraints.NotNull;

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
public class Form7ADao {
	
public Long id;
	
	@NotNull(message = "{districtCode.notempty}")
	public Integer districtCode;
	
	@NotNull(message = "{talukaCode.notempty}")
	public Integer talukaCode;
	
	@NotNull(message= "{cCode.notempty}")
    public String cCode;
	
    public String khataNo;
    
    @NotNull(message= "{pin.notempty}")
    public String pin;
    
    public String lastYearSerialNo;
    
    @NotNull(message= "{nameOfTenant.notempty}")
    public String nameOfTenant;
    
    //@NotNull(message= "{rent.notempty}")
    public String rent;
    
    //@NotNull(message= "{serialNoInMutationReg.notempty}")
    public String serialNoInMutationReg;
    
    @NotNull(message= "{hissaNo.notempty}")
    public String hissaNo;
    
    //@NotNull(message= "{remarks.notempty}")
    public String remarks;

    @NotNull(message= "{area.notempty}")
    public String area;
    
    public String landlordName;
    
    public String assessment;
    
    public String TotalArea;
	public String getcCode() {
		return cCode;
	}
	  public void setcCode(String cCode) {
		this.cCode = cCode;
	}

}

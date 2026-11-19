/**
 * 
 */
package com.probity.echawdi.dao;

import java.time.LocalDate;

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
public class Form7BDao {
	
	  private Long id ;
	  
	  @NotNull(message = "{districtCode.notempty}")
	  public Integer districtCode;
		
	  @NotNull(message = "{talukaCode.notempty}")
	  public Integer talukaCode;
	  
	  @NotNull(message = "{cCode.notempty}")
	  private String cCode ;
	  
	 
	  @NotNull(message = "{khataNo.notempty}")
	  private String khataNo ;
	  
	  
	  private String  pin ;
	  
	  private String  pin1 ;
	  private String  pin2 ;
	  private String  pin3 ;
	  private String  pin4 ;
	  private String  pin5 ;
	  private String  pin6 ;
	  private String  pin7 ;
	  private String  pin8 ;
	  
	  @NotNull(message = "{year.notempty}")
	  private String year ;
	  
	  @NotNull(message ="{fnamePersonInPossession.notempty}")
	  private String  fnamePersonInPossession ;  
	  
	  @NotNull(message ="{mnamePersonInPossession.notempty}")
	  private String mnamePersonInPossession ;
	  
	  @NotNull(message ="{lnamePersonInPossession.notempty}")
	  private String lnamePersonInPossession ;
	  
	  private  LocalDate possessionDate ;
	  
	  @NotNull(message ="{designation.notempty}")
	  private String designation ;
	  
	  private  LocalDate orderDate ;
	  
	  @NotNull(message ="{orderNo.notempty}")
	  private  String orderNo ;
	  
	 
	  private String hissaNo;
	  
	  //@NotNull(message ="{remarks.notempty}"  )
	  private  String remarks ;
	  
	  private String fullName;
	  
	public String getcCode() {
		return cCode;
	}
	public void setcCode(String cCode) {
		this.cCode = cCode;
	}
		

}

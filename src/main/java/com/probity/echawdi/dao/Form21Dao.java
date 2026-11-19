/**
 * 
 */
package com.probity.echawdi.dao;

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
public class Form21Dao {

	public Long id;
	
	public Integer districtCode;
	
	
	public Integer talukaCode;
   
    
   	public String cCode;
    
    @NotNull(message= "{villageName.notempty}")
    public String villageName;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate diaryDate ;
    
    @NotNull(message= "{day.notempty}")
    public String day;
    
    @NotNull(message= "{remarks.notempty}")
    public String  remarks ;
    
    @NotNull
 	private String userId;
    
    public String getcCode() {
 		return cCode;
 	}
 	  public void setcCode(String cCode) {
 		this.cCode = cCode;
 	}
}

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
public class Form19Dao {

public Long id;
	
	public Integer districtCode;
	
	
	public Integer talukaCode;
    
   
    public String cCode;
   
    @NotNull(message = "{descriptionOfArticle.notempty}")
    public String descriptionOfArticle;
  
  
    public String  authorityOfPurchase;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate  dateOfPurchase;
    
    @NotNull(message = "{numberOrQuantity.notempty}")
    public String  numberOrQuantity;
   
    //@NotNull(message = "{authorityOfVoucher.notempty}")
    public String  authorityOfVoucher;
    
    //@NotNull(message = "{amountWrittenOff.notempty}")
    public String  amountWrittenOff;
    
    
    public String  amountRealized;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate dateOfCreditAtTreasury;
    
    @NotNull(message = "{number.notempty}")
    public String  number;
    
    @NotNull(message = "{value.notempty}")
    public String  value;
    
    //@NotNull(message = "{remarks.notempty}")
    public String  remarks;
	
    private String sajjaCode;
    
    public String getcCode() {
 		return cCode;
 	}
 	  public void setcCode(String cCode) {
 		this.cCode = cCode;
 	}
	
 	 @NotNull
 	private String userId;


}

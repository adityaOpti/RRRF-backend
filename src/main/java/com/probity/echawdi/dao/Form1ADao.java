/**
 * 
 */
package com.probity.echawdi.dao;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

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
public class Form1ADao {
	
	 public Long id;

     @NotNull(message ="{districtCode.notempty}")
	 public Integer districtCode;
     
     @NotNull(message ="{talukaCode.notempty}")
     public Integer talukaCode;
     
     @NotNull(message ="{cCode.notempty}")
     public String cCode;
     
     public String khataNo ;
     
    @NotNull(message ="{pin.notempty}")
     public String pin;
     public String pin1;
     public String pin2;
     public String pin3;
     public String pin4;
     public String pin5;
     public String pin6;
     public String pin7;
     public String pin8;
     
     @NotNull(message="{hissaNo.notempty}")
 	 public String hissaNo;
	 
//	 @NotNull(message = "{typeOfLand.notempty}")
//     public String typeOfLand;
	 
//     public String whetherVillageOrProtectedOrReservedForest;
	 
	 @NotNull(message ="{area.notempty}")
     public BigDecimal area;
	 
	// @NotNull(message = "{rightsRecordedByTheForestOfficer.notempty}")
     public String rightsRecordedByTheForestOfficer;
	 
	 //@NotNull(message = "{remarks.notempty}")
     public String remarks;
	 
//	 @NotNull(message = "{uom.notempty}")
//     public String uom;
	 
     private String villageName;
     
   //  private String nirankType;
     
     private BigDecimal villageForest;
     
     private BigDecimal protectedForest;
     
     private BigDecimal reservedForest;
     
     private String forestNoIfAny;
	 
     public String getcCode() {
 		return cCode;
 	}
 	  public void setcCode(String cCode) {
 		this.cCode = cCode;
 	}
}

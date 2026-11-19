package com.probity.echawdi.dao;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LGDMapperDao {

	  private String lgdCode;
	  
	  private String divisionCode;

	  private String districtCode;
	  
	  private String districtNameMarathi;
	  
	  private String talukaCode;
	  
	  private String talukaMameMarathi;
	  
	  private String cCode;
	  
	  private String lgdVillageMame;

	  private Character isSelected;

	  private Character isLoggedIn;
	  
	  private LocalDateTime logInDtTm;
	  
	  private Character isChallanGenerated;
	  
	  private Character isGaonNamunaAdded;
	  
	  private Integer khataCount;
	  
	  private Character isDemandCompleted;
	  
      private Character isVasuliWithoutDemandAdd;
	  
	  private Character isVasuliCompleted;
	  
	  
	  private Integer sathbaraCount;
}

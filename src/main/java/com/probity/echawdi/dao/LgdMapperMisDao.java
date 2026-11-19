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

public class LgdMapperMisDao {
	
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
	  
	  private String revenueYear;
	  
	  private int gaonNamunaCount;
	  
	  private Character isGaonNumunaCompleted;
	  
	  private Character isAllLrCollected;
	  
	  private Character isDemandGenerated;
	  
	  private Character isLrDuesAdded;
	  
	  private Character isNaLandRatesAdded;
	  
	  private Character isKajapaAdded;
	  
	  private Character isMsLrAdded;
	  
	  private Character isNoticeAdded;
	  
	  private Character isKhataMergeAdded;
	  
	  private Character isAddLandAdded;
	  
	  private Character isManualOnlineDiff;
	  
	  private Character dyslrWorkStarted; 
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  

}

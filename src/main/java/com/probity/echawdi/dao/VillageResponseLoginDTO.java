package com.probity.echawdi.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VillageResponseLoginDTO {
	
	//for inspection
	  public String cCode;
	  public String lgdCode;
	  public String talukaCode;
	  public String districtCode;
	  public String talukaMarathiName;
	  public String distMarathiName;
	  public String villageName;
	  
	  public String getcCode() {
			return cCode;
 	  }
	  public Long fetchcCode() {
			return cCode != null ? Long.parseLong(cCode) : 0;
	  }

}
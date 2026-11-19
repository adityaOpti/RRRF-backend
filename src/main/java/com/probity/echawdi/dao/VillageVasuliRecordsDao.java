package com.probity.echawdi.dao;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class VillageVasuliRecordsDao {
	
	private String districtName;
	private String talukaName;
	private String districtCode;
	private String talukaCode;
	private String villageName;
	private String totalDemandJm;
	private String totalDemandSankirn;
	private String totalCollectedJm;
	private String totalCollectedSankirn;
	private String ccode;
	private String revenueYear;
	

}

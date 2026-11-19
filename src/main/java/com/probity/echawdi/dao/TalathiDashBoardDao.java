package com.probity.echawdi.dao;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 */

/**
 * @author Ashish Dahale
 *
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TalathiDashBoardDao {
	
	 public Long id;
	 
	 public String districtCode;
	 
	 public String talukaCode;
	
     public String cCode;
	
     public String revenueYear;
	
     public String annualVillageTarget;
}

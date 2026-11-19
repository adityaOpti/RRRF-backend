package com.probity.echawdi.dao;
import java.util.List;

import com.probity.echawdi.entity.VillageFormMaster;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ashish Dahale
 *
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VillageDao {

	  public String cCode;
	  public String userName;
	  public String userType;
	  public String villageCode;
	  public String villageName;
	  public String lgdCode;
	  public String sajjaCode;
	  public String sajjaName;
	  public String circleCode;
	  public String circleName;
	  
	  public String distCode;
	  public String talukaCode;
	  
	  public List<VillageFormMaster> niranks;
	  public String getcCode() {
			return cCode;
   	  }
	  public Long fetchcCode() {
			return cCode != null ? Long.parseLong(cCode) : 0;
	  }
}		
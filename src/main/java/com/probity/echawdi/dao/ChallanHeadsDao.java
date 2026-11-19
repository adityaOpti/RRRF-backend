package com.probity.echawdi.dao;
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
public class ChallanHeadsDao {

	  public String divisionCode;
	  public String headNo;
	  public String subHeadNo;
	  public String headName;
	  public String headNameMarathi;
}		
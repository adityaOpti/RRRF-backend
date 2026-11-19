/**
 * 
 */
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
public class TalukaDao {

	  public String districtCode;
	  public String talukaCode;
	  public String talukaName;
	  public String schName;
	  public String schName1;
	
}

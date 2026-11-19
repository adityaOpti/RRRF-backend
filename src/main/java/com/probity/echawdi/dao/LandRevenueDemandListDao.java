/**
 * 
 */
package com.probity.echawdi.dao;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * @author Sandeep Dubey
 *
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LandRevenueDemandListDao {

	public String revenueYear;
	public Integer districtCode;
	public Integer talukaCode;
	public String cCode;
	public List<LandRevenueDemandDao> lstLandRevenueDemandDao;
	public Character activeFlag;
}

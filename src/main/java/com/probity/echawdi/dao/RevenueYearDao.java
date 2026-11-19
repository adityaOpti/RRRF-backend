/**
 * 
 */
package com.probity.echawdi.dao;



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
public class RevenueYearDao {

	public Long id;
	
	@NotNull(message="{revenueYear.notempty}")
	public String revenueYear;
	
	@NotNull(message="{activeFlag.notempty}")
    public Character activeFlag;
}
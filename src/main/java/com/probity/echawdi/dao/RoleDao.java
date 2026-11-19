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
 * @author Kumar Harpude
 *
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class RoleDao {

	
	public Long id;
	
	@NotNull(message ="{menuNameMar.notempty}")
	public String name;
	
}

package com.probity.echawdi.dao;
/**
 * 
 */

import java.time.LocalDateTime;

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
public class TenureDao {

	  public Integer tenureCode;
	  public String tenureName;
	  public LocalDateTime dateStamp;
	
}

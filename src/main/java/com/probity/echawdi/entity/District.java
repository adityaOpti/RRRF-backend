/**
 * 
 */
package com.probity.echawdi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ashish Dahale
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="e_district_census")
public class District {

	@Id
	@Column(name="district_code")
	private String districtCode;
	
	@Column(name="district_name")
	private String districtName;
	
	@Column(name="division_code")
	private String divisionCode;
	
	@Transient
//	@Column(name="count")
	private String count;
}
/**
 * 
 */
package com.probity.echawdi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ashish Dahale
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="m_officermast")
public class OfficerMaster {
	 
	 @Id	
	 @Column(name="username")
	 private String userName;
	 
	 @Column(name="ccode")
	 private String cCode;
	 
	 @Column(name="district_code")
	 private String districtCode;
	 
	 @Column(name="taluka_code")
	 private String talukaCode;
	 
	 @Column(name="user_type")
	 private Character userType;

}

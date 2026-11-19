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
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author Ashish Dahale
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name="e_taluka_census")
public class Taluka {

	@Id
	@Column(name="id")
	private Long id;
	
	@Column(name="district_code")
	private String districtCode;
	
	@Column(name="taluka_code")
	private String talukaCode;
	
	@Column(name="taluka_name")
	private String talukaName;
	
	@Column(name="mhr_host")
	private String mhrHost;
	
	@Column(name="mhr_db_name")
	private String mhrDbName;
	
	@Column(name="mhr_schema_Name")
	private String mhrSchemaName;
	
	@Column(name="mhr_user")
	private String mhrUser;
	
	@Column(name="mhr_password")
	private String mhrPassword;
	
	@Column(name="ech_host")
	private String echHost;
	
	@Column(name="ech_user")
	private String echUser;
	
	@Column(name="ech_password")
	private String echPassword;
	
	@Column(name="ech_db_name")
	private String echDbName;

	@Column(name="ech_schema_Name")
	private String echSchemaName;
	
	@Transient
//	@Column(name="count")
	private String count;
	
	
	@Transient
	private String cCode;
	
	  
	  public String getcCode() {
			return cCode;
 	  }
	  public Long fetchcCode() {
			return cCode != null ? Long.parseLong(cCode) : 0;
	  }
}
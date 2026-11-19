package com.probity.echawdi.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Entity
@Table(name="m_village_mapper")
public class MVillageMapper {

	@Column(name="mhr_host")
	private String mhrHost;
	
	@Column(name="ech_host")
	private String echHost;

	@Column(name="mhr_db_name")
	private String mhrDbName;
	
	@Column(name="ech_db_name")
	private String echDbName;

	@Column(name="mhr_schema_Name")
	private String mhrSchemaName;
	
	@Column(name="ech_schema_Name")
	private String echSchemaName;

	@Column(name="district_code")
	private String districtCode;
	
	@Column(name="taluka_code")
	private String talukaCode;
	
	@Column(name="village_code")
	private String villageCode;
	
	@Id
	@Column(name="ccode")
	private String cCode;

	@Column(name="village_name")
	private String villageName;
	
	@Transient
	private Object host;
	
	@Transient
	private String districtName;
	
	@Transient
	private String talukaName;
	
	
}
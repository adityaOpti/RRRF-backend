//package com.probity.echawdi.entity;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.Table;
//
//import com.sun.istack.NotNull;
//
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Entity
//@Table(name="m_db_mapper")
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
//public class MDBMapper {
//
//	@Id
//	@Column(name="id")
//	@NotNull
//	private Long id;
//
//	@Column(name="host")
//	private String host;
//
//	@Column(name="district_code")
//	private String districtCode ;
//
//	@Column(name="taluka_code")
//	private String talukaCode ;
//
//	@Column(name="e_db_name")
//	private String eDbName;
//
//	@Column(name="e_schema_name")
//	private String eSchemaName;
//
//	@Column(name="m_db_name")
//	private String mDbName;
//
//	@Column(name="m_schema_name")
//	private String mSchemaName;
//
//}
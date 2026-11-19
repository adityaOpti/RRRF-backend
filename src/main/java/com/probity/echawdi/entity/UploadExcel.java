//package com.probity.echawdi.entity;
//
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.SequenceGenerator;
//import javax.persistence.Table;
//
//import com.sun.istack.NotNull;
//
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//
//@Data
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
//@Entity
//@Table(name="m_village_master")
//public class UploadExcel {
//
//
//	  @Id
//    @NotNull
//    @Column(name = "\"id\"", precision = 19)
//    @GeneratedValue(strategy=GenerationType.AUTO, generator="m_village_master_seq_gen")
//    @SequenceGenerator(name="m_village_master_seq_gen", sequenceName="m_village_master_seq", allocationSize=1)
//	public Long id;
//	
//	@Column(name="district_name")
//	public String districtName;
//	
//	@Column(name="taluka_name")
//	public String talukaName;
//		
//	@Column(name="district_code")
//	public String districtCode;
//	
//	@Column(name="taluka_code")
//	public String talukaCode;
//	
//	@Column(name="ccode")
//	public String cCode;
//	
//	@Column(name="lgd_code")
//	public String lgdCode;
//	
//	@Column(name="village_name")
//	public String villageName;
//	
//	@Column(name="is_applicable")
//	public Character  isApplicable;
//	
//	@Column(name="active_flag")
//	public Character activeFlag;
//	
//}

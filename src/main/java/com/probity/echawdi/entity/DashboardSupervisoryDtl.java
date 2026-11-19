package com.probity.echawdi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="dashboard_supervisory_dtl")
public class DashboardSupervisoryDtl {

	private static final long serialVersionUID = 1L;

	 @Id
     @NotNull
     @Column(name = "\"id\"", precision = 19)
     @GeneratedValue(strategy=GenerationType.AUTO, generator="dashboard_supervisory_dtl_seq_gen")
     @SequenceGenerator(name="dashboard_supervisory_dtl_seq_gen", sequenceName="dashboard_supervisory_dtl_seq", allocationSize=1)
	  private Long id;
	 
	@Column(name="division_code")
	private String divisionCode;

	@Column(name="district_code")
	private String districtCode;
	
	@Column(name="district_name")
	private String districtName;
	
	@Column(name="taluka_code")
	private String talukaCode;
	
	@Column(name="taluka_name")
	private String talukaName;
	
	@Column(name="ccode")
	private String cCode;
	
	@Column(name="village_name")
	private String villageName;
	
	@Transient
	private String totalDistricts;
	
	@Transient
	private String totalTalukas;
	
	@Transient
	private String totalVillages;
	
	
	
	
}
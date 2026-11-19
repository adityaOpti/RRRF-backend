package com.probity.echawdi.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.probity.echawdi.audit.PlainAudit;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="echawdi_khata_merge")
public class KhataMerge extends PlainAudit{

	private static final long serialVersionUID = 1L;
	
	@Id
    @NotNull
    @Column(name = "\"id\"", precision = 19)
    @GeneratedValue(strategy=GenerationType.AUTO, generator="echawdi_khata_merge_seq_gen")
	@SequenceGenerator(name="echawdi_khata_merge_seq_gen", sequenceName="echawdi_khata_merge_seq", allocationSize=1)
	private Long id;
	
	@Column(name="khata_no")
	private String khataNo;
	
	@Column(name="ccode")
	private String cCode;
	
	@Column(name="revenue_year")
	private String revenueYear;
	
	@Column(name="count")
	private Long count;
	
	@Column(name="district_Code")
	public Integer districtCode;

	@Column(name="taluka_Code")
	public Integer talukaCode;
	
	@Column(name="child_khata_nos")
	private String childKhataNumbers;
	
	@Column(name="merge_Id")
	private String mergeId;  
	
	@Column(name="survey_Hissa_No")
	private String surveyHissaNo;  //new 

	@Column(name="khata_Owner_Name")
	private String khataOwnerName;  //new 

	@Column(name="area")
	private BigDecimal area;  //new 

	@Column(name="assessment")
	private BigDecimal assessment;  //new 

	@Transient
	private String uom;  //new 
	
	@Transient
	private String bagayatArea;
	
	@Transient
	private String jirayatArea;

}
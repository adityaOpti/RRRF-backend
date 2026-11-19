package com.probity.echawdi.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

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
@Table(name="echawdi_additional_land_revenue")
public class AdditionalLandRevenue extends PlainAudit{

	private static final long serialVersionUID = 1L;
	
	@Id
    @NotNull
    @Column(name = "\"id\"", precision = 19)
    @GeneratedValue(strategy=GenerationType.AUTO, generator="echawdi_additional_land_revenue_seq_gen")
	@SequenceGenerator(name="echawdi_additional_land_revenue_seq_gen", sequenceName="echawdi_additional_land_revenue_seq", allocationSize=1)
	private Long id;
	
	@Column(name="khata_no")
	private String khataNo;
	
	@Column(name="ccode")
	private String cCode;
	
	@Column(name="survey_HissaNo")
	private String surveyHissaNo;

	@Column(name="khataOwner_Name")
	private String khataOwnerName;

	@Column(name="area")
	private BigDecimal area;

	@Column(name="assessment")
	private BigDecimal assessment;
	
	@Column(name="radio_group")
	private String radiogroup;
	
	@Column(name="checkbox_group")
	private String checkboxgroup;
	
	@Column(name="applicable_for_demand")
	private Character applicableForDemand;
	
	@Column(name="remarks")
	private String remarks;

	@Column(name="revenue_year")
	private String revenueYear;
}

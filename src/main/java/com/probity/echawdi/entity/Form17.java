package com.probity.echawdi.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.probity.echawdi.audit.Auditable;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Rushikesh Durgude
 *
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "echawdi_form17")
public class Form17 extends Auditable {

	private static final long serialVersionUID = -8588463765973026528L;

	@Id
	@NotNull
	@Column(name = "\"id\"", precision = 19)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "echawdi_form17_seq_gen")
	@SequenceGenerator(name = "echawdi_form17_seq_gen", sequenceName = "echawdi_form17_seq", allocationSize = 1)
	private Long id;

	@Column(name = "case_no")
	private String caseNo;

	@Column(name = "khata_no")
	private String khataNo;

	@Column(name = "person_liable")
	private String personLiable;

	@Column(name = "survey_hissa")
	private String surveyHissaNo;

	@Column(name = "total_area")
	private BigDecimal totalArea;

	@Column(name = "uom_of_total_area")
	public String uomOfTotalArea;

	@Column(name = "area_affected")
	private BigDecimal areaAffected;

	@Column(name = "uom_of_area_affected")
	public String uomOfAreaAffected;

	@Column(name = "nature_of_case")
	private String natureOfCase;

	@Column(name = "period_if_more_than_year")
	private String periodIfMoreThanOneYear;

	@Column(name = "period_from_date")
	private LocalDate periodFromDate;

	@Column(name = "period_to_date")
	private LocalDate periodToDate;

	@Column(name = "local_cess")
	private String localCess;

	@Column(name = "other_local_cess")
	private String otherLocalCess;

	@Column(name = "other_local_cess_desc")
	private String otherLocalCessDesc;

	@Column(name = "local_cess_amount")
	private BigDecimal localCessAmount;

	@Column(name = "other_local_cess_amount")
	private BigDecimal otherLocalCessAmount;

	@Column(name = "amount_of_jm")
	private BigDecimal amountOfJm;

	@Column(name = "amount_of_zp")
	private BigDecimal amountOfZp;

	@Column(name = "amount_of_gp")
	private BigDecimal amountOfGp;

	@Column(name = "remarks")
	private String remarks;

	@Column(name = "assessment")
	private BigDecimal assessment;
	
	@Column(name="is_amount_paid")
	public Character isAmountPaid;
	
	@Column(name="is_demand_generated")
	public Character demandGenerated;
	
	@Column(name="khata_type")
	public String khataType;
	
	@Column(name="makhta_khata_no")
	public String makhtaKhataNo;
}
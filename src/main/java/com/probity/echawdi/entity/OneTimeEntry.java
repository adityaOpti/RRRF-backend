/**
 * 
 */
package com.probity.echawdi.entity;

import java.math.BigDecimal;

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
@EqualsAndHashCode(callSuper =false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="lr_pending_dues")
public class OneTimeEntry  extends Auditable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@NotNull
	@Column(name="\"id\"" , precision = 19)
	@GeneratedValue(strategy=GenerationType.AUTO, generator="lr_pending_dues_seq_gen")
    @SequenceGenerator(name="lr_pending_dues_seq_gen", sequenceName="lr_pending_dues_seq", allocationSize=1)
	private Long id ;
	
	 @Column(name="khata_no")
     private String  khataNo;
	
	@Column(name="pre_year_pending_jm")
	private BigDecimal preYearPendingJm;
	
	@Column(name="pre_year_pending_zp")
	private BigDecimal preYearPendingZp;
	
	@Column(name="pre_year_pending_gp")
	private BigDecimal preYearPendingGp;
	
	@Column(name="pre_year_pending_addl_land_revenue")
	private BigDecimal preYearPendingAddlLandRevenue;
	
	@Column(name="pre_year_pending_educational_cess")
	private BigDecimal preYearPendingEducationalCess;
	
	@Column(name="pre_year_pending_addl_educational_cess")
	private BigDecimal preYearPendingAddlEducationalCess;

	@Column(name="pre_year_pending_employee_guarantee_scheme")
	private BigDecimal preYearPendingEmployeeGuaranteeScheme;

	@Column(name="net_pending")
	private BigDecimal netPending;
	
//	@Column(name="miscellaneous_amount")
//	private BigDecimal miscellaneousAmount;
	
	@Column(name="pre_year_pending_na_cess")
	private BigDecimal preYearPendingNaCess;

	@Column(name="pre_year_sankirn_jm_with")
	private BigDecimal preYearSankirnJmWith;
	
	@Column(name="pre_year_sankirn_jm_without")
	private BigDecimal preYearSankirnJmWithout;
	
	@Column(name="pre_year_notice_fee")
	private BigDecimal preYearNoticeFee;
}

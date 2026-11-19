package com.probity.echawdi.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.stream.Stream;

import javax.annotation.concurrent.Immutable;
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
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper =false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Immutable
@Entity
@Table(name="view_8breport")
public class CombinedChallanLandRevenueView {
	
	@Id
	@NotNull
	@Column(name = "\"id\"", precision = 19)
	@GeneratedValue(strategy=GenerationType.AUTO, generator="land_revenue_demandseq_gen")
	@SequenceGenerator(name="land_revenue_demand_seq_gen", sequenceName="land_revenue_demand_seq", allocationSize=1)
	private Long id;

	@Column(name="khata_no")
	private String  khataNo;

	@Column(name="makhtakhata_no")
	private String  makhtaKhataNo;

	@Column(name="khata_owner_name")
	private String  khataOwnerName;

	@Column(name="area")
	public BigDecimal area;

	@Column(name="survey_hissa")
	private String surveyHissaNo;

	@Column(name="area_unit")
	private String uom;

	@Column(name="assessment")
	public BigDecimal assessment;

	@Column(name="jm_bindumala")
	private BigDecimal jmBindumala;

	@Column(name="zp_bindumala")
	private BigDecimal zpBindumala;

	@Column(name="gp_bindumala")
	private BigDecimal gpBindumala;

	@Column(name="jm_dumala")
	private BigDecimal jmDumala;

	@Column(name="zp_dumala")
	private BigDecimal zpDumala;

	@Column(name="gpDumala")
	private BigDecimal gpDumala;

	@Column(name="jm_akrushik")
	private BigDecimal jmAkrushik;

	@Column(name="zp_akrushik")
	private BigDecimal zpAkrushik;

	@Column(name="gp_akrushik")
	private BigDecimal gpAkrushik;

	@Column(name="jm_sankirn")
	private BigDecimal jmSankirn;

	@Column(name="zp_sankirn")
	private BigDecimal zpSankirn;

	@Column(name="gp_sankirn")
	private BigDecimal gpSankirn;

	@Column(name="jm_vajasut")
	private BigDecimal jmVajasut;

	@Column(name="zp_vajasut")
	private BigDecimal zpVajasut;

	@Column(name="gp_vajasut")
	private BigDecimal gpVajasut;

	@Column(name="addl_land_revenue")
	private BigDecimal addlLandRevenue;

	@Column(name="educational_cess")
	private BigDecimal educationalCess;

	@Column(name="addl_educational_cess")
	private BigDecimal addlEducationalCess;

	@Column(name="employee_guarantee_scheme")
	private BigDecimal employeeGuaranteeScheme;

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

	@Column(name="miscellaneous_amount")
	private BigDecimal miscellaneousAmount;

	@Column(name="pre_year_pending_na_cess")
	private BigDecimal preYearPendingNaCess;

	@Column(name="pre_year_sankirn_jm_with")
	private BigDecimal preYearSankirnJmWith;

	@Column(name="pre_year_sankirn_jm_without")
	private BigDecimal preYearSankirnJmWithout;

	@Column(name="pre_year_notice_fee")
	private BigDecimal preYearNoticeFee;

	@Column(name="net_amount")
	private BigDecimal netAmount;

	@Column(name="net_received")
	private BigDecimal netReceived;

	@Column(name="net_pending")
	private BigDecimal netPending;

	@Column(name="notice_amount")
	public BigDecimal noticeAmount;

	@Column(name="date_of_notice")
	public LocalDateTime dateOfNotice;

	@Column(name="receipt_no")
	private String receiptNo;

	@Column(name="receipt_date")
	private LocalDateTime receiptDate;

	@Column(name="mode_of_payment")
	private String modeOfPayment;

	@Column(name="dd_name")
	private String ddName;
	
	
	
	@Column(name="challan_no")
	private String challanNo;
	
	//new change for 0045 challan number
	@Column(name="challan_no_0045")
	private String challanNo0045;
	

	@Column(name="challan_date")
	private LocalDateTime challanDate;
	
	
	
	

	@Column(name="dd_branch")
	private String ddBranch;

	@Column(name="dd_number")
	private String ddNumber;

	@Column(name="dd_date")
	private LocalDateTime ddDate;

	@Column(name="dd_amount")
	private BigDecimal ddAmount;

	@Column(name="cheque_name")
	private String chequeName;

	@Column(name="cheque_branch")
	private String chequeBranch;

	@Column(name="cheque_number")
	private String chequeNumber;

	@Column(name="cheque_date")
	private LocalDateTime chequeDate;

	@Column(name="cheque_amount")
	private BigDecimal chequeAmount;

	@Column(name = "challan_id")
	private Long challanId;

	@Column(name="is_money_deposited_bank")
	private Character isMoneyepositedBank;

	@Column(name="land_revenue_form17_id")
	private Long landReceiveForm17Id;
	
	@Column(name="agau_vasuli_remarks")
	private String agauVasuliRemarks;

	@Transient
	private Character noticeFeesApplicable;

	@Transient
	private BigDecimal totalJM;

	@Transient
	private BigDecimal totalZP;

	@Transient
	private BigDecimal totalGP;

	@Transient
	private BigDecimal totalJMZPGP;
	
	@Transient
	private BigDecimal totalOf0029;
	
	@Transient
	private BigDecimal totalOf0045;
	
	@Transient
	private String bagayatArea;
	
	
	@Transient
	private String jirayatArea;


	public static Stream<CombinedChallanLandRevenueView> stream() {
		// TODO Auto-generated method stub
		return null;
	}

}

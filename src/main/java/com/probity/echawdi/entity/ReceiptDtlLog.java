package com.probity.echawdi.entity;

import javax.persistence.*;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import com.probity.echawdi.audit.Auditable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper =false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "receipt_dtl")
public class ReceiptDtlLog  {

	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@SequenceGenerator(name = "receipt_dtl_seq_gen", sequenceName = "receipt_dtl_id_seq", allocationSize = 1)
	private Long id;

	@CreatedBy
	@Column(name = "created_user_id", insertable = true, updatable = false)
	private Long createdUserId;

	@LastModifiedBy
	@Column(name = "update_userid", insertable = false, updatable = true)
	private Long updateUserId;

	@CreatedDate
	private LocalDateTime createDtTm;

	@LastModifiedDate
	private LocalDateTime update_dt_tm;

	@Column(name = "revenue_year")
	private String revenueYear;

	@Column(name = "district_code")
	private Integer districtCode;

	@Column(name = "taluka_code")
	private Integer talukaCode;

	@Column(name = "ccode")
	private String cCode;

	@Column(name = "khata_no")
	private String khataNo;

	@Column(name = "khata_owner_name")
	private String khataOwnerName;

	@Column(name = "area")
	public BigDecimal area;

	@Column(name = "assessment")
	public BigDecimal assessment;

	@Column(name = "survey_hissa")
	private String surveyHissaNo;

	@Column(name = "area_unit")
	private String uom;

	@Column(name = "jm_bindumala")
	private BigDecimal jmBindumala;

	@Column(name = "zp_bindumala")
	private BigDecimal zpBindumala;

	@Column(name = "gp_bindumala")
	private BigDecimal gpBindumala;

	@Column(name = "jm_dumala")
	private BigDecimal jmDumala;

	@Column(name = "zp_dumala")
	private BigDecimal zpDumala;

	@Column(name = "gpDumala")
	private BigDecimal gpDumala;

	@Column(name = "jm_akrushik")
	private BigDecimal jmAkrushik;

	@Column(name = "zp_akrushik")
	private BigDecimal zpAkrushik;

	@Column(name = "gp_akrushik")
	private BigDecimal gpAkrushik;

	@Column(name = "jm_sankirn")
	private BigDecimal jmSankirn;

	@Column(name = "zp_sankirn")
	private BigDecimal zpSankirn;

	@Column(name = "gp_sankirn")
	private BigDecimal gpSankirn;

	@Column(name = "jm_vajasut")
	private BigDecimal jmVajasut;

	@Column(name = "zp_vajasut")
	private BigDecimal zpVajasut;

	@Column(name = "gp_vajasut")
	private BigDecimal gpVajasut;

	@Column(name = "addl_land_revenue")
	private BigDecimal addlLandRevenue;

	@Column(name = "educational_cess")
	private BigDecimal educationalCess;

	@Column(name = "addl_educational_cess")
	private BigDecimal addlEducationalCess;

	@Column(name = "employee_guarantee_scheme")
	private BigDecimal employeeGuaranteeScheme;

	@Column(name = "pre_year_pending_jm")
	private BigDecimal preYearPendingJm;

	@Column(name = "pre_year_pending_zp")
	private BigDecimal preYearPendingZp;

	@Column(name = "pre_year_pending_gp")
	private BigDecimal preYearPendingGp;

	@Column(name = "pre_year_pending_addl_land_revenue")
	private BigDecimal preYearPendingAddlLandRevenue;

	@Column(name = "pre_year_pending_educational_cess")
	private BigDecimal preYearPendingEducationalCess;

	@Column(name = "pre_year_pending_addl_educational_cess")
	private BigDecimal preYearPendingAddlEducationalCess;

	@Column(name = "pre_year_pending_employee_guarantee_scheme")
	private BigDecimal preYearPendingEmployeeGuaranteeScheme;

	@Column(name = "miscellaneous_amount")
	private BigDecimal miscellaneousAmount;

	@Column(name = "net_amount")
	private BigDecimal netAmount;

	@Column(name = "net_received")
	private BigDecimal netReceived;

	@Column(name = "net_pending")
	private BigDecimal netPending;

	@Column(name = "date_of_notice")
	public LocalDateTime dateOfNotice;

	@Column(name = "notice_amount")
	public BigDecimal noticeAmount;

	@Column(name = "receipt_no")
	private String receiptNo;

	@Column(name = "receipt_date")
	private LocalDateTime receiptDate;

	@Column(name = "receipt_by_userid")
	private String receiptByUserid;

	@Column(name = "mode_of_payment")
	private String modeOfPayment;

	@Column(name = "is_money_deposited_bank")
	private Character isMoneyepositedBank;

	@Column(name = "land_revenue_form17_id")
	private Long landRevenueForm17Id;

	@Column(name = "pre_year_notice_fee")
	private BigDecimal preYearNoticeFee;

	@Column(name = "pre_year_pending_na_cess")
	private BigDecimal preYearPendingNaCess;

	@Column(name = "pre_year_sankirn_jm_with")
	private BigDecimal preYearSankirnJmWith;

	@Column(name = "pre_year_sankirn_jm_without")
	private BigDecimal preYearSankirnJmWithout;

	@Column(name = "makhtakhata_no")
	private String makhtaKhataNo;

	@Column(name = "challan_id")
	private Long challanId;

	@Column(name = "challan_date")
	private LocalDateTime challanDate;

	@Column(name = "active_flag")
	private Character activeFlag;

	@Column(name = "notice_fee")
	private Integer noticeFee;
	
//	@ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "land_revenue_demand_id", insertable = false, updatable = false, referencedColumnName = "id")
//    private LandRevenueDemand landRevenueDemand;

}

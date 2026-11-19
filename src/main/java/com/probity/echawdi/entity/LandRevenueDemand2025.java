
/**
 * 
 */
package com.probity.echawdi.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.probity.echawdi.audit.Auditable;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Bhagyashri Lolage
 *
 */
@Data
@EqualsAndHashCode(callSuper =false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="land_revenue_demand_2025")  //2025-26
public class LandRevenueDemand2025 extends Auditable {

	private static final long serialVersionUID = 1L;

	@Id
	@NotNull
	@Column(name = "\"id\"", precision = 19)
	@GeneratedValue(strategy=GenerationType.AUTO, generator="land_revenue_demandseq_gen")
	@SequenceGenerator(name="land_revenue_demand_seq_gen", sequenceName="land_revenue_demand_seq", allocationSize=1)
	private Long id;

	@Column(name="khata_no")
	private String  khataNo;

	@Column(name="taluka_code")
	private Integer  talukaCode;
	
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKhataNo() {
		return khataNo;
	}

	public void setKhataNo(String khataNo) {
		this.khataNo = khataNo;
	}

	public Integer getTalukaCode() {
		return talukaCode;
	}

	public void setTalukaCode(Integer talukaCode) {
		this.talukaCode = talukaCode;
	}

	public String getMakhtaKhataNo() {
		return makhtaKhataNo;
	}

	public void setMakhtaKhataNo(String makhtaKhataNo) {
		this.makhtaKhataNo = makhtaKhataNo;
	}

	public String getKhataOwnerName() {
		return khataOwnerName;
	}

	public void setKhataOwnerName(String khataOwnerName) {
		this.khataOwnerName = khataOwnerName;
	}

	public BigDecimal getArea() {
		return area;
	}

	public void setArea(BigDecimal area) {
		this.area = area;
	}

	public String getSurveyHissaNo() {
		return surveyHissaNo;
	}

	public void setSurveyHissaNo(String surveyHissaNo) {
		this.surveyHissaNo = surveyHissaNo;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public BigDecimal getAssessment() {
		return assessment;
	}

	public void setAssessment(BigDecimal assessment) {
		this.assessment = assessment;
	}

	public BigDecimal getJmBindumala() {
		return jmBindumala;
	}

	public void setJmBindumala(BigDecimal jmBindumala) {
		this.jmBindumala = jmBindumala;
	}

	public BigDecimal getZpBindumala() {
		return zpBindumala;
	}

	public void setZpBindumala(BigDecimal zpBindumala) {
		this.zpBindumala = zpBindumala;
	}

	public BigDecimal getGpBindumala() {
		return gpBindumala;
	}

	public void setGpBindumala(BigDecimal gpBindumala) {
		this.gpBindumala = gpBindumala;
	}

	public BigDecimal getJmDumala() {
		return jmDumala;
	}

	public void setJmDumala(BigDecimal jmDumala) {
		this.jmDumala = jmDumala;
	}

	public BigDecimal getZpDumala() {
		return zpDumala;
	}

	public void setZpDumala(BigDecimal zpDumala) {
		this.zpDumala = zpDumala;
	}

	public BigDecimal getGpDumala() {
		return gpDumala;
	}

	public void setGpDumala(BigDecimal gpDumala) {
		this.gpDumala = gpDumala;
	}

	public BigDecimal getJmAkrushik() {
		return jmAkrushik;
	}

	public void setJmAkrushik(BigDecimal jmAkrushik) {
		this.jmAkrushik = jmAkrushik;
	}

	public BigDecimal getZpAkrushik() {
		return zpAkrushik;
	}

	public void setZpAkrushik(BigDecimal zpAkrushik) {
		this.zpAkrushik = zpAkrushik;
	}

	public BigDecimal getGpAkrushik() {
		return gpAkrushik;
	}

	public void setGpAkrushik(BigDecimal gpAkrushik) {
		this.gpAkrushik = gpAkrushik;
	}

	public BigDecimal getJmSankirn() {
		return jmSankirn;
	}

	public void setJmSankirn(BigDecimal jmSankirn) {
		this.jmSankirn = jmSankirn;
	}

	public BigDecimal getZpSankirn() {
		return zpSankirn;
	}

	public void setZpSankirn(BigDecimal zpSankirn) {
		this.zpSankirn = zpSankirn;
	}

	public BigDecimal getGpSankirn() {
		return gpSankirn;
	}

	public void setGpSankirn(BigDecimal gpSankirn) {
		this.gpSankirn = gpSankirn;
	}

	public BigDecimal getJmVajasut() {
		return jmVajasut;
	}

	public void setJmVajasut(BigDecimal jmVajasut) {
		this.jmVajasut = jmVajasut;
	}

	public BigDecimal getZpVajasut() {
		return zpVajasut;
	}

	public void setZpVajasut(BigDecimal zpVajasut) {
		this.zpVajasut = zpVajasut;
	}

	public BigDecimal getGpVajasut() {
		return gpVajasut;
	}

	public void setGpVajasut(BigDecimal gpVajasut) {
		this.gpVajasut = gpVajasut;
	}

	public BigDecimal getAddlLandRevenue() {
		return addlLandRevenue;
	}

	public void setAddlLandRevenue(BigDecimal addlLandRevenue) {
		this.addlLandRevenue = addlLandRevenue;
	}

	public BigDecimal getEducationalCess() {
		return educationalCess;
	}

	public void setEducationalCess(BigDecimal educationalCess) {
		this.educationalCess = educationalCess;
	}

	public BigDecimal getAddlEducationalCess() {
		return addlEducationalCess;
	}

	public void setAddlEducationalCess(BigDecimal addlEducationalCess) {
		this.addlEducationalCess = addlEducationalCess;
	}

	public BigDecimal getEmployeeGuaranteeScheme() {
		return employeeGuaranteeScheme;
	}

	public void setEmployeeGuaranteeScheme(BigDecimal employeeGuaranteeScheme) {
		this.employeeGuaranteeScheme = employeeGuaranteeScheme;
	}

	public BigDecimal getPreYearPendingJm() {
		return preYearPendingJm;
	}

	public void setPreYearPendingJm(BigDecimal preYearPendingJm) {
		this.preYearPendingJm = preYearPendingJm;
	}

	public BigDecimal getPreYearPendingZp() {
		return preYearPendingZp;
	}

	public void setPreYearPendingZp(BigDecimal preYearPendingZp) {
		this.preYearPendingZp = preYearPendingZp;
	}

	public BigDecimal getPreYearPendingGp() {
		return preYearPendingGp;
	}

	public void setPreYearPendingGp(BigDecimal preYearPendingGp) {
		this.preYearPendingGp = preYearPendingGp;
	}

	public BigDecimal getPreYearPendingAddlLandRevenue() {
		return preYearPendingAddlLandRevenue;
	}

	public void setPreYearPendingAddlLandRevenue(BigDecimal preYearPendingAddlLandRevenue) {
		this.preYearPendingAddlLandRevenue = preYearPendingAddlLandRevenue;
	}

	public BigDecimal getPreYearPendingEducationalCess() {
		return preYearPendingEducationalCess;
	}

	public void setPreYearPendingEducationalCess(BigDecimal preYearPendingEducationalCess) {
		this.preYearPendingEducationalCess = preYearPendingEducationalCess;
	}

	public BigDecimal getPreYearPendingAddlEducationalCess() {
		return preYearPendingAddlEducationalCess;
	}

	public void setPreYearPendingAddlEducationalCess(BigDecimal preYearPendingAddlEducationalCess) {
		this.preYearPendingAddlEducationalCess = preYearPendingAddlEducationalCess;
	}

	public BigDecimal getPreYearPendingEmployeeGuaranteeScheme() {
		return preYearPendingEmployeeGuaranteeScheme;
	}

	public void setPreYearPendingEmployeeGuaranteeScheme(BigDecimal preYearPendingEmployeeGuaranteeScheme) {
		this.preYearPendingEmployeeGuaranteeScheme = preYearPendingEmployeeGuaranteeScheme;
	}

	public BigDecimal getMiscellaneousAmount() {
		return miscellaneousAmount;
	}

	public void setMiscellaneousAmount(BigDecimal miscellaneousAmount) {
		this.miscellaneousAmount = miscellaneousAmount;
	}

	public BigDecimal getPreYearPendingNaCess() {
		return preYearPendingNaCess;
	}

	public void setPreYearPendingNaCess(BigDecimal preYearPendingNaCess) {
		this.preYearPendingNaCess = preYearPendingNaCess;
	}

	public BigDecimal getPreYearSankirnJmWith() {
		return preYearSankirnJmWith;
	}

	public void setPreYearSankirnJmWith(BigDecimal preYearSankirnJmWith) {
		this.preYearSankirnJmWith = preYearSankirnJmWith;
	}

	public BigDecimal getPreYearSankirnJmWithout() {
		return preYearSankirnJmWithout;
	}

	public void setPreYearSankirnJmWithout(BigDecimal preYearSankirnJmWithout) {
		this.preYearSankirnJmWithout = preYearSankirnJmWithout;
	}

	public BigDecimal getPreYearNoticeFee() {
		return preYearNoticeFee;
	}

	public void setPreYearNoticeFee(BigDecimal preYearNoticeFee) {
		this.preYearNoticeFee = preYearNoticeFee;
	}

	public BigDecimal getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(BigDecimal netAmount) {
		this.netAmount = netAmount;
	}

	public BigDecimal getNetReceived() {
		return netReceived;
	}

	public void setNetReceived(BigDecimal netReceived) {
		this.netReceived = netReceived;
	}

	public BigDecimal getNetPending() {
		return netPending;
	}

	public void setNetPending(BigDecimal netPending) {
		this.netPending = netPending;
	}

	public BigDecimal getNoticeAmount() {
		return noticeAmount;
	}

	public void setNoticeAmount(BigDecimal noticeAmount) {
		this.noticeAmount = noticeAmount;
	}

	public LocalDateTime getDateOfNotice() {
		return dateOfNotice;
	}

	public void setDateOfNotice(LocalDateTime dateOfNotice) {
		this.dateOfNotice = dateOfNotice;
	}

	public String getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	public LocalDateTime getReceiptDate() {
		return receiptDate;
	}

	public void setReceiptDate(LocalDateTime receiptDate) {
		this.receiptDate = receiptDate;
	}

	public String getModeOfPayment() {
		return modeOfPayment;
	}

	public void setModeOfPayment(String modeOfPayment) {
		this.modeOfPayment = modeOfPayment;
	}

	public String getDdName() {
		return ddName;
	}

	public void setDdName(String ddName) {
		this.ddName = ddName;
	}

	public String getDdBranch() {
		return ddBranch;
	}

	public void setDdBranch(String ddBranch) {
		this.ddBranch = ddBranch;
	}

	public String getDdNumber() {
		return ddNumber;
	}

	public void setDdNumber(String ddNumber) {
		this.ddNumber = ddNumber;
	}

	public LocalDateTime getDdDate() {
		return ddDate;
	}

	public void setDdDate(LocalDateTime ddDate) {
		this.ddDate = ddDate;
	}

	public BigDecimal getDdAmount() {
		return ddAmount;
	}

	public void setDdAmount(BigDecimal ddAmount) {
		this.ddAmount = ddAmount;
	}

	public String getChequeName() {
		return chequeName;
	}

	public void setChequeName(String chequeName) {
		this.chequeName = chequeName;
	}

	public String getChequeBranch() {
		return chequeBranch;
	}

	public void setChequeBranch(String chequeBranch) {
		this.chequeBranch = chequeBranch;
	}

	public String getChequeNumber() {
		return chequeNumber;
	}

	public void setChequeNumber(String chequeNumber) {
		this.chequeNumber = chequeNumber;
	}

	public LocalDateTime getChequeDate() {
		return chequeDate;
	}

	public void setChequeDate(LocalDateTime chequeDate) {
		this.chequeDate = chequeDate;
	}

	public BigDecimal getChequeAmount() {
		return chequeAmount;
	}

	public void setChequeAmount(BigDecimal chequeAmount) {
		this.chequeAmount = chequeAmount;
	}

	public Long getChallanId() {
		return challanId;
	}

	public void setChallanId(Long challanId) {
		this.challanId = challanId;
	}

	public Character getIsMoneyepositedBank() {
		return isMoneyepositedBank;
	}

	public void setIsMoneyepositedBank(Character isMoneyepositedBank) {
		this.isMoneyepositedBank = isMoneyepositedBank;
	}

	public Long getLandReceiveForm17Id() {
		return landReceiveForm17Id;
	}

	public void setLandReceiveForm17Id(Long landReceiveForm17Id) {
		this.landReceiveForm17Id = landReceiveForm17Id;
	}

	public String getAgauVasuliRemarks() {
		return agauVasuliRemarks;
	}

	public void setAgauVasuliRemarks(String agauVasuliRemarks) {
		this.agauVasuliRemarks = agauVasuliRemarks;
	}

	public Character getNoticeFeesApplicable() {
		return noticeFeesApplicable;
	}

	public void setNoticeFeesApplicable(Character noticeFeesApplicable) {
		this.noticeFeesApplicable = noticeFeesApplicable;
	}

	public BigDecimal getTotalJM() {
		return totalJM;
	}

	public void setTotalJM(BigDecimal totalJM) {
		this.totalJM = totalJM;
	}

	public BigDecimal getTotalZP() {
		return totalZP;
	}

	public void setTotalZP(BigDecimal totalZP) {
		this.totalZP = totalZP;
	}

	public BigDecimal getTotalGP() {
		return totalGP;
	}

	public void setTotalGP(BigDecimal totalGP) {
		this.totalGP = totalGP;
	}

	public BigDecimal getTotalJMZPGP() {
		return totalJMZPGP;
	}

	public void setTotalJMZPGP(BigDecimal totalJMZPGP) {
		this.totalJMZPGP = totalJMZPGP;
	}

	public BigDecimal getTotalOf0029() {
		return totalOf0029;
	}

	public void setTotalOf0029(BigDecimal totalOf0029) {
		this.totalOf0029 = totalOf0029;
	}

	public BigDecimal getTotalOf0045() {
		return totalOf0045;
	}

	public void setTotalOf0045(BigDecimal totalOf0045) {
		this.totalOf0045 = totalOf0045;
	}

	public String getBagayatArea() {
		return bagayatArea;
	}

	public void setBagayatArea(String bagayatArea) {
		this.bagayatArea = bagayatArea;
	}

	public String getJirayatArea() {
		return jirayatArea;
	}

	public void setJirayatArea(String jirayatArea) {
		this.jirayatArea = jirayatArea;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	

}
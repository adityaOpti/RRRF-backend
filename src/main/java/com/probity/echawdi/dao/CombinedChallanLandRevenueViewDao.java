package com.probity.echawdi.dao;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import com.probity.echawdi.entity.AdditionalLandRevenue;
import com.probity.echawdi.entity.Form17NoKaJaPa;
import com.probity.echawdi.entity.KhataMerge;
import com.probity.echawdi.entity.KhataMergeDtl;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CombinedChallanLandRevenueViewDao {
	public CombinedChallanLandRevenueViewDao(String khataNo, BigDecimal educationalCess, BigDecimal addlEducationalCess) {
		this.khataNo  = khataNo;
		this.educationalCess = educationalCess;
		this.addlEducationalCess = addlEducationalCess;
	}
	
	


	public Long id;
	
	@NotNull(message= "{revenueYear.notempty}")
	public String revenueYear;
	
	public Integer districtCode;
	
	public Integer talukaCode;
	
	@NotNull(message= "{cCode.notempty}")
	public String cCode;
	
	public String lgdCode;
	public String khataNo;
	public String makhtaKhataNo;
	public String khataOwnerName;
	public String bagayatArea;
	public String jirayatArea;
	public BigDecimal area;
	public BigDecimal assessment;
	public BigDecimal jmBindumala;
	public BigDecimal zpBindumala;
	public BigDecimal gpBindumala;
	public BigDecimal jmDumala;
	public BigDecimal zpDumala;
	public BigDecimal gpDumala;
	public BigDecimal jmAkrushik;
	public BigDecimal zpAkrushik;
	public BigDecimal gpAkrushik;
	public BigDecimal jmSankirn;
	public BigDecimal zpSankirn;
	public BigDecimal gpSankirn;
	public BigDecimal jmVajasut;
	public BigDecimal zpVajasut;
	public BigDecimal gpVajasut;
	public BigDecimal addlLandRevenue;
	public BigDecimal educationalCess;
	public BigDecimal addlEducationalCess;
	public BigDecimal employeeGuaranteeScheme;
	public BigDecimal preYearPendingJm;
	public BigDecimal preYearPendingZp;
	public BigDecimal preYearPendingGp;
	public BigDecimal preYearPendingAddlLandRevenue;
	public BigDecimal preYearPendingEducationalCess;
	public BigDecimal preYearPendingAddlEducationalCess;
	public BigDecimal preYearPendingEmployeeGuaranteeScheme;
	public BigDecimal miscellaneousAmount;
	public BigDecimal netAmount;
	public BigDecimal netReceived;
	public BigDecimal netPending;
	public BigDecimal totalOfWithSankirn;
	public BigDecimal totalOfWithuotSankirn;
	public String amountOfLc;
	public String amountOfLr;
	public String netAmountReceived;
	public String sankirn;
	public Character activeFlag;
    public BigDecimal noticeAmount;
    public LocalDateTime dateOfNotice;
    public String receiptNo;
    public LocalDateTime receiptDate;
    public String modeOfPayment;
    public String ddName;
    public String ddBranch;
    public String ddNumber;
    public LocalDateTime ddDate;
    public BigDecimal ddAmount;
    private String chequeName;
    private String chequeBranch;
    private String chequeNumber;
    private LocalDateTime chequeDate;
    private BigDecimal chequeAmount;
    public Long challanId;
    public Character isMoneyepositedBank;
    public Long landReceiveForm17Id;
    public BigDecimal localCess;
    public BigDecimal otherLocalCess;
	public String message;
	public String status;
    public String surveyHissaNo;
    public String uom;
    public String challanNo;
    public LocalDateTime challanDate;
    public BigDecimal preYearPendingNaCess;
    public BigDecimal preYearSankirnJmWith;
    public BigDecimal preYearSankirnJmWithout;
    public BigDecimal preYearNoticeFee;
    public BigDecimal totalAmount;
    public String receiptDateForReport;
    public String challanDateForReport;
    public Character noticeFeesApplicable;
    public BigDecimal totalJm;
    public BigDecimal totalZp;
    public BigDecimal totalGp;
    public BigDecimal totalJmZpGp;
    public BigDecimal totalOf0029;
    public BigDecimal totalOf0045;
    public String receiptDateForReceipt;
    public String ddDateForReceipt;
    public String chequeDateForReceipt;
    public String dateOfForReceipt;
    public List<AdditionalLandRevenue> additionalLandRevenue;
    public List<KhataMerge> khataMergeLst;
    public List<KhataMergeDtl> khataMergeDtlLst;
    public List<Form17NoKaJaPa> form17NoKaJaPaLst;
    public String agauVasuliRemarks;
    
    public String NameofTenent;
    public BigDecimal demandGeneratedKhataNo;
    
    
	public String challanNo0045;
	public BigDecimal totalOfHead0029;
	public BigDecimal totalOfHead0045;
	@NotNull(message ="{bankName.notempty}")
	public String bankName;
	@NotNull(message ="{bankAddress.notempty}")
	public String bankAddress;
	public String bankReceiptNumber;
	public String bankReceiptNumber0045;
	public LocalDateTime bankDepositeDate;
	public String bankReceiptPath;
	public String bankReceiptPath0045;
	public MultipartFile file;
	public List<LandRevenueDemandDao> lstReceipt;
	public String actualFileName;
	public String actualFileName0045;
	public String countOfReceipts;
    
    public Integer fetchKhataNumber() {
		return khataNo != null ? Integer.parseInt(khataNo) : 0;
	}
    
   
}

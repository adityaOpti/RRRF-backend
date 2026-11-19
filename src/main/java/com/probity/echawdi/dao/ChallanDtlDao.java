/**
 * 
 */
package com.probity.echawdi.dao;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Bhagyashri Lolage
 *
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChallanDtlDao {

	public Long id;
	public String revenueYear;
	public Integer districtCode;
	public Integer talukaCode;
	public String cCode;
	public String lgdCode;
	public String challanNo;
	public String challanNo0045;
	public LocalDateTime challanDate;
	public Double totalAmount;
	public Double totalOfHead0029;
	public Double totalOfHead0045;
	public String modeOfPayment;
	@NotNull(message ="{bankName.notempty}")
	public String bankName;
	@NotNull(message ="{bankAddress.notempty}")
	public String bankAddress;
	public String bankReceiptNumber;
	public String bankReceiptNumber0045;
	public LocalDateTime bankDepositeDate;
	public String bankReceiptPath;
	public String bankReceiptPath0045;
	public String status;
	public MultipartFile file;
	public List<LandRevenueDemandDao> lstReceipt;
	public String actualFileName;
	public String actualFileName0045;
	
	@JsonIgnore
    public boolean isIdSet() {
        return id != null;
    }
}
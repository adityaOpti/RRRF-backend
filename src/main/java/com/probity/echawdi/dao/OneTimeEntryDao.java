
/**
 * 
 */
package com.probity.echawdi.dao;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

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
public class OneTimeEntryDao {
	
	
	public Long id;
	
	
	@NotNull(message = "{cCode.notempty}")	
	private String cCode;	// village
	
	@NotNull(message = "{khataNo.notempty}")	
	private String khataNo;	// Khata No
	
	private String revenueYear;  // not in form
	
	@NotNull(message = "{districtCode.notempty}")	
	private Integer districtCode;  // district
	
	@NotNull(message = "{talukaCode.notempty}")	
	private Integer talukaCode;    // taluka
    
	@NotNull(message = "{preYearPendingJm.notempty }")
	private BigDecimal preYearPendingJm;  //  LR J m re   marathi jm 
	
	@NotNull(message = "{preYearPendingZp.notempty }")
	private BigDecimal preYearPendingZp;  //  ZP  Z p re  zp 
	
	@NotNull(message = "{preYearPendingGp.notempty }")
	private BigDecimal preYearPendingGp;  //  VP g. p re   grap .p 
	
	@NotNull(message = "{preYearPendingAddlLandRevenue.notempty }")
	private BigDecimal preYearPendingAddlLandRevenue;  // addl LR
	
	@NotNull(message = "{preYearPendingEducationalCess.notempty }")
	private BigDecimal preYearPendingEducationalCess;  // Education Cess
	
	@NotNull(message = "{preYearPendingAddlEducationalCess.notempty }")
	private BigDecimal preYearPendingAddlEducationalCess; // Additional Education Cess
	
	@NotNull(message = "{preYearPendingEmployeeGuaranteeScheme.notempty }")
	private BigDecimal preYearPendingEmployeeGuaranteeScheme;  // EGS 
	
	private BigDecimal netPending;  //total of All above bigdecimal vars
	
//	@NotNull(message = "{miscellaneousAmount.notempty }")
//	private BigDecimal miscellaneousAmount; //addtional cess
	
	@NotNull(message = "{preYearPendingNaCess.notempty}")
	private BigDecimal preYearPendingNaCess;

	@NotNull(message = "{preYearSankirnJmWith.notempty}")
	private BigDecimal preYearSankirnJmWith;
	
	@NotNull(message = "{preYearSankirnJmWithout.notempty}")
	private BigDecimal preYearSankirnJmWithout;
	
	@NotNull(message = "{preYearNoticeFee.notempty}")
	private BigDecimal preYearNoticeFee;
	
	public String getcCode() {
		return cCode;
	}
		public void setcCode(String cCode) {
		this.cCode = cCode;
	}
	
}

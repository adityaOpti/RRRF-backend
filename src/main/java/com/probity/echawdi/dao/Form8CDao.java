/**
 * 
 */
package com.probity.echawdi.dao;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

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
@NoArgsConstructor
@AllArgsConstructor
public class Form8CDao {
	
	private Long id ;
	
	@NotNull(message = "{districtCode.notempty}")
	public Integer districtCode;
	
	@NotNull(message = "{talukaCode.notempty}")
	public Integer talukaCode;
	
	
	
	private String pin;
	
	@NotNull(message="{nameOfPersonFromWhomRecoverable.notempty}")
	private String  nameOfPersonFromWhomRecoverable;
	
	@NotNull(message="{totalDemand.notempty}")
	private String totalDemand;
	
	@NotNull(message="{area.notempty}")
	private BigDecimal arrears;
	
	@NotNull(message="{cCode.notempty}")
	private String cCode;
	
	@NotNull(message="{currentYear.notempty}")
	private long currentYear;
	
	@NotNull(message="{receiptNo.notempty}")
	private String receiptNo;
	
	 @DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate  receiptDate;
	
	 @NotNull(message="{amountRecovered.notempty}")
	private String amountRecovered;
	
	 @NotNull(message="{balanceForRecovery.notempty}")
	private String balanceForRecovery;
	
	 @NotNull(message="{challanNoCreditInTreasury.notempty}")
	private String challanNoCreditInTreasury;
	
	 @DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate  challanDate;
	 
	 @NotNull(message="{remarks.notempty}")
	private String remarks;
	 public String getcCode() {
			return cCode;
		}
		  public void setcCode(String cCode) {
			this.cCode = cCode;
		}
	

}

/**
 * 
 */
package com.probity.echawdi.dao;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Rushikesh Durgude
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Form7KhataDao {

	public Long id;
	public String cCode;
	private Integer districtCode;
	private Integer talukaCode;
	public BigDecimal numericPin;
	public String netAmount1;// newly added 16feb2024
	public String pin;
	public String pin1;
	public String pin2;
	public String pin3;
	public String pin4;
	public String pin5;
	public String pin6;
	public String pin7;
	public String pin8;
	//public String khataNo;
	public String form7khataCount;
	public String fName;
	public String mName;
	public String lName;
	public BigDecimal usrNo;
	public BigDecimal totalAreaH;
	public BigDecimal assessment;
	public BigDecimal anne;
	public BigDecimal pai;
	public Integer mutNo;
	public String topanName;
	public Character marked;
	public Integer old_mutNo;
	public BigDecimal naveArea;
	public BigDecimal naveAssessment;
	public Integer naveAnne;
	public Integer navePai;
	public Integer khataType;
	public BigDecimal potkharaba;
	public String khataOwnerName;
	public String getcCode() {
		return cCode;
	}
	  public void setcCode(String cCode) {
		this.cCode = cCode;
	}
	
	
}

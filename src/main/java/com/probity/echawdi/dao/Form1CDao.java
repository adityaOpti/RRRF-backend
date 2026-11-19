/**
 * 
 */
package com.probity.echawdi.dao;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * @author Rushikesh Durgude
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Form1CDao {

	public Long id;

	@NotNull(message = "{districtCode.notempty}")
	public Integer districtCode;

	@NotNull(message = "{talukaCode.notempty}")
	public Integer talukaCode;
	
	@NotNull(message="cCode.notempty")
	public String cCode;

	public String khataNo;
	public String khataOwnerName;
	public BigDecimal totalAreaH;
	public BigDecimal assessment;
	public String uom;

	@NotNull(message="pin.notempty")
	public String pin;

	public String hissaNo;

	//@NotNull(message="nameOfVillagePanchayat.notempty")
	public String nameOfVillagePanchayat;

	//@NotNull(message="termsOfGrant.notempty")
	public String termsOfGrant;

	public String aadeshOnDate;

	public String authorityCompetentToPermitTransferOfLand ;

	public String detailsOfUnclaimedAmount ;

	public String breachOfCondition;

	public String inspectingOfficer ;

	public Date inspectionDate;

	public String purposeOfLandIntended;

	public String codeNo;

	public String clause;

	//@NotNull(message="remarks.notempty")
	public String  remarks;

	public String getcCode() {
		return cCode;
	}
	public void setcCode(String cCode) {
		this.cCode = cCode;
	}

	public Integer getIntPin() {
		return StringUtils.isNumeric(pin) == true ? Integer.valueOf(pin): 0;
	}
}
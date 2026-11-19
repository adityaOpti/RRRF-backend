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
@AllArgsConstructor
@NoArgsConstructor
public class Form17Dao {

	public Long id;

	@NotNull(message = "{revenueYear.notempty}")
	public String revenueYear;

	@NotNull(message = "{districtCode.notempty}")
	public Integer districtCode;

	@NotNull(message = "{talukaCode.notempty}")
	public Integer talukaCode;

	@NotNull(message = "{cCode.notempty}")
	public String cCode;

	//@NotNull(message = "{khataNo.notempty}")
	public String khataNo;

	@NotNull(message = "{personLiable.notempty}")
	public String personLiable;

	//@NotNull(message = "{surveyHissaNo.noempty}")
	public String surveyHissaNo;

//	@NotNull(message = "{totalArea.notempty}")
	public BigDecimal totalArea;

//	@NotNull(message = "{uom.notempty}")
	public String uomOfTotalArea;

//	@NotNull(message = "{caseNo.notempty}")
	public String caseNo;

//	@NotNull(message = "{areaAffected.notempty}")
	public BigDecimal areaAffected;

//	@NotNull(message = "{uom.notempty}")
	public String uomOfAreaAffected;
	
	public String uomOfAreaAffectedMarathi;

//	@NotNull(message = "{natureOfCase.notempty}")
	public String natureOfCase;

	public String periodIfMoreThanOneYear;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public LocalDate periodFromDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public LocalDate periodToDate;

//	@NotNull(message = "{localCess.notempty}")
	public String localCess;

//	@NotNull(message = "{localCessAmount.notempty}")
	public BigDecimal localCessAmount;
	
	public String otherLocalCess;

	public String otherLocalCessDesc;

//	@NotNull(message = "{amountOfALiable.notempty}")
	public BigDecimal otherLocalCessAmount;

	@NotNull(message = "{amountOfJm.notempty}")
	public BigDecimal amountOfJm;

//	@NotNull(message = "{amountOfZp.notempty}")
	public BigDecimal amountOfZp;

//	@NotNull(message = "{amountOfGp.notempty}")
	public BigDecimal amountOfGp;

	@NotNull(message = "{assessment.notempty}")
	public BigDecimal assessment;

	//@NotNull(message = "{remarks.notempty}")
	public String remarks;
	
	public Character isAmountPaid;
	
	public Character demandGenerated;
	
	public String khataType;
	
	public String makhtaKhataNo;
	
	public String khataOwnerName;
	
	public String ekunSthanikUpkar;
	
	public String sthanikUpkarAslela;

	public String sthanikUpkarNaslela;
	
	public String jaminMahsul;
	
	public String ekunJaminMahsul;
	
	public String vasuli;
	
	public String shillak;
	
	public String getcCode() {
		return cCode;
	}

	public void setcCode(String cCode) {
		this.cCode = cCode;
	}
}
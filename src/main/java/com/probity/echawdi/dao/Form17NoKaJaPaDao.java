package com.probity.echawdi.dao;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Form17NoKaJaPaDao {
	
private Long id;
	
	@NotNull(message = "{revenueYear.notempty}")
	public String revenueYear;

	@NotNull(message = "{districtCode.notempty}")
	public Integer districtCode;

	@NotNull(message = "{talukaCode.notempty}")
	public Integer talukaCode;

	@NotNull(message = "{cCode.notempty}")
	public String cCode;
	
	@NotNull(message = "{khataNo.notempty}")
	public String khataNo;
	
	@NotNull(message = "{personLiable.notempty}")
	public String personLiable;
	
	@NotNull(message = "{surveyHissaNo.noempty}")
	public String surveyHissaNo;
	
	@NotNull(message = "{totalArea.notempty}")
	public BigDecimal totalArea;
	
	@NotNull(message = "{uom.notempty}")
	public String uomOfTotalArea;
	
	//@NotNull(message = "{orderNo.notempty}")
	public String orderNo;
	
	public BigDecimal areaAffected;
	
	public String locationOfLand;
	
	public String prayojanType;
	
	public String gavthanatil;
	
	public String gavthanabaheril;
	
	public String residential;
	
	public String commercial;
	
	public String industrial;
	
	public String other;
	
	public BigDecimal kaJaPaRate;
	
	@NotNull(message = "{amountOfJm.notempty}")
	public BigDecimal amountOfJm;

	@NotNull(message = "{amountOfZp.notempty}")
	public BigDecimal amountOfZp;

	@NotNull(message = "{amountOfGp.notempty}")
	public BigDecimal amountOfGp;
	
	@NotNull(message = "{assessment.notempty}")
	public BigDecimal assessment;
	
	public String remarks;

	
	
}
/**
 * 
 */
package com.probity.echawdi.dao;

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
public class Form14Dao {

private Long id;
	
    @NotNull(message="{cCode.notempty}")
	private String cCode;
	
	private String khataNo;
	
	//@NotNull(message="{pin.notempty}")
	private String pin;
	
	@NotNull(message = "{districtCode.notempty}")
	public Integer districtCode;
	
	@NotNull(message = "{talukaCode.notempty}")
	public Integer talukaCode;
	  
	private String pin1;
	
	private String pin2;
	
	private String pin3;
	
	private String pin4;
	
	private String pin5;
	
	private String pin6;
	
	private String pin7;
	
	private String pin8;
	
	
//	@NotNull(message="{hissaNo.notempty}")
	private String hissaNo;
	
	private String sourcesOfWater;
	
	private String  otherType;
	
	private String  area;
	
	private String  govCanalPakkaInUse;
	
	private String  govCanalPakkaNotUse;

	private String  govCanalPakkaIsThrown;

	private String  govCanalPakkaIsThrownUnRepaired;
	
	private String  govCanalPakkaIsThrownOtherReason;
	
	private String  govCanalKacchaInUse;
	
	private String  govCanalKacchaNotUse;
	
	private String  govCanalKacchaIsThrown;
	
	private String  privateCanalPakkaInUse;
	
	private String  privateCanalPakkaNotUse;

	private String  privateCanalPakkaIsThrown;

	private String  privateCanalPakkaIsThrownUnRepaired;
	
	private String  privateCanalPakkaIsThrownOtherReason;
	
	private String  privateCanalKacchaInUse;
	
	private String  privateCanalKacchaNotUse;
	
	private String  privateCanalKacchaIsThrown;
	
	private String  lakePakkaInUse;
	
	private String  lakePakkaNotUse;

	private String  lakePakkaIsThrown;

	private String  lakePakkaIsThrownUnRepaired;
	
	private String  lakePakkaIsThrownOtherReason;
	
	private String  lakeKacchaInUse;
	
	private String  lakeKacchaNotUse;
	
	private String  lakeKacchaIsThrown;
	
    private String  wellPakkaInUse;
	
	private String  wellPakkaNotUse;

	private String  wellPakkaIsThrown;

	private String  wellPakkaIsThrownUnRepaired;
	
	private String  wellPakkaIsThrownOtherReason;
	
	private String  wellKacchaInUse;
	
	private String  wellKacchaNotUse;
	
	private String  wellKacchaIsThrown;
	
    private String  otherPakkaInUse;
	
	private String  otherPakkaNotUse;

	private String  otherPakkaIsThrown;

	private String  otherPakkaIsThrownUnRepaired;
	
	private String  otherPakkaIsThrownOtherReason;
	
	private String  otherKacchaInUse;
	
	private String  otherKacchaNotUse;
	
	private String  otherKacchaIsThrown;
	
	private String pakka;
	private String kaccha;
	private String thrown;
	
	@NotNull(message="{position.notempty}")
	private String position;
	
	private String eWaterSourceName;
	
	@NotNull(message="{pakkaOrKaccha.notempty}")
	private String pakkaOrKaccha;
	
	@NotNull(message="{purposeOfUse.notempty}")
	private String purposeOfUse;
	
	@NotNull(message="{governmentbodyOrPrivate.notempty}")
	private String governmentbodyOrPrivate;
	
	//@NotNull(message="{remarks.notempty}")
	private String remarks;

	public String getcCode() {
		return cCode;
	}

	public void setcCode(String cCode) {
		this.cCode = cCode;
	}
	
	public String geteWaterSourceName() {
		return eWaterSourceName;
	}

	public void seteWaterSourceName(String eWaterSourceName) {
		this.eWaterSourceName = eWaterSourceName;
	}
	
	
	
}

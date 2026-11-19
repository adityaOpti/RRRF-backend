package com.probity.echawdi.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AkrushakRateDao {

	public String ccode;
	public String rate;
	public String rateOfNapaManapa;
	public String khataNo;
	public String khatedarName;
	public String area;
	public String assessment;
	public String unit;
	public String prayojanType;
	public String surveyHissaNo;
}

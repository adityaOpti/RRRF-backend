package com.probity.echawdi.dao;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Form2SrNoDao {
	
public String khataNo;
	
	public String khatedarNames;
	
	public BigDecimal area;
	
	public BigDecimal akrushikAssessment;
	
	public String pin;
	
	public String unit;

}

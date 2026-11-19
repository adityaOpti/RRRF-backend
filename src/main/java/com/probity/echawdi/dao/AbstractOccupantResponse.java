package com.probity.echawdi.dao;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AbstractOccupantResponse {

	public String occupantType;
	public BigDecimal area ; 
	public BigDecimal assessment;
	public BigDecimal potkharabaAH;
	public BigDecimal potkharabaBH;
	
}
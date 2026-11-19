
package com.probity.echawdi.dao;


import java.math.BigDecimal;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Data
@Getter
@Setter
public class AkrushakRateDaoInspection {
	private BigDecimal rate;
	private String name;
	private String flag;
	private BigDecimal nprate;
	private BigDecimal mnparate;
	private int fivepaise;
	private int tenpaise;
	private String declaration;
}
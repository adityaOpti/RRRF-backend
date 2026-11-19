package com.probity.echawdi.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Form1AbstractTypesDao {

	public Long id;
	public Integer landTypeCode;
	public String landType;
	public String landSubType1;
	private String landSubType2;
	public String landSubType3;
	public String landTypeMarathi;
	public String landSubType1Marathi;
	private String landSubType2Marathi;
	public String landSubType3Marathi;
	public String landTypeWithSubtype;
	public String landTypeWithSubtypeMarathi;
	
}

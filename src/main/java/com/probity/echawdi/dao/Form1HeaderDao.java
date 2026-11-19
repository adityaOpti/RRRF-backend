package com.probity.echawdi.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Form1HeaderDao {

	public String settlementYear;
	 
	 public String settlementExp;
	 
	 public String garden_rate;
	 
	 public String rice_rate;
	 
	 public String survey_group;
	 
	 public String date_inst;
	 
	 public String dry_rate;
	 
	 public String warkas_rate;
	 
}

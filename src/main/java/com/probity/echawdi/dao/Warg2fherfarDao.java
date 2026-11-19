package com.probity.echawdi.dao;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Warg2fherfarDao {
	private String ccode;
	 private Integer mutNo;
	 private LocalDate mutDate;

	 private Integer mutType;

	 private String mutName;

	 private String surveyHissaNo;

}

package com.probity.echawdi.dao;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EchawadiCessDao {
	public Long id;
	

	@NotNull(message ="{cessNameEng.notempty}")
	public String cessNameEng;

	@NotNull(message ="{cessNameMar.notempty}")
	public String cessNameMar;
	
}
package com.probity.echawdi.dao;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FerfarApiRequest {
	
	@JsonProperty("lgd_code")
	private String lgd_code;
	
	@JsonProperty("mut_no")
	private String mut_no;

}

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
public class SatBaraApiRequest {

//	 private String lgd_code;
//	 private String pin;
	 

	    @JsonProperty("lgd_code")
	    private String lgd_code;

	    @JsonProperty("pinCode")
	    private String pin;
	    
	    @JsonProperty("pin1")
	    private String pin1;
	    
	    @JsonProperty("pin2")
	    private String pin2;
	    
	    @JsonProperty("pin3")
	    private String pin3;
	    
	    @JsonProperty("pin4")
	    private String pin4;
	    
	    @JsonProperty("pin5")
	    private String pin5;
	    
	    @JsonProperty("pin6")
	    private String pin6;
	    
	    @JsonProperty("pin7")
	    private String pin7;
	    
	    @JsonProperty("pin8")
	    private String pin8;
	    
	    
	    
}

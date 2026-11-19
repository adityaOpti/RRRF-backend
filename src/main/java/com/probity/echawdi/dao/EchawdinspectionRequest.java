package com.probity.echawdi.dao;


import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

@Data
public class EchawdinspectionRequest {
	
	 @NotNull(message = "districtCode is required")
	    private String districtCode;

	    @NotNull(message = "talukaCode is required")
	    private String talukaCode;

	    private String ccode;

	    private String revenueYear;
	    
	    private Integer eChawdiType;

	    @NotBlank(message = "remark is required")
	    private String remark;

}

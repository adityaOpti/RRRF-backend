package com.probity.echawdi.dao;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

@Data
public class EhakkaInspectionRequest {
	  @NotNull(message = "districtCode is required")
	    private String districtCode;

	    @NotNull(message = "talukaCode is required")
	    private String talukaCode;

	    private String ccode;

	    private String revenueYear;
    
	    private Long applicationid;
	    
	    private Integer ehakkaType;
	    
	    private String remark;

	    @NotBlank(message = "remark is required")
	    private String remarkType;
}


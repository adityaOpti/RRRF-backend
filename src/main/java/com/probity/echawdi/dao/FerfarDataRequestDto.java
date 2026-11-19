package com.probity.echawdi.dao;

import java.time.LocalDate;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class FerfarDataRequestDto {

	 private String districtCode;
	    private String talukaCode;
	    private String circleCode;
	    private String villageCode;
	    private String ccode;
	    private Integer  ferfarTypeId;
	    private String mutNo;
	    private LocalDate mutDate;
	    private String documentReference;
	    private String remark;

	    private String createdBy;
	    private String updatedBy;
}
package com.probity.echawdi.dao;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class InspectionReportMasterDao {
	  private Long id;

	    private String ccode;

	    private Integer districtCode;

	    private Integer talukaCode;

	    private String revenueYear;

	    private String status;

	    private Long createdUserId;

	    private LocalDateTime createdAt;

	    private Long updatedUserId;

	    private LocalDateTime updatedAt;

	    private String activeFlag = "Y";

	    private String inspOfficerUsername;

	    private Long version;
}

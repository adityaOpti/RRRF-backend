package com.probity.echawdi.dao;

import java.util.Date;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.probity.echawdi.entity.InspectionReportMaster;

import lombok.Data;

@JsonInclude(Include.NON_NULL)
@Data
public class TalathiRemarkInspectionDto {
	private Long id;

	  
    private InspectionReportMaster inspectionReportMaster;

    private Double version;

    private Long createdUserId;

   
    private Date createDtTm;

    private Long updateUserid;

     private Date updateDtTm;

    private Integer districtCode;

    private Integer talukaCode;

    private String ccode;

    private String activeFlag;

    private String revenueYear;

    private String remark;

    private String designation;

    private String isRemarkSubmitted;

    private String servarthId;

    private String inspectionOfficerUsername;



}

package com.probity.echawdi.dao;


import java.math.BigInteger;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
//@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class MutationRegisterDTO {

	    
	    private Integer mutNo;
	    private LocalDate mutDate;
	    private String mutName;

	    private Integer mutType;
	    private String surveyHissaNo;
	    private String rejReason;
	    private BigInteger applicationId;
	    private LocalDate appDate;
	    private Boolean isRemarkSubmitted;
	    
	    private LocalDate stayOrderDate;
	    
	    private long DyslrAkarbandDeletedCount;
	    
	    
	    //For Nirank Count
	    private Long nirankYesCount;
	    private Long completedYesCount;
	    private Long completedNoNirankNoCount;
	    
	    private long maganiDurustiKhatedarCount;
	    private long DyslrDurustiCount;

}	

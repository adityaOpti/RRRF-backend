package com.probity.echawdi.dao;


import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class InwardOutwardRegisterDto {

    private Long id;

    private String ccode;
    private String districtCode;
    private String talukaCode;
    private String sevarthId;

    private String receivedBy;
    private String letterNumber;

    private LocalDate letterReceivedDate;

    private String deliveredPersonName;
    private String actionRemarks;

    private LocalDate deliveredDate;

    private Integer recordSequenceNumber;

    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;

    private Boolean activeFlag;

    private String remark;
}

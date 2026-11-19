package com.probity.echawdi.dao;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class GovernmentPropertyRegisterDto {

    private Long id;

    private String districtCode;
    private String talukaCode;
    private String ccode;
    private String sevarthId;
    private String propertyDescription;

    private Integer quantity;

    private LocalDate purchasedDate;

    private String authorisationCertificate;

    private LocalDate dateOfDepositInTreasury;

    private String outstandingCollectionNumber;

    private String remark;

    private BigDecimal writtenOffAmount;
    private BigDecimal collectedAmount;
    private BigDecimal outstandingCollectionAmount;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Boolean activeFlag;
}
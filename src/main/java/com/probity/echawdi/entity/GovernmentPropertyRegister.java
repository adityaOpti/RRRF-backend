package com.probity.echawdi.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "government_property_register")
@Getter
@Setter
@NoArgsConstructor
public class GovernmentPropertyRegister {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "district_code", length = 10)
    private String districtCode;

    @Column(name = "taluka_code", length = 10)
    private String talukaCode;

    @Column(name = "ccode", columnDefinition = "text")
    private String ccode;

    @Column(name = "sevarth_id", length = 50)
    private String sevarthId;

    @Column(name = "property_description", length = 255)
    private String propertyDescription;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "purchased_date")
    private LocalDate purchasedDate;

    @Column(name = "authorisation_certificate", length = 255)
    private String authorisationCertificate;

    @Column(name = "date_of_deposit_in_treasury")
    private LocalDate dateOfDepositInTreasury;

    @Column(name = "outstanding_collection_number", length = 100)
    private String outstandingCollectionNumber;

    @Column(name = "remark", columnDefinition = "text")
    private String remark;

    @Column(name = "written_off_amount", precision = 14, scale = 2)
    private BigDecimal writtenOffAmount = BigDecimal.ZERO;

    @Column(name = "collected_amount", precision = 14, scale = 2)
    private BigDecimal collectedAmount = BigDecimal.ZERO;

    @Column(name = "outstanding_collection_amount", precision = 14, scale = 2)
    private BigDecimal outstandingCollectionAmount = BigDecimal.ZERO;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "active_flag")
    private Boolean activeFlag = true;

    @PrePersist
    protected void onCreate() {

        LocalDateTime now = LocalDateTime.now();

        if (createdAt == null) {
            createdAt = now;
        }

        updatedAt = now;

        if (writtenOffAmount == null) {
            writtenOffAmount = BigDecimal.ZERO;
        }

        if (collectedAmount == null) {
            collectedAmount = BigDecimal.ZERO;
        }

        if (outstandingCollectionAmount == null) {
            outstandingCollectionAmount = BigDecimal.ZERO;
        }

        if (activeFlag == null) {
            activeFlag = true;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
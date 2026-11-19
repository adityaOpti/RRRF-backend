package com.probity.echawdi.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Data
@Table(
    name = "inspection_report_master",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_inspection_report_ccode", columnNames = "ccode")
    }
)
public class InspectionReportMaster {

   

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ccode", nullable = false, length = 50)
    private String ccode;

    @Column(name = "district_code")
    private Integer districtCode;

    @Column(name = "taluka_code")
    private Integer talukaCode;

    @Column(name = "revenue_year", length = 20)
    private String revenueYear;

//    @Column(name = "status", length = 20)
//    private String status = "OPEN";

    @Column(name = "status", length = 20)
    private String status;

    @Column(name = "created_user_id")
    private Long createdUserId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_user_id")
    private Long updatedUserId;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "active_flag", nullable = false, length = 1)
    private String activeFlag = "Y";
    
    
    @Version
    @Column(name = "version")
    private Long version;
    
    
    @Column(name = "insp_officer_username")
    private String inspOfficerUsername;

    // --- Lifecycle Hooks ---
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        if (this.status == null) {
            this.status = "OPEN";
        }
        if (this.activeFlag == null) {
            this.activeFlag = "Y";
        }
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}

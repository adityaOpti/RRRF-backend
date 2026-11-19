package com.probity.echawdi.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "inward_outward_register")
@Getter
@Setter
@NoArgsConstructor
public class InwardOutwardRegister {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ccode", columnDefinition = "text")
    private String ccode;

    @Column(name = "district_code", length = 10)
    private String districtCode;

    @Column(name = "taluka_code", length = 10)
    private String talukaCode;

    @Column(name = "sevarth_id", length = 50)
    private String sevarthId;

    @Column(name = "received_by")
    private String receivedBy;

    @Column(name = "letter_number")
    private String letterNumber;

    @Column(name = "letter_received_date")
    private LocalDate letterReceivedDate;

    @Column(name = "delivered_person_name")
    private String deliveredPersonName;

    @Column(name = "action_remarks", columnDefinition = "text")
    private String actionRemarks;

    @Column(name = "delivered_date")
    private LocalDate deliveredDate;

    @Column(name = "record_sequence_number")
    private Integer recordSequenceNumber;

    @Column(name = "created_on", updatable = false)
    private LocalDateTime createdOn;

    @Column(name = "updated_on")
    private LocalDateTime updatedOn;

    @Column(name = "active_flag")
    private Boolean activeFlag = true;

    @Column(name = "remark", columnDefinition = "text")
    private String remark;

    @PrePersist
    protected void onCreate() {

        LocalDateTime now = LocalDateTime.now();

        if (createdOn == null) {
            createdOn = now;
        }

        updatedOn = now;

        if (activeFlag == null) {
            activeFlag = true;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedOn = LocalDateTime.now();
    }
}

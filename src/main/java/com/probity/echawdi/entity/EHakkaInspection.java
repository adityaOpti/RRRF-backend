package com.probity.echawdi.entity;


import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "e_hakka_inspection")
public class EHakkaInspection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal version;

    @Column(name = "created_user_id")
    private Long createdUserId;

    @Column(name = "create_dt_tm")
    private LocalDateTime createDtTm;

    @Column(name = "update_userid")
    private Long updateUserid;

    @Column(name = "update_dt_tm")
    private LocalDateTime updateDtTm;

    @Column(name = "district_code")
    private Integer districtCode;

    @Column(name = "taluka_code")
    private Integer talukaCode;

    @Column(name = "ccode")
    private String ccode;

    @Column(name = "active_flag")
    private String activeFlag;

    @Column(name = "revenue_year")
    private String revenueYear;

    @Column(name = "application_id")
    private Long applicationId;

    @Column(name = "doc_path")
    private String docPath;
    
    @Column(name = "remark")
    private String remark;

    @ManyToOne
    @JoinColumn(name = "type_of_remark", nullable = false)
    private RemarkType typeOfRemark;

    @ManyToOne
    @JoinColumn(name = "e_hakka_type", nullable = false)
    private EHakkaTypeMaster eHakkaType;
    
    @Column(name = "is_remark_submitted")
    private Character isRemarkSubmitted;
}
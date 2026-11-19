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

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "echawdi_inspection")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class EchawdiInspection {

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

    @Column(name = "remark")
    private String remark;

    @ManyToOne
    @JoinColumn(name = "echawdi_type", nullable = false)
    private EChawdiTypeMaster echawdiType;

}

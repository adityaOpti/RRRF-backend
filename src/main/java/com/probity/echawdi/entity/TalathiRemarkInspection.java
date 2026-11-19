package com.probity.echawdi.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Data
@Table(name = "talathi_remark_inspection")
public class TalathiRemarkInspection {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

// Foreign key mapping with inspection_report_master
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "inspection_id", nullable = false)
private InspectionReportMaster inspectionReportMaster;

@Column(name = "version")
private Double version;

@Column(name = "created_user_id")
private Long createdUserId;

@Temporal(TemporalType.TIMESTAMP)
@Column(name = "create_dt_tm")
private Date createDtTm;

@Column(name = "update_userid")
private Long updateUserid;

@Temporal(TemporalType.TIMESTAMP)
@Column(name = "update_dt_tm")
private Date updateDtTm;

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

@Column(name = "remark", columnDefinition = "TEXT")
private String remark;

@Column(name = "designation")
private String designation;

@Column(name = "is_remark_submitted")
private String isRemarkSubmitted;

@Column(name = "servarth_id")
private String servarthId;

@Column(name = "inspection_officer_username")
private String inspectionOfficerUsername;
}
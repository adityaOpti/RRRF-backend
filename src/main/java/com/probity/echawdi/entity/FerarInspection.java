package com.probity.echawdi.entity;

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

@Entity
@Table(name = "ferfar_inspection")
@Data
public class FerarInspection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "district_code")
    private Integer districtCode;

    @Column(name = "taluka_code")
    private Integer talukaCode;

    private String ccode;

    private String revenue_year;

    //private Integer mut_no;
   
    @Column(name = "mut_no")
    private Integer mutNo;


    private String remark;
    

    @Column(name = "doc_path")
    private String doc_path;

    @Column(name = "active_flag")
    private String active_flag;

    @ManyToOne
    @JoinColumn(name = "type_of_remark")
    private RemarkType remarkType;
    
    
    @ManyToOne
    @JoinColumn(name = "ferfar_type")
    private FerfarType ferfarType;
    
    
    @Column(name = "create_dt_tm")
    private LocalDateTime createDtTm;

    @Column(name = "update_dt_tm")
    private LocalDateTime updateDtTm;

    @Column(name = "update_userid")
    private Long updateUserId;

    @Column(name = "is_remark_submitted")
    private Character isRemarkSubmitted;
    
}

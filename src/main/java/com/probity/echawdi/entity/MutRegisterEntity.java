package com.probity.echawdi.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "mutregister", schema = "mhrorrai_tal")
@Data
public class MutRegisterEntity {

    @Column(name = "district_code")
    private Integer districtCode;

    @Column(name = "taluka_code")
    private Integer talukaCode;

    @Id
    @Column(name = "ccode", length = 18)
    private String ccode;

    @Column(name = "mut_no")
    private Integer mutNo;

    @Column(name = "mut_date")
    private LocalDate mutDate;

    @Column(name = "mut_type")
    private Integer mutType;

    @Column(name = "mut_name", length = 100)
    private String mutName;

    @Column(name = "mut_info_date")
    private LocalDate mutInfoDate;

    @Column(name = "notice_issue_date")
    private LocalDate noticeIssueDate;

    @Column(name = "notice_inti_date")
    private LocalDate noticeIntiDate;

    @Column(name = "notice_period")
    private Integer noticePeriod;

    @Column(name = "notice_board_date")
    private String noticeBoardDate;

    @Column(name = "notice_code")
    private Integer noticeCode;

    @Column(name = "certi_inti_date")
    private LocalDate certiIntiDate;

    @Column(name = "talathi_id", length = 25)
    private String talathiId;

    @Column(name = "mut_status_code")
    private Integer mutStatusCode;

    @Column(name = "circle_id")
    private String circleId;

    @Column(name = "circle_remark")
    private String circleRemark;

    @Column(name = "sdo_id", length = 200)
    private String sdoId;

    @Column(name = "sdo_remark")
    private String sdoRemark;

    @Column(name = "table_name", length = 20)
    private String tableName;

    @Column(name = "ref_no1")
    private String refNo1;

    @Column(name = "ref_no2")
    private String refNo2;

    @Column(name = "ref_date1")
    private LocalDate refDate1;

    @Column(name = "ref_date2")
    private LocalDate refDate2;

    @Column(name = "amount", precision = 18, scale = 2)
    private BigDecimal amount;

    @Column(name = "notice_last_date")
    private LocalDate noticeLastDate;

    @Column(name = "gen_type")
    private Integer genType;

    @Column(name = "gen_subtype")
    private Integer genSubtype;

    @Column(name = "from_date")
    private LocalDate fromDate;

    @Column(name = "muktiar", length = 150)
    private String muktiar;

    @Column(name = "mutcheck")
    private String mutCheck;

    @Column(name = "mutation_result", length = 30)
    private String mutationResult;

    @Column(name = "isobjection")
    private Boolean isObjection;
}
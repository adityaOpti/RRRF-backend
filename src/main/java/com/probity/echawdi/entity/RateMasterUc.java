package com.probity.echawdi.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Data
@Table(name = "rate_master_uc")
public class RateMasterUc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "district_code", columnDefinition = "character")
    private String districtCode;

    @Column(name = "taluka_code", columnDefinition = "character")
    private String talukaCode;

    @Column(name = "flag")
    private String flag;

    @Column(name = "name")
    private String name;

    @Column(name = "rate")
    private BigDecimal rate;

    @Column(name = "rate2")
    private BigDecimal rate2;

    @Column(name = "rate3")
    private BigDecimal rate3;

    @Column(name = "rate4")
    private BigDecimal rate4;

    @Column(name = "rate5")
    private BigDecimal rate5;

    @Column(name = "ccode")
    private String ccode;

    // Getters and Setters
}
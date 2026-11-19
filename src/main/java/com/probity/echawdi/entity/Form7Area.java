package com.probity.echawdi.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="form7_area")
public class Form7Area {

	
		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "ccode", length = 18)
	    private String ccode;
			
	    @Column(name = "mut_no")
	    private Long mutNo;

		

	    @Column(name = "numeric_pin")
	    private BigDecimal numericPin;

	    @Column(name = "pin", length = 20)
	    private String pin;

	    @Column(name = "pin1", length = 20)
	    private String pin1;

	    @Column(name = "pin2", length = 20)
	    private String pin2;

	    @Column(name = "pin3", length = 20)
	    private String pin3;

	    @Column(name = "pin4", length = 20)
	    private String pin4;

	    @Column(name = "pin5", length = 20)
	    private String pin5;

	    @Column(name = "pin6", length = 20)
	    private String pin6;

	    @Column(name = "pin7", length = 20)
	    private String pin7;

	    @Column(name = "pin8", length = 20)
	    private String pin8;

	    @Column(name = "jirayat_area_h", precision = 18, scale = 4)
	    private BigDecimal jirayatAreaH;

	    @Column(name = "jirayat_area_r")
	    private Integer jirayatAreaR;

	    @Column(name = "jirayat_area_sqm", precision = 2, scale = 0)
	    private BigDecimal jirayatAreaSqm;

	    @Column(name = "bagayat_area_h", precision = 18, scale = 4)
	    private BigDecimal bagayatAreaH;

	    @Column(name = "bagayat_area_r")
	    private Integer bagayatAreaR;

	    @Column(name = "bagayat_area_sqm", precision = 2, scale = 0)
	    private BigDecimal bagayatAreaSqm;

	    @Column(name = "tari_area_h", precision = 18, scale = 4)
	    private BigDecimal tariAreaH;

	    @Column(name = "tari_area_r")
	    private Integer tariAreaR;

	    @Column(name = "tari_area_sqm", precision = 2, scale = 0)
	    private BigDecimal tariAreaSqm;

	    @Column(name = "varkas_area_h", precision = 18, scale = 4)
	    private BigDecimal varkasAreaH;

	    @Column(name = "varkas_area_r")
	    private Integer varkasAreaR;

	    @Column(name = "varkas_area_sqm", precision = 2, scale = 0)
	    private BigDecimal varkasAreaSqm;

	    @Column(name = "itar_area_h", precision = 18, scale = 4)
	    private BigDecimal itarAreaH;

	    @Column(name = "itar_area_r")
	    private Integer itarAreaR;

	    @Column(name = "itar_area_sqm", precision = 2, scale = 0)
	    private BigDecimal itarAreaSqm;

	    @Column(name = "total_area_h", precision = 18, scale = 4)
	    private BigDecimal totalAreaH;

	    @Column(name = "total_area_r")
	    private Integer totalAreaR;

	    @Column(name = "total_area_sqm", precision = 2, scale = 0)
	    private BigDecimal totalAreaSqm;

	    @Column(name = "net_culti_area_h", precision = 18, scale = 4)
	    private BigDecimal netCultiAreaH;

	    @Column(name = "assessment", precision = 18, scale = 2)
	    private BigDecimal assessment;

	    @Column(name = "judi", precision = 18, scale = 2)
	    private BigDecimal judi;

	    @Column(name = "potkharaba_a_h", precision = 18, scale = 4)
	    private BigDecimal potkharabaAH;

	    @Column(name = "potkharaba_a_r")
	    private Integer potkharabaAR;

	    @Column(name = "potkharaba_a_sqm", precision = 2, scale = 0)
	    private BigDecimal potkharabaASqm;

	    @Column(name = "potkharaba_b_h", precision = 18, scale = 4)
	    private BigDecimal potkharabaBH;

	    @Column(name = "potkharaba_b_r")
	    private Integer potkharabaBR;

	    @Column(name = "potkharaba_b_sqm", precision = 2, scale = 0)
	    private BigDecimal potkharabaBSqm;

	    @Column(name = "total_potkharaba_h", precision = 18, scale = 4)
	    private BigDecimal totalPotkharabaH;

	    @Column(name = "total_potkharaba_r")
	    private Integer totalPotkharabaR;

	    @Column(name = "total_potkharaba_sqm", precision = 2, scale = 0)
	    private BigDecimal totalPotkharabaSqm;

	    @Column(name = "na_area_h", precision = 18, scale = 4)
	    private BigDecimal naAreaH;

	    @Column(name = "na_assessment", precision = 18, scale = 2)
	    private BigDecimal naAssessment;

	    @Column(name = "na_mut_no")
	    private Long naMutNo;

	    @Column(name = "marked", length = 1)
	    private String marked;

	    @Column(name = "old_mut_no")
	    private Integer oldMutNo;

	  
	
}

package com.probity.echawdi.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.probity.echawdi.audit.FormsEntity;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name="echawdi_form3")
public class Form3 extends FormsEntity{

	private static final long serialVersionUID = 1L;

	@Id
    @NotNull
    @Column(name = "\"id\"", precision = 19)
    @GeneratedValue(strategy=GenerationType.AUTO, generator="echawdi_form3_seq_gen")
    @SequenceGenerator(name="echawdi_form3_seq_gen", sequenceName="echawdi_form3_seq", allocationSize=1)
	private Long id;
	
	@Column(name="classes")
    public String classes;
    
	@Column(name="register_No_And_Type_Eligibility")
    public String registerNoAndTypeEligibility;
    
	@Column(name="sanad_No")
    public String sanadNo;

	@Column(name="na_Area")
    public String naArea;
    
	@Column(name="na_Assessment")
    public String naAssessment;
    
	@Column(name="juni_Salami")
    public BigDecimal juniSalami;

	@Column(name="total")
    public String total;

	@Column(name="remarks")
	public String remarks;
	
	@Column(name="rate")
	public String rate;
	
	@Column(name="ech_assessment")
	public String echAssessment;
	
	@Column(name="net_pending")
	public BigDecimal netPending;	
	
	@Column(name="tenure")
	public String tenure;

	@Column(name="kind_and_how_long_continuable")
	public String kindAndHowLongContinuable;

	@Column(name="running_no_each_class")
	public Long runningNoEachClass ;
	
	@Column(name="no_in_register_of_lands")
	public String noInRegisterOfLands;
}

//@Column(name="type_And_Period")
//public String typeAndPeriod;

//@Column(name="current_No")
//public String currentNo;

//@Column(name="cultivable_Area")
//public String cultivableArea;

//@Column(name="potKharab_Are")
//public String potKharabAre;

//@Column(name="Total_Area_of_Cultivable")
//public String TotalAreaofCultivable;

//@Column(name="cultivable_Assessment")
//public String cultivableAssessment;

//@Column(name="jamabandi_Rate")
//public String jamabandiRate;

//@Column(name="jamabandhi_Assessment")
//public String jamabandhiAssessment;

//@Column(name="dumaladar_Losses")
//public String dumaladarLosses;
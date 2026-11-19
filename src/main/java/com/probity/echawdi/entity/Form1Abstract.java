/**
 * 
 */
package com.probity.echawdi.entity;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import com.probity.echawdi.audit.PlainAudit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author ue
 *
 */

@Data
@Builder
@EqualsAndHashCode(callSuper=true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="echawdi_form1abstract")
public class Form1Abstract extends PlainAudit {
	
	private static final long serialVersionUID = 1L;

	 @Id
     @NotNull
     @Column(name = "\"id\"", precision = 19)
     @GeneratedValue(strategy=GenerationType.AUTO, generator="echawdi_form1abstract_seq_gen")
     @SequenceGenerator(name="echawdi_form1abstract_seq_gen", sequenceName="echawdi_form1abstract_seq", allocationSize=1)
     private Long id;
	
	 @Column(name = "district_code")
	 private Integer districtCode;
	 
	 @Column(name = "taluka_code")
	 private Integer talukaCode; 
	 
	 @Column(name = "ccode")
	 private String cCode;

//	 @Column(name="m_form1abstract_land_type_id")
//	 private Long landType;
//	 
//	 @Column(name="area")
//	 private BigDecimal area;
//	 
//	 @Column(name="area_unit")
//	 private String areaUnit;
//
//	 @Column(name="assessment")
//	 private BigDecimal assessment;
//	 
//	 @Column(name ="remarks")
//	 private String remarks;
	 
	 @Column(name = "tenure_Code1")
	 private BigDecimal tenureCode1;
	 
	 @Column(name = "tenure_Code2")
	 private BigDecimal tenureCode2;
	 
	 @Column(name = "tenure_Code3")
	 private BigDecimal tenureCode3;
	 
	 @Column(name = "non_Occupation_Gov")
	 private BigDecimal nonOccupationGov;
	 
	 @Column(name = "Revenue_Or_Leasehold_Land_Under_Special_Agreement")
	 private BigDecimal RevenueOrLeaseholdLandUnderSpecialAgreement;
	 
	 @Column(name = "dumala")
	 private BigDecimal dumala;
	 
	 @Column(name = "un_Occupied")
	 private BigDecimal unOccupied;
	 
	 @Column(name = "assigned_For_Special_Use")
	 private BigDecimal assignedForSpecialUse;
	 
	 @Column(name = "pot_Kharab")
	 private BigDecimal potKharab;
	 
	 @Column(name = "rivers_Nalas")
	 private BigDecimal riversNalas;
	 
	 @Column(name = "nalas")
	 private BigDecimal nalas;
	 
	 @Column(name = "forest")
	 private BigDecimal forest;
	 
	 @Column(name = "kuran")
	 private BigDecimal kuran;
	 
	 @Column(name = "free_Pasture_CattleStand")
	 private BigDecimal freePastureCattleStand;
	 
	 @Column(name = "village_Site")
	 private BigDecimal villageSite;
	 
	 @Column(name = "tank")
	 private BigDecimal tank;
	 
	 @Column(name = "burial_Ground")
	 private BigDecimal burialGround;
	 
	 @Column(name = "railways")
	 private BigDecimal railways;
	 
	 @Column(name = "potKharab_Assigned_Roads_WaterCourses")
	 private BigDecimal potKharabAssignedRoadsWaterCourses;
	 
	 @Column(name = "roads_And_Path")
	 private BigDecimal roadsAndPath;
	 
	 @Column(name = "pipe_Line_Canel")
	 private BigDecimal pipeLineCanel;
	 
	 @Column(name = "Cantonment_Land_MilitaryCamp")
	 private BigDecimal CantonmentLandMilitaryCamp;
	 
	 @Column(name = "schools")
	 private BigDecimal schools;
	 
	 @Column(name = "dharmshalas")
	 private BigDecimal dharmshalas;
	 
	 @Column(name = "srNo_For_NonAgriculture_Use")
	 private BigDecimal srNoForNonAgricultureUse;
	 
	 @Transient
	 private BigDecimal potKharabArea;

	 @Transient
	 private BigDecimal potKharabAssessment;

	 @Transient
	 private BigDecimal nalaAreaH;

	 @Transient
	 private BigDecimal riverAreaH;
	 
	 @Transient
	 private BigDecimal form3NetCultiArea;

	 @Transient
	 private BigDecimal form3Assessment;

	 @Transient
	 private BigDecimal tenure1Area;
	 
	 @Transient
	 private BigDecimal tenure1Assessment;

	 @Transient
	 private BigDecimal tenure2Area;
	 
	 @Transient
	 private BigDecimal tenure2Assessment;
	 
	 @Transient
	 private BigDecimal tenure3Area;
	 
	 @Transient
	 private BigDecimal tenure3Assessment;
	 
	 @Transient
	 private BigDecimal tenure4Area;
	 
	 @Transient
	 private BigDecimal tenure4Assessment;
	
	 public String getcCode() {
		return cCode;
	}

	public void setcCode(String cCode) {
		this.cCode = cCode;
	}
}
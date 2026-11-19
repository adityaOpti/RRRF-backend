package com.probity.echawdi.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="e_lgd_mapper")
public class LGDMapper {

	  @Id
	  @Column(name="lgd_code")
	  private String lgdCode;
	  
	  @Column(name="division_code")
	  private String divisionCode;

	  @Column(name="district_code")
	  private String districtCode;
	  
	  @Column(name="district_name_marathi")
	  private String districtNameMarathi;
	  
	  @Column(name="taluka_code")
	  private String talukaCode;
	  
	  @Column(name="taluka_name_marathi")
	  private String talukaMameMarathi;
	  
	  @Column(name="ccode")
	  private String cCode;
	  
	  @Column(name="lgd_village_name")
	  private String lgdVillageMame;

	  @Column(name="is_selected")
	  private Character isSelected;

	  @Column(name="is_challan_generated")
	  private Character isChallanGenerated;
	  
	  @Column(name="is_gaon_namuna_added")
	  private Character isGaonNamunaAdded;
	  
	  @Column(name="is_logged_in")
	  private Character isLoggedIn;
	  
	  @Column(name="log_in_dt_tm")
	  private LocalDateTime logInDtTm;
	  
	  @Transient
	  private String talukaCount;
	  
	  @Column(name="khata_Count")
	  private Integer khataCount;
	  
	  @Column(name="is_demand_completed")
	  private Character isDemandCompleted;
	  
	  @Column(name="is_vasuli_without_demandadd")
	  private Character isVasuliWithoutDemandAdd;
	  
	  @Column(name="is_vasuli_completed")
	  private Character isVasuliCompleted;
	  
	  @Column(name="sathbara_count")
	  private Integer sathbaraCount;
}
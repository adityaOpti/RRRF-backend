
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
@Table(name="e_lgd_mapper_mis")
public class LgdMapperMis {

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
	  
	  @Column(name="is_logged_in")
	  private Character isLoggedIn;
	  
	  @Column(name="log_in_dt_tm")
	  private LocalDateTime logInDtTm;
	  
	  @Transient
	  private String talukaCount;
	  
	  
	  @Column(name="revenue_year")
	  private String revenueYear;
	  
	  @Column(name="gaon_namuna_count")
	  private int gaonNamunaCount;
	  
	  @Column(name="is_gaon_numuna_completed")
	  private Character isGaonNumunaCompleted;
	  
	  @Column(name="is_all_lr_collected")
	  private Character isAllLrCollected;
	  
	  @Column(name="is_demand_generated")
	  private Character isDemandGenerated;
	  
	  @Column(name="is_lr_dues_added")
	  private Character isLrDuesAdded;
	  
	  @Column(name="is_na_land_rates_added")
	  private Character isNaLandRatesAdded;
	  
	  @Column(name="is_kajapa_added")
	  private Character isKajapaAdded;
	  
	  @Column(name="is_ms_lr_added")
	  private Character isMsLrAdded;
	  
	  @Column(name="is_notice_added")
	  private Character isNoticeAdded;
	  
	  @Column(name="is_khata_merge_added")
	  private Character isKhataMergeAdded; 
	  
	  @Column(name="is_add_land_added")
	  private Character isAddLandAdded; 
	  
	  @Column(name="is_manual_online_diff")
	  private Character isManualOnlineDiff;
	  
	  @Column(name="dyslr_work_started")
	  private Character dyslrWorkStarted;
	  
	  
	  //for Net_vasuli MIS
	  @Column(name="total_demand_jm")
	  private Integer totalDemandJm;
	  
	  
	  @Column(name="total_demand_sankirn")
	  private Integer totalDemandSankirn;
	  
	  
	  @Column(name="total_collected_jm")
	  private Integer totalCollectedJm;
	  
	  
	  @Column(name="total_collected_sankirn")
	  private Integer totalCollectedSankirn;
	  
	  @Column(name="is_dyslr_abstract_added")
	  private String isDyslrAbstractAdded ;
	  
}

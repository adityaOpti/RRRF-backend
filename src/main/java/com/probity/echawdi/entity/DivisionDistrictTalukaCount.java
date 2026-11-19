package com.probity.echawdi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name="e_division_district_taluka_count")
public class DivisionDistrictTalukaCount extends PlainAudit{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id")
	private Long id;

	@Column(name="division_code")
	private String divisionCode;
	
	@Column(name="district_code")
	private String districtCode;

	@Column(name="taluka_code")
	private String talukaCode;
	
	@Transient
	private Long totalTaluka;

	@Column(name="total_villages")
	private Long totalVillages;

	@Column(name="total_phase1_villages")
	private Long totalPhase1Villages;
	
	@Column(name="total_khata_numbers")
	private Long totalKhataNumbers;
	
	@Transient
	private String divisionName;

	@Transient
	private Long districtsCount;
	
	@Transient
	private String districtName;
	
	@Transient 
	private String talukaName;
	
	@Transient 
	private Long totalPhase1Login;
	
	@Transient 
	private Long totalEntry;
	
	@Transient
	private Long totalOnlineVillagesCount;
	
	
	@Transient
	private Long totalDyslrWorkCount;
	
	@Transient
	private Long totalGaonNamunaCount;
	
	@Transient
	private Long totalDemandCompletedCount;
	
	@Transient
	private Long vasuliWithoutDemandCount;
	
	@Transient
	private Long vasuliCompletedCount;
	
	 
	 @Transient
	 private Long dyslrWorkCompletedCount;
	 
	 @Transient
	 private Long dyslrAdbstractAddedCount;
	 
	 
	//added for total Demand Vasuli
	 @Transient
	 private Long totalDemandJm;

	 @Transient
	 private Long totalDemandSakirn;
	 
	 @Transient
	 private Long totalCollectedJm;

	 @Transient
	 private Long totalCollectedSankirn;
	 
	 
	 @Transient
		private Long demandCount; // Aug 2024 new Param for new Table

		@Transient
		private Long totalOfSevenEight;

		public void setTotalOfSevenEight() {
			if (this.vasuliWithoutDemandCount != null && this.demandCount != null) {
				this.totalOfSevenEight = this.vasuliWithoutDemandCount + this.demandCount;
			} else {
				this.totalOfSevenEight = 0L;
			}
		}
	
}
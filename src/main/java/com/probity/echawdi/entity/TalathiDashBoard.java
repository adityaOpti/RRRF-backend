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

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ashish Dahale
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="e_village_target")
public class TalathiDashBoard extends PlainAudit{

	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Id
      @NotNull
      @Column(name = "\"id\"", precision = 19)
      @GeneratedValue(strategy=GenerationType.AUTO, generator="e_village_target_seq_gen")
      @SequenceGenerator(name="e_village_target_seq_gen", sequenceName="e_village_target_seq", allocationSize=1)
	  private Long id;

	@Column(name="ccode")
	private String cCode;
	
	@Column(name="district_code")
	private String districtCode;
	
	@Column(name="taluka_code")
	private String talukaCode;
	
	@Column(name="revenue_year")
	private String revenueYear;
	
	@Column(name="annual_village_target")
	private String annualVillageTarget;	
}
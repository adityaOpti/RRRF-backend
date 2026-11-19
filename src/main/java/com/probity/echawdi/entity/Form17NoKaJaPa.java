package com.probity.echawdi.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.probity.echawdi.audit.Auditable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;



@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "echawdi_form17_NoKaJaPa")
public class Form17NoKaJaPa extends Auditable {
	
	private static final long serialVersionUID = -8588463765973026528L;

	@Id
	@NotNull
	@Column(name = "\"id\"", precision = 19)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "echawdi_form17_NoKaJaPa_seq_gen")
	@SequenceGenerator(name = "echawdi_form17_NoKaJaPa_seq_gen", sequenceName = "echawdi_form17_NoKaJaPa_seq", allocationSize = 1)
	private Long id;
	
	@Column(name = "khata_no")
	public String khataNo;
	
	@Column(name = "person_liable")
	public String personLiable;
	
	@Column(name = "survey_hissa")
	public String surveyHissaNo;
	
	@Column(name = "total_area")
	public BigDecimal totalArea;
	
	@Column(name = "uom_of_total_area")
	public String uomOfTotalArea;
	
	@Column(name = "order_no")
	public String orderNo;
	
	@Column(name = "area_affected")
	public BigDecimal areaAffected;
	
	@Column(name = "location_of_land")
	public String locationOfLand;
	
	
	@Column(name = "prayojan_type")
	public String prayojanType;
	
	@Column(name = "amount_of_jm")
	public BigDecimal amountOfJm;

	@Column(name = "amount_of_zp")
	public BigDecimal amountOfZp;

	@Column(name = "amount_of_gp")
	public BigDecimal amountOfGp;
	

	@Column(name = "assessment")
	private BigDecimal assessment;

	@Column(name = "remarks")
	private String remarks;
}

package com.probity.echawdi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name="mst_village_form")
public class VillageFormMaster {

	@Id
	@Column(name="form_id")
	private String formId;
	
	@Column(name="form_name")
	private String formName;

	@Column(name="form_name_mr")
	private String formNameMr;
}
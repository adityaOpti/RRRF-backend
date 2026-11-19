package com.probity.echawdi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
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
@Table(name="e_division_census")
public class Division extends PlainAudit{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="division_code")
	private String divisionCode;

	@Column(name="division_name")
	private String divisionName;
}
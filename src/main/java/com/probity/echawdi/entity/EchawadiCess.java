package com.probity.echawdi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name="echawdi_cess")
public class EchawadiCess extends PlainAudit{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	 @Column(name="id")
	 @GeneratedValue(strategy=GenerationType.AUTO, generator="echawdi_cess_seq_gen")
	 @SequenceGenerator(name="echawdi_cess_seq_gen", sequenceName="echawdi_cess_seq", allocationSize=1)	
	
	private Long id;
	
	@Column(name="cess_name_eng")
	private String cessNameEng;
	
	@Column(name="cess_name_mar")
	private String cessNameMar;
	
}
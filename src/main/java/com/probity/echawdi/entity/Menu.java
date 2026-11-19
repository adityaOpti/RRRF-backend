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
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="menu")
public class Menu extends PlainAudit{

	/**
	 * @author Kumar Harpude
	 */
	private static final long serialVersionUID =1L;

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="echawdi_menu_seq_gen")
	@SequenceGenerator(name="echawdi_menu_seq_gen", sequenceName="echawdi_menu_seq", allocationSize=1)	
	private Double id;

	@Column(name="name")
	private String name;

	@Column(name="path")
	private String path;

	@Column(name="routes")
	private Double routes;

	@Column(name="redirect")
	private String redirect;

	@Column(name="icon")
	private String icon;
	
	@Column(name="parent_key")
	private String parentKey;
	
	@Column(name="component")
	private String component;
	
}
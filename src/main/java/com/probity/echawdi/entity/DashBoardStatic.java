package com.probity.echawdi.entity;


import java.time.LocalDate;

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

@Data
@Builder
@AllArgsConstructor
//@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "collector_mis_2022_2023")
public class DashBoardStatic {



	private static final long serialVersionUID = 1L;

	@Id
	@NotNull
	@Column(name = "\"id\"", precision = 19)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "collector_mis_2022_2023_seq_gen")
	@SequenceGenerator(name = "collector_mis_2022_2023_seq_gen", sequenceName = "collector_mis_2022_2023_seq", allocationSize = 1)
	private Long id;

	@Column(name = "vibhagjillaha1")
	private String vibhagjillaha1;

	@Column(name = "jilhanchisankhya2")
	private String jilhanchisankhya2;

	@Column(name = "talukasankhya3")
	private String talukasankhya3;

	@Column(name = "totalgaonsankhya4")
	private String totalgaonsankhya4;
	
	@Column(name = "bhagonegoansankhya5")
	private String bhagonegoansankhya5;
	
	@Column(name = "logingoansankhya6")
	private String logingoansankhya6;
	
	
	@Column(name = "vasuligoansankhya7")
	private String vasuligoansankhya7;
	
	@Column(name = "totalbharlalihiti8")
	private String totalbharlalihiti8;
	
	@Column(name = "year")
	private String year;
	
	@Column(name="code")
	private String code;
	
	@Column(name="sequence")
	private String sequence;

}

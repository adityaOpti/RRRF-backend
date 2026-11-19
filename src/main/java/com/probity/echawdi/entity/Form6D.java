package com.probity.echawdi.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.probity.echawdi.audit.PlainAudit;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Entity
@Table(name="echawdi_form6d")
public class Form6D extends PlainAudit{

	private static final long serialVersionUID = -2378542224150310225L;
	
	@NotNull
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator="echawdi_form6d_seq_gen")
    @SequenceGenerator(name="echawdi_form6d_seq_gen", sequenceName="echawdi_form6d_seq", allocationSize=1)
	@Column(name = "\"id\"", precision = 19)
private Long id;
	
	@Column(name = "nature_of_change_in_map")
    private String natureOfChangeInMap;
	
	@Column(name = "by_whom_done")
    private String  byWhomDone;
	
	@Column(name = "date_of_change")
    private Date dateOfChange;
	
	@Column(name="district_code")
	private Integer districtCode;
	
	@Column(name="taluka_code")
	private Integer talukaCode;
	
	@Column(name="ccode")
	private String cCode;
	
	@Column(name="pin")
	private String pin;
	  
	@Column(name="pin1")
	private String pin1;
	
	@Column(name="pin2")
	private String pin2;
	
	@Column(name="pin3")
	private String pin3;
	
	@Column(name="pin4")
	private String pin4;
	
	@Column(name="pin5")
	private String pin5;
	
	@Column(name="pin6")
	private String pin6;
	
	@Column(name="pin7")
	private String pin7;
	
	@Column(name="pin8")
	private String pin8;

	@Column(name="mutation_Entry_In_Village_Form6")
    public String mutationEntryInVillageForm6;
}
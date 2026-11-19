package com.probity.echawdi.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Bhagyashri Lolage
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper= false)
@Entity
@Table(name="echawdi_form7b")
public class Form7B extends FormsEntity {
	
	private static final long serialVersionUID = 1L;

	// Raw attributes
	@Id
	@NotNull
	@Column(name="\"id\"", precision = 19)
	@GeneratedValue(strategy=GenerationType.AUTO, generator="echawdi_form7b_seq_gen")
    @SequenceGenerator(name="echawdi_form7b_seq_gen", sequenceName="echawdi_form7b_seq", allocationSize=1)
private Long id ;
	
	@Column(name="ccode")
	private String cCode;
	
	@Column(name="khata_no")
	private String khataNo; 
	
	@Column(name="year")
	private String year; 
	
	@Column(name="fname_person_in_possession")
	private String fnamePersonInPossession; 
	
	@Column(name="mname_person_in_possession")
	private String mnamePersonInPossession;
	
	@Column(name="lname_person_in_possession")
	private String lnamePersonInPossession;
	
	@Column(name="possession_date")
	private LocalDate possessionDate;
	
	@Column(name="designation")
	private String designation;
	
	@Column(name="order_date")
	private LocalDate orderDate;
	
	@Column(name="order_no")
	private String orderNo;
	
	
	
	
	

}

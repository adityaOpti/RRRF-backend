package com.probity.echawdi.entity;

import java.time.LocalDate;

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
@Table(name="echawdi_form6b")
public class Form6B extends PlainAudit{
	
	
	private static final long serialVersionUID = 1L;

	@NotNull
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator="echawdi_form6b_seq_gen")
    @SequenceGenerator(name="echawdi_form6b_seq_gen", sequenceName="echawdi_form6b_seq", allocationSize=1)
	@Column(name = "\"id\"", precision = 19)
private Long id;
	
	@Column(name = "order_of_tahsildar_as_fine")
    private String orderOfTahsildarAsFine;
	
	@Column(name = "receipt_no")
    private String  receiptNo;
	
	@Column(name = "receipt_date")
    private LocalDate receiptDate;
	
	@Column(name="district_code")
	private Integer districtCode;
	
	@Column(name="taluka_code")
	private Integer talukaCode;
	
	@Column(name="ccode")
	private String cCode;
	
	@Column(name="khata_No")
	public String khataNo;
	
	@Column(name="entry_village_form_6")
	private Integer entryVillageForm6;
	
	@Column(name="date_of_death")
	private LocalDate dateOfDeath ;
	
	@Column(name="reason_of_death")
	private String reasonOfDeath ;
	
	@Column(name="name_of_acquirerof_right")
	private String nameOfAcquirerOfRight ;
	
	
	

}
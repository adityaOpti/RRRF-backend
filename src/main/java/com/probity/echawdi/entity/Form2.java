/**
 * 
 */
package com.probity.echawdi.entity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.probity.echawdi.audit.FormsEntity;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author ue
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name="echawdi_form2")
public class Form2 extends FormsEntity{

	private static final long serialVersionUID = 1L;

	@Id
    @NotNull
    @Column(name = "\"id\"", precision = 19)
    @GeneratedValue(strategy=GenerationType.AUTO, generator="echawdi_form2_seq_gen")
    @SequenceGenerator(name="echawdi_form2_seq_gen", sequenceName="echawdi_form2_seq", allocationSize=1)
	  private Long id;
	
	
	@Column(name="part")
	private String part;

	@Column(name="purpose_of_use")
	private String purposeOfUse ; 
	
	@Column(name="authority_order_no")
	private String authorityOrderNo;
	
	@Column(name="authority_date")
	private Date authorityDate;
	
//	@Column(name="nature_and_terms_of_na_grant")
//	private String natureAndTermsOfNaGrant;
	
	@Column(name="occupany_price")
	private String occupanyPrice;
	
	@Column(name="period_from")
	private Date periodFrom;
	
	@Column(name="period_to")
	private Date periodTo;
	
	@Column(name="entry_number_in_taluka_form_2")
	private String entryNumberInTalukaForm2;

	@Column(name="authority")
	private String authority;
	
	@Column(name="name_of_first_occupant")
	private String nameOfFirstOccupant;
	
	
}

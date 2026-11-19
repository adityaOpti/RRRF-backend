/**
 * 
 */
package com.probity.echawdi.entity;


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
 * @author Bhagyashri Lolage
 *
 */
@Data
@EqualsAndHashCode(callSuper=true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="echawdi_form14")
public class Form14 extends FormsEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Id
    @NotNull
    @Column(name = "\"id\"", precision = 19)
    @GeneratedValue(strategy=GenerationType.AUTO, generator="echawdi_form14_seq_gen")
	@SequenceGenerator(name="echawdi_form14_seq_gen", sequenceName="echawdi_form14_seq", allocationSize=1)
private Long id;
	
	
	@Column(name="position")
	private String position;
	
	@Column(name="e_water_source_name")
	private String eWaterSourceName;
	
	@Column(name="pakka_or_kaccha")
	private String pakkaOrKaccha;
	
	@Column(name="purpose_of_use")
	private String purposeOfUse;
	
	@Column(name="governmentbody_or_private")
	private String governmentbodyOrPrivate;
	
	@Column(name="othertype")
	private String  otherType;
	
	@Column(name="is_pakka")
	private String pakka;
	
	@Column(name="is_kaccha")
	private String kaccha;
	
	@Column(name="is_thrown")
	private String thrown;
	
	
	//Added by tushar 4 sept 2024
	@Column(name="ccode")
	private String cCode;
	
	@Column(name="district_code")
	public Integer districtCode;
	
	@Column(name="taluka_code")
	public Integer talukaCode;
	
	@Column(name="remarks")
	private String remarks;
	
	//@Column(name="hissa_no")
	//private String hissaNo;
	
	
	
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
	
	//end of added by tushar

}

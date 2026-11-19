package com.probity.echawdi.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Bhagyashri Lolage
 *
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="form7")
public class Form7  {
	
  // Raw attributes	
	@Id
	@Column(name="ccode")
	private String cCode ;
	
	@Column(name="district_code")
	private Integer districtCode;
	
	@Column(name="taluka_code")
	private Integer talukaCode;
	
	@Column(name="numeric_pin")
	private Integer numericPin;
	
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
	
	@Column(name="khata_no")
	private String khataNo;
	
	@Column(name="tenure_code")
	private Integer tenureCode;
	
	@Column(name="tenure_sub_code")
	private Integer tenureSubCode;
	
	@Column(name="tenure_sub_code1")
	private Integer tenureSubCode1;
	
	@Column(name="land_usage_code")
	private Integer landUsageCode;
	
	@Column(name="land_usage_sub_code")
	private Integer landUsageSubCode;
	
	@Column(name="land_usage_text")
	private String landUsageText;
	
	@Column(name="local_name")
	private String localName;
	
	@Column(name="boundry_id")
	private String boundryId;

	@Column(name="khand_no")
	private String khandNo;
	
	@Column(name="khand_date")
	private Date khandDate;
	
	@Column(name="land_usage_mut_no")
	private Integer landUsageMutNo;
	
	@Column(name="username")
	private String userName;
	
	@Column(name="marked")
	private Character marked;
	
	@Column(name="old_mut_no")
	private Integer oldMutNo;
	
	@Column(name="mut_no")
	private Integer mutNo;
	
}

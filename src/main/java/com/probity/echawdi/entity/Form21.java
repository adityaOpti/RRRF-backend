/**
 * 
 */
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
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Rushikesh Durgude
 *
 */
@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name="echawdi_form21")
public class Form21 extends PlainAudit{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @NotNull
    @Column(name = "\"id\"", precision = 19)
    @GeneratedValue(strategy=GenerationType.AUTO, generator="echawdi_form21_seq_gen")
    @SequenceGenerator(name="echawdi_form21_seq_gen", sequenceName="echawdi_form21_seq", allocationSize=1)
	private Long id;
	
	@Column(name="district_code")
	private Integer districtCode;
	
	@Column(name="taluka_code")
	private Integer talukaCode;
	
	@Column(name="ccode")
	private String  cCode;
	

	@Column(name="village_name")
	private String villageName;

	@Column(name="diary_date")
	private LocalDate diaryDate ; 

	@Column(name="day")
	private String day;

	@Column(name="remarks")
	private String remarks;
	
	@Column(name="user_id")
	private String userId;
}

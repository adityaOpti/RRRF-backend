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
@Table(name="echawdi_form18")
public class Form18 extends PlainAudit {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Id
    @NotNull
    @Column(name = "\"id\"", precision = 19)
    @GeneratedValue(strategy=GenerationType.AUTO, generator="echawdi_form18_seq_gen")
    @SequenceGenerator(name="echawdi_form18_seq_gen", sequenceName="echawdi_form18_seq", allocationSize=1)
	private Long id;
	
	@Column(name="district_code")
	private Integer districtCode;
		
	@Column(name="taluka_code")
	private Integer talukaCode;
	
	@Column(name="ccode")
	private String  cCode;
	
	@Column(name="communication_received_from")
	private String communicationReceivedFrom;
	
	@Column(name="no_of_communication")
    private String noOfCommunication;

	@Column(name="date_of_receipt")
    private LocalDate dateOfReceipt;

	@Column(name="subject_action_taken")
	private String  subjectActionTaken;

	@Column(name="communication_dispatched_to_whom")
	private String communicationDispatchedToWhom;

	@Column(name="date_of_dispatch")
	private String  dateOfDispatch;

	@Column(name="no_in_list")
	private String  noInList;

	@Column(name="remarks")
	private String remarks;


}

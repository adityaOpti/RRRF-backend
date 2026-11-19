/**
 * 
 */
package com.probity.echawdi.entity;

import java.math.BigDecimal;
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
@EqualsAndHashCode(callSuper=true)
@AllArgsConstructor
@Entity
@Table(name="echawdi_form8c")
public class Form8C extends PlainAudit {
	
	private static final long serialVersionUID = 1L;

		// Raw attributes
		@Id
		@NotNull
		@Column(name="\"id\"", precision = 19)
		@GeneratedValue(strategy=GenerationType.AUTO, generator="echawdi_form8c_seq_gen")
	    @SequenceGenerator(name="echawdi_form8c_seq_gen", sequenceName="echawdi_form8c_seq", allocationSize=1)
		private Long id ;
		
		 @Column(name="district_code")
			private Integer districtCode;
			
		    @Column(name="taluka_code")
		    private Integer talukaCode;
			
		    @Column(name="ccode")
		    private String  cCode;
		
		@Column(name="name_of_person_from_whom_recoverable")
		private String  nameOfPersonFromWhomRecoverable;
		
		@Column(name="total_demand")
		private String totalDemand;
		
		@Column(name="arrears")
		private BigDecimal arrears;
		
		@Column(name="current_year")
		private long currentYear;
		
		@Column(name="receipt_no")
		private String receiptNo;
		
		@Column(name="receipt_date")
		private LocalDate  receiptDate;
		
		@Column(name="amount_recovered")
		private String amountRecovered;
		
		@Column(name="balance_for_recovery")
		private String balanceForRecovery;
		
		@Column(name="challan_no_credit_in_treasury")
		private String challanNoCreditInTreasury;
		
		@Column(name="challan_date")
		private LocalDate  challanDate;
		
		
		@Column(name="pin")
		private String pin;
		
		@Column(name="remarks")
		private String remarks;
		
}

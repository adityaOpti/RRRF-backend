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
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="echawdi_form19")
public class Form19 extends PlainAudit {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @NotNull
    @Column(name = "\"id\"", precision = 19)
    @GeneratedValue(strategy=GenerationType.AUTO, generator="echawdi_form19_seq_gen")
    @SequenceGenerator(name="echawdi_form19_seq_gen", sequenceName="echawdi_form19_seq", allocationSize=1)
private Long id;
	
	@Column(name="district_code")
	private Integer districtCode;
		
	 @Column(name="taluka_code")
	private Integer talukaCode;
		
	@Column(name="ccode")
	private String  cCode;
	
	@Column(name="description_of_article")
	private String descriptionOfArticle;
	
	@Column(name="authority_of_purchase")
	private String authorityOfPurchase;
	
	@Column(name="date_of_purchase")
	private LocalDate dateOfPurchase;
	
	@Column(name="number_or_quantity")
	private String numberOrQuantity;
	
	@Column(name="authority_of_voucher")
	private String authorityOfVoucher;

	@Column(name="amount_written_off")
	private String amountWrittenOff;
	
	@Column(name="amount_realized")
	private String amountRealized;
	
	@Column(name="date_of_credit_at_treasury")
	private LocalDate dateOfCreditAtTreasury;
	
	@Column(name="number")
	private String number;
	
	@Column(name="value")
	private String value;
	
	@Column(name="remarks")
	private String remarks;
	
	@Column(name="user_id")
	private String userId;
	
	@Column(name="sajja_code")
	private String sajjaCode;

}

/**
 * 
 */
package com.probity.echawdi.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

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
@EqualsAndHashCode(callSuper =false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="challan_dtl")
public class ChallanDtl extends Auditable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="\"id\"" , precision = 19)
	@GeneratedValue(strategy=GenerationType.AUTO, generator="challan_dtl_seq_gen")
    @SequenceGenerator(name="challan_dtl_seq_gen", sequenceName="challan_dtl_seq", allocationSize=1)
	private Long id ;
	
	@Column(name="challan_no")
	private String challanNo;
	
	@Column(name="challan_no_0045")
	private String challanNo0045;
	
	@Column(name="challan_date")
	private LocalDateTime challanDate;
	
	@Column(name="total_amount")
	private Double totalAmount;
	
	@Column(name="total_of_head_0029")
	private Double totalOfHead0029;
	
	@Column(name="total_of_head_0045")
	private Double totalOfHead0045;
	
	@Column(name="mode_of_payment")
	private String modeOfPayment;
	
	@Column(name="bank_name")
	private String bankName;
	
	@Column(name="bank_address")
	private String bankAddress;
	
	@Column(name="bank_receipt_number")
	private String bankReceiptNumber;
	
	@Column(name="bank_receipt_number_0045")
	private String bankReceiptNumber0045;
	
	@Column(name="bank_deposite_date")
	private LocalDateTime bankDepositeDate;
	
	@Column(name="bank_receipt_path")
	private String bankReceiptPath;
	
	@Column(name="bank_receipt_path_0045")
	private String bankReceiptPath0045;
	
	@Column(name="status")
	private String status;
	
	@Column(name="actual_file_name")
	public String actualFileName;	

	@Column(name="actual_file_name_0045")
	public String actualFileName0045;	
	
	@OneToMany(mappedBy="challanId")
    private List<LandRevenueDemand> lstReceiptDtl;
	
	@Transient
	private String path0029;
	
	@Transient
	private String path0045;
}

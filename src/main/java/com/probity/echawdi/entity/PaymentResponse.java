package com.probity.echawdi.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

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

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
@Entity
@Table(name = "payment_response")
public class PaymentResponse extends PlainAudit{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
    @NotNull
    @Column(name = "\"id\"", precision = 19)
    @GeneratedValue(strategy=GenerationType.AUTO, generator="echawdi_payment_response_seq_gen")
    @SequenceGenerator(name="echawdi_payment_response_seq_gen", sequenceName="echawdi_payment_response_seq", allocationSize=1)
    private Long id;
	
	private String DEPARTMENT_ID;
	private String GRN;
	private BigDecimal AMOUNT;
	private String BANKCODE;
	private String BANKCIN;
	private String PRN;
	private LocalDateTime TRANSCOMPLETIONDATETIME;
	private String PARTYNAME;
	private String TAXID;
	private String BANKNAME;
	private LocalDate ENTRY_DATE;
	private String STATUS;
	
}
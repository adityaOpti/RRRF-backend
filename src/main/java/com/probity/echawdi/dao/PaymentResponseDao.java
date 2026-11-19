package com.probity.echawdi.dao;

import java.math.BigDecimal;
import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponseDao {

	@JsonProperty
	private String DEPARTMENT_ID;
	@JsonProperty
	private String GRN;
	@JsonProperty
	private BigDecimal AMOUNT;
	@JsonProperty
	private String BANKCODE;
	@JsonProperty
	private String BANKCIN;
	@JsonProperty
	private String PRN;
	@JsonProperty
	private Instant TRANSCOMPLETIONDATETIME;
	@JsonProperty
	private String PARTYNAME;
	@JsonProperty
	private String TAXID;
	@JsonProperty
	private String BANKNAME;
	@JsonProperty
	private Instant ENTRY_DATE;
	@JsonProperty
	private Character STATUS;
}
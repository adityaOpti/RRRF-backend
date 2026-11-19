package com.probity.echawdi.dao;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {

	@JsonProperty
	private String SUB_SYSTEM;
	@JsonProperty
	private String DEPT_CODE; 
	@JsonProperty
	private String PAYMENT_TYPE;
	@JsonProperty
	private String TREASURY_CODE;
	@JsonProperty
	private String OFFICE_CODE;
	@JsonProperty
	private String REC_FIN_YEAR;
	@JsonProperty
	private String PERIOD;
	@JsonProperty
	private String FROM_DATE;
	@JsonProperty
	private String TO_DATE;
	@JsonProperty
	private String MAJOR_HEAD;
	@JsonProperty
	private String HOA1;
	@JsonProperty
	private String AMOUNT1;
	@JsonProperty
	private String HOA2;
	@JsonProperty
	private String AMOUNT2;
	@JsonProperty
	private String HOA3;
	@JsonProperty
	private String AMOUNT3;
	@JsonProperty
	private String HOA4;
	@JsonProperty
	private String AMOUNT4;
	@JsonProperty
	private String HOA5;
	@JsonProperty
	private String AMOUNT5;
	@JsonProperty
	private String HOA6;
	@JsonProperty
	private String AMOUNT6;
	@JsonProperty
	private String HOA7;
	@JsonProperty
	private String AMOUNT7;
	@JsonProperty
	private String HOA8;
	@JsonProperty
	private String AMOUNT8;
	@JsonProperty
	private String HOA9;
	@JsonProperty
	private String AMOUNT9;
	@JsonProperty
	private String CHALLAN_AMOUNT;
	@JsonProperty
	private String TAX_ID;
	@JsonProperty
	private String PAN_NO;
	@JsonProperty
	private String PARTY_NAME;
	@JsonProperty
	private String ADDRESS1;
	@JsonProperty
	private String ADDRESS2;
	@JsonProperty
	private String ADDRESS3;
	@JsonProperty
	private String PIN_NO;
	@JsonProperty
	private String MOBILE_NO;
	@JsonProperty
	private String DEPARTMENT_ID;
	@JsonProperty
	private String FORM_ID;
	@JsonProperty
	private String REMARKS;
	//	public PaymentRequest(String sUB_SYSTEM, String dEPT_CODE, String pAYMENT_TYPE, String tREASURY_CODE,
	//			String oFFICE_CODE, String rEC_FIN_YEAR, Character pERIOD, String fROM_DATE, String tO_DATE,
	//			String mAJOR_HEAD, String hOA1, String aMOUNT1, String hOA2, String aMOUNT2, String hOA3, String aMOUNT3,
	//			String hOA4, String aMOUNT4, String hOA5, String aMOUNT5, String hOA6, String aMOUNT6, String hOA7,
	//			String aMOUNT7, String hOA8, String aMOUNT8, String hOA9, String aMOUNT9, String cHALLAN_AMOUNT,
	//			String tAX_ID, String pAN_NO, String pARTY_NAME, String aDDRESS1, String aDDRESS2, String aDDRESS3,
	//			Integer pIN_NO, String mOBILE_NO, String dEPARTMENT_ID, String fORM_ID, String rEMARKS) {
	//		super();
	//		SUB_SYSTEM = sUB_SYSTEM;
	//		DEPT_CODE = dEPT_CODE;
	//		PAYMENT_TYPE = pAYMENT_TYPE;
	//		TREASURY_CODE = tREASURY_CODE;
	//		OFFICE_CODE = oFFICE_CODE;
	//		REC_FIN_YEAR = rEC_FIN_YEAR;
	//		PERIOD = pERIOD;
	//		FROM_DATE = fROM_DATE;
	//		TO_DATE = tO_DATE;
	//		MAJOR_HEAD = mAJOR_HEAD;
	//		HOA1 = hOA1;
	//		AMOUNT1 = aMOUNT1;
	//		HOA2 = hOA2;
	//		AMOUNT2 = aMOUNT2;
	//		HOA3 = hOA3;
	//		AMOUNT3 = aMOUNT3;
	//		HOA4 = hOA4;
	//		AMOUNT4 = aMOUNT4;
	//		HOA5 = hOA5;
	//		AMOUNT5 = aMOUNT5;
	//		HOA6 = hOA6;
	//		AMOUNT6 = aMOUNT6;
	//		HOA7 = hOA7;
	//		AMOUNT7 = aMOUNT7;
	//		HOA8 = hOA8;
	//		AMOUNT8 = aMOUNT8;
	//		HOA9 = hOA9;
	//		AMOUNT9 = aMOUNT9;
	//		CHALLAN_AMOUNT = cHALLAN_AMOUNT;
	//		TAX_ID = tAX_ID;
	//		PAN_NO = pAN_NO;
	//		PARTY_NAME = pARTY_NAME;
	//		ADDRESS1 = aDDRESS1;
	//		ADDRESS2 = aDDRESS2;
	//		ADDRESS3 = aDDRESS3;
	//		PIN_NO = pIN_NO;
	//		MOBILE_NO = mOBILE_NO;
	//		DEPARTMENT_ID = dEPARTMENT_ID;
	//		FORM_ID = fORM_ID;
	//		REMARKS = rEMARKS;
	//	}
}
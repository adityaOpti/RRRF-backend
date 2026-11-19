package com.probity.echawdi.dao;
/**
 * 
 */
import java.sql.Date;

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
@AllArgsConstructor
@NoArgsConstructor
public class Form7Dao {
	
	    public Long id;
		public String cCode ;
		private Integer districtCode;
		private Integer talukaCode;
		public Integer numericPin;
		public String pin;
		public String pin1;
		public String pin2;
		public String pin3;
		public String pin4;
		public String pin5;
		public String pin6;
		public String pin7;
		public String pin8;
		public String khataNo;
		public Integer tenureCode;
		public Integer tenureSubCode;
		public Integer tenureSubCode1;
		public Integer landUsageCode;
		public Integer landUsageSubCode;
		public String landUsageText;
		public String localName;
		public String boundryId;
		public String khandNo;
		public Date khandDate;
		public Integer landUsageMutNo;
		public String userName;
		public Character marked;
		public Integer oldMutNo;
		public Integer mutNo;
		public String netCultiAreaH; //form7area //BigDecimal
		public String naAreaH;  //form7area 
		public String naAssessment;  //form7area 
		public String potkharabaAH;  //form7area 
		public String potkharabaBH;  //form7area 
		public String tenureName;   //tenure
		public String fullName;//form7
		public String assessment;  //form7area 
		public String hissaNo;  //pin1 to 8
		public String totalAreaH;//form7area
		public String totalAreaR;//form7area
		public String totalAreaSqm;//form7area
		public String getcCode() {
			return cCode;
		}
}
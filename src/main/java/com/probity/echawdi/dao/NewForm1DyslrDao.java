package com.probity.echawdi.dao;

import java.time.LocalDate;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class NewForm1DyslrDao {
	public Long id;
    
//  @NotNull(message ="{districtCode.notempty}")
  public Integer districtCode;
  
//  @NotNull(message ="{talukaCode.notempty}")
  public Integer talukaCode;
  
  
  @NotNull(message ="{cCode.notempty}")
  public String cCode;
  
  public String khataNo;
  
  @NotNull(message ="{pin.notempty}")
  public String pin;
  
  public String pin1;
  public String pin2;
  public String pin3;
  public String pin4;
  public String pin5;
  public String pin6;
  public String pin7;
  public String pin8;
  
  public String jirayatArea;
  public String jirayatAssessment;
  public String bagayatArea;
  public String bagayatAssessment;
  public String tariArea;
  public String tariAssessment;
  public String otherArea;
  public String otherAssessment;
  
  
//  @NotNull(message ="{publicRightsOfWayAndEasements.notempty}")
//  @NotEmpty(message ="{publicRightsOfWayAndEasements.notempty}")
  public String publicRightsOfWayAndEasements;
  
//  @NotNull(message ="{particularsOfAlteration.notempty}")
  public String particularsOfAlteration;
  
  public String designation;
  
  public String designationOfAuthority;
  
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  public LocalDate orderDate;
  
//  @NotNull(message ="{orderNo.notempty}")
  public String orderNo;
  
//  @NotNull(message ="{remarks.notempty}")
  public String remarks;
  
//  @NotNull(message="{hissaNo.notempty}")
	  public String hissaNo;
  
  public String netCultiAreaH; //form7area //BigDecimal
  
	  public String assessment;  //form7area 
  
	  public String naAreaH;  //form7area 
  
	  public String naAssessment;  //form7area 
  
	  public String potkharabaAH;  //form7area 
  
	  public String potkharabaBH;  //form7area
  
	  public String tenureName;   //tenure
  
	  public String fullName;//form7
  
	  public String totalAreaH;//form7
	  
	  public String orderDt;
	  
	  public String mutNo;
	  
	  public String totalAreaSqm;
	  
	  public String uncultivableH;
	  
	  public String uncultivableR;
	  
	  private String cultivableAreaInt;
	  
	  public Date objectionDate;
	  public String  objectionRemark;
	  public String firstName;
	  public String middelName;
	  public String lastName;
	  public String surveyHissaNo;
	  
	  public String form7PotType;
	  public String form1PotType;
	  public String potkharabaType;
	  
	  public String 
	  form1DyslrTotalarea,form7Totalarea,
	  form1DyslrPotKharaba,form7PotKharaba,
	  form1DyslrAreaNetCultiArea,form7AreaNetCultiArea,
	  form1DyslrAssessment,form7Assessment,
	  form1DyslrTenureCode,form7TenureCode,form1DyslrNetCultiArea,form7NetCultiArea;
}

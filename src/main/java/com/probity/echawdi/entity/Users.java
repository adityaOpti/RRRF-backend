package com.probity.echawdi.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ashish Dahale
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users {

	  private String servarthId;

	  private String password;
	  
	  private String fullName;
	  
	  private String logIn;
	  
	  private String desgCode;
	  
	  private String desg;
	  
	  private Integer userLevel;
	  
	  private Character userStatus;
	  
	  private String districtCode;
	  
	  private String talukaCode;
	  
	  private String orderNo;
	  
	  private String remarks;
	  
	  private String mobileNo;

	  private String marathiName;
	  
	  private String transcId;
	  
	  private String emailId;

	  private String talukaName;
	  
	  private String districtName;
	  
	  private String roleName;
	  
	  private List<String> sajjaCodes;
}
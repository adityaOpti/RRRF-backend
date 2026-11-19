package com.probity.echawdi.dao;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Ashish Dahale
 *
 */

@Data
@EqualsAndHashCode(callSuper =false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsersDao  {
	
	  public String servarthId;
//	  @JsonIgnore
	  public String password;
	  public String fullName;
	  public String desgCode;
	  public String desg;
	  public String reporting;
	  public Integer userLevel;
	  public Character userStatus;
	  public String districtCode;
	  public String districtName;
	  public String talukaCode;
	  public String talukaName;
	  public String orderNo;
	  public String remarks;
	  public String mobileNo;
	  public Date createDate;
	  public Date approveDate;
	  public Integer userType;
	  public Date orderDate;
	  public String marathiName;
	  public String ip;
	  public Date loginTime;
	  public String transcId;
	  public String emailId;
	  public String securityPassword;
	  public String securityPasswordRole;
	  public Boolean updateRole;
	  public String role_id;
	  public String autoLogin;
	  public String username;
	  public String type;
	  public String currentAuthority;
	  public String status;
}

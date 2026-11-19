package com.probity.echawdi.payload;
import java.util.Date;
import java.util.List;

import com.probity.echawdi.dao.ChallanHeadsDao;
import com.probity.echawdi.dao.RevenueYearDao;
import com.probity.echawdi.dao.VillageDao;
import com.probity.echawdi.dao.VillageResponseLoginDTO;
import com.probity.echawdi.entity.Nirank;
import com.probity.echawdi.entity.Taluka;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * @author Bhagyashri Lolage
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class JwtResponse {
	//private String token;
	private String accessToken;
	private String encodedKey;
	private String servarthId;
	private String fullName;
	private String emailId;
	private String districtCode;
	private String districtName;
	private String talukaCode;
	private String talukaName;
	private String marathiName;
	private String desg;
	private Date generatedTime;
	private Date expiryTime;
	private List<String> roles;
	private String echDbName;
	private String echSchemaName;
	private String mhrDbName;
	private String mhrSchemaName;
	private String echHost;
	private String mhrHost;
	private List<VillageDao> villages;
	private List<VillageResponseLoginDTO> villageForInspection;
	private List<RevenueYearDao> revenueYear;
	private List<Nirank> niranks;
	private List<ChallanHeadsDao> challanHeads;
	
	private  List<Taluka> talukaLst;
	
	  
	public JwtResponse(String accessToken, String encodedKey, Date generatedTime,Date expiryTime) {
		 this.accessToken = accessToken;
		 this.encodedKey = encodedKey;
		 this.generatedTime=generatedTime;
		 this.expiryTime=expiryTime;
	}


	public JwtResponse(String accessToken, String encodedKey, String servarthId, String fullName, String emailId,
			Date generatedTime, Date expiryTime,String marathiName) {
		
		this.accessToken = accessToken;
		this.encodedKey = encodedKey;
		this.servarthId = servarthId;
		this.fullName = fullName;
		this.emailId = emailId;
		this.generatedTime = generatedTime;
		this.expiryTime = expiryTime;
		this.marathiName=marathiName;
		
	}
	

	public JwtResponse(String accessToken, String encodedKey, String servarthId, String fullName, String emailId,
			Date generatedTime, Date expiryTime,String marathiName, List<String> roles,List<Nirank> niranks) {
		super();
		this.accessToken = accessToken;
		this.encodedKey = encodedKey;
		this.servarthId = servarthId;
		this.fullName = fullName;
		this.emailId = emailId;
		this.generatedTime = generatedTime;
		this.expiryTime = expiryTime;
		this.marathiName=marathiName;
		this.roles = roles;
		this.niranks=niranks;
	}


	public JwtResponse(String accessToken, String encodedKey, String servarthId, String fullName, String emailId, String districtCode,
			String districtName, String talukaCode, String talukaName, String marathiName, String desg,
			Date generatedTime, Date expiryTime, List<String> roles, String echDbName, String echSchemaName,
			String mhrDbName, String mhrSchemaName, String echHost, String mhrHost, List<VillageDao> villages,
			List<VillageResponseLoginDTO> villageForInspection,
			List<RevenueYearDao> revenueYear) {
		super();
		this.accessToken = accessToken;
		this.encodedKey = encodedKey;
		this.servarthId = servarthId;
		this.fullName = fullName;
		this.emailId = emailId;
		this.districtCode = districtCode;
		this.districtName = districtName;
		this.talukaCode = talukaCode;
		this.talukaName = talukaName;
		this.marathiName = marathiName;
		this.desg = desg;
		this.generatedTime = generatedTime;
		this.expiryTime = expiryTime;
		this.roles = roles;
		this.echDbName = echDbName;
		this.echSchemaName = echSchemaName;
		this.mhrDbName = mhrDbName;
		this.mhrSchemaName = mhrSchemaName;
		this.echHost = echHost;
		this.mhrHost = mhrHost;
		this.villages = villages;
		this.revenueYear = revenueYear;
		this.villageForInspection=villageForInspection;
		
	}
	
	public JwtResponse(String accessToken, String encodedKey, String servarthId, String fullName, String emailId, String districtCode,
			String districtName, String talukaCode, String talukaName, String marathiName, String desg,
			Date generatedTime, Date expiryTime, List<String> roles, String echDbName, String echSchemaName,
			String mhrDbName, String mhrSchemaName, String echHost, String mhrHost,
			List<VillageResponseLoginDTO> villageForInspection,
			List<RevenueYearDao> revenueYear) {
		super();
		this.accessToken = accessToken;
		this.encodedKey = encodedKey;
		this.servarthId = servarthId;
		this.fullName = fullName;
		this.emailId = emailId;
		this.districtCode = districtCode;
		this.districtName = districtName;
		this.talukaCode = talukaCode;
		this.talukaName = talukaName;
		this.marathiName = marathiName;
		this.desg = desg;
		this.generatedTime = generatedTime;
		this.expiryTime = expiryTime;
		this.roles = roles;
		this.echDbName = echDbName;
		this.echSchemaName = echSchemaName;
		this.mhrDbName = mhrDbName;
		this.mhrSchemaName = mhrSchemaName;
		this.echHost = echHost;
		this.mhrHost = mhrHost;
		this.revenueYear = revenueYear;
		this.villageForInspection=villageForInspection;
		
	}
	
	//for collector login
	public JwtResponse(String accessToken,String servarthId, String fullName, String emailId, String districtCode,
			String districtName, String talukaCode, String talukaName, String marathiName, String desg,
			Date generatedTime, Date expiryTime, List<String> roles, String echDbName, String echSchemaName,
			String mhrDbName, String mhrSchemaName, String echHost, String mhrHost, List<VillageDao> villages,
			List<VillageResponseLoginDTO> villageForInspection,
			List<RevenueYearDao> revenueYear,List<Taluka> talukaLst) {
		super();
		this.accessToken = accessToken;
		this.servarthId = servarthId;
		this.fullName = fullName;
		this.emailId = emailId;
		this.districtCode = districtCode;
		this.districtName = districtName;
		this.talukaCode = talukaCode;
		this.talukaName = talukaName;
		this.marathiName = marathiName;
		this.desg = desg;
		this.generatedTime = generatedTime;
		this.expiryTime = expiryTime;
		this.roles = roles;
		this.echDbName = echDbName;
		this.echSchemaName = echSchemaName;
		this.mhrDbName = mhrDbName;
		this.mhrSchemaName = mhrSchemaName;
		this.echHost = echHost;
		this.mhrHost = mhrHost;
		this.villages = villages;
		this.revenueYear = revenueYear;
		this.villageForInspection=villageForInspection;
		this.talukaLst=talukaLst;
		
	}

	public JwtResponse(
	        String accessToken,
	        String encodedKey,
	        String servarthId,
	        String fullName,
	        String emailId,
	        String districtCode,
	        String districtName,
	        String talukaCode,
	        String talukaName,
	        String marathiName,
	        String desg,
	        Date generatedTime,
	        Date expiryTime,
	        List<String> roles,
	        String echDbName,
	        String echSchemaName,
	        String mhrDbName,
	        String mhrSchemaName,
	        String echHost,
	        String mhrHost,
	        List<VillageDao> villages,
	        List<VillageResponseLoginDTO> villageForInspection,
	        List<RevenueYearDao> revenueYear,
	        List<Nirank> niranks,
	        List<ChallanHeadsDao> challanHeads) {

	    this.accessToken = accessToken;
	    this.encodedKey = encodedKey;
	    this.servarthId = servarthId;
	    this.fullName = fullName;
	    this.emailId = emailId;
	    this.districtCode = districtCode;
	    this.districtName = districtName;
	    this.talukaCode = talukaCode;
	    this.talukaName = talukaName;
	    this.marathiName = marathiName;
	    this.desg = desg;
	    this.generatedTime = generatedTime;
	    this.expiryTime = expiryTime;
	    this.roles = roles;
	    this.echDbName = echDbName;
	    this.echSchemaName = echSchemaName;
	    this.mhrDbName = mhrDbName;
	    this.mhrSchemaName = mhrSchemaName;
	    this.echHost = echHost;
	    this.mhrHost = mhrHost;
	    this.villages = villages;
	    this.villageForInspection = villageForInspection;
	    this.revenueYear = revenueYear;
	    this.niranks = niranks;
	    this.challanHeads = challanHeads;
	}
	
	
	
	
	
}





/*package com.probity.echawdi.payload;
import java.util.Date;
import java.util.List;

import com.probity.echawdi.dao.ChallanHeadsDao;
import com.probity.echawdi.dao.RevenueYearDao;
import com.probity.echawdi.dao.VillageDao;
import com.probity.echawdi.entity.Nirank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * @author Bhagyashri Lolage
 *
 */
/*@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class JwtResponse {
	private String token;
	private String servarthId;
	private String fullName;
	private String emailId;
	private String districtCode;
	private String districtName;
	private String talukaCode;
	private String talukaName;
	private String marathiName;
	private String desg;
	private Date generatedTime;
	private Date expiryTime;
	private List<String> roles;
	private String echDbName;
	private String echSchemaName;
	private String mhrDbName;
	private String mhrSchemaName;
	private String echHost;
	private String mhrHost;
	private List<VillageDao> villages;
	private List<RevenueYearDao> revenueYear;
	private List<Nirank> niranks;
	private List<ChallanHeadsDao> challanHeads;
	
	
	
	  
	public JwtResponse(String token, Date generatedTime,Date expiryTime) {
		 this.token = token;
		 this.generatedTime=generatedTime;
		 this.expiryTime=expiryTime;
	}



	public JwtResponse(String token, String servarthId, String fullName, String emailId,
			Date generatedTime, Date expiryTime,String marathiName, List<String> roles,List<Nirank> niranks) {
		super();
		this.token = token;
		this.servarthId = servarthId;
		this.fullName = fullName;
		this.emailId = emailId;
		this.generatedTime = generatedTime;
		this.expiryTime = expiryTime;
		this.marathiName=marathiName;
		this.roles = roles;
		this.niranks=niranks;
	}
	
	
	
	
}*/
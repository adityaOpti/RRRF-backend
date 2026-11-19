package com.probity.echawdi.repository;



import java.util.List;

public interface VillageRepositoryCustom {

	  List<Object[]> getVillageListByTalukaAndDistrict(
	            String host,
	            String dbName,
	            String schemaName,
	            String user,
	            String password,
	            String username,
	            String talukaCode,
	            String districtCode);
	  
	  
	
}
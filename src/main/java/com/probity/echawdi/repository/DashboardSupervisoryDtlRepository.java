package com.probity.echawdi.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.probity.echawdi.entity.DashboardSupervisoryDtl;

@Repository
public interface DashboardSupervisoryDtlRepository extends JpaRepository<DashboardSupervisoryDtl, String>{

	@Query(value="select * from e_lgd_mapper where  is_challan_generated = 'Y' and ccode = ?1 "
			+ " "
			,nativeQuery=true)
	DashboardSupervisoryDtl findBycCode();

	
	boolean existsBycCode(String cCode);


	@Query(value="select    "
			+ "distinct ccode,  "
			+ "district_code,    "
			+ "district_name_marathi,    "
			+ "taluka_code,    "
			+ "taluka_name_marathi,    "
			+ "lgd_village_name,    "
			+ "division_code,    "
			+ "(select count(distinct taluka_code) from e_lgd_mapper where is_challan_generated='Y' and district_code=d.district_code) totaloftalukas"
			+ " from e_lgd_mapper d where is_challan_generated='Y' and district_code=?1 order by taluka_code "
			+ " ",nativeQuery=true)
	List<Object[]> getOnlineRecoveryVillageCount(String districtCode);
	
}
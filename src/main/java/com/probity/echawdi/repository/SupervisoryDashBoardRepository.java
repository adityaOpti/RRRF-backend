package com.probity.echawdi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.probity.echawdi.entity.DashboardSupervisoryDtl;
@Repository
public interface SupervisoryDashBoardRepository extends JpaRepository<DashboardSupervisoryDtl, Long> {

	@Query(value="  select  "
			+ "	   count( distinct district_code) totalDistricts,  "
			+ "	   count( distinct taluka_code) totalTalukas,  "
			+ "	   count( distinct ccode) totalVillages  "
			+ "	  from dashboard_supervisory_dtl where division_code=?1 "
			+ "",nativeQuery = true)
	
	List<Object[]> getByDivisionCode(String disionCode);
	
	@Query(value="select distinct district_code,"
			+ "district_name, "
			+ "count( distinct district_code) totalDistricts,"
			+ " count( distinct taluka_code) totalTalukas,"
			+ " count( distinct ccode) totalVillages "
			+ "from dashboard_supervisory_dtl where division_code=?1 "
			+ "group by district_code,district_name"
			+ "",nativeQuery = true)
	List<Object[]> getByDivisionByDistrictCode(String disionCode);
	
}

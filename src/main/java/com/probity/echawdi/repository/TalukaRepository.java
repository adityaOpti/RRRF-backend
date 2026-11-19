/**
 * 
 */
package com.probity.echawdi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.probity.echawdi.entity.Taluka;

/**
 * @author Ashish Dahale
 *
 */

@Repository
public interface TalukaRepository extends JpaRepository<Taluka, Long> {

	@Query(value="SELECT distinct on(taluka_code) T.district_code,T.taluka_code,T.taluka_name FROM e_taluka_census T WHERE DISTRICT_CODE=?1"
			+ "",nativeQuery = true)
	List<Object[]> findAllByDistrictCode(String districtCode);
	
	Taluka findByDistrictCodeAndTalukaCode(String districtCode,String talukaCode);

	@Query(value="select  distinct district_code,district_name,taluka_code,taluka_name,ccode,village_name from     "
			+ "public.dblink (      "
//			+ "'dbname='||'echmst'||' host='||'localhost'||' port=5432 user='||'postgres'|| ' password='||'root'  ,     "
			+ "'dbname='||'echmst'||' host='||'localhost'||' port=5432 user='||'postgres'|| ' password='||'@$h!$h_1061'  ,   "
			+ "'select district_code,district_name,taluka_code,taluka_name,ccode,village_name,division_code  from '||'public'||'.dashboard_supervisory_dtl ')     "
			+ "AS(district_code text,district_name text,taluka_code text,taluka_name text,ccode text,village_name text,division_code text )   "
			+ "where trim(division_code)= ?1   and trim(district_code)= ?2 "
			+ "group by district_code,district_name,taluka_code,taluka_name,ccode,village_name,division_code " 
			+ "",nativeQuery = true)
	List<Object[]> getDropDownsTaluka(String divisionCode,String districtCode);
	
	@Query(value="select distinct taluka_code,taluka_name,district_code from   "
			+ "public.dblink (    "
//			+ "'dbname='||'echmst'||' host='||'localhost'||' port=5432 user='||'postgres'|| ' password='||'root'  ,   "
			+ "'dbname='||'echmst'||' host='||'localhost'||' port=5432 user='||'postgres'|| ' password='||'@$h!$h_1061'  ,   "
			+ "'select taluka_code,taluka_name,district_code  from '||'public'||'.dashboard_supervisory_dtl ')   "
			+ "AS(taluka_code text,taluka_name text,district_code text ) "
			+ "where trim(district_code)= ?1 "
			+ "group by taluka_code,taluka_name,district_code " 
			+ "",nativeQuery = true)
	List<Object[]> getDropDownsTalukaDtl(String districtCode);
	
	//added 14 oct 2026
			@Query(value="select distinct taluka_code,taluka_name_marathi,district_code,count(taluka_code) from e_lgd_mapper    "
					+ "where trim(district_code)= ?1 "
					+ "group by taluka_code,taluka_name_marathi,district_code " 
					+ "",nativeQuery = true)
			List<Object[]> findByDistrictCode(String districtCode);
	  
}

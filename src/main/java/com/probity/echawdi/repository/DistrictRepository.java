/**
 * 
 */
package com.probity.echawdi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.probity.echawdi.entity.District;

/**
 * @author Ashish Dahale
 *
 */

@Repository
public interface DistrictRepository extends JpaRepository<District, Long>{
	
	District findByDistrictCode(String districtCode); 
	
	@Query(value="select * from e_challan_heads where division_code=?1",nativeQuery=true)
	List<Object[]> getByDivisionCode(String divisionCode);

	@Query(value="select distinct district_code,district_name,taluka_code,taluka_name,ccode,village_name from     "
			+ "public.dblink (      "
//			+ "'dbname='||'echmst'||' host='||'localhost'||' port=5432 user='||'postgres'|| ' password='||'root'  ,     "
			+ "'dbname='||'echmst'||' host='||'localhost'||' port=5432 user='||'postgres'|| ' password='||'@$h!$h_1061'  ,   "
			+ "'select district_code,district_name,division_code,taluka_code,taluka_name,ccode,village_name from '||'public'||'.dashboard_supervisory_dtl ')     "
			+ "AS(district_code text,district_name text,division_code text,taluka_code text,taluka_name text,ccode text,village_name text)   "
			+ "where division_code= ?1   "
			+ "group by district_name,district_code,taluka_name,taluka_code,ccode,village_name "
			+ "",nativeQuery = true)
	List<Object[]> getDropDownsDistrict(String divisionCode);
	
	
	@Query(value="select distinct district_code,district_name from   "
			+ "public.dblink (    "
//			+ "'dbname='||'echmst'||' host='||'localhost'||' port=5432 user='||'postgres'|| ' password='||'root'  ,   "
			+ "'dbname='||'echmst'||' host='||'localhost'||' port=5432 user='||'postgres'|| ' password='||'@$h!$h_1061'  ,   "
			+ "'select district_code,district_name,division_code,taluka_code from '||'public'||'.dashboard_supervisory_dtl ')   "
			+ "AS(district_code text,district_name text,division_code text,taluka_code text) "
			+ "where division_code= ?1 "
			+ "group by district_name,district_code "
			+ "",nativeQuery = true)
	List<Object[]> getDropDownsDistrictDtl(String divisionCode);
	
	@Query(value="select distinct district_code,district_name_marathi,count(distinct district_code) from  e_lgd_mapper   "
			+ "where division_code= ?1  "
			+ "group by district_name_marathi,district_code "
			+ "",nativeQuery = true)
	List<Object[]> findByDivisionCode(String divisionCode);
}
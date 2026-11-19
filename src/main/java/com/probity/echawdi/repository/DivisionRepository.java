/**
 * 
 */
package com.probity.echawdi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.probity.echawdi.entity.Division;

/**
 * @author Ashish Dahale
 *
 */

@Repository
public interface DivisionRepository extends JpaRepository<Division, Long>{
	
	@Query(value="select division_code,division_name from e_division_census  "
	+ "" , nativeQuery=true)
	List<Object[]> getDivision();


	@Query(value="select division_code,division_name from   "
			+ "public.dblink (    "
//			+ "'dbname='||'echmst'||' host='||'localhost'||' port=5432 user='||'postgres'|| ' password='||'root'  ,   "
			+ "'dbname='||'echmst'||' host='||'localhost'||' port=5432 user='||'postgres'|| ' password='||'@$h!$h_1061'   ,   "
			+ "'select division_code,division_name from '||'public'||'.e_division_census ')   "
			+ "AS(division_code text,division_name text) "
			+ "",nativeQuery = true )
	List<Object[]> findAllDivision();
	
	
}
package com.probity.echawdi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.probity.echawdi.entity.Nirank;

@Repository
public interface NirankRepository extends JpaRepository<Nirank, Long>{

	
	List<Nirank> findBycCodeIn(List<String> cCodes);

	boolean existsBycCode(String cCode);

     Nirank findBycCode(String cCode);
     //old method
/*
     @Query(value="SELECT  ccode, form_id, is_nirank"
 			+ ", is_completed,   district_code,  taluka_code    "
 			+ " FROM "
 			+" public.dblink('dbname='|| 'echmst' ||' host='|| '10.10.248.108' ||' port=5432 user='|| 'postgres' ||' password='|| 'jduri%D^7f9gf',  "
 			+ " 'SELECT ccode,form_id,is_nirank, is_completed,district_code,taluka_code  "
 	        + " FROM '  || 'public' ||'.nirank_and_completedwork_forms  "
 	        + " where "
 	        + " trim(ccode)='|| ?1 ||':: text "
 	        + "AND revenue_year = ''?2''"
 	        + "AND active_flag = ''Y'' ') "
 	        + "AS data (ccode text,form_id text, is_nirank character, is_completed character ,district_code text ,taluka_code text  )   "
 			+ " ",nativeQuery = true)
 		List<Object[]> findBycCodeAndRevenueYearAndActiveFlag(String cCode, String revenueYear, char c);
*/

	List<Nirank> findByDistrictCodeAndTalukaCode(String districtCode, String talukaCode);
	
	//-----List of Nirank for alert message for 4 Forms
			@Query(value="SELECT  ccode, lgd_code, form_id, is_nirank"
			+ ", is_completed, active_flag,   district_code,  taluka_code,  village_code, village_name    "
			+ " FROM "
		//	+" public.dblink('dbname='|| 'echmst' ||' host='|| '10.10.248.108' ||' port=5432 user='|| 'postgres' ||' password='|| 'jduri%D^7f9gf',  "
			+" public.dblink('dbname='|| 'echmst' ||' host='|| '10.48.65.110' ||' port=5432 user='|| 'postgres' ||' password='|| 'jduri%D^7f9gf',  "
			+ " 'SELECT n.ccode,e.lgd_code,n.form_id,n.is_nirank,  n.is_completed,n.active_flag,n.district_code,n.taluka_code,e.ccode,e.lgd_village_name  "
	        + " FROM '  || 'public' ||'.nirank_and_completedwork_forms n "
	        + "  left  join '  || 'public' ||'.e_lgd_mapper e on n.taluka_code=e.taluka_code and e.ccode=n.ccode  "
	      //  + "  inner join '  || 'public' ||'.m_village_master vm on vm.village_name=m.village_name and vm.ccode=m.ccode  "
	        + " where "
	        + " trim(n.ccode)='|| ?1 ||':: text  ') "
	        + "AS data (ccode text, lgd_code integer,form_id text, is_nirank character, is_completed character, active_flag character ,district_code integer,taluka_code text,village_code text,village_name text  )   "
			//+ "public.nirank_and_completedwork_forms n  "
			//+ "inner join m_village_mapper  m "
			//+ "on n.taluka_code=m.taluka_code "
			//+ "and n.district_code=m.district_code "
			//+ "inner join m_village_master  vm "
			//+ "on vm.village_name=m.village_name "
			//+ " and vm.ccode=m.ccode "
			//+ " where n.ccode=?1 "
			+ " ",nativeQuery = true)
			List <Object []> findingNirank(String cCode);
			
			
			
			//-----List of Nirank for alert message for 4 Forms
			@Query(value="SELECT  ccode, lgd_code, form_id, is_nirank"
			+ ", is_completed, active_flag,   district_code,  taluka_code,  village_code, village_name   "
			+ " FROM "
		//	+" public.dblink('dbname='|| 'echmst' ||' host='|| '10.10.248.108' ||' port=5432 user='|| 'postgres' ||' password='|| 'jduri%D^7f9gf',  "
			+" public.dblink('dbname='|| 'echmst' ||' host='|| '10.48.65.110' ||' port=5432 user='|| 'postgres' ||' password='|| 'jduri%D^7f9gf',  "
			+ " 'SELECT n.ccode,e.lgd_code,n.form_id,n.is_nirank,  n.is_completed,n.active_flag,n.district_code,n.taluka_code,e.ccode,e.lgd_village_name , n.revenue_year "
	        + " FROM '  || 'public' ||'.nirank_and_completedwork_forms n "
	        + "  left  join '  || 'public' ||'.e_lgd_mapper e on n.taluka_code=e.taluka_code and e.ccode=n.ccode  "
	      //  + "  inner join '  || 'public' ||'.m_village_master vm on vm.village_name=m.village_name and vm.ccode=m.ccode  "
	        + " where "
	        + " trim(n.ccode)='|| ?1 ||':: text  and revenue_year=''2025-26'' ') "
	        + "AS data (ccode text, lgd_code integer,form_id text, is_nirank character, is_completed character, active_flag character ,district_code integer,taluka_code text,village_code text,village_name text , revenue_year text )   "
			//+ "public.nirank_and_completedwork_forms n  "
			//+ "inner join m_village_mapper  m "
			//+ "on n.taluka_code=m.taluka_code "
			//+ "and n.district_code=m.district_code "
			//+ "inner join m_village_master  vm "
			//+ "on vm.village_name=m.village_name "
			//+ " and vm.ccode=m.ccode "
			//+ " where n.ccode=?1 "
			+ " ",nativeQuery = true)
			List <Object []> findingNirankForKhataMerge(String cCode);
			
			
			
			@Query(value =
			        "SELECT " +
			        "COUNT(CASE WHEN is_nirank = 'Y' THEN 1 END) AS nirankYesCount, " +
			        "COUNT(CASE WHEN is_completed = 'Y' THEN 1 END) AS completedYesCount, " +
			        "COUNT(CASE WHEN is_completed = 'N' AND is_nirank = 'N' THEN 1 END) AS completedNoNirankNoCount " +
			        "FROM public.dblink(" +
			    //    "    'dbname='|| 'echmst' ||' host='|| '10.10.248.108' ||' port=5432 user='|| 'postgres' ||' password='|| 'jduri%D^7f9gf', " +
			        "    'dbname='|| 'echmst' ||' host='|| '10.48.65.110' ||' port=5432 user='|| 'postgres' ||' password='|| 'jduri%D^7f9gf', " +
			        "    'SELECT is_nirank, is_completed FROM '|| 'public' ||'.nirank_and_completedwork_forms'" +
			        ") AS t(is_nirank CHARACTER(1), is_completed CHARACTER(1))",
			        nativeQuery = true)
			Object getNirankCompletedCounts( );
			
			
			//change 02/01/2025
			@Query(value="SELECT  ccode, form_id, is_nirank"
 	 			+ ", is_completed,   district_code,  taluka_code    "
 	 			+ " FROM "
 	 			+" public.dblink('dbname='|| 'echmst' ||' host='|| 'localhost' ||' port=5432 user='|| 'postgres' ||' password='|| 'root',  "
 	 			+ " 'SELECT ccode,form_id,is_nirank, is_completed,district_code,taluka_code  "
 	 	        + " FROM '  || 'public' ||'.nirank_and_completedwork_forms  "
 	 	        + " where "
 	 	        +" trim(ccode) = ''' || ?1 || ''' " 
 	 	        +" AND revenue_year = ''' || ?2 || ''' "
 	 	        + "AND active_flag = ''Y'' ') "

	 	        + "AS data (ccode text,form_id text, is_nirank character, is_completed character ,district_code text ,taluka_code text  )   "
 	 			+ " ",nativeQuery = true)
 	 		List<Object[]> findBycCodeAndRevenueYearAndActiveFlag(String cCode, String revenueYear, char activeFlag);

}
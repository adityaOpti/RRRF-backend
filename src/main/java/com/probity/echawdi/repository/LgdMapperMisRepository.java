

package com.probity.echawdi.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.probity.echawdi.entity.LgdMapperMis;

@Repository
public interface LgdMapperMisRepository extends JpaRepository<LgdMapperMis, String>{
	List<LgdMapperMis> findBycCodeInAndIsLoggedInIsNull(List<String> cCode);

	List<LgdMapperMis> findByDistrictCodeAndTalukaCode(String districtCode, String talukaCode);
	
	List<LgdMapperMis> findByIsLoggedInIsNull();

	@Query(value="select * from e_lgd_mapper_mis where  is_lr_dues_added = 'N' and ccode = ?1 and taluka_code=?2 "
			+ " ",nativeQuery=true)
	LgdMapperMis findBycCode(String cCode, String talukaCode );
	
	
	
	//for Net_vasuli Mis
	@Query(value="select * from e_lgd_mapper_mis where   ccode = ?1 and taluka_code=?2 "
			+ " ",nativeQuery=true)
	LgdMapperMis findBycCodeAndTalukaCode(String cCode, String talukaCode);

	
	
	@Query(value="select * from e_lgd_mapper_mis where  is_kajapa_added = 'N' and ccode = ?1 and taluka_code=?2 "
			+ " ",nativeQuery=true)
	LgdMapperMis findBycCodeKajapa(String cCode, String talukaCode );
	
	
	@Query(value="select * from e_lgd_mapper_mis where  is_ms_lr_added = 'N' and ccode = ?1 and taluka_code=?2 "
			+ " ",nativeQuery=true)
	LgdMapperMis findBycCodemislr(String cCode, String talukaCode );
	
	
	@Query(value="select * from e_lgd_mapper_mis where  is_notice_added = 'N' and ccode = ?1 and taluka_code=?2 "
			+ " ",nativeQuery=true)
	LgdMapperMis findBycCodeNoticefee(String cCode, String talukaCode );
	
	@Query(value="select * from e_lgd_mapper_mis where  is_khata_merge_added = 'N' and ccode = ?1 and taluka_code=?2 "
			+ " ",nativeQuery=true)
	LgdMapperMis findBycCodeKhataMerge(String cCode, String talukaCode );
	
	@Query(value="select * from e_lgd_mapper_mis where  is_add_land_added = 'N' and ccode = ?1 and taluka_code=?2 "
			+ " ",nativeQuery=true)
	LgdMapperMis findBycCodeAddlrrevenue(String cCode, String talukaCode );
	
	
	@Query(value="select * from e_lgd_mapper_mis where  is_challan_generated = 'Y'  and ccode = ?1 "
			+ " ",nativeQuery=true)
	LgdMapperMis findByIsChallanGenerated(String cCode);

	/////////////////////////////////  MIS 9(3)     ///////////////////////////////////
	
	@Query(value="select  distinct ccode from   "
			+ "(select ccode from public.dblink('dbname='|| ?2 ||' host='|| ?1 ||' user='|| ?4 ||' password='|| ?5 ,  "
			+ "'select ccode, form_id, is_nirank, is_completed, revenue_year "
			+ "from '|| ?3 ||'.nirank_and_completedwork_forms')  "
			+ "AS(ccode text, form_id text, is_nirank character(1), is_completed character(1), revenue_year text) "
			+ "where form_id='lrdues' and is_nirank='Y' and is_completed='Y' and revenue_year=?6 group by ccode"
			+ ")a "
             ,nativeQuery=true)
	List<Object[]> findBycCodeAndRevenueYearAndActiveFlagMIS(String eHost,String eDbName,String eSchemaName,String eUser,String ePassword,String revenue_year);
	
	
	
	@Query(value="select  distinct ccode from   "
			+ "(select ccode from public.dblink('dbname='|| ?2 ||' host='|| ?1 ||' user='|| ?4 ||' password='|| ?5 ,  "
			+ "'select ccode, form_id, is_nirank, is_completed, revenue_year "
			+ "from '|| ?3 ||'.nirank_and_completedwork_forms')  "
			+ "AS(ccode text, form_id text, is_nirank character(1), is_completed character(1), revenue_year text) "
			+ "where form_id='kajapa' and is_nirank='Y' and is_completed='Y' and revenue_year=?6 group by ccode"
			+ ")a "
             ,nativeQuery=true)
	List<Object[]> findBycCodeAndRevenueYearAndActiveFlagMISkajapa(String eHost,String eDbName,String eSchemaName,String eUser,String ePassword,String revenue_year);
	
	
	
	
	@Query(value="select  distinct ccode from   "
			+ "(select ccode from public.dblink('dbname='|| ?2 ||' host='|| ?1 ||' user='|| ?4 ||' password='|| ?5 ,  "
			+ "'select ccode, form_id, is_nirank, is_completed, revenue_year "
			+ "from '|| ?3 ||'.nirank_and_completedwork_forms')  "
			+ "AS(ccode text, form_id text, is_nirank character(1), is_completed character(1), revenue_year text) "
			+ "where form_id='17' and is_nirank='Y' and is_completed='Y' and revenue_year=?6 group by ccode"
			+ ")a "
             ,nativeQuery=true)
	List<Object[]> findBycCodeAndRevenueYearAndActiveFlagMISmslr(String eHost,String eDbName,String eSchemaName,String eUser,String ePassword,String revenue_year);

	
	
	@Query(value="select  distinct ccode from   "
			+ "(select ccode from public.dblink('dbname='|| ?2 ||' host='|| ?1 ||' user='|| ?4 ||' password='|| ?5 ,  "
			+ "'select ccode, form_id, is_nirank, is_completed, revenue_year "
			+ "from '|| ?3 ||'.nirank_and_completedwork_forms')  "
			+ "AS(ccode text, form_id text, is_nirank character(1), is_completed character(1), revenue_year text) "
			+ "where form_id='6b' and is_nirank='Y' and is_completed='Y' and revenue_year=?6 group by ccode"
			+ ")a "
             ,nativeQuery=true)
	List<Object[]> findBycCodeAndActiveFlagMISnoticefee(String eHost,String eDbName,String eSchemaName,String eUser,String ePassword,String revenue_year);

	
	@Query(value="select  distinct ccode from   "
			+ "(select ccode from public.dblink('dbname='|| ?2 ||' host='|| ?1 ||' user='|| ?4 ||' password='|| ?5 ,  "
			+ "'select ccode, form_id, is_nirank, is_completed, revenue_year "
			+ "from '|| ?3 ||'.nirank_and_completedwork_forms')  "
			+ "AS(ccode text, form_id text, is_nirank character(1), is_completed character(1), revenue_year text) "
			+ "where form_id='khataMerge' and is_nirank='Y' and is_completed='Y' and revenue_year=?6 group by ccode"
			+ ")a "
             ,nativeQuery=true)
	List<Object[]> findBycCodeAndRevenueYearAndActiveFlagMISkhataMerge(String eHost,String eDbName,String eSchemaName,String eUser,String ePassword, int talukaCode);

	@Query(value="select  distinct ccode from   "
			+ "(select ccode from public.dblink('dbname='|| ?2 ||' host='|| ?1 ||' user='|| ?4 ||' password='|| ?5 ,  "
			+ "'select ccode, form_id, is_nirank, is_completed, revenue_year "
			+ "from '|| ?3 ||'.nirank_and_completedwork_forms')  "
			+ "AS(ccode text, form_id text, is_nirank character(1), is_completed character(1), revenue_year text) "
			+ "where form_id='addlandlr' and is_nirank='Y' and is_completed='Y' and revenue_year=?6 group by ccode"
			+ ")a "
             ,nativeQuery=true)
	List<Object[]> findBycCodeAndRevenueYearAndActiveFlagMISAddlrrevenue(String eHost,String eDbName,String eSchemaName,String eUser,String ePassword,String revenue_year);

	///////////////////////////////////////////////////////////////////////////////////
	
	@Query(value="select   "
			+ "district_code,   "
			+ "district_name_marathi,   "
			+ "taluka_code,   "
			+ "taluka_name_marathi,   "
			+ "ccode,   "
			+ "lgd_village_name,   "
			+ "division_code,   "
			+ "(select count(distinct taluka_code) from e_lgd_mapper_mis where  district_code=d.district_code) totaloftalukas   "
			+ "from e_lgd_mapper d where is_selected='Y' and district_code=?1 order by cast(taluka_code as int) "
			+ " ",nativeQuery=true)
	List<Object[]> getphase1villagecount(String districtCode);

	
	@Query(value="select  "
			+ "district_code,  "
			+ "district_name_marathi,  "
			+ "taluka_code,  "
			+ "taluka_name_marathi,  "
			+ "ccode,  "
			+ "lgd_village_name,  "
			+ "division_code,  "
			+ "(select count(distinct taluka_code) from e_lgd_mapper_mis where  district_code=d.district_code) totaloftalukas  "
			+ "from e_lgd_mapper d where is_logged_in ='Y' and district_code=?1  order by cast(taluka_code as int) "
			+ " ",nativeQuery=true)
	List<Object[]> getphase1LoginVillageCount(String districtCode);
	
	
	
//	@Query(value="select  distinct ccode from   "
//			+ "(select ccode from public.dblink('dbname='|| ?2 ||' host='|| ?1 ||' user='|| ?4 ||' password='|| ?5 ,  "
//			+ "'select ccode,active_flag,revenue_year "
//			+ "from '|| ?3 ||'.lr_pending_dues')  "
//			+ "AS(ccode text,active_flag character(1), revenue_year text) "
//			+ "where active_flag='Y' and revenue_year=?6 group by ccode"
//			+ ")a "
//             ,nativeQuery=true)
//	List<Object[]> findBycCodeAndRevenueYearAndActiveFlagMIS(String eHost,String eDbName,String eSchemaName,String eUser,String ePassword,String revenue_year);
//	
//	
//	
//	@Query(value="select  distinct ccode from   "
//			+ "(select ccode from public.dblink('dbname='|| ?2 ||' host='|| ?1 ||' user='|| ?4 ||' password='|| ?5 ,  "
//			+ "'select ccode,active_flag,revenue_year "
//			+ "from '|| ?3 ||'.echawdi_form17_no_ka_ja_pa')  "
//			+ "AS(ccode text,active_flag character(1), revenue_year text) "
//			+ "where active_flag='Y' and revenue_year=?6 group by ccode"
//			+ ")a "
//             ,nativeQuery=true)
//	List<Object[]> findBycCodeAndRevenueYearAndActiveFlagMISkajapa(String eHost,String eDbName,String eSchemaName,String eUser,String ePassword,String revenue_year);
//	
//	
//	
//	
//	@Query(value="select  distinct ccode from   "
//			+ "(select ccode from public.dblink('dbname='|| ?2 ||' host='|| ?1 ||' user='|| ?4 ||' password='|| ?5 ,  "
//			+ "'select ccode,active_flag,revenue_year "
//			+ "from '|| ?3 ||'.echawdi_form17')  "
//			+ "AS(ccode text,active_flag character(1), revenue_year text) "
//			+ "where active_flag='Y' and revenue_year=?6 group by ccode"
//			+ ")a "
//             ,nativeQuery=true)
//	List<Object[]> findBycCodeAndRevenueYearAndActiveFlagMISmslr(String eHost,String eDbName,String eSchemaName,String eUser,String ePassword,String revenue_year);
//
//	@Query(value="select  distinct ccode from   "
//			+ "(select ccode from public.dblink('dbname='|| ?2 ||' host='|| ?1 ||' user='|| ?4 ||' password='|| ?5 ,  "
//			+ "'select ccode,active_flag "
//			+ "from '|| ?3 ||'.echawdi_form6b')  "
//			+ "AS(ccode text,active_flag character(1)) "
//			+ "where active_flag='Y'  group by ccode"
//			+ ")a "
//             ,nativeQuery=true)
//	List<Object[]> findBycCodeAndRevenueYearAndActiveFlagMISnoticefee(String eHost,String eDbName,String eSchemaName,String eUser,String ePassword,String revenue_year);
//
//	
//	@Query(value="select  distinct ccode,taluka_code from   "
//			+ "(select ccode,taluka_code from public.dblink('dbname='|| ?2 ||' host='|| ?1 ||' user='|| ?4 ||' password='|| ?5 ,  "
//			+ "'select ccode,active_flag,taluka_code "
//			+ "from '|| ?3 ||'.echawdi_khata_merge')  "
//			+ "AS(ccode text,active_flag character(1), taluka_code integer) "
//			+ "where active_flag='Y' and taluka_code=?6  group by ccode,taluka_code"
//			+ ")a "
//             ,nativeQuery=true)
//	List<Object[]> findBycCodeAndRevenueYearAndActiveFlagMISkhataMerge(String eHost,String eDbName,String eSchemaName,String eUser,String ePassword, int talukaCode);
//
//	@Query(value="select  distinct ccode from   "
//			+ "(select ccode from public.dblink('dbname='|| ?2 ||' host='|| ?1 ||' user='|| ?4 ||' password='|| ?5 ,  "
//			+ "'select ccode,active_flag, revenue_year "
//			+ "from '|| ?3 ||'.echawdi_additional_land_revenue')  "
//			+ "AS(ccode text,active_flag character(1), revenue_year text) "
//			+ "where active_flag='Y' and  revenue_year=?6  group by ccode"
//			+ ")a "
//             ,nativeQuery=true)
//	List<Object[]> findBycCodeAndRevenueYearAndActiveFlagMISAddlrrevenue(String eHost,String eDbName,String eSchemaName,String eUser,String ePassword,String revenue_year);

	
	@Query(value="select  distinct ccode from   "
			+ "(select ccode from public.dblink('dbname='|| ?2 ||' host='|| ?1 ||' user='|| ?4 ||' password='|| ?5 ,  "
			+ "'select ccode, active_flag "
			+ "from '|| ?3 ||'.echawdi_form1_dyslr')  "
			+ "AS(ccode text, active_flag character(1)) "
			+ "where active_flag='Y' "
			+ ")a "
             ,nativeQuery=true)
	List<Object[]> findBycCodeAndActiveFlagMISDyslr(String eHost,String eDbName,String eSchemaName,String eUser,String ePassword);
	
	
	@Query(value="select * from e_lgd_mapper_mis where  dyslr_work_started = 'N'  and ccode = ?1 and taluka_code=?2 "
			+ " ",nativeQuery=true)
	LgdMapperMis findBycCodeforDyslrSave(String cCode, String talukaCode); 
	
	
	
	@Query(value = "select  " 
	        + "m.district_code, "
	        + "m.district_name_marathi, "
	        + "m.taluka_code, "
	        + "m.taluka_name_marathi, "
	        + "m.ccode, "
	        + "m.lgd_village_name, "
	        + "sum(m.total_demand_jm) as totalDandJm, "
	        + "sum(m.total_demand_sankirn) as totalDandSankirn, "
	        + "sum(m.total_collected_jm) as totalCollectedJm, "
	        + "sum(m.total_collected_sankirn) as totalCollectedSankirn, "
	        + "m.revenue_year "
	        + "from e_lgd_mapper_mis m "
	        + "where m.district_code = ?1 "
	        + "and m.taluka_code = ?2 "
	        + "and m.revenue_year = ?3 "
	        + "group by "
	        + "m.district_code, "
	        + "m.district_name_marathi, "
	        + "m.taluka_code, "
	        + "m.taluka_name_marathi, "
	        + "m.ccode, "
	        + "m.lgd_village_name,"
	        + "m.revenue_year ",
	        nativeQuery = true)
	List<Object[]> getByVillageVasuliRecords(String districtCode, String talukaCode, String revenueYear);
	
	
	
		@Modifying
		@Transactional
		@Query(value =
		        "UPDATE e_lgd_mapper_mis m " +
		        "SET is_dyslr_abstract_added = 'Y' " +
		        "FROM ( " +
		        "   SELECT ccode " +
		        "   FROM dblink( " +
		        "       'dbname='|| ?2 ||' host='|| ?1 ||' port=5432 user='|| ?4 ||' password='|| ?5, " +
		        "       'SELECT ccode FROM '|| ?3 ||'.echawdi_form1abstract_dyslr' " +
		        "   ) AS t(ccode varchar) " +
		        ") dyslr " +
		        "WHERE m.ccode = dyslr.ccode",
		        nativeQuery = true)
		int updateDyslrFlag(
		        String host,
		        String dbName,
		        String schemaName,
		        String user,
		        String password);
	
	
	/*@Query(value = "select  " 
			+ "m.district_code, "
		    + "m.district_name_marathi,"
		    + "m.taluka_code,"
		    + "m.taluka_name_marathi,"
		    + "m.ccode,lgd_village_name,"
		    + "sum(m.total_demand_jm) as totalDandJm,"
		    + "sum(m.total_demand_sankirn) as totalDandSankirn,"
		    + "sum(m.total_collected_jm) as totalCollectedJm,"
		    + "sum(m.total_collected_sankirn) as totalCollectedSankirn"
			+ "from  e_lgd_mapper_mis m"
			+ "where  "
		    + "m.district_code = '12' "
		    + "and "
			+ "m.taluka_code = '11'" 
			+ "group by "
			+ "district_code,"
			+ "district_name_marathi,"
			+ "taluka_code,"
			+ "taluka_name_marathi,"  
			+ "ccode,"
			+ "lgd_village_name "
			+ "", nativeQuery = true)
	List<Object[]> getByVillageVasuliRecords(String districtCode, String talukaCode);*/
	
}

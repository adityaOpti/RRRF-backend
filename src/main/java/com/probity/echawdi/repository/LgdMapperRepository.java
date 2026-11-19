package com.probity.echawdi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.probity.echawdi.entity.LGDMapper;

@Repository
public interface LgdMapperRepository extends JpaRepository<LGDMapper, String>{
	List<LGDMapper> findBycCodeInAndIsLoggedInIsNull(List<String> cCode);

	List<LGDMapper> findByDistrictCodeAndTalukaCode(String districtCode, String talukaCode);
	
	List<LGDMapper> findByIsLoggedInIsNull();

	@Query(value="select * from e_lgd_mapper where  is_challan_generated = 'N' and ccode = ?1 and taluka_code=?2 "
			+ " ",nativeQuery=true)
	LGDMapper findBycCode(String cCode, String talukaCode);
	
	@Query(value="select * from e_lgd_mapper where  is_challan_generated = 'Y'  and ccode = ?1 "
			+ " ",nativeQuery=true)
	LGDMapper findByIsChallanGenerated(String cCode);
	
	@Query(value = "SELECT district_name_marathi FROM e_lgd_mapper WHERE district_code = ?1 LIMIT 1", nativeQuery = true)
	String getDistrictNameMarathi(String districtCode);

	
	@Query(value="select   "
			+ "district_code,   "
			+ "district_name_marathi,   "
			+ "taluka_code,   "
			+ "taluka_name_marathi,   "
			+ "ccode,   "
			+ "lgd_village_name,   "
			+ "division_code,   "
			+ "(select count(distinct taluka_code) from e_lgd_mapper where  district_code=d.district_code) totaloftalukas   "
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
			+ "(select count(distinct taluka_code) from e_lgd_mapper where  district_code=d.district_code) totaloftalukas  "
			+ "from e_lgd_mapper d where is_logged_in ='Y' and district_code=?1  order by cast(taluka_code as int) "
			+ " ",nativeQuery=true)
	List<Object[]> getphase1LoginVillageCount(String districtCode);
	
	//+" public.dblink('dbname='|| 'linkgedb_emuroll' ||' host='|| '10.10.248.153' ||' port=5432 user='|| 'echawadi' ||' password='|| 'echwadi@Nic22', "
    
	@Query(value="SELECT lgd_code  FROM "
           // +" public.dblink('dbname='|| 'echmst' ||' host='|| '10.10.248.108' ||' port=5432 user='|| 'postgres' ||' password='|| 'jduri%D^7f9gf', "
            +" public.dblink('dbname='|| 'echmst' ||' host='|| '10.48.65.110' ||' port=5432 user='|| 'postgres' ||' password='|| 'jduri%D^7f9gf', "
            + "'SELECT o.lgd_code "
            + "FROM '  || 'public' ||'.e_lgd_mapper   o where trim(o.ccode)='|| ?1 ||':: text and o.taluka_code='|| ?2 ||':: text ') "
            + "AS data (lgd_code text)   "
            +" "
            + "",nativeQuery = true)
	Object getCurrentLgdCodeForReciept(  String cCode, Integer taluka_code);
	
	/*@Query(value="select count(distinct khata_no) , ccode from   "
			+ "(select  khata_no , ccode from public.dblink('dbname='|| ?2 ||' host='|| ?1 ||' port='|| ?6 ||' user='|| ?4 ||' password='|| ?5 ,  "
			+ "'select khata_no  , ccode  "
			+ "from '|| ?3 ||'.form7_khata where khata_no not like ''TKN%'' and ccode!='''' ')  "
			+ "AS(khata_no text, ccode text) "
			+ " "
			+ ")a  group by ccode  "
             ,nativeQuery=true)
	List<Object[]> findBycCodeForKhataCount(String eHost,String eDbName,String eSchemaName,String eUser,String ePassword,String portAug);
	*/
	@Query(value=
			"select  "
			+ "count(distinct khata_no) as khataNo, "
			+ "count( distinct pin||'/' ||  pin1 || '/'|| pin2 || '/'|| pin3 || '/' || pin4 || '/' || pin5 || '/' || pin6 || '/' || pin7 || '/' || pin8) as pin "
			+ "from     "
			+ "public.dblink(    "
			+ "'dbname='|| ?2 ||' host='|| ?1 ||' port='|| ?7 ||' user='|| ?4 || ' password='|| ?5 ,    "
			+ "'SELECT f7k.khata_no,f7k.MARKED,f7a.ccode,f7a.pin,f7a.pin1,f7a.pin2,f7a.pin3,f7a.pin4,f7a.pin5,f7a.pin6,f7a.pin7,f7a.pin8   "
			+ "from    "
			+ "' || ?3 ||'.form7_area f7a,   "
			+ "' || ?3 ||'.form7_khata f7k,    "
			+ "' || ?3 ||'.FORM7 F7   "
			+ "where    "
			+ " F7.CCODE = F7A.CCODE     "
			+ "AND F7.CCODE = F7K.CCODE     "
			+ "AND F7.PIN = F7K.PIN     "
			+ "AND F7.PIN1 = F7K.PIN1     "
			+ "AND F7.PIN2 = F7K.PIN2     "
			+ "AND F7.PIN3 = F7K.PIN3     "
			+ "AND F7.PIN4 = F7K.PIN4     "
			+ "AND F7.PIN5 = F7K.PIN5     "
			+ "AND F7.PIN6 = F7K.PIN6     "
			+ "AND F7.PIN7 = F7K.PIN7     "
			+ "AND F7.PIN8 = F7K.PIN8     "
			+ "AND F7.PIN = F7A.PIN     "
			+ "AND F7.PIN1 = F7A.PIN1     "
			+ "AND F7.PIN2 = F7A.PIN2     "
			+ "AND F7.PIN3 = F7A.PIN3     "
			+ "AND F7.PIN4 = F7A.PIN4     "
			+ "AND F7.PIN5 = F7A.PIN5     "
			+ "AND F7.PIN6 = F7A.PIN6     "
			+ "AND F7.PIN7 = F7A.PIN7     "
			+ "AND F7.PIN8 = F7A.PIN8   "
			+ "AND f7.ccode= '|| ?6 ||'::text		"
			+ "AND f7k.ccode= '|| ?6 ||'::text		"
			+ "and f7a.ccode='|| ?6 ||'::text ')   "
			+ "AS khata(khata_no text,MARKED character(1),ccode text,pin text,pin1 text,pin2 text,pin3 text,pin4 text,pin5 text,pin6 text,pin7 text,pin8 text )    "
			+ "where trim(ccode) = ?6  "
			+ "AND KHATA_NO <> '500001' and MARKED <> 'Y'  "
			+ ""
			+ "",nativeQuery=true)
	public List<Object[]> findBycCodeForKhataCount(String fhost,String fdbName,String fschemaName,String fuser,String fpassword, String cCode, String port);
			
/*	@Query(value=
			" select  "
			+ " count(distinct ccode) as ccode"
			+ " from     "
			+ "public.dblink(    "
			+ "'dbname='|| ?2 ||' host='|| ?1 ||' port='|| ?7 ||' user='|| ?4 || ' password='|| ?5 ,    "
			+ "'SELECT distinct ccode   "
			+ "from    "
			+ "' || ?3 ||'.land_revenue_demand lr   "
			+ " where    "			
			+ "  "
			+ " lr.active_flag= '|| 'Y' ||'::text		"
			+ "  ')   "
			+ "AS khata(ccode text  )    "
			+ "where revenue_year='2024-25'   "		 
			+ "",nativeQuery=true)
	public List<Object[]> findBycCodeForDemandCount(String fhost,String fdbName,String fschemaName,String fuser,String fpassword, String cCode, String port);
			
	*/
	
	@Query(value="select * from e_lgd_mapper where ccode = ?1 and taluka_code=?2 "
			+ " ",nativeQuery=true)
	LGDMapper findBycCodeforkhataCountForsave(String cCode, String talukaCode );
	
	@Query(value="select (SELECT count(distinct khata_no)  FROM "
			+" public.dblink('dbname='|| ?2 ||' host='|| ?1 ||' port=5432 user='|| ?4 ||' password='|| ?5 , "
			+ "'SELECT lrd.khata_no "
			+ "FROM  '  || ?3 ||'.land_revenue_demand lrd where active_flag=''Y'':: text and khata_no is not null and khata_owner_name is not null and  revenue_year=''2024-25'':: text and  trim(ccode)= '|| ?6 ||':: text ') "
			+ "AS data (khata_no text)) +(select count(distinct  khata_no)  FROM "
			+ "public.dblink('dbname='|| ?2 ||' host='|| ?1 ||' port=5432 user='|| ?4 ||' password='|| ?5 , "
			+ "'SELECT o.khata_no "
			+ "FROM '  || ?3 ||'.echawdi_khata_merge_dtl o where ccode= '|| ?6 ||':: text  and active_flag=''Y'' ')"
			+ "AS data (khata_no text)) - (select count(distinct  khata_no)  FROM "
			+ "public.dblink('dbname='|| ?2 ||' host='|| ?1 ||' port=5432 user='|| ?4 ||' password='|| ?5 ,"
			+ "'SELECT o.khata_no "
			+ "FROM '  || ?3 ||'.echawdi_khata_merge o where ccode= '|| ?6 ||':: text and active_flag=''Y'' ') "
			+ "AS data (khata_no text)) AS result  "
			+ "",nativeQuery = true)
	int getDemandGeneration(String echHost, String echDbName, String echSchemaName, String echuser,
			String echpassword, String ccode );
	
	@Query(value="select (SELECT count(distinct khata_no)  FROM "
			+" public.dblink('dbname='|| ?2 ||' host='|| ?1 ||' port=5432 user='|| ?4 ||' password='|| ?5 , "
			+ "'SELECT lrd.khata_no,ccode "
			+ "FROM  '  || ?3 ||'.land_revenue_demand lrd where active_flag=''Y'':: text and khata_no is not null and revenue_year=''2024-25'':: text   ') "
			+ "AS data (khata_no text,ccode text) where trim(ccode)=?6 ) +(select count(distinct  khata_no)  FROM "
			+ "public.dblink('dbname='|| ?2 ||' host='|| ?1 ||' port=5432 user='|| ?4 ||' password='|| ?5 , "
			+ "'SELECT o.khata_no,ccode "
			+ "FROM '  || ?3 ||'.echawdi_khata_merge_dtl o where   active_flag=''Y'' ')"
			+ "AS data (khata_no text,ccode text) where trim(ccode)=?6 ) - (select count(distinct  khata_no)  FROM "
			+ "public.dblink('dbname='|| ?2 ||' host='|| ?1 ||' port=5432 user='|| ?4 ||' password='|| ?5 ,"
			+ "'SELECT o.khata_no, ccode  "
			+ "FROM '  || ?3 ||'.echawdi_khata_merge o where  active_flag=''Y'' ') "
			+ "AS data (khata_no text,ccode text) where trim(ccode)=?6 ) AS result  "
			+ "",nativeQuery = true)
	int getDemandGenerationUpdated(String echHost, String echDbName, String echSchemaName, String echuser,
			String echpassword, String ccode );
	
	/*@Query(value="select (SELECT count(distinct khata_no)  FROM "
			+" public.dblink('dbname='|| ?2 ||' host='|| ?1 ||' port=5432 user='|| ?4 ||' password='|| ?5 , "
			+ "'SELECT lrd.khata_no "
			+ "FROM '  || ?3 ||'.land_revenue_demand lrd where active_flag=''Y'':: text and khata_no is not null and revenue_year=''2023-24'':: text and  trim(ccode)='|| ?6 ||':: text') "
			+ "AS data (khata_no text)) +(select count(distinct  khata_no)  FROM "
			+ "public.dblink('dbname='|| ?2 || ' host='|| ?1 || ' port=5432  user='|| ?4 || ' password='|| ?5 , "
			+ "'SELECT o.khata_no "
			+ "FROM '|| ?3 || '.echawdi_khata_merge_dtl o where ccode='|| ?6 ||':: text and active_flag=''Y'' ')"
			+ "AS data (khata_no text)) - (select count(distinct  khata_no)  FROM "
			+ "public.dblink('dbname='|| ?2 || ' host='|| ?1 || ' port=5432  user='|| ?4 || ' password='|| ?5 ,"
			+ "'SELECT o.khata_no "
			+ "FROM '|| ?3 || '.echawdi_khata_merge o where ccode='|| ?6 ||':: text and active_flag=''Y'' ') "
			+ "AS data (khata_no text)) AS result  "
			+ "",nativeQuery = true)
	int getDemandGeneration(String echHost, String echDbName, String echSchemaName, String echuser,
			String echpassword, String ccode);
	 * 
	 * */
	 
	
	@Query(value="SELECT count(distinct khata_no)  FROM "
			+" public.dblink('dbname='|| ?2 ||' host='|| ?1 ||' port=5432 user='|| ?4 ||' password='|| ?5 , "
			+ "'SELECT kmd.khata_no "
			+ "FROM '  || ?3 ||'.echawdi_khata_merge_dtl kmd where active_flag=''Y'' and  trim(ccode)='|| ?6 ||':: text') "
			+ "AS data (khata_no text)   "
			+ "",nativeQuery = true)
	int getKhataMergeDtlCount(String echHost, String echDbName, String echSchemaName, String echuser,
			String echpassword, String ccode);
	
	@Query(value="SELECT count(distinct khata_no)  FROM "
			+" public.dblink('dbname='|| ?2 ||' host='|| ?1 ||' port=5432 user='|| ?4 ||' password='|| ?5 , "
			+ "'SELECT km.khata_no "
			+ "FROM '  || ?3 ||'.echawdi_khata_merge km where active_flag=''Y'' and trim(ccode)='|| ?6 ||':: text ') "
			+ "AS data (khata_no text)   "
			+ "",nativeQuery = true)
    int  getKhataMergeCount(String echHost, String echDbName, String echSchemaName, String echuser,
			String echpassword, String ccode);
	
	@Query(value="select * from e_lgd_mapper where  is_demand_completed = 'N'  and ccode = ?1 and taluka_code=?2 "
			+ " ",nativeQuery=true)
	LGDMapper findByIsDemandCompleted(String cCode,String talukaCode);
	
	@Query(value="select * from e_lgd_mapper where  is_demand_completed = 'Y'  and ccode = ?1 and taluka_code=?2 "
			+ " ",nativeQuery=true)
	LGDMapper findByIsDemandNotCompleted(String cCode,String talukaCode);
	
	@Query(value="select khata_count from e_lgd_mapper where  khata_count is not null  and ccode = ?1 and  taluka_code = ?2 "
			+ " ",nativeQuery=true)
	int getKhataF7k(String cCode, String talukaCode);
	
	@Query(value="select (SELECT count(distinct khata_no)  FROM "
			+" public.dblink('dbname='|| ?2 ||' host='|| ?1 ||' port=5432 user='|| ?4 ||' password='|| ?5 , "
			+ "'SELECT lrd.khata_no "
			+ "FROM '  || ?3 ||'.land_revenue_demand lrd where active_flag=''Y'':: text and khata_no is not null and revenue_year=''2024-25'':: text and "
			+ " trim(ccode)='|| ?6 ||':: text and khata_owner_name is not null and case when net_amount > 0 then receipt_no is not null else net_amount=0 end ') "
			+ "AS data (khata_no text)) +(select count(distinct  khata_no)  FROM "
			+ "public.dblink('dbname='|| ?2 || ' host='|| ?1 || ' port=5432  user='|| ?4 || ' password='|| ?5 , "
			+ "'SELECT o.khata_no "
			+ "FROM '|| ?3 || '.echawdi_khata_merge_dtl o where ccode='|| ?6 ||':: text and active_flag=''Y'' ')"
			+ "AS data (khata_no text)) - (select count(distinct  khata_no)  FROM "
			+ "public.dblink('dbname='|| ?2 || ' host='|| ?1 || ' port=5432  user='|| ?4 || ' password='|| ?5 ,"
			+ "'SELECT o.khata_no "
			+ "FROM '|| ?3 || '.echawdi_khata_merge o where ccode='|| ?6 ||':: text and active_flag=''Y'' ') "
			+ "AS data (khata_no text)) AS result  "
			+ "",nativeQuery = true)
	int getVasuliCompleted(String echHost, String echDbName, String echSchemaName, String echuser,
			String echpassword, String ccode);
	
	
	/*@Query(value="select (SELECT count(distinct khata_no)  FROM "
			+" public.dblink('dbname='|| ?2 ||' host='|| ?1 ||' port=5432 user='|| ?4 ||' password='|| ?5 , "
			+ "'SELECT lrd.khata_no "
			+ "FROM '  || ?3 ||'.land_revenue_demand lrd where active_flag=''Y'':: text and khata_no is not null and revenue_year=''2024-25'':: text and "
			+ " trim(ccode)='|| ?6 ||':: text and case when net_amount > 0 then receipt_no is not null else net_amount=0 end ') "
			+ "AS data (khata_no text)) +(select count(distinct  khata_no)  FROM "
			+ "public.dblink('dbname='|| ?2 || ' host='|| ?1 || ' port=5432  user='|| ?4 || ' password='|| ?5 , "
			+ "'SELECT o.khata_no "
			+ "FROM '|| ?3 || '.echawdi_khata_merge_dtl o where ccode='|| ?6 ||':: text and active_flag=''Y'' ')"
			+ "AS data (khata_no text)) - (select count(distinct  khata_no)  FROM "
			+ "public.dblink('dbname='|| ?2 || ' host='|| ?1 || ' port=5432  user='|| ?4 || ' password='|| ?5 ,"
			+ "'SELECT o.khata_no "
			+ "FROM '|| ?3 || '.echawdi_khata_merge o where ccode='|| ?6 ||':: text and active_flag=''Y'' ') "
			+ "AS data (khata_no text)) AS result  "
			+ "",nativeQuery = true)
	int getVasuliCompleted(String echHost, String echDbName, String echSchemaName, String echuser,
			String echpassword, String ccode);
	*/
	
	@Query(value="select * from e_lgd_mapper where  is_vasuli_completed = 'N'  and ccode = ?1  and taluka_code=?2"
			+ " ",nativeQuery=true)
	LGDMapper findByIsVasuliCompleted(String cCode, String talukaCode);
	
	
	@Query(value="select * from e_lgd_mapper where  is_vasuli_completed = 'Y'  and ccode = ?1  and taluka_code=?2"
			+ " ",nativeQuery=true)
	LGDMapper findByIsVasuliNotCompleted(String cCode, String talukaCode);
	
	@Query(value="SELECT form_count  FROM "
           // +" public.dblink('dbname='|| 'echmst' ||' host='|| '10.10.248.108' ||' port=5432 user='|| 'postgres' ||' password='|| 'jduri%D^7f9gf', "
            +" public.dblink('dbname='|| 'echmst' ||' host='|| '10.48.65.110' ||' port=5432 user='|| 'postgres' ||' password='|| 'jduri%D^7f9gf', "
            + "'SELECT count(distinct o.form_id) as form_count "
            + "FROM '  || 'public' ||'.nirank_and_completedwork_forms  o where  is_completed=''Y''  and  trim(o.ccode)='|| ?1 ||':: text and o.taluka_code='|| ?2 ||':: text ') "
            + "AS data (form_count integer)   "
            +" "
            + "",nativeQuery = true)
	Object findBycCodeNamune( String cCode, String talukaCode);
	
	@Query(value="select * from e_lgd_mapper where  is_gaon_namuna_added = 'N' and ccode = ?1 and taluka_code=?2 "
			+ " ",nativeQuery=true)
	LGDMapper findBycCodeGaonNamuna(String cCode, String talukaCode);
	
	
	@Query(value="select * from e_lgd_mapper where  is_gaon_namuna_added  = 'Y' and ccode = ?1 and taluka_code=?2 "
			+ " ",nativeQuery=true)
	LGDMapper findBycCodeGaonNamunaNtype(String cCode, String taluka_code);
	
	
	 @Query(value = 
			    "SELECT lgd_code, ccode, district_code, district_name_marathi, division_code, " +
			    "is_challan_generated, is_demand_completed, is_gaon_namuna_added, is_logged_in, " +
			    "is_selected, is_vasuli_completed, is_vasuli_without_demandadd, khata_count, " +
			    "lgd_village_name, log_in_dt_tm, sathbara_count, taluka_code, taluka_name_marathi " +
			    "FROM public.dblink( " +
			 //   "'dbname=' || 'echmst' || ' host=' || '10.10.248.108' || ' port=5432 user=' || 'postgres' || ' password=' || 'jduri%D^7f9gf', " +
			    "'dbname=' || 'echmst' || ' host=' || '10.48.65.110' || ' port=5432 user=' || 'postgres' || ' password=' || 'jduri%D^7f9gf', " +
			    "'SELECT lgd_code, ccode, district_code, district_name_marathi, division_code, " +
			    "is_challan_generated, is_demand_completed, is_gaon_namuna_added, is_logged_in, " +
			    "is_selected, is_vasuli_completed, is_vasuli_without_demandadd, khata_count, " +
			    "lgd_village_name, log_in_dt_tm, sathbara_count, taluka_code, taluka_name_marathi " +
			    "FROM ' || 'public' || '.e_lgd_mapper WHERE is_selected = ''Y''') " +
			    "AS data( " +
			    "lgd_code text, ccode text, district_code text, district_name_marathi text, division_code text, " +
			    "is_challan_generated text, is_demand_completed text, is_gaon_namuna_added text, is_logged_in text, " +
			    "is_selected text, is_vasuli_completed text, is_vasuli_without_demandadd text, khata_count text, " +
			    "lgd_village_name text, log_in_dt_tm text, sathbara_count text, taluka_code text, taluka_name_marathi text) " +
			    "ORDER BY district_name_marathi ASC",
			    nativeQuery = true)
			public List<LGDMapper>  getSelectedLgdMapperData();

	
	 
	

	

}
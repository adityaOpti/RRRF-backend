package com.probity.echawdi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.probity.echawdi.entity.AdditionalLandRevenue;

@Repository
public interface AdditionalLandRevenueRepository extends JpaRepository<AdditionalLandRevenue,Long>  {

	@Query(value="select                "
			+ " khata_no               "
			+ " ,string_agg(distinct surveyHissaNo,',') as surveyHissaNo ,              "
			+ " string_agg(distinct KHATEDAR_NAMES,'|') as khatedar_name ,               "
			+ " sum(total_area_h) as total_area_h  ,               "
			+ " sum(assessment)  as assessment           "
			+ " from (               "
			+ " select               "
			+ " distinct             "
			+ " cast(f7k.khata_no as integer),           "
			+ " ccode,               "
			+ " pin||'/' || pin1 || '/'||pin2 || '/'||pin3 || '/' || pin4 || '/' || pin5 || '/' || pin6 || '/' || pin7 || '/' || pin8 surveyHissaNo,               "
			+ " CONCAT(FNAME, ' ', MNAME, ' ', LNAME) AS KHATEDAR_NAMES,                 "
			+ " total_area_h,                 "
			+ " assessment               "
			+ "from public.dblink     "
			+ "(     "
			+ "'dbname='||?2||' host='||?1||' user='||?4||' password='||?5,    "
			+ "'select f7k.khata_no,f7.ccode,f7.pin,f7.pin1,f7.pin2,f7.pin3,f7.pin4,f7.pin5,f7.pin6,f7.pin7,f7.pin8,     "
			+ "f7k.FNAME,f7k.MNAME,f7k.LNAME,f7k.total_area_h,f7k.assessment,f7k.MARKED     "
			+ "from '||?3||'.form7_khata f7k,'||?3||'.form7 f7     "
			+ "where f7k.ccode=f7.ccode and f7.pin=f7k.pin     "
			+ "and f7.pin1=f7k.pin1 and f7.pin2=f7k.pin2     "
			+ "and f7.pin3=f7k.pin3 and f7.pin4=f7k.pin4     "
			+ "and f7.pin5=f7k.pin5 and f7.pin6=f7k.pin6     "
			+ "and f7.pin7=f7k.pin7 and f7.pin8=f7k.pin8')     "
			+ "AS F7K     "
			+ "(     "
			+ "khata_no text,ccode text,pin text,pin1 text,pin2 text,pin3 text,pin4 text,pin5 text,pin6 text,pin7 text,pin8 text,     "
			+ "FNAME text,MNAME text,LNAME text,     "
			+ "total_area_h numeric,     "
			+ "assessment numeric,MARKED character(1)     "
			+ ")     "
			+ "where     "
			+ "f7k.MARKED <> 'Y' and f7k.khata_no<>'500001' and trim(f7k.ccode)= ?6 and f7k.khata_no=?7 order by cast(f7k.khata_no as integer)     "
			+ "	     "
			+ ")             "
			+ "A                 "
			+ " group by khata_no  "
			+ ""
			,nativeQuery=true)
	
	List<Object[]> getAddLRDtls(String fHost,String fDbName,String fSchemaName,String fUser,String fPassword,
			String cCode, String khataNo);

	
	@Query(value="select id,khata_no,khata_owner_name,assessment,area,checkbox_group,radio_group,remarks from   "
			+ "(select * from public.dblink('dbname='||?2||' host='||?1||' user='||?4||' password='||?5,  "
			+ "'select id,khata_no,khata_owner_name,assessment,area,checkbox_group,radio_group,remarks,ccode,active_flag,applicable_for_demand,revenue_year  "
			+ " from '||?3||'.echawdi_additional_land_revenue')  "
			+ "AS(id bigint,khata_no text,khata_owner_name text,assessment numeric,area numeric,checkbox_group text,radio_group text,remarks text,ccode text,active_flag character(1),applicable_for_demand character(1),revenue_year text) "
			+ "where trim(ccode)=?6 and active_flag='Y' and applicable_for_demand ='Y' and revenue_year=?7  "
			+ ")a "
	,nativeQuery=true)
	List<Object[]> getAddLTablesRecords(String fHost,String fDbName,String fSchemaName,String fUser,String fPassword,String cCode,String revenueYear);
	
	@Query(value="select id,khata_no,khata_owner_name,assessment,area,checkbox_group,radio_group,remarks from   "
			+ "(select * from public.dblink('dbname='||?2||' host='||?1||' user='||?4||' password='||?5,  "
			+ "'select id,khata_no,khata_owner_name,assessment,area,checkbox_group,radio_group,remarks,ccode,active_flag,applicable_for_demand,revenue_year  "
			+ " from '||?3||'.echawdi_additional_land_revenue')  "
			+ "AS(id bigint,khata_no text,khata_owner_name text,assessment numeric,area numeric,checkbox_group text,radio_group text,remarks text,ccode text,active_flag character(1),applicable_for_demand character(1),revenue_year text) "
			+ "where trim(ccode)=?6 and active_flag='Y' and applicable_for_demand ='Y' and revenue_year=?7  "
			+ ")a "
	,nativeQuery=true)
	List<Object[]> getAddLReportRecords(String fHost,String fDbName,String fSchemaName,String fUser,String fPassword,String cCode,String revenueYear);

	List<AdditionalLandRevenue> findByIdIn(List<Long> ids);

	
	Boolean existsBycCodeAndRevenueYearAndKhataNoAndActiveFlag(String cCode,String revenueYear,String khataNo,Character activeFlag );


	@Query("select a from AdditionalLandRevenue a where trim(a.cCode)=?1 and a.revenueYear=?2 and a.activeFlag=?3 and a.applicableForDemand=?4 and a.khataNo not in("
			+ "select khataNo from LandRevenueDemand l where trim(l.cCode)=?1 and l.revenueYear=?2 and l.activeFlag in('Y','M'))")
	List<AdditionalLandRevenue> findBycCodeAndRevenueYearAndActiveFlagAndApplicableForDemand(String cCode,
			String revenueYear, Character activeFlag, Character applicableForDemand);
//																applicableForDemand
	AdditionalLandRevenue findBycCodeAndRevenueYearAndKhataNoAndActiveFlagAndApplicableForDemand(String cCode,
			String revenueYear,String khataNo,Character activeFlag, Character applicableForDemnad);

}

package com.probity.echawdi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.probity.echawdi.entity.KhataMerge;

@Repository
public interface KhataMergeRepository extends JpaRepository<KhataMerge,Long> {

	@Query(value="select * from (select        "
			+ "case when y.khata_no      is null then  cast(x.khata_no as text)   else  y.khata_no      end,      "
			+ "case when y.surveyHissaNo is null then  x.surveyHissaNo else  y.surveyHissaNo end,          "
			+ "case when y.khatedar_name is null then  x.khatedar_name else  y.khatedar_name end,          "
			+ "case when y.total_area_h  is null then  x.total_area_h  else  y.total_area_h  end,          "
			+ "case when y.assessment is null    then  x.assessment    else  y.assessment    end,          "
			+ "(case when y.khata_no is null then 'N' else 'Y' end) as IsPresent,  "
			+ "parent_id  ,    "
			+ "child_khata_nos,"
			+ "x.ccode  "
			+ "from        "
			+ "(      "
			+ "select            "
			+ "khata_no          "
			+ ",string_agg(distinct surveyHissaNo,',') as surveyHissaNo ,          "
			+ "string_agg(distinct KHATEDAR_NAMES,'|') as khatedar_name ,          "
			+ "sum(total_area_h) as total_area_h  ,          "
			+ "sum(assessment)  as assessment,"
			+ "ccode      "
			+ "from (          "
			+ "select          "
			+ "distinct        "
			+ "cast(f7k.khata_no as integer),      "
			+ "ccode,          "
			+ "pin||'/' || pin1 || '/'|| pin2 || '/'|| pin3 || '/' || pin4 || '/' || pin5 || '/' || pin6 || '/' || pin7 || '/' || pin8 surveyHissaNo,          "
			+ "CONCAT(FNAME, ' ', MNAME, ' ', LNAME, ' ', topan_name) AS KHATEDAR_NAMES,            "
			+ "total_area_h,            "
			+ "assessment          "
			+ "from public.dblink  "
			+ "(  "
			+ "'dbname='|| ?2 ||' host='|| ?1 ||' port=' || ?12 ||' user='|| ?4 ||' password='|| ?5,   "
			+ "'select f7k.khata_no,f7.ccode,f7.pin,f7.pin1,f7.pin2,f7.pin3,f7.pin4,f7.pin5,f7.pin6,f7.pin7,f7.pin8,  "
			+ " f7k.FNAME,f7k.MNAME,f7k.LNAME,f7k.topan_name,f7k.total_area_h,f7k.assessment,f7k.MARKED  "
			+ "from '|| ?3 ||'.form7_khata f7k,'|| ?3 ||'.form7 f7  "
			+ "where f7k.ccode=f7.ccode and f7.pin=f7k.pin  "
			+ "and f7.pin1=f7k.pin1 and f7.pin2=f7k.pin2  "
			+ "and f7.pin3=f7k.pin3 and f7.pin4=f7k.pin4  "
			+ "and f7.pin5=f7k.pin5 and f7.pin6=f7k.pin6  "
			+ "and f7.pin7=f7k.pin7 and f7.pin8=f7k.pin8')  "
			+ "AS F7K  "
			+ "(  "
			+ "khata_no text,ccode text,pin text,pin1 text,pin2 text,pin3 text,pin4 text,pin5 text,pin6 text,pin7 text,pin8 text,  "
			+ "FNAME text,MNAME text,LNAME text, topan_name text,  "
			+ "total_area_h numeric(18,2),  "
			+ "assessment numeric(18,2),MARKED character(1)  "
			+ ")  "
			+ "where  "
			+ "f7k.MARKED <> 'Y' and f7k.khata_no<>'500001' and trim(f7k.ccode)= ?11 order by cast(f7k.khata_no as integer)  "
			+ "         "
			+ ")          "
			+ "A            "
			+ "group by khata_no,ccode      "
			+ ")X left join        "
			+ "(select  "
			+ " khata_no,khata_owner_name as khatedar_name,parent_id,survey_hissa_no as surveyHissaNo,  "
			+ " area as total_area_h,assessment,ccode,child_khata_nos, revenue_year  "
			+ " from public.dblink('dbname='|| ?7 ||' host='|| ?6 ||' user='|| ?9 ||' password='|| ?10 ,  "
			+ "'select ekmdtl.khata_no,ekmdtl.khata_owner_name,ekmdtl.parent_id,ekmdtl.survey_hissa_no ,ekmdtl.area,ekmdtl.assessment,  "
			+ "ekmdtl.ccode,ekm.child_khata_nos,ekmdtl.active_flag, ekmdtl.revenue_year from  "
			+ "'|| ?8 ||'.echawdi_khata_merge ekm,  "
			+ "'|| ?8 ||'.echawdi_khata_merge_dtl ekmdtl where  "
			+ "ekmdtl.parent_id=ekm.id and ekm.ccode='|| ?11 ||' ::text and ekmdtl.ccode='|| ?11 ||' ::text and  ekmdtl.revenue_year= ''' || ?13 || ''' ::text and ekm.revenue_year= ''' || ?13 || ''' ::text ')  "
			+ "AS(khata_no text,  "
			+ "  khata_owner_name text,  "
			+ "  parent_id bigint,  "
			+ "  survey_hissa_no text,  "
			+ "  area numeric(18,2),  "
			+ "  assessment numeric(18,2),  "
			+ "  ccode text,  "
			+ "  child_khata_nos text ,  "
			+ "  active_flag character(1),  "
			+ "  revenue_year text  "
			+ " )  "
			+ "where active_flag='Y'  "
			+ ") Y on cast(X.khata_no as text)=Y.khata_no and ?11=y.ccode)a "
			+ "order by child_khata_nos,cast(khata_no as bigint)  "
			+ ""
			,nativeQuery=true)
	public List<Object[]> getKhataMergeDtl(
			String fHost,String fDbName,String fSchemaName,String fUser,String fPassword,
			String eHost,String eDbName,String eSchemaName,String eUser,String ePassword,
			String cCode,String augPort,String revenueYear
			);
	
	@Query("select count(k) from KhataMerge k where trim(cCode)=?1 and activeFlag='Y'")
	public Long getCountByCCode(String cCode);

	@Query("select max(id) from KhataMerge k")
	public Long getParentId();

	

	@Query("select kd.khataNo,kd.khataOwnerName as khatedarName,kd.mergeId,kd.surveyHissaNo as surveyHissaNo,kd.area as total_area_h,kd.assessment,kd.cCode,kd.childKhataNumbers "
			+ "from KhataMerge kd "
			+ "where "
			+ "trim(kd.cCode)=?1 and kd.activeFlag=?2")
//	@Query(value="select khata_no,khata_owner_name as khatedar_name,merge_id,survey_hissa_no as surveyHissaNo,area as total_area_h,assessment,ccode,child_khata_nos from  "
//			+ " (select * from public.dblink(   "
//			+ " 'dbname='||?2||' host='||?1||' user='||?4||' password='|| ?5,   "
//			+ " 'SELECT ekmdtl.khata_no,ekmdtl.khata_owner_name,ekm.merge_id,ekmdtl.survey_hissa_no,ekmdtl.area,ekmdtl.assessment,ekmdtl.ccode,ekm.child_khata_nos,ekmdtl.active_flag   "
//			+ " from   "
//			+ " '||?3||'.echawdi_khata_merge_dtl ekmdtl,  "
//			+ " '||?3||'.echawdi_khata_merge ekm    "
//			+ " where  ekmdtl.ccode=ekm.ccode     "
//			+ "and ekmdtl.khata_no=ekm.khata_no '  "
//			+ " )  "
//			+ " AS (khata_no text,khata_owner_name text,merge_id text,survey_hissa_no text,area numeric,assessment numeric ,ccode text ,child_khata_nos text,active_flag character(1))  "
//			+ " where active_flag=?7   "
//			+ "and ccode=?6)a  "
//			+ "",nativeQuery=true)
	public List<Object[]> getKhataMergeDtlReport(
//			String eHost,String eDbName,String eSchemaName,String eUser,String ePassword,
			String cCode, char active_flag);
	
	@Query("select k from KhataMerge k where "
			+ "trim(cCode)=?1 "
			+ "and activeFlag=?2 "
			+ "and revenueYear=?3 "
			+ "and khataNo not in(select khataNo from LandRevenueDemand l where trim(l.cCode)=?1 and l.revenueYear=?3 and l.activeFlag in('Y','M'))")
	public List<KhataMerge> getMergeDetails(String cCode,Character activeFlag,String revenueYear);

	
	@Query(value="select ccode, username, village_name from   "
			+ "public.dblink('dbname='|| ?2 ||' host='|| ?1 ||' port=5432 user='|| ?4 ||' password='|| ?5 , "
			+ "'SELECT v.ccode, o.username, o.user_type, v.village_code, v.village_name "
			+ "FROM '  || ?3 ||'.m_village_census v,'|| ?3 ||'.m_officermast o where trim(o.ccode)=trim(v.ccode)') "
			+ "AS data (ccode text,username text,user_type text,village_code text,village_name text)  "
			+ "where user_Type = '1' AND trim(username) = ?6"
	+ ""
	,nativeQuery=true)
	public List<Object[]> getccodeDetails(
			String fHost,String fDbName,String fSchemaName,String fUser,String fPassword,String servarthId);
	
}
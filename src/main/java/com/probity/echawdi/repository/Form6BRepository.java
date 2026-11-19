package com.probity.echawdi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.probity.echawdi.entity.Form6B;

public interface Form6BRepository extends JpaRepository<Form6B,Long>{

	List<Form6B> findBycCodeAndActiveFlag(String cCode, Character activeFlag);
		
	//eferfar 
	@Query(value= "SELECT MUT_NO,STRING_AGG(DISTINCT CONCAT(FNAME,' ',MNAME,' ',LNAME),' | ') AS DEATH_AND_VARAS_NAMES "
			+ "FROM public.dblink('dbname=' || ?2 || ' host=' || ?1 || ' user=' || ?4 || ' password=' || ?5, "
			+ "'select MR.MUT_NO,FNAME,MNAME,LNAME,mr.ccode from ' "
			+ "|| ?3 || '.MUTREGISTER MR ,' ||  "
			+ "?3 || '.MUT_KHAREDI_BUYER MKB WHERE    "
			+ "MR.MUT_NO = MKB.MUT_NO  AND MR.CCODE = MKB.CCODE')AS(MUT_NO int,FNAME text,MNAME text,LNAME text,CCODE text) "
			+ "WHERE trim(CCODE) = ?6 "
			+ "AND MUT_NO = ?7 "
			+ "GROUP BY MUT_NO" 
			+"",nativeQuery=true)
	List<Object[]> getForm6BEferfarDetails(
			String fHost,String fDbName,String fSchemaName,String fUser,String fPassword, 
			String cCode
			,Integer mutNo);

	List<Form6B> findBycCodeAndEntryVillageForm6AndActiveFlag(
															  String cCode,
															  Integer entryVillageForm6,
															  Character activeFlag
															  );

	@Query(value="select distinct(f17.khata_no),   "
			+ "receipt_no,   "
			+ "receipt_date,   "
			+ "f17.ccode,   "
			+ "nature_of_case from public.dblink    "
			+ "(   "
			+ "'dbname='|| ?2 ||' host='|| ?1 ||' user='|| ?4 ||' password='|| ?5  ,   "
			+ "'select distinct (f17.khata_no),receipt_no,receipt_date,f17.ccode,nature_of_case,lrd.active_flag from ' || ?3 ||'.land_revenue_demand lrd,'|| ?3 ||'.echawdi_form17 f17 where lrd.ccode=f17.ccode and f17.khata_no=lrd.khata_no '   "
			+ ")   "
			+ "AS f17   "
			+ "(   "
			+ "khata_no text,receipt_no text,receipt_date date,ccode text,nature_of_case text,active_flag character(1)   "
			+ ")   "
			+ "where    "
			+ "nature_of_case='वारस फेरफार विलंब शुल्‍क'      "
			+ "and khata_no= ?7   "
			+ "and trim(ccode)= ?6    "
			+ "and active_flag='Y' "
	,nativeQuery=true)
	List<Object[]> getBySankirnReceiptDetail(String fHost,String fDbName,String fSchemaName,String fUser,String fPassword,
			String cCode, String khataNo, char c);
}
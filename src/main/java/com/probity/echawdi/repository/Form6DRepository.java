package com.probity.echawdi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.probity.echawdi.entity.Form6D;

public interface Form6DRepository extends JpaRepository<Form6D,Long>{

	@Query(value="SELECT DISTINCT HISSANO FROM "
			+ "( "
			+ "SELECT   "
			+ "PIN ,   "
			+ "CONCAT(     "
			+ "case when pin1 != '' then       pin1 end,     "
			+ "case when pin2 != '' then '' || pin2 end,     "
			+ "case when pin3 != '' then '' || pin3 end,     "
			+ "case when pin4 != '' then '' || pin4 end,     "
			+ "case when pin5 != '' then '' || pin5 end,     "
			+ "case when pin6 != '' then '' || pin6 end,     "
			+ "case when pin7 != '' then '' || pin7 end,     "
			+ "case when pin8 != '' then '' || pin8 end     "
			+ ")as HissaNo ,   "
			+ "MUT_NO   FROM "
			+ "public.dblink "
			+ "( "
			+ "'dbname='|| ?2 ||' host='|| ?1 ||' user='|| ?4 ||' password='|| ?5 , "
			+ "'select   "
			+ "MK.PIN , MK.pin1 , MK.pin2 , MK.pin3 , MK.pin4 , MK.pin5 , MK.pin6 , MK.pin7 , MK.pin8 , "
			+ "MR.MUT_NO,MR.ccode "
			+ "from '|| ?3 ||'.mutregister MR ,'|| ?3 ||'.MUT_KHAREDI MK "
			+ "where   "
			+ "MR.CCODE = MK.CCODE   "
			+ "AND MR.MUT_NO = MK.MUT_NO' "
			+ ") "
			+ "As   "
			+ "( "
			+ "PIN text , pin1 text , pin2 text , pin3 text , pin4 text , pin5 text , pin6 text , pin7 text , pin8 text , MUT_NO text,ccode text "
			+ ") "
			+ "WHERE trim(CCODE) = ?6  AND PIN= ?7  "
			+ ") A" 
			+ "",nativeQuery=true)
	List<Object> findBycCodeAndPin(  
			 String fHost,String fDbName,String fSchemaName,String fUser,String fPassword,
             String cCode , String pin);
	
	@Query(value="SELECT * FROM (SELECT   "
			+ "PIN ,   "
			+ "CONCAT(     "
			+ "case when pin1 != '' then       pin1 end,     "
			+ "case when pin2 != '' then '' || pin2 end,     "
			+ "case when pin3 != '' then '' || pin3 end,     "
			+ "case when pin4 != '' then '' || pin4 end,     "
			+ "case when pin5 != '' then '' || pin5 end,     "
			+ "case when pin6 != '' then '' || pin6 end,     "
			+ "case when pin7 != '' then '' || pin7 end,     "
			+ "case when pin8 != '' then '' || pin8 end     "
			+ ")as HissaNo,   "
			+ "MUT_NO   "
			+ "FROM "
			+ "public.dblink "
			+ "( "
			+ "'dbname='|| ?2 ||' host='|| ?1 ||' user='|| ?4 ||' password='|| ?5 , "
			+ "'select  PIN , MK.pin1 , MK.pin2 , MK.pin3 , MK.pin4 , MK.pin5 , MK.pin6 , MK.pin7 , MK.pin8 , MR.MUT_NO,MR.ccode from   "
			+ "'|| ?3 ||'.mutregister MR ,'|| ?3 ||'.MUT_KHAREDI MK "
			+ "where "
			+ "MR.CCODE = MK.CCODE   "
			+ "AND MR.MUT_NO = MK.MUT_NO' "
			+ ") "
			+ "As   "
			+ "( "
			+ "PIN text , pin1 text , pin2 text , pin3 text , pin4 text , pin5 text , pin6 text , pin7 text , pin8 text , MUT_NO text,ccode text )     "
			+ "where trim(CCODE) = ?6   "
			+ "AND PIN= ?7 "
			+ ") A WHERE HISSANO= ?8" 
			+ "",nativeQuery=true)
	List<Object[]> findByInputs( 
			 String fHost,String fDbName,String fSchemaName,String fUser,String fPassword,
			 String cCode,String pin,String hissaNo);

	@Query(value="SELECT  "
			+ "CASE  "
			+ "WHEN EXISTS( "
			+ "SELECT * FROM( "
			+ "SELECT CCODE,PIN, "
			+ "CONCAT(    "
			+ "case when pin1 != '' then       pin1 end,    "
			+ "case when pin2 != '' then '' || pin2 end,    "
			+ "case when pin3 != '' then '' || pin3 end,    "
			+ "case when pin4 != '' then '' || pin4 end,    "
			+ "case when pin5 != '' then '' || pin5 end,    "
			+ "case when pin6 != '' then '' || pin6 end,    "
			+ "case when pin7 != '' then '' || pin7 end,    "
			+ "case when pin8 != '' then '' || pin8 end    "
			+ ")as HissaNo "
			+ "FROM "
			+ "public.dblink( "
			+ "'dbname='||?2||' host='||?1||' user='||?4||' password='||?5, "
			+ "'select "
			+ "CCODE, "
			+ "PIN, "
			+ "pin1,    "
			+ "pin2,    "
			+ "pin3,    "
			+ "pin4,    "
			+ "pin5,    "
			+ "pin6,    "
			+ "pin7,    "
			+ "pin8, "
			+ "mutation_entry_in_village_form6, "
			+ "ACTIVE_FLAG "
			+ "from '||?3||'.ECHAWDI_FORM6D ') "
			+ "AS "
			+ "( "
			+ "CCODE text, "
			+ "PIN  text, "
			+ "pin1 text,    "
			+ "pin2 text,    "
			+ "pin3 text,    "
			+ "pin4 text,    "
			+ "pin5 text,    "
			+ "pin6 text,    "
			+ "pin7 text,    "
			+ "pin8 text, "
			+ "mutation_entry_in_village_form6 text, "
			+ "ACTIVE_FLAG character(1) "
			+ ") "
			+ "WHERE  "
			+ "trim(CCODE)=?6  "
			+ "AND PIN= ?7 "
			+ "AND mutation_entry_in_village_form6= ?9 "
			+ "AND ACTIVE_FLAG='Y') A WHERE hissaNo= ?8 "
			+ ") "
			+ "THEN true "
			+ "ELSE false  "
			+ "END"
			+ "",nativeQuery = true)
	boolean existsByMutationNo(
			String eHost,String eDbName,String eSchemaName,String eUser,String ePassword,
			String cCode,String pin,String hissaNo,String mutationEntryInVillageForm6
			);

	List<Form6D> findBycCodeAndPinAndActiveFlag(String cCode, String pin,Character activeFlag);
	
	List<Form6D> findBycCodeAndActiveFlag(String cCode, Character activeFlag);
}

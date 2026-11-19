package com.probity.echawdi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.probity.echawdi.entity.Form7B;

/**
 * @author Bhagyashri Lolage
 *
 */
@Repository
public interface Form7BRepository  extends JpaRepository<Form7B, Long> {
		
	List<Form7B> findBycCodeAndPinAndActiveFlag(String cCode, String pin, Character activeFlag) ;

	List<Form7B> findBycCodeAndActiveFlagOrderByPinAsc(String cCode, Character activeFlag);
	
	@Query(value="select  "
			+ "distinct  "
			+ "khata_no, "
			+ "pin, "
			+ "hissa_no from  "
			+ "(  "
			+ "select  "
			+ "khata_no, "
			+ "pin, "
			+ "CONCAT(  "
			+ "case when pin1 != '' then   		   pin1 end, "
			+ "case when pin2 != '' then concat('/' , pin2) end, "
			+ "case when pin3 != '' then concat('/' , pin3) end, "
			+ "case when pin4 != '' then concat('/' , pin4) end, "
			+ "case when pin5 != '' then concat('/' , pin5) end, "
			+ "case when pin6 != '' then concat('/' , pin6) end, "
			+ "case when pin7 != '' then concat('/' , pin7) end, "
			+ "case when pin8 != '' then concat('/' , pin8) end)as Hissa_No  "
			+ "from public.dblink "
			+ " ( "
			+ "' dbname='|| ?2 ||' host='|| ?1 ||' user='|| ?4 || ' password='|| ?5 , "
			+ "'select  "
			+ "khata_no, "
			+ "pin, "
			+ "F7.pin1, "
			+ "F7.pin2, "
			+ "F7.pin3, "
			+ "F7.pin4, "
			+ "F7.pin5, "
			+ "F7.pin6, "
			+ "F7.pin7, "
			+ "F7.pin8, "
			+ "F7.ccode "
			+ "from '|| ?3 || '.form7_khata f7 ' "
			+ ") "
			+ "AS "
			+ "( "
			+ "khata_no text, "
			+ "pin    text, "
			+ "pin1 text, "
			+ "pin2 text, "
			+ "pin3 text, "
			+ "pin4 text, "
			+ "pin5 text, "
			+ "pin6 text, "
			+ "pin7 text, "
			+ "pin8 text, "
			+ "ccode text "
			+ ") "
			+ "where  "
			+ "trim(cCode) = ?6 "
			+ "AND pin = ?7  "
			+ ")  "
			+ "a where hissa_no= ?8 "
			+ ""
			,nativeQuery=true)
	List<Object[]> getKhataNodetails(String fHost,String fDbName,String fSchemaName,String fUser,String fPassword,
			String cCode, String pin,String hissaNo);
}
package com.probity.echawdi.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.probity.echawdi.entity.Form17NoKaJaPa;

@Repository
public interface Form17NoKaJaPaRepository extends JpaRepository<Form17NoKaJaPa, Long>{

	List<Form17NoKaJaPa> findBycCodeAndKhataNoAndRevenueYearAndActiveFlag(String cCode,String khataNo, String revenueYear, Character activeFlag);
	
	@Query(value="select "
			+ "khataNo,personLiable,"
			+ "sum(areaAffected) as areaAffected,"
			+ "sum(assessment) as assessment,"
			+ "sum(amountOfJm) as amountOfJm,"
			+ "sum(amountOfGp) as amountOfGp,"
			+ "sum(amountOfZp) as amountOfZp "
			+ "from Form17NoKaJaPa "
			+ "where "
			+ "trim(cCode)=?1 "
			+ "and revenueYear=?2 "
			+ "and activeFlag=?3 "
			+ "and khataNo not in "
			+ "(select khataNo from LandRevenueDemand l where trim(l.cCode)=?1 and l.revenueYear=?2 and l.activeFlag in('Y','M')) "
			+ "group by khataNo,personLiable")
	List<Object[]> findAllBycCodeAndRevenueYearAndActiveFlag(String cCode,String revenueYear,Character activeFlag);
	
	@Query(value="select * from     "
			+ "public.dblink('dbname='|| ?2 ||' host='|| ?1 ||' port=5432 user='|| ?4 ||' password='|| ?5 ,   "
			+ "'SELECT ccode,id,khata_no,person_liable,survey_hissa,total_area,uom_of_total_area    "
			+ "FROM '  || ?3 ||'.echawdi_form17_no_ka_ja_pa' )   "
			+ "AS data (ccode text,id bigint,khata_no text,person_liable text,survey_hissa text,total_area text,uom_of_total_area text)    "
			+ "where trim(ccode)=?6 and id= ?7   "
	+ "", nativeQuery = true)
	List<Object[]> getDetailsById(String eHost,String eDbName,String eSchemaName,String eUser,String ePassword,
			String cCode, Long id);
	
	@Query(value="select khata_no, survey_hissa, total_area, assessment, person_liable, order_no, uom_of_total_area, location_of_land, prayojan_type, amount_of_gp, amount_of_jm, amount_of_zp, remarks "
			+ "from "
			+ "(select * from public.dblink('dbname='||?2 ||' host='||?1||' user='||?4||' password='||?5,"
			+ "'select khata_no, survey_hissa, total_area, assessment, person_liable, order_no, uom_of_total_area, location_of_land, prayojan_type, amount_of_gp, amount_of_jm, amount_of_zp, remarks, ccode, active_flag, revenue_year "
			+ " from '||?3||'.echawdi_form17_no_ka_ja_pa') "
			+ "AS(khata_no text, survey_hissa character varying, total_area numeric, assessment numeric, person_liable character varying, order_no character varying, uom_of_total_area character varying, location_of_land character varying, prayojan_type character varying, "
			+ " amount_of_gp numeric, amount_of_jm numeric, amount_of_zp numeric, remarks character varying, ccode text, active_flag character, revenue_year character varying )"
			+ "where trim(ccode)=?6 and active_flag='Y' and revenue_year=?7 "
			+ ")a "
	,nativeQuery=true)
	List<Object[]> getKajapaRecords(
			String fHost,String fDbName,String fSchemaName,String fUser,String fPassword,
			String cCode, String revenueYear);
	
	
	@Query(value = "SELECT rate "
	        + "FROM dblink( "
	        + " 'dbname=' || ?2 || ' host=' || ?1 || ' user=' || ?4 || ' password=' || ?5 , "
	        + " 'SELECT rate, ccode, pin FROM  '|| ?3 ||' .m_manapa_npa_village') "
	        + " AS mn(rate text, ccode text, pin text) "
	        + " WHERE trim(ccode) = ?6 AND pin = ?7 limit 1 ",
	        nativeQuery = true)
	BigDecimal getnapamanaparate(String fHost, String fDbName,String fSchemaName,  String fUser,
            String fPassword,String ccode, String pin);
	
	
	
	
	
	
	
}
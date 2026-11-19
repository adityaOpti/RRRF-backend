package com.probity.echawdi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.probity.echawdi.entity.EChawdiTypeMaster;
import com.probity.echawdi.entity.EchawdiInspection;

public interface EchawdiInspectionRepository extends JpaRepository<EchawdiInspection, Long> { 
	

	/*@Query(value =
		    "SELECT echawdi_type, remark FROM " +
		    "public.dblink('dbname='|| 'echnag' ||" +
		   // "' host='|| '10.10.248.108' ||" +
		    "' host='|| '10.48.65.110' ||" +
		    "' port=5432 user='|| 'postgres' ||" +
		    "' password='|| 'jduri%D^7f9gf', " +

		    "'SELECT e.echawdi_type, e.remark, e.ccode, e.district_code, e.taluka_code, e.active_flag " +
		    "FROM ' || 'echnag_kuh' || '.echawdi_inspection e') " +

		    "AS a(echawdi_type BIGINT, remark TEXT, ccode TEXT, district_code INTEGER, taluka_code INTEGER, active_flag CHARACTER(1)) " +

		    "WHERE ccode = :ccode " +
		    "AND district_code = :districtCode " +
		    "AND taluka_code = :talukaCode " +
		    "AND active_flag = 'Y'",
		    nativeQuery = true)
		List<Object[]> fetchEchawadiRemark(
		        String ccode,
		         Integer districtCode,
		         Integer talukaCode,
		         String dbName,
		         String schemaName,
		         String user,
		         String password
		);*/

	
	@Query(value =
		    "SELECT echawdi_type, remark FROM " +
		    "public.dblink(" +
		    "'dbname=' || :dbName || " +
		    "' host=' || '10.48.65.110' || " +
		    "' port=5432 user=' || :dbUser || " +
		    "' password=' || :dbPassword, " +

		    "'SELECT e.echawdi_type, e.remark, e.ccode, e.district_code, e.taluka_code, e.active_flag " +
		    "FROM ' || :schemaName || '.echawdi_inspection e') " +

		    "AS a(echawdi_type BIGINT, remark TEXT, ccode TEXT, district_code INTEGER, taluka_code INTEGER, active_flag CHARACTER(1)) " +

		    "WHERE ccode = :ccode " +
		    "AND district_code = :districtCode " +
		    "AND taluka_code = :talukaCode " +
		    "AND active_flag = 'Y'",
		    nativeQuery = true)
		List<Object[]> fetchEchawadiRemark(
		        @Param("ccode") String ccode,
		        @Param("districtCode") Integer districtCode,
		        @Param("talukaCode") Integer talukaCode,
		        @Param("dbName") String dbName,
		        @Param("schemaName") String schemaName,
		        @Param("dbUser") String dbUser,
		        @Param("dbPassword") String dbPassword
		);
	
	boolean existsByEchawdiTypeAndDistrictCodeAndTalukaCodeAndCcodeAndRevenueYearAndActiveFlag(
			EChawdiTypeMaster echawdiType, Integer districtCode, Integer talukaCode, String ccode, String revenueYear,
			String activeFlag);

}
package com.probity.echawdi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.probity.echawdi.entity.RateMasterUc;

@Repository
public interface AkrushakDarRepository extends JpaRepository<RateMasterUc, Integer>{
	//digambar sir query 
			@Query(value = 

				    "SELECT T2.rate, T2.flag as rate_flag, T2.name, T1.flag as paise_flag, trim(T1.ccode) AS piase_ccode, T2.ccode AS rate_ccode " +
				    "FROM ( " +
				    "  SELECT ccode, flag " +
				    "  FROM public.dblink( " +
				    "    'dbname=' || ?2 || ' host=' || ?1 || ' port=' || ?6 || ' user=' || ?4 || ' password=' || ?5, " +
				    "    'SELECT DISTINCT p.ccode, p.flag FROM ' || ?3 || '.m_paise_village p' " +
				    "  ) AS Fer1(ccode text, flag character varying) " +
				    "  WHERE trim(ccode) = ?7 " +
				    ") T1 " +
				    "FULL OUTER JOIN ( " +
				    "  SELECT DISTINCT ccode, rate, flag, name " +
				    "  FROM public.dblink( " +
				    "    'dbname=' || ?2 || ' host=' || ?1 || ' port=' || ?6 || ' user=' || ?4 || ' password=' || ?5, " +
				    "    'SELECT rm.ccode, rm.rate, rm.flag, rm.name FROM ' || ?3 || '.rate_master_uc rm' " +
				    "  ) AS Fer2(ccode text, rate numeric, flag character varying, name character varying) " +
				    "  WHERE trim(ccode) = ?7 " +
				    ") T2 ON (T1.ccode = T2.ccode)",
				    nativeQuery = true)
				List<Object[]> fetchAkruShakRate(
				    String fhost,
				    String fDbname,
				    String fSchemaName,
				    String fuser,
				    String fpassword,
				    String port,
				    String ccode
				);
}
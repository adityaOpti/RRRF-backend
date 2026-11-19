package com.probity.echawdi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.probity.echawdi.entity.TalathiRemarkInspection;
import com.probity.echawdi.service.TalathiRemarkRepositoryCustom;

public interface TalathiRemarkInspectionRepository extends JpaRepository<TalathiRemarkInspection,Long>, TalathiRemarkRepositoryCustom {
	boolean existsByCcode(String ccode);

	TalathiRemarkInspection findByCcode(String ccode);

	/* @Query(value =
	    	    "SELECT " +
	    	    "create_dt_tm AS created_dt_tm, " +
	    	    "CAST(remark AS TEXT) AS remark, " +
	    	    "CAST(designation AS TEXT) AS designation, " +
	    	    "CAST(inspection_officer_username AS TEXT) AS inspection_officer_username, " +
	    	    "CAST(ccode AS TEXT) AS ccode, " +
	    	    "CAST(revenue_year AS TEXT) AS revenue_year " +
	    	    "FROM dblink( " +
	    	    "'dbname=' || ?2 || ' host=' || ?1 || ' user=' || ?4 || ' password=' || ?5, " +
	    	    "'SELECT create_dt_tm, remark, designation, inspection_officer_username, ccode, revenue_year " +
	    	    "FROM ' || ?3 || '.talathi_remark_inspection') " +
	    	    "AS t( " +
	    	    "create_dt_tm TIMESTAMP, " +
	    	    "remark TEXT, " +
	    	    "designation TEXT, " +
	    	    "inspection_officer_username TEXT, " +
	    	    "ccode TEXT, " +
	    	    "revenue_year TEXT) " +
	    	    "WHERE ccode = ?6 " +
	    	    "AND revenue_year = ?7 " +
	    	    "ORDER BY create_dt_tm ASC",
                 nativeQuery = true)*/
	/*@Query(value =
	        "SELECT " +
	        "create_dt_tm AS created_dt_tm, " +
	        "CAST(remark AS TEXT) AS remark, " +
	        "CAST(designation AS TEXT) AS designation, " +
	        "CAST(inspection_officer_username AS TEXT) AS inspection_officer_username, " +
	        "CAST(ccode AS TEXT) AS ccode, " +
	        "CAST(revenue_year AS TEXT) AS revenue_year " +

	        "FROM dblink( " +

	        "CAST( " +
	        "'dbname=' || CAST(?2 AS TEXT) || " +
	        "' host=' || CAST(?1 AS TEXT) || " +
	        "' user=' || CAST(?4 AS TEXT) || " +
	        "' password=' || CAST(?5 AS TEXT) " +
	        "AS TEXT), " +

	        "CAST( " +
	        "'SELECT create_dt_tm, remark, designation, inspection_officer_username, ccode, revenue_year " +
	        "FROM ' || CAST(?3 AS TEXT) || '.talathi_remark_inspection' " +
	        "AS TEXT) " +

	        ") AS t( " +
	        "create_dt_tm TIMESTAMP, " +
	        "remark TEXT, " +
	        "designation TEXT, " +
	        "inspection_officer_username TEXT, " +
	        "ccode TEXT, " +
	        "revenue_year TEXT " +
	        ") " +

	        "WHERE ccode = CAST(?6 AS TEXT) " +
	        "AND revenue_year = CAST(?7 AS TEXT) " +
	        "ORDER BY create_dt_tm ASC",
	        nativeQuery = true)
	    public List<Object[]> getTalathiRemarkInspection(
	    		String echHost,
	    		String echDbname,
	    		String echSchemaName,
	    		String echUser,
	    		String echPassword,
	    		String ccode,
	    		String revenueYear);*/

}

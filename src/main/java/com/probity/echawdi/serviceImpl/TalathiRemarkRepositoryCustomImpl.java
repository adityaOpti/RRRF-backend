package com.probity.echawdi.serviceImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.probity.echawdi.service.TalathiRemarkRepositoryCustom;

@Repository
public class TalathiRemarkRepositoryCustomImpl
        implements TalathiRemarkRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Object[]> getTalathiRemarkInspection(
            String host,
            String dbName,
            String schema,
            String user,
            String password,
            String ccode,
            String revenueYear) {

     /*   String sql =
                "SELECT * FROM dblink(" +
                "'dbname=" + dbName +
                " host=" + host +
                " user=" + user +
                " password=" + password + "', " +

                "'SELECT create_dt_tm, remark, designation, " +
                "inspection_officer_username, ccode, revenue_year " +
                "FROM " + schema + ".talathi_remark_inspection " +
                "WHERE ccode = '''" + ccode + "''' " +
                "AND revenue_year = '''" + revenueYear + "''' " +
                "ORDER BY create_dt_tm ASC' " +

                ") AS t(" +
                "create_dt_tm TIMESTAMP, " +
                "remark TEXT, " +
                "designation TEXT, " +
                "inspection_officer_username TEXT, " +
                "ccode TEXT, " +
                "revenue_year TEXT)";*/
    	
    	
    	String sql =
    	        "SELECT * FROM dblink(" +
    	        "'dbname=" + dbName +
    	        " host=" + host +
    	        " user=" + user +
    	        " password=" + password + "', " +

    	        "'SELECT create_dt_tm, remark, designation, " +
    	        "inspection_officer_username, ccode, revenue_year " +
    	        "FROM " + schema + ".talathi_remark_inspection " +
    	        "WHERE ccode = ''" + ccode + "'' " +
    	        "AND revenue_year = ''" + revenueYear + "'' " +
    	        "ORDER BY create_dt_tm ASC' " +

    	        ") AS t(" +
    	        "create_dt_tm TIMESTAMP, " +
    	        "remark TEXT, " +
    	        "designation TEXT, " +
    	        "inspection_officer_username TEXT, " +
    	        "ccode TEXT, " +
    	        "revenue_year TEXT)";

        return entityManager
                .createNativeQuery(sql)
                .getResultList();
    }
}
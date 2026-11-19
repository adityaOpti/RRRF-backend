package com.probity.echawdi.serviceImpl;



import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.probity.echawdi.repository.VillageRepositoryCustom;

@Repository
public class VillageRepositoryImpl implements VillageRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;
    
   
    @Override
    public List<Object[]> getVillageListByTalukaAndDistrict(
            String host,
            String dbName,
            String schemaName,
            String user,
            String password,
            String username,
            String talukaCode,
            String districtCode) {

        String sql =
                "SELECT DISTINCT " +
                "v.ccode, " +
                "v.district_code, " +
                "v.taluka_code, " +
                "v.village_code, " +
                "v.village_name, " +
                "v.sajja_code " +
                "FROM public.dblink(" +
                " 'dbname=" + dbName +
                " host=" + host +
                " port=5432 user=" + user +
                " password=" + password + "', " +

                " 'SELECT ccode, district_code, taluka_code, " +
                "         village_code, village_name, sajja_code " +
                "  FROM " + schemaName + ".m_village_census " +
                "  WHERE trim(taluka_code) = ''" + talukaCode + "'' " +
                "    AND trim(district_code) = ''" + districtCode + "''' " +

                ") AS v(" +
                " ccode text, " +
                " district_code text, " +
                " taluka_code text, " +
                " village_code text, " +
                " village_name text, " +
                " sajja_code text" +
                ") " +

                "WHERE trim(v.ccode) IN (" +
                " SELECT trim(m.ccode) " +
                " FROM public.dblink(" +
                "  'dbname=echmst host=10.10.248.243 " +
             //   "  'dbname=echmst host=localhost " +
                "   user=postgres password=jduri%D^7f9gf', " +
             //	"   user=postgres password=root', " +
                "  'SELECT ccode, is_selected " +
                "   FROM public.e_lgd_mapper " +
                "   WHERE is_selected = ''Y''' " +
                " ) AS m(" +
                "  ccode text, " +
                "  is_selected character(1)" +
                " )" +
                ") " +

                "ORDER BY v.village_name ASC";

        return entityManager
                .createNativeQuery(sql)
                .getResultList();
    }
    
  
}
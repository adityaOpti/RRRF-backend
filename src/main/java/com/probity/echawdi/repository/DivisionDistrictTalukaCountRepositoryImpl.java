package com.probity.echawdi.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

@Repository
public class DivisionDistrictTalukaCountRepositoryImpl
        implements DivisionDistrictTalukaCountRepositoryCustom {
	
	 

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Object[]> getTotalByDivisionCodeUpdatedMISPre(
            String divisionCode,
            String yearName) {

        String tableName = "e_lgd_mapper_" + yearName;

        String sql =
                "select "
                + "sum(totalVillagesCount) totalVillagesCount, "
                + "sum(totalEntryCount) totalEntryCount, "
                + "sum(talukaCount) talukaCount, "
                + "count(*) districtCount, "
                + "sum(totalGaonNamunaCount) totalGaonNamunaCount, "
                + "sum(totalDemandCompletedCount) totalDemandCompletedCount, "
                + "sum(vasuliWithoutDemandCount) vasuliWithoutDemandCount, "
                + "sum(vasuliCompletedCount) vasuliCompletedCount, "
                + "sum(demandCount) demandCount "
                + "from ( "
                + "select "
                + "f.division_code, "
                + "f.district_code, "
                + "f.district_name, "
                + "(select sum(total_villages) from e_division_district_taluka_count "
                + "where trim(district_code)=trim(f.district_code)) totalVillagesCount, "
                + "(select sum(total_entry) from e_village_entry_count_live "
                + "where trim(district_code)=trim(f.district_code)) totalEntryCount, "
                + "(select count(*) from e_division_district_taluka_count "
                + "where trim(district_code)=trim(f.district_code)) talukaCount, "
                + "(select count(ccode) from " + tableName + " "
                + "where is_gaon_namuna_added='Y' and district_code=f.district_code) totalGaonNamunaCount, "
                + "(select count(ccode) from " + tableName + " "
                + "where is_demand_completed='Y' and district_code=f.district_code) totalDemandCompletedCount, "
                + "(select count(ccode) from " + tableName + " "
                + "where is_vasuli_without_demandadd='Y' and district_code=f.district_code) vasuliWithoutDemandCount, "
                + "(select count(ccode) from " + tableName + " "
                + "where is_vasuli_completed='Y' and district_code=f.district_code) vasuliCompletedCount, "
                + "(select count(ccode) from " + tableName + " "
                + "where is_demand_completed='N' and is_challan_generated='N' "
                + "and district_code=f.district_code) demandCount "
                + "from e_district_census f "
                + "order by cast(district_code as int) "
                + ") B "
                + "where division_code=?1 "
                + "group by division_code";

        Query query = entityManager.createNativeQuery(sql);

        query.setParameter(1, divisionCode);

        return query.getResultList();
    }
    
    
    
    @Override
    public List<Object[]> getTotalByAllDivisionCodeUpdatedMISPre(String yearName) {

        String tableName = "e_lgd_mapper_" + yearName;

        String sql =
                "select "
                + "sum(totalVillagesCount) totalVillagesCount, "
                + "sum(totalEntryCount) totalEntryCount, "
                + "sum(talukaCount) talukaCount, "
                + "count(*) districtCount, "
                + "sum(totalGaonNamunaCount) totalGaonNamunaCount, "
                + "sum(totalDemandCompletedCount) totalDemandCompletedCount, "
                + "sum(vasuliWithoutDemandCount) vasuliWithoutDemandCount, "
                + "sum(vasuliCompletedCount) vasuliCompletedCount, "
                + "sum(demandCount) demandCount "
                + "from ( "
                + "select "
                + "f.division_code, "
                + "f.district_code, "
                + "f.district_name, "
                + "(select sum(total_villages) "
                + "from e_division_district_taluka_count "
                + "where trim(district_code)=trim(f.district_code)) totalVillagesCount, "
                + "(select sum(total_entry) "
                + "from e_village_entry_count_live "
                + "where trim(district_code)=trim(f.district_code)) totalEntryCount, "
                + "(select count(*) "
                + "from e_division_district_taluka_count "
                + "where trim(district_code)=trim(f.district_code)) talukaCount, "
                + "(select count(ccode) from " + tableName + " "
                + "where is_gaon_namuna_added='Y' "
                + "and district_code=f.district_code) totalGaonNamunaCount, "
                + "(select count(ccode) from " + tableName + " "
                + "where is_demand_completed='Y' "
                + "and district_code=f.district_code) totalDemandCompletedCount, "
                + "(select count(ccode) from " + tableName + " "
                + "where is_vasuli_without_demandadd='Y' "
                + "and district_code=f.district_code) vasuliWithoutDemandCount, "
                + "(select count(ccode) from " + tableName + " "
                + "where is_vasuli_completed='Y' "
                + "and district_code=f.district_code) vasuliCompletedCount, "
                + "(select count(ccode) from " + tableName + " "
                + "where is_demand_completed='N' "
                + "and is_challan_generated='N' "
                + "and district_code=f.district_code) demandCount "
                + "from e_district_census f "
                + "order by cast(district_code as int) "
                + ") B";

        Query query = entityManager.createNativeQuery(sql);

        return query.getResultList();
    }
    
    
    
    @Override
    public List<Object[]> getByDivisionCodeUpdatedMisPre(
            String divisionCode,
            String yearName) {

        String tableName = "e_lgd_mapper_" + yearName;
        String misTableName = "e_lgd_mapper_mis_" + yearName;

        String sql =
                "select "
                + "f.district_code, "
                + "f.district_name, "
                + "(select sum(total_villages) "
                + "from e_division_district_taluka_count "
                + "where district_code=f.district_code) totalVillagesCount, "
                + "(select sum(total_entry) "
                + "from e_village_entry_count_live "
                + "where district_code=f.district_code) totalEntryCount, "
                + "(select count(*) "
                + "from e_division_district_taluka_count "
                + "where district_code=f.district_code) talukaCount, "
                + "(select count(division_code) "
                + "from e_district_census "
                + "where district_code=f.district_code) districtCount, "
                + "(select count(ccode) from " + tableName + " "
                + "where is_gaon_namuna_added='Y' "
                + "and district_code=f.district_code) totalGaonNamunaCount, "
                + "(select count(ccode) from " + tableName + " "
                + "where is_demand_completed='Y' "
                + "and district_code=f.district_code) totalDemandCompletedCount, "
                + "(select count(ccode) from " + tableName + " "
                + "where is_vasuli_without_demandadd='Y' "
                + "and district_code=f.district_code) vasuliWithoutDemandCount, "
                + "(select count(ccode) from " + tableName + " "
                + "where is_vasuli_completed='Y' "
                + "and district_code=f.district_code) vasuliCompletedCount, "
                + "(select count(ccode) from " + tableName + " "
                + "where is_demand_completed='N' "
                + "and is_challan_generated='N' "
                + "and district_code=f.district_code) demandCount ,"
                
                //new line added for mis vasuli API 02/11/2026
                + "(select sum(total_demand_jm) from " + misTableName + " where district_code=f.district_code) totalDemandJm, "
                + "(select sum(total_demand_sankirn) from " + misTableName + " where district_code=f.district_code) totalDemandSankirn, "
                + "(select sum(total_collected_jm) from " + misTableName + " where district_code=f.district_code) totalCollectedJm, "
                //04/11
                + "(select sum(total_collected_sankirn) from " + misTableName + " where district_code=f.district_code) totalCollectedSankirn "
     

                + "from e_district_census f "
                + "where division_code=?1 "
                + "order by cast(district_code as int)";

        Query query = entityManager.createNativeQuery(sql);

        query.setParameter(1, divisionCode);

        return query.getResultList();
    }
    
    
    
    
    @Override
    public List<Object[]> getByAllDivisionCodeUpdatedMIsPre(String yearName) {

        String tableName = "e_lgd_mapper_" + yearName;
        
        String misTableName = "e_lgd_mapper_mis_" + yearName;


        String sql =
                "select "
                + "f.division_code, "
                + "f.division_name, "
                + "(select sum(total_villages) "
                + "from e_division_district_taluka_count "
                + "where division_code=f.division_code) totalVillagesCount, "
                + "(select sum(total_entry) "
                + "from e_village_entry_count_live "
                + "where division_code=f.division_code) totalEntryCount, "
                + "(select count(*) "
                + "from e_division_district_taluka_count "
                + "where division_code=f.division_code) talukaCount, "
                + "(select count(division_code) "
                + "from e_district_census "
                + "where division_code=f.division_code) districtCount, "
                + "(select count(ccode) from " + tableName + " "
                + "where is_gaon_namuna_added='Y' "
                + "and division_code=f.division_code) totalGaonNamunaCount, "
                + "(select count(ccode) from " + tableName + " "
                + "where is_demand_completed='Y' "
                + "and division_code=f.division_code) totalDemandCompletedCount, "
                + "(select count(ccode) from " + tableName + " "
                + "where is_vasuli_without_demandadd='Y' "
                + "and division_code=f.division_code) vasuliWithoutDemandCount, "
                + "(select count(ccode) from " + tableName + " "
                + "where is_vasuli_completed='Y' "
                + "and division_code=f.division_code) vasuliCompletedCount, "
                + "(select count(ccode) from " + tableName + " "
                + "where is_demand_completed='N' "
                + "and is_challan_generated='N' "
                + "and division_code=f.division_code) demandCount, "
                
                //new line added for mis vasuli API 02/11/2026
                
                + "(select sum(total_demand_jm) "
                + "from " + misTableName + " "
                + "where division_code=f.division_code) totalDemandJm, "
                + "(select sum(total_demand_sankirn) "
                + "from " + misTableName + " "
                + "where division_code=f.division_code) totalDemandSankirn, "
                + "(select sum(total_collected_jm) "
                + "from " + misTableName + " "
                + "where division_code=f.division_code) totalCollectedJm, "
                + "(select sum(total_collected_sankirn) "
                + "from " + misTableName + " "
                + "where division_code=f.division_code) totalCollectedSankirn "
                + "from e_division_census f";

        Query query = entityManager.createNativeQuery(sql);

        return query.getResultList();
    }
    
    
    
    @Override
    public List<Object[]> getByTalukaNameAndCodePre(
            String divisionCode,
            String districtCode,
            String yearName) {

        String tableName = "e_lgd_mapper_" + yearName;

        String sql =
                "select "
                + "f.district_code, "
                + "f.district_name, "
                + "et.taluka_code, "
                + "et.taluka_name, "

                + "(select count(*) "
                + "from e_lgd_mapper "
                + "where taluka_code=et.taluka_code "
                + "and district_code=f.district_code) totalVillagesCount, "

                + "(select sum(total_entry) "
                + "from e_village_entry_count_live "
                + "where taluka_code=et.taluka_code "
                + "and district_code=f.district_code) totalEntryCount, "

                + "(select count(*) "
                + "from e_division_district_taluka_count "
                + "where taluka_code=et.taluka_code) talukaCount, "

                + "(select count(distinct division_code) "
                + "from e_district_census "
                + "where district_code=f.district_code) districtCount, "

                + "(select count(distinct ccode) from " + tableName + " "
                + "where is_gaon_namuna_added='Y' "
                + "and taluka_code=et.taluka_code "
                + "and district_code=f.district_code) totalGaonNamunaCount, "

                + "(select count(distinct ccode) from " + tableName + " "
                + "where is_demand_completed='Y' "
                + "and taluka_code=et.taluka_code "
                + "and district_code=f.district_code) totalDemandCompletedCount, "

                + "(select count(distinct ccode) from " + tableName + " "
                + "where is_vasuli_without_demandadd='Y' "
                + "and taluka_code=et.taluka_code "
                + "and district_code=f.district_code) vasuliWithoutDemandCount, "

                + "(select count(distinct ccode) from " + tableName + " "
                + "where is_vasuli_completed='Y' "
                + "and taluka_code=et.taluka_code "
                + "and district_code=f.district_code) vasuliCompletedCount, "

                + "(select count(distinct ccode) from " + tableName + " "
                + "where is_demand_completed='N' "
                + "and is_challan_generated='N' "
                + "and taluka_code=et.taluka_code "
                + "and district_code=f.district_code) demandCount "

                + "from e_district_census f "
                + "inner join e_taluka_census et "
                + "on et.district_code=f.district_code "
                + "where f.division_code=?1 "
                + "and f.district_code=?2";

        Query query = entityManager.createNativeQuery(sql);

        query.setParameter(1, divisionCode);
        query.setParameter(2, districtCode);

        return query.getResultList();
    }
    
    
   
    
}
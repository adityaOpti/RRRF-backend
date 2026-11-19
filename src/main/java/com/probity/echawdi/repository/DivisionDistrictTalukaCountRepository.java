/**
 * 
 */
package com.probity.echawdi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.probity.echawdi.entity.DivisionDistrictTalukaCount;

/**
 * @author Ashish Dahale
 *
 */

@Repository
public interface DivisionDistrictTalukaCountRepository extends JpaRepository<DivisionDistrictTalukaCount, Long>, DivisionDistrictTalukaCountRepositoryCustom{
	
	//In this Repo there are two Queries of Same Name one for curent Year another is for Previous Year
	//new 2024-25
	@Query(value="select  "
			+ "f.district_code, "
			+ "f.district_name, "
			+ "(select sum(total_villages) from e_division_district_taluka_count where district_code=f.district_code) totalVillagesCount, "
			+ "(select sum(total_phase1_villages) from e_division_district_taluka_count where district_code=f.district_code) phase1VillagesCount, "
			+ "(select sum(total_phase1_login) from e_village_entry_count_live  where district_code=f.district_code) phase1VillagesLoginCount, "
			+ "(select sum(total_entry) from e_village_entry_count_live  where district_code=f.district_code) totalEntryCount, "
			+ "(select count(*) from e_division_district_taluka_count where district_code=f.district_code) talukaCount,"
			+ "(select count(ccode) from e_lgd_mapper where is_challan_generated='Y' and district_code=f.district_code) totalOnlineVillagesCount,"
			+ "(select count(division_code) from e_district_census where district_code=f.district_code) districtCount,"
			+ "(select sum(total_khata_numbers) from e_division_district_taluka_count where district_code=f.district_code) totalKhataNos "
			+ "from  e_district_census f where division_code=?1 order by cast(district_code as int)"
			+ "" , nativeQuery=true)
	List<Object[]> getByDivisionCode(String disionCode);
	
	
	//pre 2023-24
	@Query(value="select  "
			+ "f.district_code, "
			+ "f.district_name, "
			+ "(select sum(total_villages) from e_division_district_taluka_count where district_code=f.district_code) totalVillagesCount, "
			+ "(select sum(total_phase1_villages) from e_division_district_taluka_count where district_code=f.district_code) phase1VillagesCount, "
			+ "(select sum(total_phase1_login) from e_village_entry_count_live  where district_code=f.district_code) phase1VillagesLoginCount, "
			+ "(select sum(total_entry) from e_village_entry_count_live  where district_code=f.district_code) totalEntryCount, "
			+ "(select count(*) from e_division_district_taluka_count where district_code=f.district_code) talukaCount,"
			+ "(select count(ccode) from e_lgd_mapper_2023 where is_challan_generated='Y' and district_code=f.district_code) totalOnlineVillagesCount,"
			+ "(select count(division_code) from e_district_census where district_code=f.district_code) districtCount,"
			+ "(select sum(total_khata_numbers) from e_division_district_taluka_count where district_code=f.district_code) totalKhataNos "
			+ "from  e_district_census f where division_code=?1 order by cast(district_code as int)"
			+ "" , nativeQuery=true)
	List<Object[]> getByDivisionCodePre(String disionCode);
	
	
	//new 2024-25
	@Query(value="select    "
			+ "f.division_code,   "
			+ "f.division_name,   "
			+ "(select sum(total_villages) from e_division_district_taluka_count where division_code=f.division_code) totalVillagesCount,   "
			+ "(select sum(total_phase1_villages) from e_division_district_taluka_count where division_code=f.division_code) phase1VillagesCount,   "
			+ "(select sum(total_phase1_login) from e_village_entry_count_live  where division_code=f.division_code) phase1VillagesLoginCount,   "
			+ "(select sum(total_entry) from e_village_entry_count_live  where division_code=f.division_code) totalEntryCount,   "
			+ "(select count(*) from e_division_district_taluka_count where division_code=f.division_code) talukaCount,  "
			+ "(select count(ccode) from e_lgd_mapper where is_challan_generated='Y' and division_code=f.division_code) totalOnlineVillagesCount,"
			+ "(select count(division_code) from e_district_census where division_code=f.division_code) districtCount,"
			+ "(select sum(total_khata_numbers) from e_division_district_taluka_count where division_code=f.division_code) totalKhataNos   "
			+ "from  e_division_census f "
			+ "" , nativeQuery=true)
	List<Object[]> getByAllDivisionCode();
	
	
	//pre 2023-24
	
	@Query(value="select    "
			+ "f.division_code,   "
			+ "f.division_name,   "
			+ "(select sum(total_villages) from e_division_district_taluka_count where division_code=f.division_code) totalVillagesCount,   "
			+ "(select sum(total_phase1_villages) from e_division_district_taluka_count where division_code=f.division_code) phase1VillagesCount,   "
			+ "(select sum(total_phase1_login) from e_village_entry_count_live  where division_code=f.division_code) phase1VillagesLoginCount,   "
			+ "(select sum(total_entry) from e_village_entry_count_live  where division_code=f.division_code) totalEntryCount,   "
			+ "(select count(*) from e_division_district_taluka_count where division_code=f.division_code) talukaCount,  "
			+ "(select count(ccode) from e_lgd_mapper_2023 where is_challan_generated='Y' and division_code=f.division_code) totalOnlineVillagesCount,"
			+ "(select count(division_code) from e_district_census where division_code=f.division_code) districtCount,"
			+ "(select sum(total_khata_numbers) from e_division_district_taluka_count where division_code=f.division_code) totalKhataNos   "
			+ "from  e_division_census f "
			+ "" , nativeQuery=true)
	List<Object[]> getByAllDivisionCodePre();
	
	
	//new 2024-25
	@Query(value="select  "
			+ "division_code, "
			+ "sum(totalVillagesCount) totalVillagesCount, "
			+ "sum(phase1VillagesCount) phase1VillagesCount, "
			+ "sum(phase1VillagesLoginCount) phase1VillagesLoginCount, "
			+ "sum(totalEntryCount) totalEntryCount, "
			+ "sum(talukaCount) talukaCount ,"
			+ "count(*) districtCount,"
			+ "sum(totalOnlineVillagesCount) totalOnlineVillagesCount,"
			+ "sum(totalKhataNos) "
			+ "from "
			+ "( "
			+ "select "
			+ "f.division_code, "
			+ "f.district_code,"
			+ "f.district_name, "
			+ "(select sum(total_villages) from e_division_district_taluka_count where trim(district_code)=trim(f.district_code)) totalVillagesCount, "
			+ "(select sum(total_phase1_villages) from e_division_district_taluka_count where trim(district_code)=trim(f.district_code)) phase1VillagesCount, "
			+ "(select sum(total_phase1_login) from e_village_entry_count_live where trim(district_code)=trim(f.district_code)) phase1VillagesLoginCount, "
			+ "(select sum(total_entry) from e_village_entry_count_live  where trim(district_code)=trim(f.district_code)) totalEntryCount, "
			+ "(select count(*) from e_division_district_taluka_count where trim(district_code)=trim(f.district_code)) talukaCount,"
			+ "(select count(ccode) from e_lgd_mapper where is_challan_generated='Y' and trim(district_code)=trim(f.district_code)) totalOnlineVillagesCount,"
			+ "(select sum(total_khata_numbers) from e_division_district_taluka_count where trim(district_code)=trim(f.district_code)) totalKhataNos "
			+ "from  e_district_census f order by cast(district_code as int) "
			+ ")B where division_code=?1 "
			+ "group by division_code;"
			+ "" , nativeQuery=true)
	List<Object[]> getTotalByDivisionCode(String divisionCode);
	
	
	//pre 2023-24
	@Query(value="select  "
			+ "division_code, "
			+ "sum(totalVillagesCount) totalVillagesCount, "
			+ "sum(phase1VillagesCount) phase1VillagesCount, "
			+ "sum(phase1VillagesLoginCount) phase1VillagesLoginCount, "
			+ "sum(totalEntryCount) totalEntryCount, "
			+ "sum(talukaCount) talukaCount ,"
			+ "count(*) districtCount,"
			+ "sum(totalOnlineVillagesCount) totalOnlineVillagesCount,"
			+ "sum(totalKhataNos) "
			+ "from "
			+ "( "
			+ "select "
			+ "f.division_code, "
			+ "f.district_code,"
			+ "f.district_name, "
			+ "(select sum(total_villages) from e_division_district_taluka_count where trim(district_code)=trim(f.district_code)) totalVillagesCount, "
			+ "(select sum(total_phase1_villages) from e_division_district_taluka_count where trim(district_code)=trim(f.district_code)) phase1VillagesCount, "
			+ "(select sum(total_phase1_login) from e_village_entry_count_live where trim(district_code)=trim(f.district_code)) phase1VillagesLoginCount, "
			+ "(select sum(total_entry) from e_village_entry_count_live  where trim(district_code)=trim(f.district_code)) totalEntryCount, "
			+ "(select count(*) from e_division_district_taluka_count where trim(district_code)=trim(f.district_code)) talukaCount,"
			+ "(select count(ccode) from e_lgd_mapper_2023 where is_challan_generated='Y' and trim(district_code)=trim(f.district_code)) totalOnlineVillagesCount,"
			+ "(select sum(total_khata_numbers) from e_division_district_taluka_count where trim(district_code)=trim(f.district_code)) totalKhataNos "
			+ "from  e_district_census f order by cast(district_code as int) "
			+ ")B where division_code=?1 "
			+ "group by division_code;"
			+ "" , nativeQuery=true)
	List<Object[]> getTotalByDivisionCodePre(String divisionCode);
	
	//new  2024-25
	@Query(value="select  "
			+ "sum(totalVillagesCount) totalVillagesCount, "
			+ "sum(phase1VillagesCount) phase1VillagesCount, "
			+ "sum(phase1VillagesLoginCount) phase1VillagesLoginCount, "
			+ "sum(totalEntryCount) totalEntryCount, "
			+ "sum(talukaCount) talukaCount ,"
			+ "count(*) districtCount,"
			+ "sum(totalOnlineVillagesCount) totalOnlineVillagesCount,"
			+ "sum(totalKhataNos) totalKhataNos "
			+ "from "
			+ "( "
			+ "select "
			+ "f.division_code, "
			+ "f.district_code,"
			+ "f.district_name, "
			+ "(select sum(total_villages) from e_division_district_taluka_count where trim(district_code)=trim(f.district_code)) totalVillagesCount, "
			+ "(select sum(total_phase1_villages) from e_division_district_taluka_count where trim(district_code)=trim(f.district_code)) phase1VillagesCount, "
			+ "(select sum(total_phase1_login) from e_village_entry_count_live where trim(district_code)=trim(f.district_code)) phase1VillagesLoginCount, "
			+ "(select sum(total_entry) from e_village_entry_count_live  where trim(district_code)=trim(f.district_code)) totalEntryCount, "
			+ "(select count(*) from e_division_district_taluka_count where trim(district_code)=trim(f.district_code)) talukaCount,"
			+ "(select count(ccode) from e_lgd_mapper where is_challan_generated='Y' and trim(district_code)=trim(f.district_code)) totalOnlineVillagesCount,"
			+ " (select sum(total_khata_numbers) from e_division_district_taluka_count where trim(district_code)=trim(f.district_code)) totalKhataNos "
			+ "from  e_district_census f order by cast(district_code as int) "
			+ ")B  "
			+ "" , nativeQuery=true)
	List<Object[]> getTotalByAllDivisionCode();
	
	
	//pre 2023-24
	@Query(value="select  "
			+ "sum(totalVillagesCount) totalVillagesCount, "
			+ "sum(phase1VillagesCount) phase1VillagesCount, "
			+ "sum(phase1VillagesLoginCount) phase1VillagesLoginCount, "
			+ "sum(totalEntryCount) totalEntryCount, "
			+ "sum(talukaCount) talukaCount ,"
			+ "count(*) districtCount,"
			+ "sum(totalOnlineVillagesCount) totalOnlineVillagesCount,"
			+ "sum(totalKhataNos) totalKhataNos "
			+ "from "
			+ "( "
			+ "select "
			+ "f.division_code, "
			+ "f.district_code,"
			+ "f.district_name, "
			+ "(select sum(total_villages) from e_division_district_taluka_count where trim(district_code)=trim(f.district_code)) totalVillagesCount, "
			+ "(select sum(total_phase1_villages) from e_division_district_taluka_count where trim(district_code)=trim(f.district_code)) phase1VillagesCount, "
			+ "(select sum(total_phase1_login) from e_village_entry_count_live where trim(district_code)=trim(f.district_code)) phase1VillagesLoginCount, "
			+ "(select sum(total_entry) from e_village_entry_count_live  where trim(district_code)=trim(f.district_code)) totalEntryCount, "
			+ "(select count(*) from e_division_district_taluka_count where trim(district_code)=trim(f.district_code)) talukaCount,"
			+ "(select count(ccode) from e_lgd_mapper_2023 where is_challan_generated='Y' and trim(district_code)=trim(f.district_code)) totalOnlineVillagesCount,"
			+ " (select sum(total_khata_numbers) from e_division_district_taluka_count where trim(district_code)=trim(f.district_code)) totalKhataNos "
			+ "from  e_district_census f order by cast(district_code as int) "
			+ ")B  "
			+ "" , nativeQuery=true)
	List<Object[]> getTotalByAllDivisionCodePre();

	DivisionDistrictTalukaCount findByDistrictCodeAndTalukaCode(String districtCode,String talukaCode);
	
	
	//2024-25 new old one before MIS of NetVasuli
	   @Query(value="select  "
				+ "f.district_code, "
				+ "f.district_name, "
				+ "(select sum(total_villages) from e_division_district_taluka_count where district_code=f.district_code) totalVillagesCount, "
				+ "(select sum(total_entry) from e_village_entry_count_live  where district_code=f.district_code) totalEntryCount, "
				+ "(select count(*) from e_division_district_taluka_count where district_code=f.district_code) talukaCount,"
				+ "(select count(division_code) from e_district_census where district_code=f.district_code) districtCount,"
				+ "(select count(ccode) from e_lgd_mapper where is_gaon_namuna_added='Y' and district_code=f.district_code) totalGaonNamunaCount,   "
				+ "(select count(ccode) from e_lgd_mapper where is_demand_completed='Y' and district_code=f.district_code) totalDemandCompletedCount,   "
				+ "(select count(ccode) from e_lgd_mapper where is_vasuli_without_demandadd='Y' and is_selected='Y' and district_code=f.district_code) vasuliWithoutDemandCount,   "
				+ "(select count(ccode) from e_lgd_mapper where is_vasuli_completed='Y' and district_code=f.district_code) vasuliCompletedCount,   "
				+ "(select count(ccode) from e_lgd_mapper where is_demand_completed='N' and is_challan_generated ='N' and is_selected='Y' and district_code=f.district_code) demandCount ,  "
				+ "(select sum(total_demand_jm) from e_lgd_mapper_mis where district_code=f.district_code) totalDemandJm, "
				+ "(select sum(total_demand_sankirn) from e_lgd_mapper_mis where district_code=f.district_code) totalDemandSankirn, "
				+ "(select sum(total_collected_jm) from e_lgd_mapper_mis where district_code=f.district_code) totalCollectedJm, "
				+ "(select sum(total_collected_sankirn) from e_lgd_mapper_mis where district_code=f.district_code) totalCollectedSankirn "
				+ "from  e_district_census f where division_code=?1 order by cast(district_code as int)"
				+ "" , nativeQuery=true)
		List<Object[]> getByDivisionCodeUpdatedMis(String divisionCode);
		
		//2024
		 /*  @Query(value="select  "
					+ "f.district_code, "
					+ "f.district_name, "
					+ "(select count(*) from e_lgd_mapper where district_code=f.district_code) totalVillagesCount, "
					+ "(select sum(total_entry) from e_village_entry_count_live  where district_code=f.district_code) totalEntryCount, "
					+ "(select count(*) from e_division_district_taluka_count where district_code=f.district_code) talukaCount,"
					+ "(select count(division_code) from e_district_census where district_code=f.district_code) districtCount,"
					+ "(select count(ccode) from e_lgd_mapper where is_gaon_namuna_added='Y' and district_code=f.district_code) totalGaonNamunaCount,   "
					+ "(select count(ccode) from e_lgd_mapper where is_demand_completed='Y' and district_code=f.district_code) totalDemandCompletedCount,   "
					+ "(select count(ccode) from e_lgd_mapper where is_vasuli_without_demandadd='Y' and is_selected='Y' and district_code=f.district_code) vasuliWithoutDemandCount,   "
					+ "(select count(ccode) from e_lgd_mapper where is_vasuli_completed='Y' and district_code=f.district_code) vasuliCompletedCount,   "
					+ "(select count(ccode) from e_lgd_mapper where is_demand_completed='N' and is_selected='Y' and is_challan_generated ='N' and district_code=f.district_code) demandCount ,"
					+ "(select sum(total_demand_jm) from e_lgd_mapper_mis where district_code=f.district_code) totalDemandJm, "
					+ "(select sum(total_demand_sankirn) from e_lgd_mapper_mis where district_code=f.district_code) totalDemandSankirn, "
					+ "(select sum(total_collected_jm) from e_lgd_mapper_mis where district_code=f.district_code) totalCollectedJm, "
					+ "(select sum(total_collected_sankirn) from e_lgd_mapper_mis where district_code=f.district_code) totalCollectedSankirn "
					+ "from  e_district_census f where division_code=?1 order by cast(district_code as int)"
					+ "" , nativeQuery=true)
			List<Object[]> getByDivisionCodeUpdatedMis(String divisionCode);*/


	  
		//2023-24 pre
//		 @Query(value="select  "
//					+ "f.district_code, "
//					+ "f.district_name, "
//					+ "(select sum(total_villages) from e_division_district_taluka_count where district_code=f.district_code) totalVillagesCount, "
//					+ "(select sum(total_entry) from e_village_entry_count_live  where district_code=f.district_code) totalEntryCount, "
//					+ "(select count(*) from e_division_district_taluka_count where district_code=f.district_code) talukaCount,"
//					+ "(select count(division_code) from e_district_census where district_code=f.district_code) districtCount,"
//					+ "(select count(ccode) from e_lgd_mapper_2023 where is_gaon_namuna_added='Y' and district_code=f.district_code) totalGaonNamunaCount,   "
//					+ "(select count(ccode) from e_lgd_mapper_2023 where is_demand_completed='Y' and district_code=f.district_code) totalDemandCompletedCount,   "
//					+ "(select count(ccode) from e_lgd_mapper_2023 where is_vasuli_without_demandadd='Y' and district_code=f.district_code) vasuliWithoutDemandCount,   "
//					+ "(select count(ccode) from e_lgd_mapper_2023 where is_vasuli_completed='Y' and district_code=f.district_code) vasuliCompletedCount,   "
//					+ "(select count(ccode) from e_lgd_mapper_2023 where is_demand_completed='N' and is_challan_generated ='N' and district_code=f.district_code) demandCount   "
//					+ "from  e_district_census f where division_code=?1 order by cast(district_code as int)"
//					+ "" , nativeQuery=true)
//			List<Object[]> getByDivisionCodeUpdatedMisPre(String divisionCode);
			
		//2024-25 new old one before NetVasuli MIS
	           @Query(value="select    "
				+ "f.division_code,   "
				+ "f.division_name,   "
				+ "(select sum(total_villages) from e_division_district_taluka_count where division_code=f.division_code) totalVillagesCount,   "
				+ "(select sum(total_entry) from e_village_entry_count_live  where division_code=f.division_code) totalEntryCount,   "
				+ "(select count(*) from e_division_district_taluka_count where division_code=f.division_code) talukaCount,  "
				+ "(select count(division_code) from e_district_census where division_code=f.division_code) districtCount,"
				+ "(select count(ccode) from e_lgd_mapper where is_gaon_namuna_added='Y' and division_code=f.division_code) totalGaonNamunaCount,   "
				+ "(select count(ccode) from e_lgd_mapper where is_demand_completed='Y' and division_code=f.division_code) totalDemandCompletedCount,   "
				+ "(select count(ccode) from e_lgd_mapper where is_vasuli_without_demandadd='Y' and is_selected='Y' and division_code=f.division_code) vasuliWithoutDemandCount,   "
				+ "(select count(ccode) from e_lgd_mapper where is_vasuli_completed='Y' and division_code=f.division_code) vasuliCompletedCount,   "
				+ "(select count(ccode) from e_lgd_mapper where is_demand_completed='N' and is_selected='Y' and is_challan_generated ='N' and division_code=f.division_code) demandCount,   "
				+ "(select sum(total_demand_jm) from e_lgd_mapper_mis where division_code=f.division_code) totalDemandJm,   "
				+ "(select sum(total_demand_sankirn) from e_lgd_mapper_mis where division_code=f.division_code) totalDemandSankirn,   "
				+ "(select sum(total_collected_jm) from e_lgd_mapper_mis where division_code=f.division_code) totalCollectedJm,   "
				+ "(select sum(total_collected_sankirn) from e_lgd_mapper_mis where division_code=f.division_code) totalCollectedSankirn  "
				+ "from  e_division_census f "
				+ "" , nativeQuery=true)
		List<Object[]> getByAllDivisionCodeUpdatedMIs();
		
		
		//2024
        /*@Query(value="select    "
			+ "f.division_code,   "
			+ "f.division_name,   "
			+ "(select count(*) from e_lgd_mapper where division_code=f.division_code) totalVillagesCount,   "
			+ "(select sum(total_entry) from e_village_entry_count_live  where division_code=f.division_code) totalEntryCount,   "
			+ "(select count(*) from e_division_district_taluka_count where division_code=f.division_code) talukaCount,  "
			+ "(select count(division_code) from e_district_census where division_code=f.division_code) districtCount,"
			+ "(select count(ccode) from e_lgd_mapper where is_gaon_namuna_added='Y' and division_code=f.division_code) totalGaonNamunaCount,   "
			+ "(select count(ccode) from e_lgd_mapper where is_demand_completed='Y' and division_code=f.division_code) totalDemandCompletedCount,   "
			+ "(select count(ccode) from e_lgd_mapper where is_vasuli_without_demandadd='Y' and is_selected='Y' and division_code=f.division_code) vasuliWithoutDemandCount,   "
			+ "(select count(ccode) from e_lgd_mapper where is_vasuli_completed='Y' and division_code=f.division_code) vasuliCompletedCount,  "
			+ "(select count(ccode) from e_lgd_mapper where is_demand_completed='N' and is_selected='Y' and is_challan_generated ='N' and division_code=f.division_code) demandCount,"
			+ "(select sum(total_demand_jm) from e_lgd_mapper_mis where division_code=f.division_code) totalDemandJm,   "
			+ "(select sum(total_demand_sankirn) from e_lgd_mapper_mis where division_code=f.division_code) totalDemandSankirn,   "
			+ "(select sum(total_collected_jm) from e_lgd_mapper_mis where division_code=f.division_code) totalCollectedJm,   "
			+ "(select sum(total_collected_sankirn) from e_lgd_mapper_mis where division_code=f.division_code) totalCollectedSankirn  "
			+ "from  e_division_census f "
			+ "" , nativeQuery=true)
	List<Object[]> getByAllDivisionCodeUpdatedMIs();*/
		
		//2023-24 pre
		
//		  @Query(value="select    "
//					+ "f.division_code,   "
//					+ "f.division_name,   "
//					+ "(select sum(total_villages) from e_division_district_taluka_count where division_code=f.division_code) totalVillagesCount,   "
//					+ "(select sum(total_entry) from e_village_entry_count_live  where division_code=f.division_code) totalEntryCount,   "
//					+ "(select count(*) from e_division_district_taluka_count where division_code=f.division_code) talukaCount,  "
//					+ "(select count(division_code) from e_district_census where division_code=f.division_code) districtCount,"
//					+ "(select count(ccode) from e_lgd_mapper_2023 where is_gaon_namuna_added='Y' and division_code=f.division_code) totalGaonNamunaCount,   "
//					+ "(select count(ccode) from e_lgd_mapper_2023 where is_demand_completed='Y' and division_code=f.division_code) totalDemandCompletedCount,   "
//					+ "(select count(ccode) from e_lgd_mapper_2023 where is_vasuli_without_demandadd='Y' and division_code=f.division_code) vasuliWithoutDemandCount,   "
//					+ "(select count(ccode) from e_lgd_mapper_2023 where is_vasuli_completed='Y' and division_code=f.division_code) vasuliCompletedCount,   "
//					+ "(select count(ccode) from e_lgd_mapper_2023 where is_demand_completed='N' and is_challan_generated ='N' and division_code=f.division_code) demandCount   "
//					+ "from  e_division_census f "
//					+ "" , nativeQuery=true)
//			List<Object[]> getByAllDivisionCodeUpdatedMIsPre();
		
			
			//new 2024-25 old before MIS of NetVasuli
	            @Query(value="select  "
				+ "sum(totalVillagesCount) totalVillagesCount, "
				+ "sum(totalEntryCount) totalEntryCount, "
				+ "sum(talukaCount) talukaCount ,"
				+ "count(*) districtCount,"
				+ "sum(totalGaonNamunaCount) totalGaonNamunaCount ,"
				+ "sum(totalDemandCompletedCount) totalDemandCompletedCount,"
				+ "sum(vasuliWithoutDemandCount) vasuliWithoutDemandCount, "
				+ "sum(vasuliCompletedCount) vasuliCompletedCount, "
				+ "sum(demandCount) demandCount, "
				+ "sum(totalDemandJm) totalDemandJm, "
				+ "sum(totalDemandSankirn) totalDemandSankirn, "
				+ "sum(totalCollectedJm) totalCollectedJm, "
				+ "sum(totalCollectedSankirn) totalCollectedSankirn "
				+ "from "
				+ "( "
				+ "select "
				+ "f.division_code, "
				+ "f.district_code,"
				+ "f.district_name, "
				+ "(select sum(total_villages) from e_division_district_taluka_count where trim(district_code)=trim(f.district_code)) totalVillagesCount, "
				+ "(select sum(total_entry) from e_village_entry_count_live  where trim(district_code)=trim(f.district_code)) totalEntryCount, "
				+ "(select count(*) from e_division_district_taluka_count where trim(district_code)=trim(f.district_code)) talukaCount,"
				+ "(select count(ccode) from e_lgd_mapper where is_gaon_namuna_added='Y' and district_code=f.district_code) totalGaonNamunaCount,   "
				+ "(select count(ccode) from e_lgd_mapper where is_demand_completed='Y' and district_code=f.district_code) totalDemandCompletedCount,   "
				+ "(select count(ccode) from e_lgd_mapper where is_vasuli_without_demandadd='Y' and is_selected='Y' and district_code=f.district_code) vasuliWithoutDemandCount,   "
				+ "(select count(ccode) from e_lgd_mapper where is_vasuli_completed='Y' and district_code=f.district_code) vasuliCompletedCount,   "
				+ "(select count(ccode) from e_lgd_mapper where is_demand_completed='N' and is_challan_generated ='N' and is_selected='Y' and district_code=f.district_code) demandCount ,  "
				+ "(select sum(total_demand_jm) from  e_lgd_mapper_mis  where trim(district_code)=trim(f.district_code)) totalDemandJm, "
				+ "(select sum(total_demand_sankirn) from  e_lgd_mapper_mis  where trim(district_code)=trim(f.district_code)) totalDemandSankirn, "
				+ "(select sum(total_collected_jm) from  e_lgd_mapper_mis  where trim(district_code)=trim(f.district_code)) totalCollectedJm, "
				+ "(select sum(total_collected_sankirn) from  e_lgd_mapper_mis  where trim(district_code)=trim(f.district_code)) totalCollectedSankirn "
				+ "from  e_district_census f order by cast(district_code as int) "
				+ ")B where division_code=?1 "
				+ "group by division_code;"
				+ "" , nativeQuery=true)
		List<Object[]> getTotalByDivisionCodeUpdatedMIS(String divisionCode);
		
		
		//2025 new changes for MIS netVasuli 
      /*  @Query(value="select  "
		+ "sum(totalVillagesCount) totalVillagesCount, "
		+ "sum(totalEntryCount) totalEntryCount, "
		+ "sum(talukaCount) talukaCount ,"
		+ "count(*) districtCount,"
		+ "sum(totalGaonNamunaCount) totalGaonNamunaCount ,"
		+ "sum(totalDemandCompletedCount) totalDemandCompletedCount,"
		+ "sum(vasuliWithoutDemandCount) vasuliWithoutDemandCount, "
		+ "sum(vasuliCompletedCount) vasuliCompletedCount, "
		+ "sum(demandCount) demandCount, "
		+ "sum(totalDemandJm) totalDemandJm, "
		+ "sum(totalDemandSankirn) totalDemandSankirn, "
		+ "sum(totalCollectedJm) totalCollectedJm, "
		+ "sum(totalCollectedSankirn) totalCollectedSankirn "
		+ "from "
		+ "( "
		+ "select "
		+ "f.division_code, "
		+ "f.district_code,"
		+ "f.district_name, "
		+ "(select count(*) from e_lgd_mapper where trim(district_code)=trim(f.district_code)) totalVillagesCount, "
		+ "(select sum(total_entry) from e_village_entry_count_live  where trim(district_code)=trim(f.district_code)) totalEntryCount, "
		+ "(select count(*) from e_division_district_taluka_count where trim(district_code)=trim(f.district_code)) talukaCount,"
		+ "(select count(ccode) from e_lgd_mapper where is_gaon_namuna_added='Y' and district_code=f.district_code) totalGaonNamunaCount,   "
		+ "(select count(ccode) from e_lgd_mapper where is_demand_completed='Y' and district_code=f.district_code) totalDemandCompletedCount,   "
		+ "(select count(ccode) from e_lgd_mapper where is_vasuli_without_demandadd='Y' and district_code=f.district_code) vasuliWithoutDemandCount,   "
		+ "(select count(ccode) from e_lgd_mapper where is_vasuli_completed='Y' and district_code=f.district_code) vasuliCompletedCount,   "
		+ "(select count(ccode) from e_lgd_mapper where is_demand_completed='N' and is_challan_generated ='N' and district_code=f.district_code) demandCount ,"
		+ "(select sum(total_demand_jm) from  e_lgd_mapper_mis  where trim(district_code)=trim(f.district_code)) totalDemandJm, "
		+ "(select sum(total_demand_sankirn) from  e_lgd_mapper_mis  where trim(district_code)=trim(f.district_code)) totalDemandSankirn, "
		+ "(select sum(total_collected_jm) from  e_lgd_mapper_mis  where trim(district_code)=trim(f.district_code)) totalCollectedJm, "
		+ "(select sum(total_collected_sankirn) from  e_lgd_mapper_mis  where trim(district_code)=trim(f.district_code)) totalCollectedSankirn "
		+ "from  e_district_census f order by cast(district_code as int) "
		+ ")B where division_code=?1 "
		+ "group by division_code;"
		+ "" , nativeQuery=true)
        List<Object[]> getTotalByDivisionCodeUpdatedMIS(String divisionCode);*/


		
		//pre
		//2023-24
//        @Query(value="select  "
//		+ "sum(totalVillagesCount) totalVillagesCount, "
//		+ "sum(totalEntryCount) totalEntryCount, "
//		+ "sum(talukaCount) talukaCount ,"
//		+ "count(*) districtCount,"
//		+ "sum(totalGaonNamunaCount) totalGaonNamunaCount ,"
//		+ "sum(totalDemandCompletedCount) totalDemandCompletedCount,"
//		+ "sum(vasuliWithoutDemandCount) vasuliWithoutDemandCount, "
//		+ "sum(vasuliCompletedCount) vasuliCompletedCount, "
//		+ "sum(demandCount) demandCount "
//		+ "from "
//		+ "( "
//		+ "select "
//		+ "f.division_code, "
//		+ "f.district_code,"
//		+ "f.district_name, "
//		+ "(select sum(total_villages) from e_division_district_taluka_count where trim(district_code)=trim(f.district_code)) totalVillagesCount, "
//		+ "(select sum(total_entry) from e_village_entry_count_live  where trim(district_code)=trim(f.district_code)) totalEntryCount, "
//		+ "(select count(*) from e_division_district_taluka_count where trim(district_code)=trim(f.district_code)) talukaCount,"
//		+ "(select count(ccode) from e_lgd_mapper_2023 where is_gaon_namuna_added='Y' and district_code=f.district_code) totalGaonNamunaCount,   "
//		+ "(select count(ccode) from e_lgd_mapper_2023 where is_demand_completed='Y' and district_code=f.district_code) totalDemandCompletedCount,   "
//		+ "(select count(ccode) from e_lgd_mapper_2023 where is_vasuli_without_demandadd='Y' and district_code=f.district_code) vasuliWithoutDemandCount,   "
//		+ "(select count(ccode) from e_lgd_mapper_2023 where is_vasuli_completed='Y' and district_code=f.district_code) vasuliCompletedCount,   "
//		+ "(select count(ccode) from e_lgd_mapper_2023 where is_demand_completed='N' and is_challan_generated ='N' and district_code=f.district_code) demandCount   "
//		+ "from  e_district_census f order by cast(district_code as int) "
//		+ ")B where division_code=?1 "
//		+ "group by division_code;"
//		+ "" , nativeQuery=true)
//List<Object[]> getTotalByDivisionCodeUpdatedMISPre(String divisionCode);
		
		
//		 @Query(value="select  "
//					+ "sum(totalVillagesCount) totalVillagesCount, "
//					+ "sum(totalEntryCount) totalEntryCount, "
//					+ "sum(talukaCount) talukaCount ,"
//					+ "count(*) districtCount,"
//					+ "sum(totalGaonNamunaCount) totalGaonNamunaCount ,"
//					+ "sum(totalDemandCompletedCount) totalDemandCompletedCount,"
//					+ "sum(vasuliWithoutDemandCount) vasuliWithoutDemandCount, "
//					+ "sum(vasuliCompletedCount) vasuliCompletedCount, "
//					+ "sum(demandCount) demandCount "
//					+ "from "
//					+ "( "
//					+ "select "
//					+ "f.division_code, "
//					+ "f.district_code,"
//					+ "f.district_name, "
//					+ "(select sum(total_villages) from e_division_district_taluka_count where trim(district_code)=trim(f.district_code)) totalVillagesCount, "
//					+ "(select sum(total_entry) from e_village_entry_count_live  where trim(district_code)=trim(f.district_code)) totalEntryCount, "
//					+ "(select count(*) from e_division_district_taluka_count where trim(district_code)=trim(f.district_code)) talukaCount,"
//					+ "(select count(ccode) from e_lgd_mapper_'?2' where is_gaon_namuna_added='Y' and district_code=f.district_code) totalGaonNamunaCount,   "
//					+ "(select count(ccode) from e_lgd_mapper_ '?2' where is_demand_completed='Y' and district_code=f.district_code) totalDemandCompletedCount,   "
//					+ "(select count(ccode) from e_lgd_mapper_ ?2 where is_vasuli_without_demandadd='Y' and district_code=f.district_code) vasuliWithoutDemandCount,   "
//					+ "(select count(ccode) from e_lgd_mapper_ ?2 where is_vasuli_completed='Y' and district_code=f.district_code) vasuliCompletedCount,   "
//					+ "(select count(ccode) from e_lgd_mapper_?2 where is_demand_completed='N' and is_challan_generated ='N' and district_code=f.district_code) demandCount   "
//					+ "from  e_district_census f order by cast(district_code as int) "
//					+ ")B where division_code=?1 "
//					+ "group by division_code;"
//					+ "" , nativeQuery=true)
//					List<Object[]> getTotalByDivisionCodeUpdatedMISPre(String divisionCode, String yearName);
		
		
		
		//new 2024-25 old before Mis of NetVasuli
		@Query(value="select  "
				+ "sum(totalVillagesCount) totalVillagesCount, "
				+ "sum(totalEntryCount) totalEntryCount, "
				+ "sum(talukaCount) talukaCount ,"
				+ "count(*) districtCount,"
				+ "sum(totalGaonNamunaCount) totalGaonNamunaCount ,"
				+ "sum(totalDemandCompletedCount) totalDemandCompletedCount,"
				+ "sum(vasuliWithoutDemandCount) vasuliWithoutDemandCount, "
				+ "sum(vasuliCompletedCount) vasuliCompletedCount, "
				+ "sum(demandCount) demandCount, "
				+ "sum(totalDemandJm) totalDemandJm , "
				+ "sum(totalDemandSankirn) totalDemandSankirn , "
				+ "sum(totalCollectedJm) totalCollectedJm , "
				+ "sum(totalCollectedSankirn) totalCollectedSankirn  "
				+ "from "
				+ "( "
				+ "select "
				+ "f.division_code, "
				+ "f.district_code,"
				+ "f.district_name, "
				+ "(select sum(total_villages) from e_division_district_taluka_count where trim(district_code)=trim(f.district_code)) totalVillagesCount, "
				+ "(select sum(total_entry) from e_village_entry_count_live  where trim(district_code)=trim(f.district_code)) totalEntryCount, "
				+ "(select count(*) from e_division_district_taluka_count where trim(district_code)=trim(f.district_code)) talukaCount,"
				+ "(select count(ccode) from e_lgd_mapper where is_gaon_namuna_added='Y' and district_code=f.district_code) totalGaonNamunaCount,   "
				+ "(select count(ccode) from e_lgd_mapper where is_demand_completed='Y' and district_code=f.district_code) totalDemandCompletedCount,   "
				+ "(select count(ccode) from e_lgd_mapper where is_vasuli_without_demandadd='Y' and is_selected='Y' and district_code=f.district_code) vasuliWithoutDemandCount,   "
				+ "(select count(ccode) from e_lgd_mapper where is_vasuli_completed='Y' and district_code=f.district_code) vasuliCompletedCount,   "
				+ "(select count(ccode) from e_lgd_mapper where is_demand_completed='N' and is_challan_generated ='N' and is_selected='Y' and district_code=f.district_code) demandCount ,  "
				+ "(select sum(total_demand_jm) from e_lgd_mapper_mis  where trim(district_code)=trim(f.district_code)) totalDemandJm, "
				+ "(select sum(total_demand_sankirn) from e_lgd_mapper_mis  where trim(district_code)=trim(f.district_code)) totalDemandSankirn, "
				+ "(select sum(total_collected_jm) from e_lgd_mapper_mis  where trim(district_code)=trim(f.district_code)) totalCollectedJm, "
				+ "(select sum(total_collected_sankirn) from e_lgd_mapper_mis  where trim(district_code)=trim(f.district_code)) totalCollectedSankirn "		
				+ "from  e_district_census f order by cast(district_code as int) "
				+ ")B  "
				+ "" , nativeQuery=true)
		List<Object[]> getTotalByAllDivisionCodeUpdatedMIS();
		
		
		//2024
				/*@Query(value="select  "
						+ "sum(totalVillagesCount) totalVillagesCount, "
						+ "sum(totalEntryCount) totalEntryCount, "
						+ "sum(talukaCount) talukaCount ,"
						+ "count(*) districtCount,"
						+ "sum(totalGaonNamunaCount) totalGaonNamunaCount ,"
						+ "sum(totalDemandCompletedCount) totalDemandCompletedCount,"
						+ "sum(vasuliWithoutDemandCount) vasuliWithoutDemandCount, "
						+ "sum(vasuliCompletedCount) vasuliCompletedCount, "
						+ "sum(demandCount) demandCount, "
						+ "sum(totalDemandJm) totalDemandJm , "
						+ "sum(totalDemandSankirn) totalDemandSankirn , "
						+ "sum(totalCollectedJm) totalCollectedJm , "
						+ "sum(totalCollectedSankirn) totalCollectedSankirn  "
						+ "from "
						+ "( "
						+ "select "
						+ "f.division_code, "
						+ "f.district_code,"
						+ "f.district_name, "
						+ "(select count(*) from e_lgd_mapper where trim(district_code)=trim(f.district_code)) totalVillagesCount, "
						+ "(select sum(total_entry) from e_village_entry_count_live  where trim(district_code)=trim(f.district_code)) totalEntryCount, "
						+ "(select count(*) from e_division_district_taluka_count where trim(district_code)=trim(f.district_code)) talukaCount,"
						+ "(select count(ccode) from e_lgd_mapper where is_gaon_namuna_added='Y' and district_code=f.district_code) totalGaonNamunaCount,   "
						+ "(select count(ccode) from e_lgd_mapper where is_demand_completed='Y' and district_code=f.district_code) totalDemandCompletedCount,   "
						+ "(select count(ccode) from e_lgd_mapper where is_vasuli_without_demandadd='Y' and district_code=f.district_code) vasuliWithoutDemandCount,   "
						+ "(select count(ccode) from e_lgd_mapper where is_vasuli_completed='Y' and district_code=f.district_code) vasuliCompletedCount,   "
						+ "(select count(ccode) from e_lgd_mapper where is_demand_completed='N' and is_challan_generated ='N' and district_code=f.district_code) demandCount,"
						+ "(select sum(total_demand_jm) from e_lgd_mapper_mis  where trim(district_code)=trim(f.district_code)) totalDemandJm, "
						+ "(select sum(total_demand_sankirn) from e_lgd_mapper_mis  where trim(district_code)=trim(f.district_code)) totalDemandSankirn, "
						+ "(select sum(total_collected_jm) from e_lgd_mapper_mis  where trim(district_code)=trim(f.district_code)) totalCollectedJm, "
						+ "(select sum(total_collected_sankirn) from e_lgd_mapper_mis  where trim(district_code)=trim(f.district_code)) totalCollectedSankirn "
						+ "from  e_district_census f order by cast(district_code as int) "
						+ ")B  "
						+ "" , nativeQuery=true)
				List<Object[]> getTotalByAllDivisionCodeUpdatedMIS();*/
		
		
		// pre 2023-24
		/*@Query(value="select  "
				+ "sum(totalVillagesCount) totalVillagesCount, "
				+ "sum(totalEntryCount) totalEntryCount, "
				+ "sum(talukaCount) talukaCount ,"
				+ "count(*) districtCount,"
				+ "sum(totalGaonNamunaCount) totalGaonNamunaCount ,"
				+ "sum(totalDemandCompletedCount) totalDemandCompletedCount,"
				+ "sum(vasuliWithoutDemandCount) vasuliWithoutDemandCount, "
				+ "sum(vasuliCompletedCount) vasuliCompletedCount, "
				+ "sum(demandCount) demandCount "
				+ "from "
				+ "( "
				+ "select "
				+ "f.division_code, "
				+ "f.district_code,"
				+ "f.district_name, "
				+ "(select sum(total_villages) from e_division_district_taluka_count where trim(district_code)=trim(f.district_code)) totalVillagesCount, "
				+ "(select sum(total_entry) from e_village_entry_count_live  where trim(district_code)=trim(f.district_code)) totalEntryCount, "
				+ "(select count(*) from e_division_district_taluka_count where trim(district_code)=trim(f.district_code)) talukaCount,"
				+ "(select count(ccode) from e_lgd_mapper_2023 where is_gaon_namuna_added='Y' and district_code=f.district_code) totalGaonNamunaCount,   "
				+ "(select count(ccode) from e_lgd_mapper_2023 where is_demand_completed='Y' and district_code=f.district_code) totalDemandCompletedCount,   "
				+ "(select count(ccode) from e_lgd_mapper_2023 where is_vasuli_without_demandadd='Y' and district_code=f.district_code) vasuliWithoutDemandCount,   "
				+ "(select count(ccode) from e_lgd_mapper_2023 where is_vasuli_completed='Y' and district_code=f.district_code) vasuliCompletedCount,   "
				+ "(select count(ccode) from e_lgd_mapper_2023 where is_demand_completed='N' and is_challan_generated ='N' and district_code=f.district_code) demandCount   "
				+ "from  e_district_census f order by cast(district_code as int) "
				+ ")B  "
				+ "" , nativeQuery=true)
		List<Object[]> getTotalByAllDivisionCodeUpdatedMISPre();*/
		
		
		//2024-25 new old one before MIS netVasuli
		@Query(value = "select  " 
				+ "f.district_code," + "f.district_name," + "et.taluka_code," + "et.taluka_name ,"			
				+ "(select count(*) from e_lgd_mapper where taluka_code=et.taluka_code and district_code=f.district_code) totalVillagesCount,"
				+ "(select sum(total_entry) from e_village_entry_count_live  where taluka_code=et.taluka_code and district_code=f.district_code) totalEntryCount, "
				+ "(select count(*) from e_division_district_taluka_count where taluka_code=et.taluka_code) talukaCount, "
				+ "(select count(distinct division_code) from e_district_census where district_code=f.district_code) districtCount,"
				+ "(select count(distinct ccode) from e_lgd_mapper where is_gaon_namuna_added='Y' and taluka_code=et.taluka_code and district_code=f.district_code) totalGaonNamunaCount,"
				+ "(select count(distinct ccode) from e_lgd_mapper where is_demand_completed='Y' and taluka_code=et.taluka_code and district_code=f.district_code) totalDemandCompletedCount,"
				+ "(select count(distinct ccode) from e_lgd_mapper where is_vasuli_without_demandadd='Y' and taluka_code=et.taluka_code and is_selected='Y' and district_code=f.district_code) vasuliWithoutDemandCount,"
				+ "(select count(distinct ccode) from e_lgd_mapper where is_vasuli_completed='Y' and taluka_code=et.taluka_code and district_code=f.district_code) vasuliCompletedCount, "
				+ "(select count(distinct ccode) from e_lgd_mapper where is_demand_completed='N' and is_challan_generated ='N' and is_selected='Y' and taluka_code=et.taluka_code and district_code=f.district_code) demandCount, "
				+ "(select sum(total_demand_jm) from e_lgd_mapper_mis  where taluka_code=et.taluka_code and district_code=f.district_code) totalDemandJm, "
				+ "(select sum(total_demand_sankirn) from e_lgd_mapper_mis  where taluka_code=et.taluka_code and district_code=f.district_code) totalDemandSankirn, "
				+ "(select sum(total_collected_jm) from e_lgd_mapper_mis  where taluka_code=et.taluka_code and district_code=f.district_code) totalCollectedJm, "
				+ "(select sum(total_collected_sankirn) from e_lgd_mapper_mis  where taluka_code=et.taluka_code and district_code=f.district_code) totalCollectedSankirn "
				+ "from  e_district_census f  inner join e_taluka_census et on et.district_code=f.district_code where "
				+ " f.division_code=?1 and f.district_code=?2" 
				+ "", nativeQuery = true)
		List<Object[]> getByTalukaNameAndCode(String divisionCode, String districtCode);
		
		
		/*@Query(value = "select  " 
				+ "f.district_code," + "f.district_name," + "et.taluka_code," + "et.taluka_name ,"
				
				+ "(select count(*) from e_lgd_mapper where taluka_code=et.taluka_code and district_code=f.district_code) totalVillagesCount,"
				+ "(select sum(total_entry) from e_village_entry_count_live  where taluka_code=et.taluka_code and district_code=f.district_code) totalEntryCount, "
				+ "(select count(*) from e_division_district_taluka_count where taluka_code=et.taluka_code) talukaCount, "
				+ "(select count(distinct division_code) from e_district_census where district_code=f.district_code) districtCount,"
				+ "(select count(distinct ccode) from e_lgd_mapper where is_gaon_namuna_added='Y' and taluka_code=et.taluka_code and district_code=f.district_code) totalGaonNamunaCount,"
				+ "(select count(distinct ccode) from e_lgd_mapper where is_demand_completed='Y' and taluka_code=et.taluka_code and district_code=f.district_code) totalDemandCompletedCount,"
				+ "(select count(distinct ccode) from e_lgd_mapper where is_vasuli_without_demandadd='Y' and taluka_code=et.taluka_code and is_selected='Y' and district_code=f.district_code) vasuliWithoutDemandCount,"
				+ "(select count(distinct ccode) from e_lgd_mapper where is_vasuli_completed='Y' and taluka_code=et.taluka_code and district_code=f.district_code) vasuliCompletedCount, "
				+ "(select count(distinct ccode) from e_lgd_mapper where is_demand_completed='N' and is_challan_generated ='N' and is_selected='Y' and taluka_code=et.taluka_code and district_code=f.district_code) demandCount,"
				+ "(select sum(total_demand_jm) from e_lgd_mapper_mis  where taluka_code=et.taluka_code and district_code=f.district_code) totalDemandJm, "
				+ "(select sum(total_demand_sankirn) from e_lgd_mapper_mis  where taluka_code=et.taluka_code and district_code=f.district_code) totalDemandSankirn, "
				+ "(select sum(total_collected_jm) from e_lgd_mapper_mis  where taluka_code=et.taluka_code and district_code=f.district_code) totalCollectedJm, "
				+ "(select sum(total_collected_sankirn) from e_lgd_mapper_mis  where taluka_code=et.taluka_code and district_code=f.district_code) totalCollectedSankirn "
				+ "from  e_district_census f  inner join e_taluka_census et on et.district_code=f.district_code where "
				+ " f.division_code=?1 and f.district_code=?2" 
				+ "", nativeQuery = true)
		List<Object[]> getByTalukaNameAndCode(String divisionCode, String districtCode);*/
		
		
		
		//2023-24 pre
//		@Query(value = "select  " 
//				+ "f.district_code," + "f.district_name," + "et.taluka_code," + "et.taluka_name ,"
//				
//				+ "(select count(*) from e_lgd_mapper where taluka_code=et.taluka_code and district_code=f.district_code) totalVillagesCount,"
//				+ "(select sum(total_entry) from e_village_entry_count_live  where taluka_code=et.taluka_code and district_code=f.district_code) totalEntryCount, "
//				+ "(select count(*) from e_division_district_taluka_count where taluka_code=et.taluka_code) talukaCount, "
//				+ "(select count(distinct division_code) from e_district_census where district_code=f.district_code) districtCount,"
//				+ "(select count(distinct ccode) from e_lgd_mapper_2023 where is_gaon_namuna_added='Y' and taluka_code=et.taluka_code and district_code=f.district_code) totalGaonNamunaCount,"
//				+ "(select count(distinct ccode) from e_lgd_mapper_2023 where is_demand_completed='Y' and taluka_code=et.taluka_code and district_code=f.district_code) totalDemandCompletedCount,"
//				+ "(select count(distinct ccode) from e_lgd_mapper_2023 where is_vasuli_without_demandadd='Y' and taluka_code=et.taluka_code and district_code=f.district_code) vasuliWithoutDemandCount,"
//				+ "(select count(distinct ccode) from e_lgd_mapper_2023 where is_vasuli_completed='Y' and taluka_code=et.taluka_code and district_code=f.district_code) vasuliCompletedCount, "
//				+ "(select count(distinct ccode) from e_lgd_mapper_2023 where is_demand_completed='N' and is_challan_generated ='N' and taluka_code=et.taluka_code and district_code=f.district_code) demandCount "
//				+ "from  e_district_census f  inner join e_taluka_census et on et.district_code=f.district_code where "
//				+ " f.division_code=?1 and f.district_code=?2" 
//				+ "", nativeQuery = true)
//		List<Object[]> getByTalukaNameAndCodePre(String divisionCode, String districtCode);
		
		//-------------MIS 14 DEC 2023-----------------
		
				//---9 Column
				@Query(value = "select district_name_marathi,taluka_name_marathi,lgd_village_name "
						+ " from e_lgd_mapper "
						+ " where is_vasuli_completed='Y' and district_code =?1 and taluka_code=?2 " 
						+ "", nativeQuery = true)
				List<Object[]> getTotalVasooliCount( String districtCode, String talukaCode );
				
				//----8 Column
				//MisQuery Vasooli_WithoutDemand Village Count
				@Query(value = "select district_name_marathi,	taluka_name_marathi,lgd_village_name "
						+ " from e_lgd_mapper "
						+ " where is_vasuli_without_demandadd='Y' and is_selected='Y' and district_code =?1 and taluka_code=?2 " 
						+ "", nativeQuery = true)
				List<Object[]> getVasooliCountWithOutDemand( String districtCode, String talukaCode );
				
				//getDemandGeneratedVillageCount
			    //--- 7 column
				@Query(value="select  "
						
						+ " district_name_marathi,  "
						
						+ " taluka_name_marathi,  "
						
						+ " lgd_village_name  "
								
						+ " from e_lgd_mapper  where is_demand_completed ='Y' and district_code=?1 and taluka_code =?2"   
						+ " ",nativeQuery=true)
				List<Object[]> getDemandGeneratedVillageCount(String districtCode,String talukaCode);
				
				//is_gaon_namuna_added
			    //----6 Column 
				@Query(value="select  "
						
						+ " district_name_marathi,  "
						
						+ " taluka_name_marathi,  "
						
						+ " lgd_village_name  "
						
						+ " from e_lgd_mapper  where  is_gaon_namuna_added ='Y' and district_code=?1 and taluka_code =?2"   
						+ " ",nativeQuery=true)
				List<Object[]> getGaonNamunaCompleted(String districtCode,String talukaCode);
				
				@Query(value="select  "
						
						+ " district_name_marathi,  "
						
						+ " taluka_name_marathi,  "
						
						+ " lgd_village_name  "
								
						+ " from e_lgd_mapper_mis  where dyslr_work_started ='Y' and district_code=?1 and taluka_code =?2"   
						+ " ",nativeQuery=true)
				List<Object[]> getDyslrStarted(String districtCode,String talukaCode);
				
				
				
				//Dyslr changes 26-06-2024
				//total
				@Query(value="select  "
						+ "sum(totalVillagesCount) totalVillagesCount, "
						+ "sum(totalDyslrWorkCount) totalDyslrWorkCount, "
						+ "sum(dyslrWorkCompletedCount) dyslrWorkCompletedCount, "
						+ "sum(talukaCount) talukaCount ,"
						+ "count(*) districtCount ,"
						+ "sum(totalDyslrAbstractAddedCount) totalDyslrAbstractAddedCount "
						+ "from "
						+ "( "
						+ "select "
						+ "f.division_code, "
						+ "f.district_code,"
						+ "f.district_name, "
						+ "(select sum(total_villages) from e_division_district_taluka_count where trim(district_code)=trim(f.district_code)) totalVillagesCount, "
						+ "(select count(*) from e_lgd_mapper_mis where dyslr_work_started='Y' and trim(district_code)=trim(f.district_code)) totalDyslrWorkCount, "
						+ "(select count(*) from e_lgd_mapper_mis where is_dyslr_work_completed='Y' and trim(district_code)=trim(f.district_code)) dyslrWorkCompletedCount, "
						+ "(select count(*) from e_division_district_taluka_count where trim(district_code)=trim(f.district_code)) talukaCount, "
						+ "(select count(*) from e_lgd_mapper_mis where is_dyslr_abstract_added='Y' and trim(district_code)=trim(f.district_code)) totalDyslrAbstractAddedCount "
						+ "from  e_district_census f order by cast(district_code as int) "
						+ ")B where division_code=?1 "
						+ "group by division_code;"
						+ "" , nativeQuery=true)
				List<Object[]> getTotalByDivisionCodeforDYSLR(String divisionCode);
				
				@Query(value="select  "
						+ "f.division_code, "
						+ "f.district_code, "
						+ "f.district_name, "
						+ "(select sum(total_villages) from e_division_district_taluka_count where district_code=f.district_code) totalVillagesCount, "
						+ "(select count(ccode) from e_lgd_mapper_mis where dyslr_work_started='Y' and district_code=f.district_code) totalDyslrWorkCount, "
						+ "(select count(ccode) from e_lgd_mapper_mis where is_dyslr_work_completed='Y' and district_code=f.district_code) dyslrWorkCompletedCount, "
						+ "(select count(*) from e_division_district_taluka_count where district_code=f.district_code) talukaCount, "
						+ "(select count(division_code) from e_district_census where district_code=f.district_code) districtCount ,"
						+ "(select count(ccode) from e_lgd_mapper_mis where is_dyslr_abstract_added='Y' and district_code=f.district_code) totaldyslrAdbstractAddedCount "
						+ "from  e_district_census f where division_code=?1 order by cast(district_code as int)"
						+ "" , nativeQuery=true)
				List<Object[]> getByDivisionCodeforDYSLR(String disionCode);
				
				@Query(value="select    "
						+ "f.division_code,   "
						+ "f.division_name,   "
						+ "(select sum(total_villages) from e_division_district_taluka_count where division_code=f.division_code) totalVillagesCount,   "
						+ "(select count(ccode) from e_lgd_mapper_mis where dyslr_work_started='Y' and division_code=f.division_code) totalDyslrWorkCount, "
						+ "(select count(ccode) from e_lgd_mapper_mis where is_dyslr_work_completed='Y' and division_code=f.division_code) dyslrWorkCompletedCount, "
						+ "(select count(*) from e_division_district_taluka_count where division_code=f.division_code) talukaCount,  "
						+ "(select count(division_code) from e_district_census where division_code=f.division_code) districtCount, "
						+ "(select count(ccode) from e_lgd_mapper_mis where is_dyslr_abstract_added ='Y' and division_code=f.division_code) dyslrAbstractAddedCount "
						+ "from  e_division_census f "
						+ "" , nativeQuery=true)
				List<Object[]> getByAllDivisionCodeforDYSLR();
				
				
				@Query(value="select  "
						+ "sum(totalVillagesCount) totalVillagesCount, "
						+ "SUM(totalDyslrWorkCount) totalDyslrWorkCount, "
						+ "sum(dyslrWorkCompletedCount) dyslrWorkCompletedCount, "
						+ "sum(talukaCount) talukaCount, "
						+ "count(*) districtCount, "
						+ "sum(totalDyslrAbstractAddedCount) totalDyslrAbstractAddedCount "
						+ "from "
						+ "( "
						+ "select "
						+ "f.division_code, "
						+ "f.district_code,"
						+ "f.district_name, "

						+ "(select sum(total_villages) from e_division_district_taluka_count where trim(district_code)=trim(f.district_code)) totalVillagesCount, "
						+ "(select count(*) from e_division_district_taluka_count where trim(district_code)=trim(f.district_code)) talukaCount,"
						+ " (select count(*) from e_lgd_mapper_mis where dyslr_work_started='Y' and trim(district_code)=trim(f.district_code)) totalDyslrWorkCount, "
						+ " (select count(*) from e_lgd_mapper_mis where is_dyslr_work_completed='Y' and trim(district_code)=trim(f.district_code)) dyslrWorkCompletedCount, "
						+ " (select count(*) from e_lgd_mapper_mis where is_dyslr_abstract_added='Y' and trim(district_code)=trim(f.district_code)) totalDyslrAbstractAddedCount "
						+ "from  e_district_census f order by cast(district_code as int) "
						+ ")B  "
						+ "" , nativeQuery=true)
				List<Object[]> getTotalByAllDivisionCodeforDYSLR();
				
				//taluka query for tab 4
				@Query(value = "select  " 
						+ "f.district_code," 
						+ "f.district_name," 
						+ "et.taluka_code," 
						+ "et.taluka_name ,"
						//change in villagecount
						+ "(select count(*) from e_lgd_mapper where taluka_code=et.taluka_code and district_code=f.district_code) totalVillagesCount,  "
						//+ "(select sum(total_villages) from e_division_district_taluka_count where taluka_code=et.taluka_code) totalVillagesCount,"	
						+ "(select count(*) from e_division_district_taluka_count where taluka_code=et.taluka_code) talukaCount, "
						+ "(select count(distinct ccode) from e_lgd_mapper_mis where dyslr_work_started='Y' and taluka_code=et.taluka_code and district_code=f.district_code) totalDyslrWorkCount, "
						+ "(select count(distinct ccode) from e_lgd_mapper_mis where is_dyslr_work_completed='Y' and taluka_code=et.taluka_code and district_code=f.district_code) dyslrWorkCompletedCount,"		
						+ "(select count(distinct division_code) from e_district_census where district_code=f.district_code) districtCount,"
						+ "(select count(distinct ccode) from e_lgd_mapper_mis where is_dyslr_abstract_added='Y' and taluka_code=et.taluka_code and district_code=f.district_code) dyslrAbstractAddedCount "		
						//+ "from e_district_census f  inner join e_taluka_census et on et.district_code=f.district_code where "
						+ "from  e_district_census f  inner join e_taluka_census et on et.district_code=f.district_code where "
						+ " f.division_code=?1 and f.district_code=?2" 
						+ "", nativeQuery = true)
				List<Object[]> getByTalukaNameAndCodeTab4(String divisionCode, String districtCode);

				@Query(value = "select district_name_marathi,	taluka_name_marathi,lgd_village_name "
						+ " from e_lgd_mapper "
						+ " where is_demand_completed='N' and is_selected='Y' and is_challan_generated ='N' and district_code =?1 and taluka_code=?2 " 
						+ "", nativeQuery = true)
				List<Object[]> getVasooliCountWithOutSingleDemandGeneration( String districtCode, String talukaCode );
				
				@Query(value = "select district_name_marathi,	taluka_name_marathi,lgd_village_name "
						+ " from e_lgd_mapper_2023 "
						+ " where is_demand_completed='N' and is_challan_generated ='N' and district_code =?1 and taluka_code=?2 " 
						+ "", nativeQuery = true)
				List<Object[]> getVasooliCountWithOutSingleDemandGenerationPre( String districtCode, String talukaCode );
	
}
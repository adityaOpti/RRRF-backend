package com.probity.echawdi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.probity.echawdi.entity.OneTimeEntry;

/**
 * @author Bhagyashri Lolage
 *
 */

public interface OneTimeEntryRepository extends JpaRepository<OneTimeEntry, Long>{
	
	List<OneTimeEntry> findAllOneTimeEntryBycCodeAndKhataNoAndActiveFlagOrderByIdDesc(String cCode,String khataNo, char c);

	List<OneTimeEntry> findAllOneTimeEntryBycCodeAndActiveFlagAndRevenueYearOrderByKhataNoAsc(String cCode,Character activeFlag,String revenueYear);
	
	@Query(value="select * from  "
			+ "public.dblink "
			+ "( "
			+ "'dbname='||?2||' host='||?1||' user='||?4||' password='||?5, "
			+ "'select * from '||?3||'.lr_pending_dues o' "
			+ ")  "
			+ "AS "
			+ "OTE "
			+ "( "
			+ "	id bigint, "
			+ "    version integer, "
			+ "    created_user_id bigint, "
			+ "    create_dt_tm timestamp without time zone, "
			+ "    update_userid bigint, "
			+ "    update_dt_tm timestamp without time zone, "
			+ "    district_code integer, "
			+ "    taluka_code integer, "
			+ "    ccode text, "
			+ "    khata_no character varying(255), "
			+ "    miscellaneous_amount numeric(19,2), "
			+ "    net_pending numeric(19,2), "
			+ "    pre_year_pending_addl_educational_cess numeric(19,2), "
			+ "    pre_year_pending_addl_land_revenue numeric(19,2), "
			+ "    pre_year_pending_educational_cess numeric(19,2), "
			+ "    pre_year_pending_na_cess numeric(19,2), "
			+ "    pre_year_pending_employee_guarantee_scheme numeric(19,2), "
			+ "    pre_year_pending_gp numeric(19,2), "
			+ "    pre_year_pending_jm numeric(19,2), "
			+ "    pre_year_pending_zp numeric(19,2), "
			+ "    pre_year_pending_sankirn_jm_with numeric(19,2), "
			+ "    pre_year_pending_sankirn_jm_without numeric(19,2), "
			+ "    pre_year_pending_notice_fee numeric(19,2), "
			+ "    revenue_year character varying(255), "
			+ "    active_flag character(1), "
			+ "    pre_year_notice_fee numeric(19,2), "
			+ "    pre_year_sankirn_jm_with numeric(19,2), "
			+ "    pre_year_sankirn_jm_without numeric(19,2) "
			+ ") "
			+ "where  "
			+ "trim(ccode) =?6  "
			+ "and active_flag=?7  "
			+ "and revenue_year=?8 "
			+ "and khata_no not in "
			+ "( "
			+ "select khata_no from public.dblink "
			+ "( "
			+ "'dbname='||?2||' host='||?1||' user='||?4||' password='||?5, "
			+ "'select khata_no,ccode,revenue_year,active_flag from '  "
			+ "||?3||'.land_revenue_demand' "
			+ ") "
			+ "AS LRD(khata_no text,ccode text,revenue_year text,active_flag character(1))	 "
			+ "where trim(ccode) =?6 and revenue_year=?8  and active_flag='Y') "
			+ "order by cast(khata_no as bigint) "
			+"", nativeQuery = true)
	List<OneTimeEntry> findAllOneTimeEntry(
			String eHost,String eDbName,String eSchemaName,String eUser,String ePassword,
			String cCode,Character activeFlag,String revenueYear
			);

	@Query(value="SELECT  "
			+ "KHATA_NO  "
			+ "FROM  "
			+ "public.dblink "
			+ "( "
			+ "'dbname='||?2||' host='||?1||' user='||?4||' password='||?5, "
			+ "'select  "
			+ "khata_no, "
			+ "ccode, "
			+ "revenue_year, "
			+ "active_flag "
			+ "from '||?3||'.LR_PENDING_DUES' "
			+ ") "
			+ "as  "
			+ "ote "
			+ "( "
			+ "khata_no text, "
			+ "ccode text, "
			+ "revenue_year text, "
			+ "active_flag character(1) "
			+ ")  "
			+ "WHERE  "
			+ "trim(CCODE)=?6  "
			+ "AND REVENUE_YEAR=?7  "
			+ "AND ACTIVE_FLAG=?8"+"", nativeQuery = true)
	List<Object> findKhataNo(
			String eHost,String eDbName,String eSchemaName,String eUser,String ePassword,
			String cCode,String revenueYear,Character activeFlag
			);
	
	 @Query(value="select "
		   		+ " khata_no, pre_year_pending_jm, pre_year_pending_zp, pre_year_pending_gp,pre_year_pending_addl_land_revenue, "
		   		+ " pre_year_pending_educational_cess,pre_year_pending_addl_educational_cess,pre_year_pending_employee_guarantee_scheme, "
		   		+ " pre_year_pending_na_cess,pre_year_sankirn_jm_with,pre_year_sankirn_jm_without,pre_year_notice_fee, net_pending from   "
		   		+ " (select * from public.dblink('dbname='||?2||' host='||?1||' user='||?4||' password='||?5,  "
		   		+ " 'select khata_no, pre_year_pending_jm, pre_year_pending_zp, pre_year_pending_gp,pre_year_pending_addl_land_revenue, "
		   		+ " pre_year_pending_educational_cess,pre_year_pending_addl_educational_cess,pre_year_pending_employee_guarantee_scheme,  "
		   		+ " pre_year_pending_na_cess,pre_year_sankirn_jm_with,pre_year_sankirn_jm_without,pre_year_notice_fee, net_pending,ccode,active_flag, revenue_year "
		   		+ " from '||?3||'.land_revenue_demand ' )   "
		   		+ " AS(khata_no text,pre_year_pending_jm numeric,pre_year_pending_zp numeric, pre_year_pending_gp numeric,pre_year_pending_addl_land_revenue numeric, "
		   		+ " pre_year_pending_educational_cess numeric,pre_year_pending_addl_educational_cess numeric,pre_year_pending_employee_guarantee_scheme numeric,  "
		   		+ " pre_year_pending_na_cess numeric,pre_year_sankirn_jm_with numeric,pre_year_sankirn_jm_without numeric,pre_year_notice_fee numeric, net_pending numeric, ccode text, active_flag character, revenue_year character varying ) "
		   		+ " where trim(ccode)=?6 and active_flag='Y' and revenue_year=?7 "
		   		+ " )a "
			,nativeQuery=true)
			List<Object[]> getOneTimeReportRecords(String fHost,String fDbName,String fSchemaName,String fUser,String fPassword,String cCode, String revenueYear);
}
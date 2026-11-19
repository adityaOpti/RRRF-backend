/**
 * 
 */
package com.probity.echawdi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.probity.echawdi.entity.ChallanDtl;

/**
 * @author Sandeep
 *
 */

@Repository
public interface ChallanRepository extends JpaRepository<ChallanDtl, Long>{

//	List<ChallanDtl> findByChallanNoContaining(String challanNo);

//	List<ChallanDtl> findByChallanNoLike(String challanNo);

	List<ChallanDtl> findBycCodeAndRevenueYearAndStatusAndActiveFlag(String cCode, String revenueYear, String status, Character activeFlag);
	/* 
	 * old query 
	@Query(value="SELECT   "
			+ "distinct challan_id,   "
			+ "id,  "
			+ "ccode,  "
			+ "bank_receipt_number_0045,  "
			+ "bank_receipt_path,  "
			+ "bank_receipt_path_0045,    "
			+ "challan_no,  "
			+ "challan_no_0045,  "
			+ "mode_of_payment,  "
			+ "total_amount,  "
			+ "total_of_head_0029,  "
			+ "total_of_head_0045,   "
			+ "bank_name,  "
			+ "bank_address,  "
			+ "actual_file_name,  "
			+ "bank_receipt_number,  "
			+ "sum(addl_land_revenue) as addl_land_revenue,  "
			+ "sum(addl_educational_cess) as addl_educational_cess,    "
			+ "sum(educational_cess) as educational_cess,  "
			+ "sum(employee_guarantee_scheme) as employee_guarantee_scheme,  "
			+ "sum(gp_akrushik) as gp_akrushik,  "
			+ "sum(gp_bindumala) as gp_bindumala,  "
			+ "sum(gp_dumala) as gp_dumala,  "
			+ "sum(gp_sankirn) as gp_sankirn,  "
			+ "sum(gp_vajasut) as gp_vajasut,  "
			+ "sum(jm_akrushik) as jm_akrushik,  "
			+ "sum(jm_bindumala) as jm_bindumala,  "
			+ "sum(jm_dumala) as jm_dumala,  "
			+ "sum(jm_sankirn) as jm_sankirn,  "
			+ "sum(jm_vajasut) as jm_vajasut,    "
			+ "sum(net_amount) as net_amount,  "
			+ "sum(net_pending) as net_pending,  "
			+ "sum(net_received) as net_received,  "
			+ "sum(notice_amount) as notice_amount,    "
			+ "sum(pre_year_notice_fee) as pre_year_notice_fee,  "
			+ "sum(pre_year_pending_addl_educational_cess) as pre_year_pending_addl_educational_cess,  "
			+ "sum(pre_year_pending_addl_land_revenue) as pre_year_pending_addl_land_revenue,  "
			+ "sum(pre_year_pending_educational_cess) as pre_year_pending_educational_cess,  "
			+ "sum(pre_year_pending_employee_guarantee_scheme) as pre_year_pending_employee_guarantee_scheme,  "
			+ "sum(pre_year_pending_gp) as pre_year_pending_gp,    "
			+ "sum(pre_year_pending_jm) as pre_year_pending_jm,  "
			+ "sum(pre_year_pending_na_cess) as pre_year_pending_na_cess,  "
			+ "sum(pre_year_pending_zp) as pre_year_pending_zp,  "
			+ "sum(pre_year_sankirn_jm_with) as pre_year_sankirn_jm_with,  "
			+ "sum(pre_year_sankirn_jm_without) as pre_year_sankirn_jm_without,  "
			+ "sum(zp_akrushik) as zp_akrushik,  "
			+ "sum(zp_bindumala) as zp_bindumala,    "
			+ "sum(zp_dumala) as zp_dumala,  "
			+ "sum(zp_sankirn) as zp_sankirn,  "
			+ "sum(zp_vajasut) as zp_vajasut,  "
			+ "string_agg(receipt_no,',  ') as receiptno,"
			+ "status,"
			+ "challan_date,"
			+ "count(receipt_no) as countofReceipts "
			+ "FROM public.dblink('dbname='|| ?2 ||' host='|| ?1 ||' user='|| ?4 || ' password='|| ?5 ,'      "
			+ "select   "
			+ "challan_id,   "
			+ "cd.id,  "
			+ "cd.ccode,  "
			+ "bank_receipt_number_0045,  "
			+ "bank_receipt_path,  "
			+ "bank_receipt_path_0045,    "
			+ "challan_no,  "
			+ "challan_no_0045,  "
			+ "cd.mode_of_payment,  "
			+ "total_amount,  "
			+ "total_of_head_0029,  "
			+ "total_of_head_0045,   "
			+ "bank_name,  "
			+ "bank_address,  "
			+ "actual_file_name,  "
			+ "bank_receipt_number,  "
			+ "addl_land_revenue,  "
			+ "addl_educational_cess,    "
			+ "educational_cess,  "
			+ "employee_guarantee_scheme,  "
			+ "gp_akrushik,  "
			+ "gp_bindumala,  "
			+ "gp_dumala,  "
			+ "gp_sankirn,  "
			+ "gp_vajasut,  "
			+ "jm_akrushik,  "
			+ "jm_bindumala,  "
			+ "jm_dumala,  "
			+ "jm_sankirn,  "
			+ "jm_vajasut,    "
			+ "net_amount,  "
			+ "net_pending,  "
			+ "net_received,  "
			+ "notice_amount,    "
			+ "pre_year_notice_fee,  "
			+ "pre_year_pending_addl_educational_cess,  "
			+ "pre_year_pending_addl_land_revenue,  "
			+ "pre_year_pending_educational_cess,  "
			+ "pre_year_pending_employee_guarantee_scheme,  "
			+ "pre_year_pending_gp,    "
			+ "pre_year_pending_jm,  "
			+ "pre_year_pending_na_cess,  "
			+ "pre_year_pending_zp,  "
			+ "pre_year_sankirn_jm_with,  "
			+ "pre_year_sankirn_jm_without,  "
			+ "zp_akrushik,  "
			+ "zp_bindumala,    "
			+ "zp_dumala,  "
			+ "zp_sankirn,  "
			+ "zp_vajasut,  "
			+ "receipt_no,  "
			+ "cd.revenue_year,  "
			+ "status,"
			+ "challan_date,  "
			+ "cd.active_flag  "
			+ "from '|| ?3 ||'.land_revenue_demand  lrd, '|| ?3 ||'.challan_dtl cd  ')   "
			+ "As(  "
			+ "challan_id text,  "
			+ "id text,  "
			+ "ccode text,  "
			+ "bank_receipt_number_0045 text,  "
			+ "bank_receipt_path text,  "
			+ "bank_receipt_path_0045 text,    "
			+ "challan_no text,  "
			+ "challan_no_0045 text,  "
			+ "mode_of_payment text,  "
			+ "total_amount text,  "
			+ "total_of_head_0029 text,  "
			+ "total_of_head_0045 text,   "
			+ "bank_name text,  "
			+ "bank_address text,  "
			+ "actual_file_name text,  "
			+ "bank_receipt_number text,  "
			+ "addl_land_revenue numeric,  "
			+ "addl_educational_cess numeric,    "
			+ "educational_cess numeric,  "
			+ "employee_guarantee_scheme numeric,  "
			+ "gp_akrushik numeric,  "
			+ "gp_bindumala numeric,  "
			+ "gp_dumala numeric,  "
			+ "gp_sankirn numeric,  "
			+ "gp_vajasut numeric,  "
			+ "jm_akrushik numeric,  "
			+ "jm_bindumala numeric,  "
			+ "jm_dumala numeric,  "
			+ "jm_sankirn numeric,  "
			+ "jm_vajasut numeric,    "
			+ "net_amount numeric,  "
			+ "net_pending numeric,  "
			+ "net_received numeric,  "
			+ "notice_amount numeric,    "
			+ "pre_year_notice_fee numeric,  "
			+ "pre_year_pending_addl_educational_cess numeric,  "
			+ "pre_year_pending_addl_land_revenue numeric,  "
			+ "pre_year_pending_educational_cess numeric,  "
			+ "pre_year_pending_employee_guarantee_scheme numeric,  "
			+ "pre_year_pending_gp numeric,    "
			+ "pre_year_pending_jm numeric,  "
			+ "pre_year_pending_na_cess numeric,  "
			+ "pre_year_pending_zp numeric,  "
			+ "pre_year_sankirn_jm_with numeric,  "
			+ "pre_year_sankirn_jm_without numeric,  "
			+ "zp_akrushik numeric,  "
			+ "zp_bindumala numeric,    "
			+ "zp_dumala numeric,  "
			+ "zp_sankirn numeric,  "
			+ "zp_vajasut numeric,  "
			+ "receipt_no text,  "
			+ "revenue_year text,  "
			+ "status text,"
			+ "challan_date timestamp without time zone ,  "
			+ "active_flag character(1)  "
			+ ")  "
			+ "where  trim(ccode)= ?6  and revenue_year= ?7 and  status= ?8 and  challan_id=id   "
			+ "and active_flag='Y'  "
			+ "group by   "
			+ "challan_id,   "
			+ "id,  "
			+ "ccode,  "
			+ "bank_receipt_number_0045,  "
			+ "bank_receipt_path,  "
			+ "bank_receipt_path_0045,    "
			+ "challan_no,  "
			+ "challan_no_0045,  "
			+ "mode_of_payment,  "
			+ "total_amount,  "
			+ "total_of_head_0029,  "
			+ "total_of_head_0045,   "
			+ "bank_name,  "
			+ "bank_address,  "
			+ "actual_file_name,  "
			+ "bank_receipt_number,  "
			+ "ccode,  "
			+ "bank_receipt_number_0045,  "
			+ "bank_receipt_path,  "
			+ "bank_receipt_path_0045,    "
			+ "challan_no,  "
			+ "challan_no_0045,  "
			+ "mode_of_payment,  "
			+ "total_amount,  "
			+ "total_of_head_0029,  "
			+ "total_of_head_0045,   "
			+ "bank_name,  "
			+ "bank_address,  "
			+ "actual_file_name,  "
			+ "bank_receipt_number,"
			+ "status,"
			+ "challan_date  "
			+ ""
			+ "", nativeQuery = true)
			public List<Object[]> getChallanDetailsAll(
			String eHost,String eDbName,String eSchemaName,String eUser,String ePassword,
			String cCode, String revenueYear, String status, Character activeFlag
            );
			*/
	//new change in query for challan tapshil Api is pending  03/09/2026
	@Query(value =
	        "SELECT " +
	        "    challan_id, " +
	        "    id, " +
	        "    ccode, " +
	        "    bank_receipt_number_0045, " +
	        "    bank_receipt_path, " +
	        "    bank_receipt_path_0045, " +
	        "    challan_no, " +
	        "    challan_no_0045, " +
	        "    mode_of_payment, " +
	        "    total_amount, " +
	        "    total_of_head_0029, " +
	        "    total_of_head_0045, " +
	        "    bank_name, " +
	        "    bank_address, " +
	        "    actual_file_name, " +
	        "    bank_receipt_number, " +
	        "    SUM(addl_land_revenue) AS addl_land_revenue, " +
	        "    SUM(addl_educational_cess) AS addl_educational_cess, " +
	        "    SUM(educational_cess) AS educational_cess, " +
	        "    SUM(employee_guarantee_scheme) AS employee_guarantee_scheme, " +
	        "    SUM(gp_akrushik) AS gp_akrushik, " +
	        "    SUM(gp_bindumala) AS gp_bindumala, " +
	        "    SUM(gp_dumala) AS gp_dumala, " +
	        "    SUM(gp_sankirn) AS gp_sankirn, " +
	        "    SUM(gp_vajasut) AS gp_vajasut, " +
	        "    SUM(jm_akrushik) AS jm_akrushik, " +
	        "    SUM(jm_bindumala) AS jm_bindumala, " +
	        "    SUM(jm_dumala) AS jm_dumala, " +
	        "    SUM(jm_sankirn) AS jm_sankirn, " +
	        "    SUM(jm_vajasut) AS jm_vajasut, " +
	        "    SUM(net_amount) AS net_amount, " +
	        "    SUM(net_pending) AS net_pending, " +
	        "    SUM(net_received) AS net_received, " +
	        "    SUM(notice_amount) AS notice_amount, " +
	        "    SUM(pre_year_notice_fee) AS pre_year_notice_fee, " +
	        "    SUM(pre_year_pending_addl_educational_cess) AS pre_year_pending_addl_educational_cess, " +
	        "    SUM(pre_year_pending_addl_land_revenue) AS pre_year_pending_addl_land_revenue, " +
	        "    SUM(pre_year_pending_educational_cess) AS pre_year_pending_educational_cess, " +
	        "    SUM(pre_year_pending_employee_guarantee_scheme) AS pre_year_pending_employee_guarantee_scheme, " +
	        "    SUM(pre_year_pending_gp) AS pre_year_pending_gp, " +
	        "    SUM(pre_year_pending_jm) AS pre_year_pending_jm, " +
	        "    SUM(pre_year_pending_na_cess) AS pre_year_pending_na_cess, " +
	        "    SUM(pre_year_pending_zp) AS pre_year_pending_zp, " +
	        "    SUM(pre_year_sankirn_jm_with) AS pre_year_sankirn_jm_with, " +
	        "    SUM(pre_year_sankirn_jm_without) AS pre_year_sankirn_jm_without, " +
	        "    SUM(zp_akrushik) AS zp_akrushik, " +
	        "    SUM(zp_bindumala) AS zp_bindumala, " +
	        "    SUM(zp_dumala) AS zp_dumala, " +
	        "    SUM(zp_sankirn) AS zp_sankirn, " +
	        "    SUM(zp_vajasut) AS zp_vajasut, " +
	        "    STRING_AGG(receipt_no, ',  ') AS receiptno, " +
	        "    status, " +
	        "    challan_date, " +
	        "    COUNT(receipt_no) AS countofReceipts " +

	        "FROM public.dblink(" +

	        "    'dbname=' || ?2 || " +
	        "    ' host=' || ?1 || " +
	        "    ' user=' || ?4 || " +
	        "    ' password=' || ?5, " +

	        "    format(" +
	        "        'SELECT " +
	        "            lrd.challan_id, " +
	        "            cd.id, " +
	        "            cd.ccode, " +
	        "            cd.bank_receipt_number_0045, " +
	        "            cd.bank_receipt_path, " +
	        "            cd.bank_receipt_path_0045, " +
	        "            cd.challan_no, " +
	        "            cd.challan_no_0045, " +
	        "            cd.mode_of_payment, " +
	        "            cd.total_amount, " +
	        "            cd.total_of_head_0029, " +
	        "            cd.total_of_head_0045, " +
	        "            cd.bank_name, " +
	        "            cd.bank_address, " +
	        "            cd.actual_file_name, " +
	        "            cd.bank_receipt_number, " +
	        "            lrd.addl_land_revenue, " +
	        "            lrd.addl_educational_cess, " +
	        "            lrd.educational_cess, " +
	        "            lrd.employee_guarantee_scheme, " +
	        "            lrd.gp_akrushik, " +
	        "            lrd.gp_bindumala, " +
	        "            lrd.gp_dumala, " +
	        "            lrd.gp_sankirn, " +
	        "            lrd.gp_vajasut, " +
	        "            lrd.jm_akrushik, " +
	        "            lrd.jm_bindumala, " +
	        "            lrd.jm_dumala, " +
	        "            lrd.jm_sankirn, " +
	        "            lrd.jm_vajasut, " +
	        "            lrd.net_amount, " +
	        "            lrd.net_pending, " +
	        "            lrd.net_received, " +
	        "            lrd.notice_amount, " +
	        "            lrd.pre_year_notice_fee, " +
	        "            lrd.pre_year_pending_addl_educational_cess, " +
	        "            lrd.pre_year_pending_addl_land_revenue, " +
	        "            lrd.pre_year_pending_educational_cess, " +
	        "            lrd.pre_year_pending_employee_guarantee_scheme, " +
	        "            lrd.pre_year_pending_gp, " +
	        "            lrd.pre_year_pending_jm, " +
	        "            0 AS pre_year_pending_na_cess, " +
	        "            lrd.pre_year_pending_zp, " +
	        "            lrd.pre_year_sankirn_jm_with, " +
	        "            lrd.pre_year_sankirn_jm_without, " +
	        "            lrd.zp_akrushik, " +
	        "            lrd.zp_bindumala, " +
	        "            lrd.zp_dumala, " +
	        "            lrd.zp_sankirn, " +
	        "            lrd.zp_vajasut, " +
	        "            lrd.receipt_no, " +
	        "            lrd.revenue_year, " +
	        "            cd.status, " +
	        "            cd.challan_date, " +
	        "            cd.active_flag " +
	        "        FROM %I.land_revenue_demand lrd " +
	        "        INNER JOIN %I.challan_dtl cd " +
	        "            ON lrd.challan_id = cd.id " +
	        "        WHERE TRIM(cd.ccode) = %L " +
	        "          AND lrd.revenue_year = %L " +
	        "          AND cd.status = %L " +
	        "          AND cd.active_flag = %L', " +
	        "        ?3, ?3, ?6, ?7, ?8, ?9" +
	        "    )" +

	        ") AS x(" +
	        "    challan_id text, " +
	        "    id text, " +
	        "    ccode text, " +
	        "    bank_receipt_number_0045 text, " +
	        "    bank_receipt_path text, " +
	        "    bank_receipt_path_0045 text, " +
	        "    challan_no text, " +
	        "    challan_no_0045 text, " +
	        "    mode_of_payment text, " +
	        "    total_amount text, " +
	        "    total_of_head_0029 text, " +
	        "    total_of_head_0045 text, " +
	        "    bank_name text, " +
	        "    bank_address text, " +
	        "    actual_file_name text, " +
	        "    bank_receipt_number text, " +
	        "    addl_land_revenue numeric, " +
	        "    addl_educational_cess numeric, " +
	        "    educational_cess numeric, " +
	        "    employee_guarantee_scheme numeric, " +
	        "    gp_akrushik numeric, " +
	        "    gp_bindumala numeric, " +
	        "    gp_dumala numeric, " +
	        "    gp_sankirn numeric, " +
	        "    gp_vajasut numeric, " +
	        "    jm_akrushik numeric, " +
	        "    jm_bindumala numeric, " +
	        "    jm_dumala numeric, " +
	        "    jm_sankirn numeric, " +
	        "    jm_vajasut numeric, " +
	        "    net_amount numeric, " +
	        "    net_pending numeric, " +
	        "    net_received numeric, " +
	        "    notice_amount numeric, " +
	        "    pre_year_notice_fee numeric, " +
	        "    pre_year_pending_addl_educational_cess numeric, " +
	        "    pre_year_pending_addl_land_revenue numeric, " +
	        "    pre_year_pending_educational_cess numeric, " +
	        "    pre_year_pending_employee_guarantee_scheme numeric, " +
	        "    pre_year_pending_gp numeric, " +
	        "    pre_year_pending_jm numeric, " +
	        "    pre_year_pending_na_cess numeric, " +
	        "    pre_year_pending_zp numeric, " +
	        "    pre_year_sankirn_jm_with numeric, " +
	        "    pre_year_sankirn_jm_without numeric, " +
	        "    zp_akrushik numeric, " +
	        "    zp_bindumala numeric, " +
	        "    zp_dumala numeric, " +
	        "    zp_sankirn numeric, " +
	        "    zp_vajasut numeric, " +
	        "    receipt_no text, " +
	        "    revenue_year text, " +
	        "    status text, " +
	        "    challan_date timestamp without time zone, " +
	        "    active_flag character(1)" +
	        ") " +

	        "GROUP BY " +
	        "    challan_id, id, ccode, " +
	        "    bank_receipt_number_0045, " +
	        "    bank_receipt_path, " +
	        "    bank_receipt_path_0045, " +
	        "    challan_no, challan_no_0045, " +
	        "    mode_of_payment, total_amount, " +
	        "    total_of_head_0029, total_of_head_0045, " +
	        "    bank_name, bank_address, " +
	        "    actual_file_name, bank_receipt_number, " +
	        "    status, challan_date",

	        nativeQuery = true)
	List<Object[]> getChallanDetailsAll(
	        String eHost,
	        String eDbName,
	        String eSchemaName,
	        String eUser,
	        String ePassword,
	        String cCode,
	        String revenueYear,
	        String status,
	        Character activeFlag
	);
		/*	//pre
			@Query(value="SELECT   "
					+ "distinct challan_id,   "
					+ "id,  "
					+ "ccode,  "
					+ "bank_receipt_number_0045,  "
					+ "bank_receipt_path,  "
					+ "bank_receipt_path_0045,    "
					+ "challan_no,  "
					+ "challan_no_0045,  "
					+ "mode_of_payment,  "
					+ "total_amount,  "
					+ "total_of_head_0029,  "
					+ "total_of_head_0045,   "
					+ "bank_name,  "
					+ "bank_address,  "
					+ "actual_file_name,  "
					+ "bank_receipt_number,  "
					+ "sum(addl_land_revenue) as addl_land_revenue,  "
					+ "sum(addl_educational_cess) as addl_educational_cess,    "
					+ "sum(educational_cess) as educational_cess,  "
					+ "sum(employee_guarantee_scheme) as employee_guarantee_scheme,  "
					+ "sum(gp_akrushik) as gp_akrushik,  "
					+ "sum(gp_bindumala) as gp_bindumala,  "
					+ "sum(gp_dumala) as gp_dumala,  "
					+ "sum(gp_sankirn) as gp_sankirn,  "
					+ "sum(gp_vajasut) as gp_vajasut,  "
					+ "sum(jm_akrushik) as jm_akrushik,  "
					+ "sum(jm_bindumala) as jm_bindumala,  "
					+ "sum(jm_dumala) as jm_dumala,  "
					+ "sum(jm_sankirn) as jm_sankirn,  "
					+ "sum(jm_vajasut) as jm_vajasut,    "
					+ "sum(net_amount) as net_amount,  "
					+ "sum(net_pending) as net_pending,  "
					+ "sum(net_received) as net_received,  "
					+ "sum(notice_amount) as notice_amount,    "
					+ "sum(pre_year_notice_fee) as pre_year_notice_fee,  "
					+ "sum(pre_year_pending_addl_educational_cess) as pre_year_pending_addl_educational_cess,  "
					+ "sum(pre_year_pending_addl_land_revenue) as pre_year_pending_addl_land_revenue,  "
					+ "sum(pre_year_pending_educational_cess) as pre_year_pending_educational_cess,  "
					+ "sum(pre_year_pending_employee_guarantee_scheme) as pre_year_pending_employee_guarantee_scheme,  "
					+ "sum(pre_year_pending_gp) as pre_year_pending_gp,    "
					+ "sum(pre_year_pending_jm) as pre_year_pending_jm,  "
					+ "sum(pre_year_pending_na_cess) as pre_year_pending_na_cess,  "
					+ "sum(pre_year_pending_zp) as pre_year_pending_zp,  "
					+ "sum(pre_year_sankirn_jm_with) as pre_year_sankirn_jm_with,  "
					+ "sum(pre_year_sankirn_jm_without) as pre_year_sankirn_jm_without,  "
					+ "sum(zp_akrushik) as zp_akrushik,  "
					+ "sum(zp_bindumala) as zp_bindumala,    "
					+ "sum(zp_dumala) as zp_dumala,  "
					+ "sum(zp_sankirn) as zp_sankirn,  "
					+ "sum(zp_vajasut) as zp_vajasut,  "
					+ "string_agg(receipt_no,',  ') as receiptno,"
					+ "status,"
					+ "challan_date,"
					+ "count(receipt_no) as countofReceipts "
					+ "FROM public.dblink('dbname='|| ?2 ||' host='|| ?1 ||' user='|| ?4 || ' password='|| ?5 ,'      "
					+ "select   "
					+ "challan_id,   "
					+ "cd.id,  "
					+ "cd.ccode,  "
					+ "bank_receipt_number_0045,  "
					+ "bank_receipt_path,  "
					+ "bank_receipt_path_0045,    "
					+ "challan_no,  "
					+ "challan_no_0045,  "
					+ "cd.mode_of_payment,  "
					+ "total_amount,  "
					+ "total_of_head_0029,  "
					+ "total_of_head_0045,   "
					+ "bank_name,  "
					+ "bank_address,  "
					+ "actual_file_name,  "
					+ "bank_receipt_number,  "
					+ "addl_land_revenue,  "
					+ "addl_educational_cess,    "
					+ "educational_cess,  "
					+ "employee_guarantee_scheme,  "
					+ "gp_akrushik,  "
					+ "gp_bindumala,  "
					+ "gp_dumala,  "
					+ "gp_sankirn,  "
					+ "gp_vajasut,  "
					+ "jm_akrushik,  "
					+ "jm_bindumala,  "
					+ "jm_dumala,  "
					+ "jm_sankirn,  "
					+ "jm_vajasut,    "
					+ "net_amount,  "
					+ "net_pending,  "
					+ "net_received,  "
					+ "notice_amount,    "
					+ "pre_year_notice_fee,  "
					+ "pre_year_pending_addl_educational_cess,  "
					+ "pre_year_pending_addl_land_revenue,  "
					+ "pre_year_pending_educational_cess,  "
					+ "pre_year_pending_employee_guarantee_scheme,  "
					+ "pre_year_pending_gp,    "
					+ "pre_year_pending_jm,  "
					+ "pre_year_pending_na_cess,  "
					+ "pre_year_pending_zp,  "
					+ "pre_year_sankirn_jm_with,  "
					+ "pre_year_sankirn_jm_without,  "
					+ "zp_akrushik,  "
					+ "zp_bindumala,    "
					+ "zp_dumala,  "
					+ "zp_sankirn,  "
					+ "zp_vajasut,  "
					+ "receipt_no,  "
					+ "cd.revenue_year,  "
					+ "status,"
					+ "challan_date,  "
					+ "cd.active_flag  "
					+ "from '|| ?3 ||'.land_revenue_demand_2023  lrd, '|| ?3 ||'.challan_dtl cd  ')   "
					+ "As(  "
					+ "challan_id text,  "
					+ "id text,  "
					+ "ccode text,  "
					+ "bank_receipt_number_0045 text,  "
					+ "bank_receipt_path text,  "
					+ "bank_receipt_path_0045 text,    "
					+ "challan_no text,  "
					+ "challan_no_0045 text,  "
					+ "mode_of_payment text,  "
					+ "total_amount text,  "
					+ "total_of_head_0029 text,  "
					+ "total_of_head_0045 text,   "
					+ "bank_name text,  "
					+ "bank_address text,  "
					+ "actual_file_name text,  "
					+ "bank_receipt_number text,  "
					+ "addl_land_revenue numeric,  "
					+ "addl_educational_cess numeric,    "
					+ "educational_cess numeric,  "
					+ "employee_guarantee_scheme numeric,  "
					+ "gp_akrushik numeric,  "
					+ "gp_bindumala numeric,  "
					+ "gp_dumala numeric,  "
					+ "gp_sankirn numeric,  "
					+ "gp_vajasut numeric,  "
					+ "jm_akrushik numeric,  "
					+ "jm_bindumala numeric,  "
					+ "jm_dumala numeric,  "
					+ "jm_sankirn numeric,  "
					+ "jm_vajasut numeric,    "
					+ "net_amount numeric,  "
					+ "net_pending numeric,  "
					+ "net_received numeric,  "
					+ "notice_amount numeric,    "
					+ "pre_year_notice_fee numeric,  "
					+ "pre_year_pending_addl_educational_cess numeric,  "
					+ "pre_year_pending_addl_land_revenue numeric,  "
					+ "pre_year_pending_educational_cess numeric,  "
					+ "pre_year_pending_employee_guarantee_scheme numeric,  "
					+ "pre_year_pending_gp numeric,    "
					+ "pre_year_pending_jm numeric,  "
					+ "pre_year_pending_na_cess numeric,  "
					+ "pre_year_pending_zp numeric,  "
					+ "pre_year_sankirn_jm_with numeric,  "
					+ "pre_year_sankirn_jm_without numeric,  "
					+ "zp_akrushik numeric,  "
					+ "zp_bindumala numeric,    "
					+ "zp_dumala numeric,  "
					+ "zp_sankirn numeric,  "
					+ "zp_vajasut numeric,  "
					+ "receipt_no text,  "
					+ "revenue_year text,  "
					+ "status text,"
					+ "challan_date timestamp without time zone ,  "
					+ "active_flag character(1)  "
					+ ")  "
					+ "where  trim(ccode)= ?6  and revenue_year= ?7 and  status= ?8 and  challan_id=id   "
					+ "and active_flag='Y'  "
					+ "group by   "
					+ "challan_id,   "
					+ "id,  "
					+ "ccode,  "
					+ "bank_receipt_number_0045,  "
					+ "bank_receipt_path,  "
					+ "bank_receipt_path_0045,    "
					+ "challan_no,  "
					+ "challan_no_0045,  "
					+ "mode_of_payment,  "
					+ "total_amount,  "
					+ "total_of_head_0029,  "
					+ "total_of_head_0045,   "
					+ "bank_name,  "
					+ "bank_address,  "
					+ "actual_file_name,  "
					+ "bank_receipt_number,  "
					+ "ccode,  "
					+ "bank_receipt_number_0045,  "
					+ "bank_receipt_path,  "
					+ "bank_receipt_path_0045,    "
					+ "challan_no,  "
					+ "challan_no_0045,  "
					+ "mode_of_payment,  "
					+ "total_amount,  "
					+ "total_of_head_0029,  "
					+ "total_of_head_0045,   "
					+ "bank_name,  "
					+ "bank_address,  "
					+ "actual_file_name,  "
					+ "bank_receipt_number,"
					+ "status,"
					+ "challan_date  "
					+ ""
					+ "", nativeQuery = true)
					public List<Object[]> getChallanDetailsAllPre(
					String eHost,String eDbName,String eSchemaName,String eUser,String ePassword,
					String cCode, String revenueYear, String status, Character activeFlag
		            );
			*/
	
	@Query(value =
	        "SELECT " +
	        "challan_id, " +
	        "id, " +
	        "ccode, " +
	        "bank_receipt_number_0045, " +
	        "bank_receipt_path, " +
	        "bank_receipt_path_0045, " +
	        "challan_no, " +
	        "challan_no_0045, " +
	        "mode_of_payment, " +
	        "total_amount, " +
	        "total_of_head_0029, " +
	        "total_of_head_0045, " +
	        "bank_name, " +
	        "bank_address, " +
	        "actual_file_name, " +
	        "bank_receipt_number, " +
	        "SUM(addl_land_revenue) AS addl_land_revenue, " +
	        "SUM(addl_educational_cess) AS addl_educational_cess, " +
	        "SUM(educational_cess) AS educational_cess, " +
	        "SUM(employee_guarantee_scheme) AS employee_guarantee_scheme, " +
	        "SUM(gp_akrushik) AS gp_akrushik, " +
	        "SUM(gp_bindumala) AS gp_bindumala, " +
	        "SUM(gp_dumala) AS gp_dumala, " +
	        "SUM(gp_sankirn) AS gp_sankirn, " +
	        "SUM(gp_vajasut) AS gp_vajasut, " +
	        "SUM(jm_akrushik) AS jm_akrushik, " +
	        "SUM(jm_bindumala) AS jm_bindumala, " +
	        "SUM(jm_dumala) AS jm_dumala, " +
	        "SUM(jm_sankirn) AS jm_sankirn, " +
	        "SUM(jm_vajasut) AS jm_vajasut, " +
	        "SUM(net_amount) AS net_amount, " +
	        "SUM(net_pending) AS net_pending, " +
	        "SUM(net_received) AS net_received, " +
	        "SUM(notice_amount) AS notice_amount, " +
	        "SUM(pre_year_notice_fee) AS pre_year_notice_fee, " +
	        "SUM(pre_year_pending_addl_educational_cess) AS pre_year_pending_addl_educational_cess, " +
	        "SUM(pre_year_pending_addl_land_revenue) AS pre_year_pending_addl_land_revenue, " +
	        "SUM(pre_year_pending_educational_cess) AS pre_year_pending_educational_cess, " +
	        "SUM(pre_year_pending_employee_guarantee_scheme) AS pre_year_pending_employee_guarantee_scheme, " +
	        "SUM(pre_year_pending_gp) AS pre_year_pending_gp, " +
	        "SUM(pre_year_pending_jm) AS pre_year_pending_jm, " +
	        "SUM(pre_year_pending_na_cess) AS pre_year_pending_na_cess, " +
	        "SUM(pre_year_pending_zp) AS pre_year_pending_zp, " +
	        "SUM(pre_year_sankirn_jm_with) AS pre_year_sankirn_jm_with, " +
	        "SUM(pre_year_sankirn_jm_without) AS pre_year_sankirn_jm_without, " +
	        "SUM(zp_akrushik) AS zp_akrushik, " +
	        "SUM(zp_bindumala) AS zp_bindumala, " +
	        "SUM(zp_dumala) AS zp_dumala, " +
	        "SUM(zp_sankirn) AS zp_sankirn, " +
	        "SUM(zp_vajasut) AS zp_vajasut, " +
	        "STRING_AGG(receipt_no, ',  ') AS receiptno, " +
	        "status, " +
	        "challan_date, " +
	        "COUNT(receipt_no) AS countofReceipts " +

	        "FROM public.dblink(" +
	        "'dbname=' || ?2 || " +
	        "' host=' || ?1 || " +
	        "' user=' || ?4 || " +
	        "' password=' || ?5, " +

	        "'SELECT " +
	        "lrd.challan_id, " +
	        "cd.id, " +
	        "cd.ccode, " +
	        "cd.bank_receipt_number_0045, " +
	        "cd.bank_receipt_path, " +
	        "cd.bank_receipt_path_0045, " +
	        "cd.challan_no, " +
	        "cd.challan_no_0045, " +
	        "cd.mode_of_payment, " +
	        "cd.total_amount, " +
	        "cd.total_of_head_0029, " +
	        "cd.total_of_head_0045, " +
	        "cd.bank_name, " +
	        "cd.bank_address, " +
	        "cd.actual_file_name, " +
	        "cd.bank_receipt_number, " +
	        "lrd.addl_land_revenue, " +
	        "lrd.addl_educational_cess, " +
	        "lrd.educational_cess, " +
	        "lrd.employee_guarantee_scheme, " +
	        "lrd.gp_akrushik, " +
	        "lrd.gp_bindumala, " +
	        "lrd.gp_dumala, " +
	        "lrd.gp_sankirn, " +
	        "lrd.gp_vajasut, " +
	        "lrd.jm_akrushik, " +
	        "lrd.jm_bindumala, " +
	        "lrd.jm_dumala, " +
	        "lrd.jm_sankirn, " +
	        "lrd.jm_vajasut, " +
	        "lrd.net_amount, " +
	        "lrd.net_pending, " +
	        "lrd.net_received, " +
	        "lrd.notice_amount, " +
	        "lrd.pre_year_notice_fee, " +
	        "lrd.pre_year_pending_addl_educational_cess, " +
	        "lrd.pre_year_pending_addl_land_revenue, " +
	        "lrd.pre_year_pending_educational_cess, " +
	        "lrd.pre_year_pending_employee_guarantee_scheme, " +
	        "lrd.pre_year_pending_gp, " +
	        "lrd.pre_year_pending_jm, " +
	        "lrd.pre_year_pending_na_cess, " +
	        "lrd.pre_year_pending_zp, " +
	        "lrd.pre_year_sankirn_jm_with, " +
	        "lrd.pre_year_sankirn_jm_without, " +
	        "lrd.zp_akrushik, " +
	        "lrd.zp_bindumala, " +
	        "lrd.zp_dumala, " +
	        "lrd.zp_sankirn, " +
	        "lrd.zp_vajasut, " +
	        "lrd.receipt_no, " +
	        "lrd.revenue_year, " +
	        "cd.status, " +
	        "cd.challan_date, " +
	        "cd.active_flag " +
	        "FROM ' || ?3 || '.land_revenue_demand_2023 lrd " +
	        "JOIN ' || ?3 || '.challan_dtl cd " +
	        "ON lrd.challan_id = cd.id " +
	        "WHERE TRIM(cd.ccode) = ''' || ?6 || ''' " +
	        "AND lrd.revenue_year = ''' || ?7 || ''' " +
	        "AND cd.status = ''' || ?8 || ''' " +
	        "AND cd.active_flag = ''' || ?9 || '''" +
	        "') AS x(" +

	        "challan_id text, " +
	        "id text, " +
	        "ccode text, " +
	        "bank_receipt_number_0045 text, " +
	        "bank_receipt_path text, " +
	        "bank_receipt_path_0045 text, " +
	        "challan_no text, " +
	        "challan_no_0045 text, " +
	        "mode_of_payment text, " +
	        "total_amount text, " +
	        "total_of_head_0029 text, " +
	        "total_of_head_0045 text, " +
	        "bank_name text, " +
	        "bank_address text, " +
	        "actual_file_name text, " +
	        "bank_receipt_number text, " +
	        "addl_land_revenue numeric, " +
	        "addl_educational_cess numeric, " +
	        "educational_cess numeric, " +
	        "employee_guarantee_scheme numeric, " +
	        "gp_akrushik numeric, " +
	        "gp_bindumala numeric, " +
	        "gp_dumala numeric, " +
	        "gp_sankirn numeric, " +
	        "gp_vajasut numeric, " +
	        "jm_akrushik numeric, " +
	        "jm_bindumala numeric, " +
	        "jm_dumala numeric, " +
	        "jm_sankirn numeric, " +
	        "jm_vajasut numeric, " +
	        "net_amount numeric, " +
	        "net_pending numeric, " +
	        "net_received numeric, " +
	        "notice_amount numeric, " +
	        "pre_year_notice_fee numeric, " +
	        "pre_year_pending_addl_educational_cess numeric, " +
	        "pre_year_pending_addl_land_revenue numeric, " +
	        "pre_year_pending_educational_cess numeric, " +
	        "pre_year_pending_employee_guarantee_scheme numeric, " +
	        "pre_year_pending_gp numeric, " +
	        "pre_year_pending_jm numeric, " +
	        "pre_year_pending_na_cess numeric, " +
	        "pre_year_pending_zp numeric, " +
	        "pre_year_sankirn_jm_with numeric, " +
	        "pre_year_sankirn_jm_without numeric, " +
	        "zp_akrushik numeric, " +
	        "zp_bindumala numeric, " +
	        "zp_dumala numeric, " +
	        "zp_sankirn numeric, " +
	        "zp_vajasut numeric, " +
	        "receipt_no text, " +
	        "revenue_year text, " +
	        "status text, " +
	        "challan_date timestamp without time zone, " +
	        "active_flag character(1)" +

	        ") " +

	        "GROUP BY " +
	        "challan_id, " +
	        "id, " +
	        "ccode, " +
	        "bank_receipt_number_0045, " +
	        "bank_receipt_path, " +
	        "bank_receipt_path_0045, " +
	        "challan_no, " +
	        "challan_no_0045, " +
	        "mode_of_payment, " +
	        "total_amount, " +
	        "total_of_head_0029, " +
	        "total_of_head_0045, " +
	        "bank_name, " +
	        "bank_address, " +
	        "actual_file_name, " +
	        "bank_receipt_number, " +
	        "status, " +
	        "challan_date",

	        nativeQuery = true)
	List<Object[]> getChallanDetailsAllPre(
	        String eHost,
	        String eDbName,
	        String eSchemaName,
	        String eUser,
	        String ePassword,
	        String cCode,
	        String revenueYear,
	        String status,
	        Character activeFlag
	);
			
	@Query(value="select  distinct ccode from   "
			+ "(select ccode from public.dblink('dbname='|| ?2 ||' host='|| ?1 ||' user='|| ?4 ||' password='|| ?5 ,  "
			+ "'select ccode,active_flag,revenue_year "
			+ "from '|| ?3 ||'.challan_dtl')  "
			+ "AS(ccode text,active_flag character(1),revenue_year text ) "
			+ "where active_flag='Y' and revenue_year='2024-25' group by ccode "
			+ ")a "
             ,nativeQuery=true)
	List<Object[]> findBycCodeAndRevenueYearAndActiveFlag(String eHost,String eDbName,String eSchemaName,String eUser,String ePassword);
//	List<ChallanDtl> findByChallanNoIsNotNull();String cCode, String revenueYear, Character activeFlag

//	List<ChallanDtl> getcCode();
}

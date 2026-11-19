package com.probity.echawdi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.probity.echawdi.entity.LandRevenueDemand2023;

@Repository
public interface LandRevenueDemandRepo2023 extends JpaRepository<LandRevenueDemand2023, Long> {

	public List<LandRevenueDemand2023> findReceiptDetailsBycCodeAndActiveFlagAndRevenueYearAndReceiptNoIsNotNull(
			String cCode, char c, String revenueYear);
	
// Commented on 5 sept 2025 , old MakhtaKhata reciept query	
//	public List<LandRevenueDemand2023> findReceiptDetailsBycCodeAndMakhtaKhataNoAndRevenueYearAndActiveFlagAndReceiptNoIsNotNull
//	(String cCode, String makhtaKhataNo, String revenueYear, Character activeFlag);

	
	//New MakhtaKhataQuery for previous year reciept [5 sep 2025]
	@Query(value = 
		    "SELECT id, " +
		    " active_flag, " +
		    " ccode, " +
		    " create_dt_tm, " +
		    " created_user_id, " +
		    " district_code, " +
		    " revenue_year, " +
		    " taluka_code, " +
		    " update_userid, " +
		    " update_dt_tm, " +
		    " version, " +
		    " addl_educational_cess, " +
		    " addl_land_revenue, " +
		    " agau_vasuli_remarks, " +
		    " area, " +
		    " assessment, " +
		    " challan_id, " +
		    " cheque_amount, " +
		    " cheque_branch, " +
		    " cheque_date, " +
		    " cheque_name, " +
		    " cheque_number, " +
		    " date_of_notice, " +
		    " dd_amount, " +
		    " dd_branch, " +
		    " dd_date, " +
		    " dd_name, " +
		    " dd_number, " +
		    " educational_cess, " +
		    " employee_guarantee_scheme, " +
		    " gp_akrushik, " +
		    " gp_bindumala, " +
		    " gp_dumala, " +
		    " gp_sankirn, " +
		    " gp_vajasut, " +
		    " is_money_deposited_bank, " +
		    " jm_akrushik, " +
		    " jm_bindumala, " +
		    " jm_dumala, " +
		    " jm_sankirn, " +
		    " jm_vajasut, " +
		    " khata_no, " +
		    " khata_owner_name, " +
		    " land_revenue_form17_id, " +
		    " makhtakhata_no, " +
		    " miscellaneous_amount, " +
		    " mode_of_payment, " +
		    " net_amount, " +
		    " net_pending, " +
		    " net_received, " +
		    " notice_amount, " +
		    " pre_year_notice_fee, " +
		    " pre_year_pending_addl_educational_cess, " +
		    " pre_year_pending_addl_land_revenue, " +
		    " pre_year_pending_educational_cess, " +
		    " pre_year_pending_employee_guarantee_scheme, " +
		    " pre_year_pending_gp, " +
		    " pre_year_pending_jm, " +
		    " pre_year_pending_na_cess, " +
		    " pre_year_pending_zp, " +
		    " pre_year_sankirn_jm_with, " +
		    " pre_year_sankirn_jm_without, " +
		    " receipt_date, " +
		    " receipt_no, " +
		    " survey_hissa, " +
		    " area_unit, " +
		    " zp_akrushik, " +
		    " zp_bindumala, " +
		    " zp_dumala, " +
		    " zp_sankirn, " +
		    " zp_vajasut " +
		    "FROM public.dblink(" +
		    " 'dbname=' || ?2 || ' host=' || ?1 || ' user=' || ?4 || ' port=' || ?7 || ' password=' || ?5, " +
		    " 'SELECT " +
		    " lrd.id, " +
		    " lrd.active_flag, " +
		    " lrd.ccode, " +
		    " lrd.create_dt_tm, " +
		    " lrd.created_user_id, " +
		    " lrd.district_code, " +
		    " lrd.revenue_year, " +
		    " lrd.taluka_code, " +
		    " lrd.update_userid, " +
		    " lrd.update_dt_tm, " +
		    " lrd.version, " +
		    " lrd.addl_educational_cess, " +
		    " lrd.addl_land_revenue, " +
		    " lrd.agau_vasuli_remarks, " +
		    " lrd.area, " +
		    " lrd.assessment, " +
		    " lrd.challan_id, " +
		    " lrd.cheque_amount, " +
		    " lrd.cheque_branch, " +
		    " lrd.cheque_date, " +
		    " lrd.cheque_name, " +
		    " lrd.cheque_number, " +
		    " lrd.date_of_notice, " +
		    " lrd.dd_amount, " +
		    " lrd.dd_branch, " +
		    " lrd.dd_date, " +
		    " lrd.dd_name, " +
		    " lrd.dd_number, " +
		    " lrd.educational_cess, " +
		    " lrd.employee_guarantee_scheme, " +
		    " lrd.gp_akrushik, " +
		    " lrd.gp_bindumala, " +
		    " lrd.gp_dumala, " +
		    " lrd.gp_sankirn, " +
		    " lrd.gp_vajasut, " +
		    " lrd.is_money_deposited_bank, " +
		    " lrd.jm_akrushik, " +
		    " lrd.jm_bindumala, " +
		    " lrd.jm_dumala, " +
		    " lrd.jm_sankirn, " +
		    " lrd.jm_vajasut, " +
		    " lrd.khata_no, " +
		    " lrd.khata_owner_name, " +
		    " lrd.land_revenue_form17_id, " +
		    " lrd.makhtakhata_no, " +
		    " lrd.miscellaneous_amount, " +
		    " lrd.mode_of_payment, " +
		    " lrd.net_amount, " +
		    " lrd.net_pending, " +
		    " lrd.net_received, " +
		    " lrd.notice_amount, " +
		    " lrd.pre_year_notice_fee, " +
		    " lrd.pre_year_pending_addl_educational_cess, " +
		    " lrd.pre_year_pending_addl_land_revenue, " +
		    " lrd.pre_year_pending_educational_cess, " +
		    " lrd.pre_year_pending_employee_guarantee_scheme, " +
		    " lrd.pre_year_pending_gp, " +
		    " lrd.pre_year_pending_jm, " +
		    " lrd.pre_year_pending_na_cess, " +
		    " lrd.pre_year_pending_zp, " +
		    " lrd.pre_year_sankirn_jm_with, " +
		    " lrd.pre_year_sankirn_jm_without, " +
		    " lrd.receipt_date, " +
		    " lrd.receipt_no, " +
		    " lrd.survey_hissa, " +
		    " lrd.area_unit, " +
		    " lrd.zp_akrushik, " +
		    " lrd.zp_bindumala, " +
		    " lrd.zp_dumala, " +
		    " lrd.zp_sankirn, " +
		    " lrd.zp_vajasut " +
		    " FROM ' || ?10 || '.land_revenue_demand_' || ?11 || ' lrd') " +
		    " AS (" +
		    " id bigint, " +
		    " active_flag character(1), " +
		    " ccode text, " +
		    " create_dt_tm timestamp without time zone, " +
		    " created_user_id bigint, " +
		    " district_code integer, " +
		    " revenue_year text, " +
		    " taluka_code integer, " +
		    " update_userid bigint, " +
		    " update_dt_tm timestamp without time zone, " +
		    " version numeric, " +
		    " addl_educational_cess double precision, " +
		    " addl_land_revenue double precision, " +
		    " agau_vasuli_remarks text, " +
		    " area text, " +
		    " assessment text, " +
		    " challan_id bigint, " +
		    " cheque_amount text, " +
		    " cheque_branch text, " +
		    " cheque_date date, " +
		    " cheque_name text, " +
		    " cheque_number text, " +
		    " date_of_notice text, " +
		    " dd_amount text, " +
		    " dd_branch text, " +
		    " dd_date date, " +
		    " dd_name text, " +
		    " dd_number text, " +
		    " educational_cess text, " +
		    " employee_guarantee_scheme text, " +
		    " gp_akrushik numeric, " +
		    " gp_bindumala text, " +
		    " gp_dumala text, " +
		    " gp_sankirn text, " +
		    " gp_vajasut text, " +
		    " is_money_deposited_bank character(1), " +
		    " jm_akrushik text, " +
		    " jm_bindumala text, " +
		    " jm_dumala text, " +
		    " jm_sankirn text, " +
		    " jm_vajasut text, " +
		    " khata_no text, " +
		    " khata_owner_name text, " +
		    " land_revenue_form17_id bigint, " +
		    " makhtakhata_no text, " +
		    " miscellaneous_amount double precision, " +
		    " mode_of_payment text, " +
		    " net_amount text, " +
		    " net_pending text, " +
		    " net_received text, " +
		    " notice_amount text, " +
		    " pre_year_notice_fee text, " +
		    " pre_year_pending_addl_educational_cess text, " +
		    " pre_year_pending_addl_land_revenue text, " +
		    " pre_year_pending_educational_cess text, " +
		    " pre_year_pending_employee_guarantee_scheme text, " +
		    " pre_year_pending_gp text, " +
		    " pre_year_pending_jm text, " +
		    " pre_year_pending_na_cess text, " +
		    " pre_year_pending_zp text, " +
		    " pre_year_sankirn_jm_with text, " +
		    " pre_year_sankirn_jm_without text, " +
		    " receipt_date date, " +
		    " receipt_no text, " +
		    " survey_hissa text, " +
		    " area_unit text, " +
		    " zp_akrushik text, " +
		    " zp_bindumala text, " +
		    " zp_dumala text, " +
		    " zp_sankirn text, " +
		    " zp_vajasut text) " +
		    "WHERE ccode = ?3 AND makhtakhata_no = ?6 AND revenue_year = ?8 AND active_flag = ?9 AND receipt_no IS NOT NULL",
		    nativeQuery = true)
		public List<LandRevenueDemand2023> findReceiptDetailsBycCodeAndMakhtaKhataNoAndRevenueYearAndActiveFlagAndReceiptNoIsNotNull(
		    String eHost,           
		    String eDbName,         
		    String cCode,           
		    String eUser,           
		    String ePassword,      
		    String makhtaKhataNo,   
		    String port,           
		    String revenueYear,     
		    Character activeFlag,
		    String echSchemaName,
		    String preYear
		);

	
	public List<LandRevenueDemand2023> findReceiptDetailsBycCodeAndIsMoneyepositedBankAndRevenueYearAndActiveFlag
	   (String cCode, Character isMoneyepositedBank, String revenueYear,Character activeFlag);

	public List<LandRevenueDemand2023> findReceiptDetailsBycCodeAndIsMoneyepositedBankAndRevenueYearAndModeOfPaymentAndActiveFlagAndChallanIdIsNull
									   (String cCode, Character isMoneyepositedBank, String revenueYear,String modeOfPayment,Character activeFlag);

	
	@Query(value="select a1.khata_no,  "
			+ "khata_owner_name,  "
			+ "a1.ccode,  "
			+ "a1.assessment,  "
			+ "a1.area,  "
			+ "suvery_hissa,  "
			+ "area_unit,  "
			+ "jm_bindumala,  "
			+ "zp_bindumala,  "
			+ "gp_bindumala,  "
			+ "jm_dumala,  "
			+ "zp_dumala,  "
			+ "gp_dumala,  "
			+ "jm_akrushik,  "
			+ "zp_akrushik,  "
			+ "gp_akrushik,  "
			+ "jm_sankirn,  "
			+ "zp_sankirn,  "
			+ "gp_sankirn,  "
			+ "jm_vajasut,  "
			+ "zp_vajasut,  "
			+ "gp_vajasut,  "
			+ "addl_land_revenue,  "
			+ "educational_cess,  "
			+ "addl_educational_cess,  "
			+ "employee_guarantee_scheme,  "
			+ "pre_year_pending_jm,  "
			+ "pre_year_pending_gp, "
			+ "pre_year_pending_zp, "
			+ "pre_year_pending_addl_land_revenue,  "
			+ "pre_year_pending_educational_cess,  "
			+ "pre_year_pending_addl_educational_cess,  "
			+ "pre_year_pending_employee_guarantee_scheme,  "
			+ "net_amount,  "
			+ "net_received,  "
			+ "net_pending,  "
			+ "date_of_notice,  "
			+ "notice_amount,  "
			+ "survey_hissa,  "
			+ "pre_year_notice_fee,  "
			+ "pre_year_pending_na_cess,  "
			+ "pre_year_sankirn_jm_with,  "
			+ "pre_year_sankirn_jm_without,  "
			+ "makhtakhata_no,  "
			+ "name_of_tenant,"
			+ "a1.id,"
			+ "mode_of_payment,"
			+ "receipt_no,"
			+ "receipt_date,"
			+ "dd_name ,"
			+ "cheque_amount,"
			+ "dd_amount,"
			+ "dd_branch,"
			+ "cheque_branch,"
			+ "dd_date,"
			+ "cheque_date,"
			+ "cheque_number,"
			+ "dd_number,"
			+ "cheque_name  "
			+ "from    "
			+ "(    "
			+ "(select * from public.dblink('dbname='|| ?2 ||' host='|| ?1 ||' user='|| ?4 ||' password='|| ?5 ,     "
			+ "'select khata_no,  "
			+ "khata_owner_name,  "
			+ "assessment,  "
			+ "area,  "
			+ "active_flag,  "
			+ "lrd.ccode,  "
			+ "revenue_year,  "
			+ "suvery_hissa,  "
			+ "area_unit,  "
			+ "jm_bindumala,  "
			+ "zp_bindumala,  "
			+ "gp_bindumala,  "
			+ "jm_dumala,  "
			+ "zp_dumala,  "
			+ "gp_dumala,  "
			+ "jm_akrushik,  "
			+ "zp_akrushik,  "
			+ "gp_akrushik,  "
			+ "jm_sankirn,  "
			+ "zp_sankirn,  "
			+ "gp_sankirn,  "
			+ "jm_vajasut,  "
			+ "zp_vajasut,  "
			+ "gp_vajasut,  "
			+ "addl_land_revenue,  "
			+ "educational_cess,  "
			+ "addl_educational_cess,  "
			+ "employee_guarantee_scheme,  "
			+ "pre_year_pending_jm,  "
			+ "pre_year_pending_gp, "
			+ "pre_year_pending_zp, "
			+ "pre_year_pending_addl_land_revenue,  "
			+ "pre_year_pending_educational_cess,  "
			+ "pre_year_pending_addl_educational_cess,  "
			+ "pre_year_pending_employee_guarantee_scheme,  "
			+ "net_amount,  "
			+ "net_received,  "
			+ "net_pending,  "
			+ "date_of_notice,  "
			+ "notice_amount,  "
			+ "survey_hissa,  "
			+ "pre_year_notice_fee,  "
			+ "pre_year_pending_na_cess,  "
			+ "pre_year_sankirn_jm_with,  "
			+ "pre_year_sankirn_jm_without,  "
			+ "makhtakhata_no,"
			+ "id,"
			+ "mode_of_payment,"
			+ "receipt_no,"
			+ "receipt_date,"
			+ "dd_name ,"
			+ "cheque_amount,"
			+ "dd_amount ,"
			+ "dd_branch,"
			+ "cheque_branch,"
			+ "dd_date ,"
			+ "cheque_date,"
			+ "cheque_number,"
			+ "dd_number  "
			+ "from '|| ?3 ||'.land_revenue_demand_'||?9||' lrd')     "
			+ "AS  "
			+ "(khata_no text,  "
			+ " khata_owner_name text,  "
			+ " assessment text,  "
			+ " area text,  "
			+ " active_flag character(1),  "
			+ " ccode text,  "
			+ " revenue_year text,  "
			+ " suvery_hissa text,  "
			+ "area_unit text,  "
			+ "jm_bindumala text,  "
			+ "zp_bindumala text,  "
			+ "gp_bindumala text,  "
			+ "jm_dumala text,  "
			+ "zp_dumala text,  "
			+ "gp_dumala text,  "
			+ "jm_akrushik text,  "
			+ "zp_akrushik text,  "
			+ "gp_akrushik text,  "
			+ "jm_sankirn text,  "
			+ "zp_sankirn text,  "
			+ "gp_sankirn text,  "
			+ "jm_vajasut text,  "
			+ "zp_vajasut text,  "
			+ "gp_vajasut text,  "
			+ "addl_land_revenue text,  "
			+ "educational_cess text,  "
			+ "addl_educational_cess text,  "
			+ "employee_guarantee_scheme text,  "
			+ "pre_year_pending_jm text,  "
			+ "pre_year_pending_gp text,"
			+ "pre_year_pending_zp text,  "
			+ "pre_year_pending_addl_land_revenue text,  "
			+ "pre_year_pending_educational_cess text,  "
			+ "pre_year_pending_addl_educational_cess text,   "
			+ "pre_year_pending_employee_guarantee_scheme text,  "
			+ "net_amount text,  "
			+ "net_received text,   "
			+ "net_pending text,  "
			+ "date_of_notice text,  "
			+ "notice_amount text,  "
			+ "survey_hissa text,  "
			+ "pre_year_notice_fee text,  "
			+ "pre_year_pending_na_cess text,  "
			+ "pre_year_sankirn_jm_with text,  "
			+ "pre_year_sankirn_jm_without text,  "
			+ "makhtakhata_no text,"
			+ "id bigint ,"
			+ "mode_of_payment text ,"
			+ "receipt_no text,"
			+ "receipt_date date ,"
			+ "dd_name text,"
			+ "cheque_amount text ,"
			+ "dd_amount text,"
			+ "dd_branch text,"
			+ "cheque_branch text ,"
			+ "dd_date date  ,"
			+ "cheque_date date ,"
			+ "cheque_number text,"
			+ "dd_number text,"
			+ "cheque_name text "
			+ "  )where trim(ccode)=?6 "
			+ " and active_flag='Y' "
			+ "and revenue_year= ?8   "
			+ " and khata_owner_name like '%'||?7||'%'  "
			+ ")as a1 left join  "
			+ "(select * from public.dblink('dbname='|| ?2 ||' host='|| ?1 ||' user='|| ?4 ||' password='|| ?5  ,     "
			+ "'select khata_no,name_of_tenant,area,active_flag,f7a.ccode     "
			+ "from '|| ?3 ||'.echawdi_form7a f7a')     "
			+ "AS(khata_no text,name_of_tenant text,area text,active_flag character(1),ccode text ) where trim(ccode)=?6 and active_flag='Y'  "
			+ " ) as b1  "
			+ " on(a1.ccode=b1.ccode and a1.khata_no=b1.khata_no)  "
			+ ") "
			+ "group by(a1.khata_no, "
			+ "khata_owner_name,a1.ccode,a1.area,a1.assessment,  "
			+ "	suvery_hissa,  "
			+ "	area_unit,  "
			+ "	jm_bindumala,  "
			+ "	zp_bindumala,  "
			+ "	gp_bindumala,  "
			+ "	jm_dumala,  "
			+ "	zp_dumala,  "
			+ "	gp_dumala,  "
			+ "	jm_akrushik,  "
			+ "	zp_akrushik,  "
			+ "	gp_akrushik,  "
			+ "	jm_sankirn,  "
			+ "	zp_sankirn,  "
			+ "	gp_sankirn,  "
			+ "	jm_vajasut,  "
			+ "	zp_vajasut,  "
			+ "	gp_vajasut,  "
			+ "	addl_land_revenue,  "
			+ "	educational_cess,  "
			+ "	addl_educational_cess,  "
			+ "	employee_guarantee_scheme,  "
			+ "	pre_year_pending_jm,  "
			+ "	pre_year_pending_gp, "
			+ "pre_year_pending_zp, "
			+ "	pre_year_pending_addl_land_revenue,  "
			+ "	pre_year_pending_educational_cess,  "
			+ "	pre_year_pending_addl_educational_cess,  "
			+ "	pre_year_pending_employee_guarantee_scheme,  "
			+ "	net_amount,  "
			+ "	net_received,  "
			+ "	net_pending,  "
			+ "	date_of_notice,  "
			+ "	notice_amount,  "
			+ "	survey_hissa,  "
			+ "	pre_year_notice_fee,  "
			+ "	pre_year_pending_na_cess,  "
			+ "	pre_year_sankirn_jm_with,  "
			+ "	pre_year_sankirn_jm_without,  "
			+ "	makhtakhata_no,  "
			+ "	name_of_tenant,"
			+ "a1.id ,"
			+ "mode_of_payment,"
			+ "receipt_no,"
			+ "receipt_date,"
			+ "dd_name ,"
			+ "cheque_amount,"
			+ "dd_amount ,"
			+ "dd_branch,"
			+ "cheque_branch,"
			+ "dd_date ,"
			+ "cheque_date,"
			+ "cheque_number,"
			+ "dd_number,"
			+ "cheque_name  "
			+ ")  "
			+ "",nativeQuery=true)
	public List<Object[]> findByKhataOwnerName(String eHost,String eDbName,String eSchemaName,String eUser,String ePassword,String cCode,
			 @Param ("khataOwnerName") String  khataOwnerName,String revenueYear, String singStringpre); 
	
	@Query(value="select a1.khata_no,  "
			+ "khata_owner_name,  "
			+ "a1.ccode,  "
			+ "a1.assessment,  "
			+ "a1.area,  "
			+ "suvery_hissa,  "
			+ "area_unit,  "
			+ "jm_bindumala,  "
			+ "zp_bindumala,  "
			+ "gp_bindumala,  "
			+ "jm_dumala,  "
			+ "zp_dumala,  "
			+ "gp_dumala,  "
			+ "jm_akrushik,  "
			+ "zp_akrushik,  "
			+ "gp_akrushik,  "
			+ "jm_sankirn,  "
			+ "zp_sankirn,  "
			+ "gp_sankirn,  "
			+ "jm_vajasut,  "
			+ "zp_vajasut,  "
			+ "gp_vajasut,  "
			+ "addl_land_revenue,  "
			+ "educational_cess,  "
			+ "addl_educational_cess,  "
			+ "employee_guarantee_scheme,  "
			+ "pre_year_pending_jm,  "
			+ "pre_year_pending_gp, "
			+ "pre_year_pending_zp, "
			+ "pre_year_pending_addl_land_revenue,  "
			+ "pre_year_pending_educational_cess,  "
			+ "pre_year_pending_addl_educational_cess,  "
			+ "pre_year_pending_employee_guarantee_scheme,  "
			+ "net_amount,  "
			+ "net_received,  "
			+ "net_pending,  "
			+ "date_of_notice,  "
			+ "notice_amount,  "
			+ "survey_hissa,  "
			+ "pre_year_notice_fee,  "
			+ "pre_year_pending_na_cess,  "
			+ "pre_year_sankirn_jm_with,  "
			+ "pre_year_sankirn_jm_without,  "
			+ "makhtakhata_no,  "
			+ "name_of_tenant,"
			+ "a1.id,"
			+ "mode_of_payment,"
			+ "receipt_no,"
			+ "receipt_date,"
			+ "dd_name ,"
			+ "cheque_amount,"
			+ "dd_amount,"
			+ "dd_branch,"
			+ "cheque_branch,"
			+ "dd_date,"
			+ "cheque_date,"
			+ "cheque_number,"
			+ "dd_number,"
			+ "cheque_name  "
			+ "from    "
			+ "(    "
			+ "(select * from public.dblink('dbname='|| ?2 ||' host='|| ?1 ||' user='|| ?4 ||' password='|| ?5 ,     "
			+ "'select khata_no,  "
			+ "khata_owner_name,  "
			+ "assessment,  "
			+ "area,  "
			+ "active_flag,  "
			+ "lrd.ccode,  "
			+ "revenue_year,  "
			+ "suvery_hissa,  "
			+ "area_unit,  "
			+ "jm_bindumala,  "
			+ "zp_bindumala,  "
			+ "gp_bindumala,  "
			+ "jm_dumala,  "
			+ "zp_dumala,  "
			+ "gp_dumala,  "
			+ "jm_akrushik,  "
			+ "zp_akrushik,  "
			+ "gp_akrushik,  "
			+ "jm_sankirn,  "
			+ "zp_sankirn,  "
			+ "gp_sankirn,  "
			+ "jm_vajasut,  "
			+ "zp_vajasut,  "
			+ "gp_vajasut,  "
			+ "addl_land_revenue,  "
			+ "educational_cess,  "
			+ "addl_educational_cess,  "
			+ "employee_guarantee_scheme,  "
			+ "pre_year_pending_jm,  "
			+ "pre_year_pending_gp, "
			+ "pre_year_pending_zp, "
			+ "pre_year_pending_addl_land_revenue,  "
			+ "pre_year_pending_educational_cess,  "
			+ "pre_year_pending_addl_educational_cess,  "
			+ "pre_year_pending_employee_guarantee_scheme,  "
			+ "net_amount,  "
			+ "net_received,  "
			+ "net_pending,  "
			+ "date_of_notice,  "
			+ "notice_amount,  "
			+ "survey_hissa,  "
			+ "pre_year_notice_fee,  "
			+ "pre_year_pending_na_cess,  "
			+ "pre_year_sankirn_jm_with,  "
			+ "pre_year_sankirn_jm_without,  "
			+ "makhtakhata_no,"
			+ "id,"
			+ "mode_of_payment,"
			+ "receipt_no,"
			+ "receipt_date,"
			+ "dd_name ,"
			+ "cheque_amount,"
			+ "dd_amount ,"
			+ "dd_branch,"
			+ "cheque_branch,"
			+ "dd_date ,"
			+ "cheque_date,"
			+ "cheque_number,"
			+ "dd_number,"
			+ "cheque_name  "
			+ "from '|| ?3 ||'.land_revenue_demand_'||?9||' lrd')     "
			+ "AS  "
			+ "(khata_no text,  "
			+ " khata_owner_name text,  "
			+ " assessment text,  "
			+ " area text,  "
			+ " active_flag character(1),  "
			+ " ccode text,  "
			+ " revenue_year text,  "
			+ " suvery_hissa text,  "
			+ "area_unit text,  "
			+ "jm_bindumala text,  "
			+ "zp_bindumala text,  "
			+ "gp_bindumala text,  "
			+ "jm_dumala text,  "
			+ "zp_dumala text,  "
			+ "gp_dumala text,  "
			+ "jm_akrushik text,  "
			+ "zp_akrushik text,  "
			+ "gp_akrushik text,  "
			+ "jm_sankirn text,  "
			+ "zp_sankirn text,  "
			+ "gp_sankirn text,  "
			+ "jm_vajasut text,  "
			+ "zp_vajasut text,  "
			+ "gp_vajasut text,  "
			+ "addl_land_revenue text,  "
			+ "educational_cess text,  "
			+ "addl_educational_cess text,  "
			+ "employee_guarantee_scheme text,  "
			+ "pre_year_pending_jm text,  "
			+ "pre_year_pending_gp text,"
			+ "pre_year_pending_zp text,  "
			+ "pre_year_pending_addl_land_revenue text,  "
			+ "pre_year_pending_educational_cess text,  "
			+ "pre_year_pending_addl_educational_cess text,   "
			+ "pre_year_pending_employee_guarantee_scheme text,  "
			+ "net_amount text,  "
			+ "net_received text,   "
			+ "net_pending text,  "
			+ "date_of_notice text,  "
			+ "notice_amount text,  "
			+ "survey_hissa text,  "
			+ "pre_year_notice_fee text,  "
			+ "pre_year_pending_na_cess text,  "
			+ "pre_year_sankirn_jm_with text,  "
			+ "pre_year_sankirn_jm_without text,  "
			+ "makhtakhata_no text,"
			+ "id bigint ,"
			+ "mode_of_payment text ,"
			+ "receipt_no text,"
			+ "receipt_date date ,"
			+ "dd_name text,"
			+ "cheque_amount text ,"
			+ "dd_amount text,"
			+ "dd_branch text,"
			+ "cheque_branch text ,"
			+ "dd_date date  ,"
			+ "cheque_date date ,"
			+ "cheque_number text,"
			+ "dd_number text,"
			+ "cheque_name text  "
			+ "  )where trim(ccode)=?6 "
			+ " and active_flag='Y' "
			+ "and revenue_year= ?8   "
			+ " and khata_no=?7  "
			+ ")as a1 left join  "
			+ "(select * from public.dblink('dbname='|| ?2 ||' host='|| ?1 ||' user='|| ?4 ||' password='|| ?5  ,     "
			+ "'select khata_no,name_of_tenant,area,active_flag,f7a.ccode     "
			+ "from '|| ?3 ||'.echawdi_form7a f7a')     "
			+ "AS(khata_no text,name_of_tenant text,area text,active_flag character(1),ccode text ) where ccode=?6 and active_flag='Y'  "
			+ " ) as b1  "
			+ " on(a1.ccode=b1.ccode and a1.khata_no=b1.khata_no)  "
			+ ") "
			+ "group by(a1.khata_no, "
			+ "khata_owner_name,a1.ccode,a1.area,a1.assessment,  "
			+ "	suvery_hissa,  "
			+ "	area_unit,  "
			+ "	jm_bindumala,  "
			+ "	zp_bindumala,  "
			+ "	gp_bindumala,  "
			+ "	jm_dumala,  "
			+ "	zp_dumala,  "
			+ "	gp_dumala,  "
			+ "	jm_akrushik,  "
			+ "	zp_akrushik,  "
			+ "	gp_akrushik,  "
			+ "	jm_sankirn,  "
			+ "	zp_sankirn,  "
			+ "	gp_sankirn,  "
			+ "	jm_vajasut,  "
			+ "	zp_vajasut,  "
			+ "	gp_vajasut,  "
			+ "	addl_land_revenue,  "
			+ "	educational_cess,  "
			+ "	addl_educational_cess,  "
			+ "	employee_guarantee_scheme,  "
			+ "	pre_year_pending_jm,  "
			+ "	pre_year_pending_gp, "
			+ "pre_year_pending_zp, "
			+ "	pre_year_pending_addl_land_revenue,  "
			+ "	pre_year_pending_educational_cess,  "
			+ "	pre_year_pending_addl_educational_cess,  "
			+ "	pre_year_pending_employee_guarantee_scheme,  "
			+ "	net_amount,  "
			+ "	net_received,  "
			+ "	net_pending,  "
			+ "	date_of_notice,  "
			+ "	notice_amount,  "
			+ "	survey_hissa,  "
			+ "	pre_year_notice_fee,  "
			+ "	pre_year_pending_na_cess,  "
			+ "	pre_year_sankirn_jm_with,  "
			+ "	pre_year_sankirn_jm_without,  "
			+ "	makhtakhata_no,  "
			+ "	name_of_tenant,"
			+ "a1.id ,"
			+ "mode_of_payment,"
			+ "receipt_no,"
			+ "receipt_date,"
			+ "dd_name ,"
			+ "cheque_amount,"
			+ "dd_amount ,"
			+ "dd_branch,"
			+ "cheque_branch,"
			+ "dd_date ,"
			+ "cheque_date,"
			+ "cheque_number,"
			+ "dd_number,"
			+ "cheque_name  "
			+ ")  "
			+ "",nativeQuery=true)
	public  List<Object[]> findByKhataNo(String eHost,String eDbName,String eSchemaName,String eUser,String ePassword,
			String cCode, String khataNo, String revenueYear, String singStringpre);
	
	
/*	@Query(value = 
		    "SELECT * FROM ( " +
		    "  SELECT * FROM public.dblink( " +
		    "    'dbname='|| ?2 ||' host='|| ?1 ||' user='|| ?4 ||' port=' || ?7 ||' password='|| ?5, " +
		    "    'SELECT " +
		    "      id, active_flag, ccode, create_dt_tm, created_user_id, district_code, revenue_year, taluka_code, " +
		    "      update_userid, update_dt_tm, version, addl_educational_cess, addl_land_revenue, agau_vasuli_remarks, " +
		    "      area, assessment, challan_id, cheque_amount, cheque_branch, cheque_date, cheque_name, cheque_number, " +
		    "      date_of_notice, dd_amount, dd_branch, dd_date, dd_name, dd_number, educational_cess, employee_guarantee_scheme, " +
		    "      gp_akrushik, gp_bindumala, gp_dumala, gp_sankirn, gp_vajasut, is_money_deposited_bank, " +
		    "      jm_akrushik, jm_bindumala, jm_dumala, jm_sankirn, jm_vajasut, khata_no, khata_owner_name, " +
		    "      land_revenue_form17_id, makhtakhata_no, miscellaneous_amount, mode_of_payment, net_amount, net_pending, net_received, " +
		    "      notice_amount, pre_year_notice_fee, pre_year_pending_addl_educational_cess, pre_year_pending_addl_land_revenue, " +
		    "      pre_year_pending_educational_cess, pre_year_pending_employee_guarantee_scheme, pre_year_pending_gp, pre_year_pending_jm, " +
		    "      pre_year_pending_na_cess, pre_year_pending_zp, pre_year_sankirn_jm_with, pre_year_sankirn_jm_without, " +
		    "      receipt_date, receipt_no, survey_hissa, area_unit, zp_akrushik, zp_bindumala, zp_dumala, zp_sankirn, zp_vajasut, " +
		    "      challan_no, challan_date " +
		    "    FROM '|| ?3 ||'.land_revenue_demand_'|| ?9 ||' lrd " +
		    "    WHERE lrd.active_flag=''Y'' AND lrd.ccode='''|| ?6 ||''' AND lrd.revenue_year='''|| ?8 ||''' AND lrd.receipt_no IS NOT NULL' " +
		    "  ) AS data ( " +
		    "    id bigint, active_flag character(1), ccode text, create_dt_tm timestamp without time zone, " +
		    "    created_user_id bigint, district_code integer, revenue_year text, taluka_code integer, update_userid bigint, " +
		    "    update_dt_tm timestamp without time zone, version numeric, addl_educational_cess text, addl_land_revenue text, " +
		    "    agau_vasuli_remarks text, area text, assessment text, challan_id bigint, cheque_amount text, cheque_branch text, " +
		    "    cheque_date date, cheque_name text, cheque_number text, date_of_notice text, dd_amount text, dd_branch text, dd_date date, " +
		    "    dd_name text, dd_number text, educational_cess text, employee_guarantee_scheme text, gp_akrushik text, gp_bindumala text, " +
		    "    gp_dumala text, gp_sankirn text, gp_vajasut text, is_money_deposited_bank character(1), jm_akrushik text, jm_bindumala text, " +
		    "    jm_dumala text, jm_sankirn text, jm_vajasut text, khata_no text, khata_owner_name text, land_revenue_form17_id bigint, " +
		    "    makhtakhata_no text, miscellaneous_amount text, mode_of_payment text, net_amount text, net_pending text, net_received text, " +
		    "    notice_amount text, pre_year_notice_fee text, pre_year_pending_addl_educational_cess text, pre_year_pending_addl_land_revenue text, " +
		    "    pre_year_pending_educational_cess text, pre_year_pending_employee_guarantee_scheme text, pre_year_pending_gp text, pre_year_pending_jm text, " +
		    "    pre_year_pending_na_cess text, pre_year_pending_zp text, pre_year_sankirn_jm_with text, pre_year_sankirn_jm_without text, " +
		    "    receipt_date date, receipt_no text, survey_hissa text, area_unit text, zp_akrushik text, zp_bindumala text, zp_dumala text, " +
		    "    zp_sankirn text, zp_vajasut text, challan_no text, challan_date timestamp without time zone " +
		    "  ) " +
		    ")",
		    nativeQuery = true
		)
		List<Object[]> findPreviousYearData(
		    String host,
		    String dbName,
		    String schema,
		    String user,
		    String password,
		    String cCode,
		    String port,
		    String revenueYear,   
		    String revSingleStr   
		);*/
	
	
/* Query with errors
	@Query(value =
	        "SELECT * FROM ( " +
	        "  SELECT * FROM public.dblink( " +
	        "    'dbname='|| ?2 ||' host='|| ?1 ||' user='|| ?4 ||' port='|| ?7 ||' password='|| ?5, " +
	        "    'SELECT " +
	        "      id, active_flag, ccode, create_dt_tm, created_user_id, district_code, revenue_year, taluka_code, " +
	        "      update_userid, update_dt_tm, version, addl_educational_cess, addl_land_revenue, agau_vasuli_remarks, " +
	        "      area, assessment, challan_id, cheque_amount, cheque_branch, cheque_date, cheque_name, cheque_number, " +
	        "      date_of_notice, dd_amount, dd_branch, dd_date, dd_name, dd_number, educational_cess, employee_guarantee_scheme, " +
	        "      gp_akrushik, gp_bindumala, gp_dumala, gp_sankirn, gp_vajasut, is_money_deposited_bank, " +
	        "      jm_akrushik, jm_bindumala, jm_dumala, jm_sankirn, jm_vajasut, khata_no, khata_owner_name, " +
	        "      land_revenue_form17_id, makhtakhata_no, miscellaneous_amount, mode_of_payment, net_amount, net_pending, net_received, " +
	        "      notice_amount, pre_year_notice_fee, pre_year_pending_addl_educational_cess, pre_year_pending_addl_land_revenue, " +
	        "      pre_year_pending_educational_cess, pre_year_pending_employee_guarantee_scheme, pre_year_pending_gp, pre_year_pending_jm, " +
	        "      pre_year_pending_na_cess, pre_year_pending_zp, pre_year_sankirn_jm_with, pre_year_sankirn_jm_without, " +
	        "      receipt_date, receipt_no, survey_hissa, area_unit, zp_akrushik, zp_bindumala, zp_dumala, zp_sankirn, zp_vajasut, " +
	        "      challan_no, challan_date " +
	        "    FROM '|| ?3 ||'.land_revenue_demand_'|| ?9 ||' lrd " +
	        "    WHERE lrd.active_flag=''Y'' " +
	        "    AND lrd.ccode='''|| ?6 ||''' " +
	        "    AND lrd.revenue_year='''|| ?8 ||''' " +
	        "    AND lrd.receipt_no IS NOT NULL' " +
	        "  ) AS data ( " +
	        "    id bigint, active_flag character(1), ccode text, create_dt_tm timestamp without time zone, " +
	        "    created_user_id bigint, district_code integer, revenue_year text, taluka_code integer, update_userid bigint, " +
	        "    update_dt_tm timestamp without time zone, version numeric, addl_educational_cess text, addl_land_revenue text, " +
	        "    agau_vasuli_remarks text, area text, assessment text, challan_id bigint, cheque_amount text, cheque_branch text, " +
	        "    cheque_date date, cheque_name text, cheque_number text, date_of_notice text, dd_amount text, dd_branch text, dd_date date, " +
	        "    dd_name text, dd_number text, educational_cess text, employee_guarantee_scheme text, gp_akrushik text, gp_bindumala text, " +
	        "    gp_dumala text, gp_sankirn text, gp_vajasut text, is_money_deposited_bank character(1), jm_akrushik text, jm_bindumala text, " +
	        "    jm_dumala text, jm_sankirn text, jm_vajasut text, khata_no text, khata_owner_name text, land_revenue_form17_id bigint, " +
	        "    makhtakhata_no text, miscellaneous_amount text, mode_of_payment text, net_amount text, net_pending text, net_received text, " +
	        "    notice_amount text, pre_year_notice_fee text, pre_year_pending_addl_educational_cess text, pre_year_pending_addl_land_revenue text, " +
	        "    pre_year_pending_educational_cess text, pre_year_pending_employee_guarantee_scheme text, pre_year_pending_gp text, pre_year_pending_jm text, " +
	        "    pre_year_pending_na_cess text, pre_year_pending_zp text, pre_year_sankirn_jm_with text, pre_year_sankirn_jm_without text, " +
	        "    receipt_date date, receipt_no text, survey_hissa text, area_unit text, zp_akrushik text, zp_bindumala text, zp_dumala text, " +
	        "    zp_sankirn text, zp_vajasut text, challan_no text, challan_date timestamp without time zone " +
	        "  ) " +
	        ") AS final_data",
	        nativeQuery = true
	)
	List<Object[]> findPreviousYearData(
	        String host,          // ?1
	        String dbName,        // ?2
	        String schema,        // ?3
	        String user,          // ?4
	        String password,      // ?5
	        String cCode,         // ?6
	        String port,          // ?7
	        String revenueYear,   // ?8
	        String revSingleStr   // ?9
	);*/
	
	
			@Query(value =
			"select * from public.dblink( " +
					"   'dbname='|| ?2 ||' host='|| ?1 ||' user='|| ?4 ||' password='|| ?5 , " +
					"   'select khata_no, khata_owner_name, assessment, area, active_flag, ccode, revenue_year, " +
					"    survey_hissa, area_unit, jm_bindumala, zp_bindumala, gp_bindumala, " +
					"    jm_dumala, zp_dumala, gp_dumala, jm_akrushik, zp_akrushik, gp_akrushik, " +
					"    jm_sankirn, zp_sankirn, gp_sankirn, jm_vajasut, zp_vajasut, gp_vajasut, " +
					"    addl_land_revenue, educational_cess, addl_educational_cess, employee_guarantee_scheme, " +
					"    pre_year_pending_jm, pre_year_pending_gp, pre_year_pending_zp, " +
					"    pre_year_pending_addl_land_revenue, pre_year_pending_educational_cess, " +
					"    pre_year_pending_addl_educational_cess, pre_year_pending_employee_guarantee_scheme, " +
					"    net_amount, net_received, net_pending, date_of_notice, notice_amount, " +
					"    pre_year_notice_fee, pre_year_pending_na_cess, " +
					"    pre_year_sankirn_jm_with, pre_year_sankirn_jm_without, makhtakhata_no, " +
					"    id, mode_of_payment, receipt_no, receipt_date, dd_name, cheque_amount, " +
					"    dd_amount, dd_branch, cheque_branch, dd_date, cheque_date, cheque_number, dd_number, cheque_name " +
					"    FROM '|| ?3 ||'.land_revenue_demand_'|| ?8 ||'  " +
					"    where trim(ccode)='''|| ?6 ||''' " +
					"    and trim(revenue_year)='''|| ?7 ||''' " +
					"    and receipt_no IS NOT NULL " +
					//"    and active_flag=''Y'' ' " +
					//new line added for receipt no != null
					"    and active_flag=''Y'' " +
                       " and receipt_no is not null "+
					     "  and trim(receipt_no) <> '''''"+
					") as t( " +
					"  khata_no text, khata_owner_name text, assessment text, area text, active_flag char(1), " +
					"  ccode text, revenue_year text, survey_hissa text, area_unit text, " +
					"  jm_bindumala text, zp_bindumala text, gp_bindumala text, " +
					"  jm_dumala text, zp_dumala text, gp_dumala text, jm_akrushik text, zp_akrushik text, gp_akrushik text, " +
					"  jm_sankirn text, zp_sankirn text, gp_sankirn text, jm_vajasut text, zp_vajasut text, gp_vajasut text, " +
					"  addl_land_revenue text, educational_cess text, addl_educational_cess text, employee_guarantee_scheme text, " +
					"  pre_year_pending_jm text, pre_year_pending_gp text, pre_year_pending_zp text, " +
					"  pre_year_pending_addl_land_revenue text, pre_year_pending_educational_cess text, " +
					"  pre_year_pending_addl_educational_cess text, pre_year_pending_employee_guarantee_scheme text, " +
					"  net_amount text, net_received text, net_pending text, date_of_notice text, notice_amount text, " +
					"  pre_year_notice_fee text, pre_year_pending_na_cess text, " +
					"  pre_year_sankirn_jm_with text, pre_year_sankirn_jm_without text, makhtakhata_no text, " +
					"  id bigint, mode_of_payment text, receipt_no text, receipt_date date, dd_name text, " +
					"  cheque_amount text, dd_amount text, dd_branch text, cheque_branch text, " +
					"  dd_date date, cheque_date date, cheque_number text, dd_number text, cheque_name text )",
					nativeQuery = true)
				List<Object[]> findPreviousYearData(
	        String host,          // ?1
	        String dbName,        // ?2
	        String schema,        // ?3
	        String user,          // ?4
	        String password,      // ?5
	        String cCode,         // ?6
	        String revenueYear,   // ?7
	        String revSingleStr   // ?8
	);
	
	

}

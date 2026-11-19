/**
 * 
 */
package com.probity.echawdi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.probity.echawdi.entity.LandRevenueDemand;

/**
 * @author Sandeep Dubey
 *
 */

@Repository
public interface LandRevenueDemandRepository extends JpaRepository<LandRevenueDemand, Long> {
	
	//8B
	public  List<LandRevenueDemand> findBycCodeAndRevenueYearAndActiveFlag(
							  String cCode,String revenueYear,Character activeFlag);

	public  List<LandRevenueDemand> findBycCodeAndRevenueYearAndKhataNoAndModeOfPaymentIsNullAndActiveFlagNotIn(String cCode,String revenueYear,String khataNo,List<Character> activeflags);
	
//	public List<LandRevenueDemand> findBycCodeAndRevenueYearAndActiveFlag(
//				   String cCode, String revenueYear, Character activeFlag);
	
	
	public LandRevenueDemand findBycCodeAndMakhtaKhataNoAndRevenueYear(String cCode, String makhtaKhataNo,
			String revenueYear);
	//form 17 save old query
	public LandRevenueDemand findBycCodeAndKhataNoAndRevenueYear(String cCode, String makhtaKhataNo,
			String revenueYear);
	
	//form 17 updated query
	public LandRevenueDemand findBycCodeAndKhataNoAndRevenueYearAndActiveFlag(String cCode, String makhtaKhataNo,
			String revenueYear,Character activeFlag);
	
	public  List<LandRevenueDemand> findBycCodeAndKhataNoInAndRevenueYearAndActiveFlagAndLandReceiveForm17IdIsNull
									   (String cCode, List<String> khataNo, String revenueYear,Character activeFlag);
//oct 2024 28

	public List<LandRevenueDemand> findBycCodeAndMakhtaKhataNoInAndRevenueYearAndActiveFlagAndLandReceiveForm17IdIsNull(
			String cCode, List<String> makhtakhataNo, String revenueYear, Character activeFlag);

	public List<LandRevenueDemand> findReceiptDetailsBycCodeAndKhataNoAndRevenueYearAndActiveFlagAndReceiptNoIsNotNull
									   (String cCode, String khataNo, String revenueYear,Character activeFlag);
	public List<LandRevenueDemand> findReceiptDetailsBycCodeAndMakhtaKhataNoAndRevenueYearAndActiveFlagAndReceiptNoIsNotNull
										(String cCode, String makhtaKhataNo, String revenueYear, Character activeFlag);


	
	
	
	public List<LandRevenueDemand> findReceiptDetailsBycCodeAndIsMoneyepositedBankAndRevenueYearAndModeOfPaymentAndActiveFlagAndChallanIdIsNull
									   (String cCode, Character isMoneyepositedBank, String revenueYear,String modeOfPayment,Character activeFlag);

	public Optional<LandRevenueDemand> findBycCodeAndKhataNoAndActiveFlagAndRevenueYear
									   (String cCode,String khataNo,Character activeFlag,String revenueYear);

	List<LandRevenueDemand> findByChallanId(Long challanId);
	
	public List<LandRevenueDemand> findReceiptDetailsBycCodeAndIsMoneyepositedBankAndRevenueYearAndActiveFlag
	   (String cCode, Character isMoneyepositedBank, String revenueYear,Character activeFlag);
	
	public List<LandRevenueDemand> findReceiptDetailsBycCodeAndActiveFlagAndReceiptNoIsNotNull(
			String cCode, char c);
	
	public List<LandRevenueDemand> findAllBycCodeIn(List<String> cCodeList);
	
	/* original query / 10 sept 2024
	@Query(value="SELECT       "
			+ "trim(T1.KHATA_NO),       "
			+ "T2.KHATEDAR_NAMES,       "
			+ "T1.AREA,       "
			+ "T1.BINDUMALA_ASSESSMENT,       "
			+ "T2.SURVEY_HISSA,   "
			+ "T1.BAGAYAT_AREA_H,  "
			+ "T1.JIRAYAT_AREA_H,      "
			+ "T1.UNIT        "
			+ "FROM       "
			+ "(    "
			+ "SELECT     "
			+ "KHATA_NO,    "
			+ "SUM(TOTAL_AREA_H) AS AREA,     "
			+ "SUM(ASSESSMENT) AS BINDUMALA_ASSESSMENT,  "
			+ "SUM(BAGAYAT_AREA_H) AS BAGAYAT_AREA_H,  "
			+ "SUM(JIRAYAT_AREA_H) AS JIRAYAT_AREA_H,      "
			+ "CASE WHEN KHAND_NO = '2' THEN 'R.Sq.M.' ELSE 'H-R.Sq.M.' END AS UNIT     "
			+ "FROM       "
			+ "public.dblink(       "
			+ "'dbname=' || ?2 || ' host=' || ?1 || ' port=' || ?13 ||' user=' || ?4 || ' password=' || ?5 ,    "
			+ "'SELECT DISTINCT       "
			+ "F7K.KHATA_NO,       "
			+ "F7K.TOTAL_AREA_H,       "
			+ "F7K.ASSESSMENT,  "
			+ "F7A.BAGAYAT_AREA_H,  "
			+ "F7A.JIRAYAT_AREA_H,    "
			+ "F7.KHAND_NO,    "
			+ "MVC.IMAGINARY_MUT_NO_CHECK,    "
			+ "F7K.MUT_NO,    "
			+ "F7K.ccode,f7k.pin,f7k.pin1,f7k.pin2,f7k.pin3,f7k.pin4,f7k.pin5,f7k.pin6,f7k.pin7,f7k.pin8,    "
			+ "F7K.MARKED,        "
			+ "F7.TENURE_CODE,        "
			+ "F7.TENURE_SUB_CODE     "
			+ "FROM        "
			+ "'|| ?3 ||'.FORM7_KHATA F7K,       "
			+ "'|| ?3 ||'.FORM7 F7,    "
			+ "'|| ?3 ||'.FORM7_area F7A,     "
			+ "'|| ?3 ||'.M_VILLAGE_CENSUS_CHECK MVC        "
			+ "WHERE        "
			+ "F7K.CCODE = F7.CCODE        "
			+ "AND F7K.CCODE = MVC.CCODE        "
			+ "AND F7.PIN = F7K.PIN        "
			+ "AND F7.PIN1 = F7K.PIN1        "
			+ "AND F7.PIN2 = F7K.PIN2        "
			+ "AND F7.PIN3 = F7K.PIN3        "
			+ "AND F7.PIN4 = F7K.PIN4        "
			+ "AND F7.PIN5 = F7K.PIN5        "
			+ "AND F7.PIN6 = F7K.PIN6        "
			+ "AND F7.PIN7 = F7K.PIN7        "
			+ "AND F7.PIN8 = F7K.PIN8  "
			+ "AND F7A.CCODE = F7K.CCODE        "
			+ "AND F7A.CCODE = MVC.CCODE        "
			+ "AND F7A.PIN = F7K.PIN        "
			+ "AND F7A.PIN1 = F7K.PIN1        "
			+ "AND F7A.PIN2 = F7K.PIN2        "
			+ "AND F7A.PIN3 = F7K.PIN3        "
			+ "AND F7A.PIN4 = F7K.PIN4        "
			+ "AND F7A.PIN5 = F7K.PIN5        "
			+ "AND F7A.PIN6 = F7K.PIN6        "
			+ "AND F7A.PIN7 = F7K.PIN7        "
			+ "AND F7A.PIN8 = F7K.PIN8        "
			+ "AND F7.KHAND_NO != ''2'' "
			+ "AND F7K.MARKED != ''Y'' "
			+ "AND F7K.KHATA_NO != ''500001'' " 
			+ "AND F7K.CCODE= '|| ?12 ||'::text		"
			+ "AND F7.CCODE=  '|| ?12 ||'::text	 		"
			+ "AND F7A.CCODE=  '|| ?12 ||'::text' 		"
			+ ")    "
			+ "AS 	    "
			+ "Fer1    "
			+ "(    "
			+ "KHATA_NO text,       "
			+ "TOTAL_AREA_H numeric,       "
			+ "ASSESSMENT numeric,  "
			+ "BAGAYAT_AREA_H NUMERIC,  "
			+ "JIRAYAT_AREA_H NUMERIC,   "
			+ "KHAND_NO  text,    "
			+ "IMAGINARY_MUT_NO_CHECK character(1),    "
			+ "MUT_NO  text,    "
			+ "ccode text,    "
			+ "pin  text,    "
			+ "pin1 text,    "
			+ "pin2 text,    "
			+ "pin3 text,    "
			+ "pin4 text,    "
			+ "pin5 text,    "
			+ "pin6 text,    "
			+ "pin7 text,    "
			+ "pin8 text,    "
			+ "MARKED text,    "
			+ "TENURE_CODE text,    "
			+ "TENURE_SUB_CODE text    "
			+ ")    "
			+ "where  "
			+ " trim(CCODE) = ?12    "
			+ "AND KHATA_NO NOT IN   "
			+ "(   "
			+ "SELECT KHATA_NO FROM public.dblink   "
			+ "(   "
			+ "'dbname='|| ?7  ||' host='|| ?6 ||' user='|| ?9 ||' password='|| ?10 ,   "
			+ "'select KHATA_NO,ccode,revenue_year,active_flag from '|| ?8 ||'.LAND_REVENUE_DEMAND LRD where trim(CCODE)= '|| ?12 ||'::text '   "
			+ ")   "
			+ "AS    "
			+ "LRDD(khata_no text,ccode text,revenue_year text,active_flag character(1))    "
			+ "WHERE    "
			+ "trim(CCODE)= ?12 and active_flag in ('Y','M') and revenue_year =?11 and khata_no <> '' and khata_no is not NULL   "
			+ ")           "
			//+ "AND MARKED != 'Y'        "
			//+ "AND KHAND_NO != '2'        "
			//+ "AND CONCAT(PIN, '/', PIN1, '/', PIN2, '/', PIN3, '/', PIN4, '/', PIN5, '/', PIN6, '/', PIN7, '/', PIN8) not in    "
			//+ "(    "
			//+ "select CONCAT(PIN, '/', PIN1, '/', PIN2, '/', PIN3, '/', PIN4, '/', PIN5, '/', PIN6, '/', PIN7, '/', PIN8)    "
			//+ "from public.dblink  "
			//+ "(  "
			//+ "'dbname=' || ?2 || ' host=' || ?1 || ' port=' || ?13 || ' user=' || ?4 || ' password=' || ?5 ,  "
			//+ "'select fk.pin,fk.pin1,fk.pin2,fk.pin3,fk.pin4,fk.pin5,fk.pin6,fk.pin7,fk.pin8,ccode from '|| ?3 ||'.form7_khata fk'  "
			//+ ")  "
			//+ "AS  "
			//+ "(  "
			//+ "pin text,pin1 text,pin2 text,pin3 text,pin4 text,pin5 text,pin6 text,pin7 text,pin8 text,ccode text  "
			//+ ")  "
			//+ "where trim(ccode)= ?12  AND KHATA_NO = '500001'    "
			//+ ")   "
			+ "GROUP BY       "
			+ "KHATA_NO ,UNIT   "
			+ ")       "
			+ "T1        "
			+ "INNER JOIN       "
			+ "(       "
			+ "SELECT DISTINCT       "
			+ "KHATA_NO,    "
			+ "STRING_AGG(DISTINCT CONCAT(FNAME, ' ', MNAME, ' ', LNAME), ' | ') AS KHATEDAR_NAMES,       "
			+ "STRING_AGG(DISTINCT CONCAT(PIN, '/', PIN1, '/', PIN2, '/', PIN3, '/', PIN4, '/', PIN5, '/', PIN6, '/', PIN7, '/', PIN8), ',') AS SURVEY_HISSA        "
			+ "FROM    "
			+ "public.dblink    "
			+ "(       "
			+ "'dbname=' || ?2 || ' host=' || ?1 || ' port=' || ?13 ||' user=' || ?4 || ' password=' || ?5 ,     "
			+ "'SELECT     "
			+ "KHATA_NO,CCODE,    "
			+ "F7K.FNAME,F7K.MNAME,F7K.LNAME,    "
			+ "F7K.PIN,F7K.PIN1,F7K.PIN2,F7K.PIN3,F7K.PIN4,F7K.PIN5,F7K.PIN6,F7K.PIN7,F7K.PIN8         "
			+ "FROM '|| ?3 ||'.FORM7_KHATA F7K WHERE trim(CCODE) = '|| ?12 ||'::text'        "
			+ ")    "
			+ "AS 	    "
			+ "Fer1    "
			+ "(    "
			+ "KHATA_NO text,       "
			+ "ccode text,    "
			+ "FNAME text,    "
			+ "MNAME text,    "
			+ "LNAME text,    "
			+ "pin  text,    "
			+ "pin1 text,    "
			+ "pin2 text,    "
			+ "pin3 text,    "
			+ "pin4 text,    "
			+ "pin5 text,    "
			+ "pin6 text,    "
			+ "pin7 text,    "
			+ "pin8 text    "
			+ ")    "
			+ "WHERE trim(CCODE) = ?12     "
			+ " GROUP BY KHATA_NO     "
			+ ")       "
			+ "T2        "
			+ "ON (T1.KHATA_NO = T2.KHATA_NO)     "
			+ "order by cast(t1.khata_no as bigint) "
			+ "", nativeQuery = true)
	public List<Object[]> getLRForBinbumala(
			String fAugHost,String fDbName,String fSchemaName,String fUser,String fPassword,
			String eHost,String eDbName,String eSchemaName,String eUser,String ePassword,
			String revenueYear,
			String cCode,String fAugPort
			);*/
	
	//10 sept 2024 for changes tari area, varkar, itar area
	@Query(value="SELECT       "
			+ "trim(T1.KHATA_NO),       "
			+ "T2.KHATEDAR_NAMES,       "
			+ "T1.AREA,       "
			+ "T1.BINDUMALA_ASSESSMENT,       "
			+ "T2.SURVEY_HISSA,   "
			+ "T1.BAGAYAT_AREA_H,  "
			+ "T1.JIRAYAT_AREA_H,      "
			+ "T1.UNIT   ,     "
			+ "T1.TARI_AREA_H,  " 	//New 
			+ "T1.VARKAS_AREA_H,  " 	//New 
			+ "T1.ITAR_AREA_H  " 	//New
			+ "FROM       "
			+ "(    "
			+ "SELECT     "
			+ "KHATA_NO,    "
			+ "SUM(TOTAL_AREA_H) AS AREA,     "
			+ "SUM(ASSESSMENT) AS BINDUMALA_ASSESSMENT,  "
			+ "SUM(BAGAYAT_AREA_H) AS BAGAYAT_AREA_H,  "
			+ "SUM(JIRAYAT_AREA_H) AS JIRAYAT_AREA_H,      "
			+ "SUM(TARI_AREA_H) AS TARI_AREA_H,      "   		//NEW
			+ "SUM(VARKAS_AREA_H) AS VARKAS_AREA_H,      "		//NEW
			+ "SUM(ITAR_AREA_H) AS ITAR_AREA_H,      "			//NEW
			+ "CASE WHEN KHAND_NO = '2' THEN 'R.Sq.M.' ELSE 'H-R.Sq.M.' END AS UNIT     "
			+ "FROM       "
			+ "public.dblink(       "
			+ "'dbname=' || ?2 || ' host=' || ?1 || ' port=' || ?13 ||' user=' || ?4 || ' password=' || ?5 ,    "
			+ "'SELECT DISTINCT       "
			+ "F7K.KHATA_NO,       "
			+ "F7K.TOTAL_AREA_H,       "
			+ "F7K.ASSESSMENT,  "
			+ "F7A.BAGAYAT_AREA_H,  "
			+ "F7A.JIRAYAT_AREA_H,    "
			+ "F7A.TARI_AREA_H,    " //new
			+ "F7A.VARKAS_AREA_H,    " //new
			+ "F7A.ITAR_AREA_H,    " //new
			+ "F7.KHAND_NO,    "
			+ "MVC.IMAGINARY_MUT_NO_CHECK,    "
			+ "F7K.MUT_NO,    "
			+ "F7K.ccode,f7k.pin,f7k.pin1,f7k.pin2,f7k.pin3,f7k.pin4,f7k.pin5,f7k.pin6,f7k.pin7,f7k.pin8,    "
			+ "F7K.MARKED,        "
			+ "F7.TENURE_CODE,        "
			+ "F7.TENURE_SUB_CODE     "
			+ "FROM        "
			+ "'|| ?3 ||'.FORM7_KHATA F7K,       "
			+ "'|| ?3 ||'.FORM7 F7,    "
			+ "'|| ?3 ||'.FORM7_area F7A,     "
			+ "'|| ?3 ||'.M_VILLAGE_CENSUS_CHECK MVC        "
			+ "WHERE        "
			+ "F7K.CCODE = F7.CCODE        "
			+ "AND F7K.CCODE = MVC.CCODE        "
			+ "AND F7.PIN = F7K.PIN        "
			+ "AND F7.PIN1 = F7K.PIN1        "
			+ "AND F7.PIN2 = F7K.PIN2        "
			+ "AND F7.PIN3 = F7K.PIN3        "
			+ "AND F7.PIN4 = F7K.PIN4        "
			+ "AND F7.PIN5 = F7K.PIN5        "
			+ "AND F7.PIN6 = F7K.PIN6        "
			+ "AND F7.PIN7 = F7K.PIN7        "
			+ "AND F7.PIN8 = F7K.PIN8  "
			+ "AND F7.TENURE_SUB_CODE !=  ''7'' "
			+ "AND F7A.CCODE = F7K.CCODE       "
			+ "AND F7A.CCODE = MVC.CCODE       "
			+ "AND F7A.PIN = F7K.PIN           "
			+ "AND F7A.PIN1 = F7K.PIN1         "
			+ "AND F7A.PIN2 = F7K.PIN2         "
			+ "AND F7A.PIN3 = F7K.PIN3         "
			+ "AND F7A.PIN4 = F7K.PIN4         "
			+ "AND F7A.PIN5 = F7K.PIN5         "
			+ "AND F7A.PIN6 = F7K.PIN6         "
			+ "AND F7A.PIN7 = F7K.PIN7         "
			+ "AND F7A.PIN8 = F7K.PIN8         "
			+ "AND F7.KHAND_NO != ''2'' "
			+ "AND F7K.MARKED != ''Y'' "
			+ "AND F7K.KHATA_NO != ''500001'' " 
			+ "AND F7K.CCODE= '|| ?12 ||'::text		"
			+ "AND F7.CCODE=  '|| ?12 ||'::text	 		"
			+ "AND F7A.CCODE=  '|| ?12 ||'::text' 		"
			+ ")    "
			+ "AS 	    "
			+ "Fer1    "
			+ "(    "
			+ "KHATA_NO text,       "
			+ "TOTAL_AREA_H numeric,       "
			+ "ASSESSMENT numeric,  "
			+ "BAGAYAT_AREA_H NUMERIC,  "
			+ "JIRAYAT_AREA_H NUMERIC,   "
			+ "TARI_AREA_H NUMERIC,   " //new
			+ "VARKAS_AREA_H NUMERIC,   " //new
			+ "ITAR_AREA_H NUMERIC,   " //new
			+ "KHAND_NO  text,    "
			+ "IMAGINARY_MUT_NO_CHECK character(1),    "
			+ "MUT_NO  text,    "
			+ "ccode text,    "
			+ "pin  text,    "
			+ "pin1 text,    "
			+ "pin2 text,    "
			+ "pin3 text,    "
			+ "pin4 text,    "
			+ "pin5 text,    "
			+ "pin6 text,    "
			+ "pin7 text,    "
			+ "pin8 text,    "
			+ "MARKED text,    "
			+ "TENURE_CODE text,    "
			+ "TENURE_SUB_CODE text    "
			+ ")    "
			+ "where  "
			+ " trim(CCODE) = ?12    "
			+ "AND KHATA_NO NOT IN   "
			+ "(   "
			+ "SELECT KHATA_NO FROM public.dblink   "
			+ "(   "
			+ "'dbname='|| ?7  ||' host='|| ?6 ||' user='|| ?9 ||' password='|| ?10 ,   "
			+ "'select KHATA_NO,ccode,revenue_year,active_flag from '|| ?8 ||'.LAND_REVENUE_DEMAND LRD where trim(CCODE)= '|| ?12 ||'::text '   "
			+ ")   "
			+ "AS    "
			+ "LRDD(khata_no text,ccode text,revenue_year text,active_flag character(1))    "
			+ "WHERE    "
			+ "trim(CCODE)= ?12 and active_flag in ('Y','M') and revenue_year =?11 and khata_no <> '' and khata_no is not NULL   "
			+ ")           "
			//+ "AND MARKED != 'Y'        "
			//+ "AND KHAND_NO != '2'        "
			//+ "AND CONCAT(PIN, '/', PIN1, '/', PIN2, '/', PIN3, '/', PIN4, '/', PIN5, '/', PIN6, '/', PIN7, '/', PIN8) not in    "
			//+ "(    "
			//+ "select CONCAT(PIN, '/', PIN1, '/', PIN2, '/', PIN3, '/', PIN4, '/', PIN5, '/', PIN6, '/', PIN7, '/', PIN8)    "
			//+ "from public.dblink  "
			//+ "(  "
			//+ "'dbname=' || ?2 || ' host=' || ?1 || ' port=' || ?13 || ' user=' || ?4 || ' password=' || ?5 ,  "
			//+ "'select fk.pin,fk.pin1,fk.pin2,fk.pin3,fk.pin4,fk.pin5,fk.pin6,fk.pin7,fk.pin8,ccode from '|| ?3 ||'.form7_khata fk'  "
			//+ ")  "
			//+ "AS  "
			//+ "(  "
			//+ "pin text,pin1 text,pin2 text,pin3 text,pin4 text,pin5 text,pin6 text,pin7 text,pin8 text,ccode text  "
			//+ ")  "
			//+ "where trim(ccode)= ?12  AND KHATA_NO = '500001'    "
			//+ ")   "
			+ "GROUP BY       "
			+ "KHATA_NO ,UNIT   "
			+ ")       "
			+ "T1        "
			+ "INNER JOIN       "
			+ "(       "
			+ "SELECT DISTINCT       "
			+ "KHATA_NO,    "
			//+ "STRING_AGG(DISTINCT CONCAT(FNAME, ' ', MNAME, ' ', LNAME), ' | ') AS KHATEDAR_NAMES,       "
			+ "STRING_AGG(DISTINCT CONCAT(FNAME, ' ', MNAME, ' ', LNAME,' ',topan_name), ' | ') AS KHATEDAR_NAMES,  "
			+ "STRING_AGG(DISTINCT CONCAT(PIN, '/', PIN1, '/', PIN2, '/', PIN3, '/', PIN4, '/', PIN5, '/', PIN6, '/', PIN7, '/', PIN8), ',') AS SURVEY_HISSA        "
			+ "FROM    "
			+ "public.dblink    "
			+ "(       "
			+ "'dbname=' || ?2 || ' host=' || ?1 || ' port=' || ?13 ||' user=' || ?4 || ' password=' || ?5 ,     "
			+ "'SELECT     "
			+ "KHATA_NO,CCODE,    "
			+ "F7K.FNAME,F7K.MNAME,F7K.LNAME, F7K.topan_name,   "
			+ "F7K.PIN,F7K.PIN1,F7K.PIN2,F7K.PIN3,F7K.PIN4,F7K.PIN5,F7K.PIN6,F7K.PIN7,F7K.PIN8         "
			+ "FROM '|| ?3 ||'.FORM7_KHATA F7K WHERE trim(CCODE) = '|| ?12 ||'::text  AND F7K.MARKED != ''Y''  '        "
			+ ")    "
			+ "AS 	    "
			+ "Fer1    "
			+ "(    "
			+ "KHATA_NO text,       "
			+ "ccode text,    "
			+ "FNAME text,    "
			+ "MNAME text,    "
			+ "LNAME text,    "
			+ "topan_name text, "
			+ "pin  text,    "
			+ "pin1 text,    "
			+ "pin2 text,    "
			+ "pin3 text,    "
			+ "pin4 text,    "
			+ "pin5 text,    "
			+ "pin6 text,    "
			+ "pin7 text,    "
			+ "pin8 text    "
			+ ")    "
			+ "WHERE trim(CCODE) = ?12     "
			+ " GROUP BY KHATA_NO     "
			+ ")       "
			+ "T2        "
			+ "ON (T1.KHATA_NO = T2.KHATA_NO)     "
			+ "order by cast(t1.khata_no as bigint) "
			+ "", nativeQuery = true)
	public List<Object[]> getLRForBinbumala(
			String fAugHost,String fDbName,String fSchemaName,String fUser,String fPassword,
			String eHost,String eDbName,String eSchemaName,String eUser,String ePassword,
			String revenueYear,
			String cCode,String fAugPort
			);
	
	//10 sept 2024 for changes tari area, varkar, itar area End.
	
				
    @Query(value="SELECT       "
    		+ "trim(T1.KHATA_NO),       "
    		+ "T2.KHATEDAR_NAMES,       "
    		+ "T1.AREA,       "
    		+ "T1.BINDUMALA_ASSESSMENT,       "
    		+ "T2.SURVEY_HISSA,   "
    		+ "T1.BAGAYAT_AREA_H,  "
    		+ "T1.JIRAYAT_AREA_H,      "
    		+ "T1.UNIT        "
    		+ "FROM       "
    		+ "(    "
    		+ "SELECT     "
    		+ "KHATA_NO,    "
    		+ "SUM(TOTAL_AREA_H) AS AREA,     "
    		+ "SUM(ASSESSMENT) AS BINDUMALA_ASSESSMENT,  "
    		+ "SUM(BAGAYAT_AREA_H) AS BAGAYAT_AREA_H,  "
    		+ "SUM(JIRAYAT_AREA_H) AS JIRAYAT_AREA_H,      "
    		+ "CASE WHEN KHAND_NO = '2' THEN 'R.Sq.M.' ELSE 'H-R.Sq.M.' END AS UNIT     "
    		+ "FROM       "
    		+ "public.dblink(       "
    		+ "'dbname=' || ?2 || ' host=' || ?1 || ' port=' || ?13 || ' user=' || ?4 || ' password=' || ?5 ,    "
    		+ "'SELECT DISTINCT       "
    		+ "F7K.KHATA_NO,       "
    		+ "F7K.TOTAL_AREA_H,       "
    		+ "F7K.ASSESSMENT,  "
    		+ "F7A.BAGAYAT_AREA_H,  "
    		+ "F7A.JIRAYAT_AREA_H,    "
    		+ "F7.KHAND_NO,    "
    		+ "MVC.IMAGINARY_MUT_NO_CHECK,    "
    		+ "F7K.MUT_NO,    "
    		+ "F7K.ccode,f7k.pin,f7k.pin1,f7k.pin2,f7k.pin3,f7k.pin4,f7k.pin5,f7k.pin6,f7k.pin7,f7k.pin8,    "
    		+ "F7K.MARKED,   "
    		+ "F7A.NA_AREA_H,       "
    		+ "F7.TENURE_CODE,        "
    		+ "F7.TENURE_SUB_CODE     "
    		+ "FROM        "
    		+ "'|| ?3 ||'.FORM7_KHATA F7K,       "
    		+ "'|| ?3 ||'.FORM7 F7,    "
    		+ "'|| ?3 ||'.FORM7_area F7A,     "
    		+ "'|| ?3 ||'.M_VILLAGE_CENSUS_CHECK MVC        "
    		+ "WHERE        "
    		+ "F7K.CCODE = F7.CCODE        "
    		+ "AND F7K.CCODE = MVC.CCODE        "
    		+ "AND F7.PIN = F7K.PIN        "
    		+ "AND F7.PIN1 = F7K.PIN1        "
    		+ "AND F7.PIN2 = F7K.PIN2        "
    		+ "AND F7.PIN3 = F7K.PIN3        "
    		+ "AND F7.PIN4 = F7K.PIN4        "
    		+ "AND F7.PIN5 = F7K.PIN5        "
    		+ "AND F7.PIN6 = F7K.PIN6        "
    		+ "AND F7.PIN7 = F7K.PIN7        "
    		+ "AND F7.PIN8 = F7K.PIN8  "
    		+ "AND F7A.CCODE = F7K.CCODE        "
    		+ "AND F7A.CCODE = MVC.CCODE        "
    		+ "AND F7A.PIN = F7K.PIN        "
    		+ "AND F7A.PIN1 = F7K.PIN1        "
    		+ "AND F7A.PIN2 = F7K.PIN2        "
    		+ "AND F7A.PIN3 = F7K.PIN3        "
    		+ "AND F7A.PIN4 = F7K.PIN4        "
    		+ "AND F7A.PIN5 = F7K.PIN5        "
    		+ "AND F7A.PIN6 = F7K.PIN6        "
    		+ "AND F7A.PIN7 = F7K.PIN7        "
    		+ "AND F7A.PIN8 = F7K.PIN8        "
    		+ "AND F7K.MARKED != ''Y'' 		  "
    		+ "AND F7K.CCODE= '|| ?12 ||'::text		"
			+ "AND F7.CCODE=  '|| ?12 ||'::text	 		"
			+ "AND F7A.CCODE=  '|| ?12 ||'::text' 		"
    		+ ")    "
    		+ "AS 	    "
    		+ "Fer1    "
    		+ "(    "
    		+ "KHATA_NO text,       "
    		+ "TOTAL_AREA_H numeric,       "
    		+ "ASSESSMENT numeric,  "
    		+ "BAGAYAT_AREA_H NUMERIC,  "
    		+ "JIRAYAT_AREA_H NUMERIC,   "
    		+ "KHAND_NO  text,    "
    		+ "IMAGINARY_MUT_NO_CHECK character(1),    "
    		+ "MUT_NO  text,    "
    		+ "ccode text,    "
    		+ "pin  text,    "
    		+ "pin1 text,    "
    		+ "pin2 text,    "
    		+ "pin3 text,    "
    		+ "pin4 text,    "
    		+ "pin5 text,    "
    		+ "pin6 text,    "
    		+ "pin7 text,    "
    		+ "pin8 text,    "
    		+ "MARKED text,  "
    		+ "NA_AREA_H numeric,    "
    		+ "TENURE_CODE text,    "
    		+ "TENURE_SUB_CODE text    "
    		+ ")    "
    		+ "where  "
    		+ " trim(CCODE) = ?12    "
    		+ "AND KHATA_NO NOT IN   "
    		+ "(   "
    		+ "SELECT KHATA_NO FROM public.dblink   "
    		+ "(   "
    		+ "'dbname='|| ?7 ||' host='|| ?6 ||' user='|| ?9 ||' password='|| ?10  ,   "
    		+ "'select KHATA_NO,ccode,revenue_year,active_flag from '|| ?8 ||'.LAND_REVENUE_DEMAND LRD '   "
    		+ ")   "
    		+ "AS    "
    		+ "LRDD(khata_no text,ccode text,revenue_year text,active_flag character(1))    "
    		+ "WHERE    "
    		+ "trim(CCODE)= ?12 and active_flag in ('Y','M') and revenue_year =?11 and khata_no <> ''  and khata_no is not NULL  "
    		+ ")           "
    		+ "AND MARKED != 'Y'   "
    		+ "AND NA_AREA_H = 0  "
    		+ "AND TENURE_CODE = '2'  "
    		+ "AND TENURE_SUB_CODE = '7'       "
    		+ "AND KHAND_NO != '2'        "
    		+ "AND CONCAT(PIN, '/', PIN1, '/', PIN2, '/', PIN3, '/', PIN4, '/', PIN5, '/', PIN6, '/', PIN7, '/', PIN8) not in    "
    		+ "(    "
    		+ "select CONCAT(PIN, '/', PIN1, '/', PIN2, '/', PIN3, '/', PIN4, '/', PIN5, '/', PIN6, '/', PIN7, '/', PIN8)    "
    		+ "from public.dblink  "
    		+ "(  "
    		+ "'dbname=' || ?2 || ' host=' || ?1 || ' port=' || ?13 || ' user=' || ?4 || ' password=' || ?5 ,  "
    		+ "'select fk.pin,fk.pin1,fk.pin2,fk.pin3,fk.pin4,fk.pin5,fk.pin6,fk.pin7,fk.pin8,ccode from '|| ?3 ||'.form7_khata fk'  "
    		+ ")  "
    		+ "AS  "
    		+ "(  "
    		+ "pin text,pin1 text,pin2 text,pin3 text,pin4 text,pin5 text,pin6 text,pin7 text,pin8 text,ccode text  "
    		+ ")  "
    		+ "where trim(ccode)= ?12  AND KHATA_NO = '500001'    "
    		+ ")   "
    		+ "GROUP BY       "
    		+ "KHATA_NO ,UNIT   "
    		+ ")       "
    		+ "T1        "
    		+ "INNER JOIN       "
    		+ "(       "
    		+ "SELECT DISTINCT       "
    		+ "KHATA_NO,    "
    		//+ "STRING_AGG(DISTINCT CONCAT(FNAME, ' ', MNAME, ' ', LNAME), ' | ') AS KHATEDAR_NAMES,       "
    		+ "STRING_AGG(DISTINCT CONCAT(FNAME, ' ', MNAME, ' ', LNAME,' ',topan_name), ' | ') AS KHATEDAR_NAMES,  "
    		+ "STRING_AGG(DISTINCT CONCAT(PIN, '/', PIN1, '/', PIN2, '/', PIN3, '/', PIN4, '/', PIN5, '/', PIN6, '/', PIN7, '/', PIN8), ',') AS SURVEY_HISSA        "
    		+ "FROM    "
    		+ "public.dblink    "
    		+ "(       "
    		+ "'dbname=' || ?2 || ' host=' || ?1 || ' port=' || ?13 || ' user=' || ?4 || ' password=' || ?5 ,     "
    		+ "'SELECT     "
    		+ "KHATA_NO,CCODE,    "
    		+ "F7K.FNAME,F7K.MNAME,F7K.LNAME, F7K.topan_name,    "
    		+ "F7K.PIN,F7K.PIN1,F7K.PIN2,F7K.PIN3,F7K.PIN4,F7K.PIN5,F7K.PIN6,F7K.PIN7,F7K.PIN8         "
    		+ "FROM '|| ?3 ||'.FORM7_KHATA F7K  where  F7K.MARKED != ''Y'' '        "
    		+ ")    "
    		+ "AS 	    "
    		+ "Fer1    "
    		+ "(    "
    		+ "KHATA_NO text,       "
    		+ "ccode text,    "
    		+ "FNAME text,    "
    		+ "MNAME text,    "
    		+ "LNAME text,    "
    		+ "topan_name text,"
    		+ "pin  text,    "
    		+ "pin1 text,    "
    		+ "pin2 text,    "
    		+ "pin3 text,    "
    		+ "pin4 text,    "
    		+ "pin5 text,    "
    		+ "pin6 text,    "
    		+ "pin7 text,    "
    		+ "pin8 text    "
    		+ ")    "
    		+ "WHERE trim(CCODE) = ?12    "
    		+ " GROUP BY KHATA_NO     "
    		+ ")       "
    		+ "T2        "
    		+ "ON (T1.KHATA_NO = T2.KHATA_NO)     "
    		+ "order by cast(t1.khata_no as bigint) "
	 		+ "", nativeQuery = true) 
	public List<Object[]> getLRForDumala(
			String fAugHost,String fDbName,String fSchemaName,String fUser,String fPassword,
			String eHost,String eDbName,String eSchemaName,String eUser,String ePassword,
			@Param("revenueYear") String revenueYear,@Param("cCode") String cCode,String fAugPort);		
	
	@Query(value ="SELECT DISTINCT    "
			+ "trim(KHATA_NO),    "
			//+ "STRING_AGG(DISTINCT CONCAT(FNAME, ' ', MNAME, ' ', LNAME), ' | ') AS KHATEDAR_NAMES,    "
			+ "STRING_AGG(DISTINCT CONCAT(FNAME, ' ', MNAME, ' ', LNAME,' ',topan_name), ' | ') AS KHATEDAR_NAMES,  "
			+ "case WHEN sum(TOTAL_AREA_H ) > 0 THEN sum(TOTAL_AREA_H )  else  SUM(TOTAL_AREA_H + total_potkharaba_h)  END AS AREA ,    "
			+ "SUM(ASSESSMENT) AS AKRUSHIK_ASSESSMENT,    "
			+ "STRING_AGG(DISTINCT CONCAT(PIN, '/', PIN1, '/', PIN2, '/', PIN3, '/', PIN4,    "
			+ "'/', PIN5, '/', PIN6, '/', PIN7, '/', PIN8), ',') AS SURVEY_HISSA,    "
			+ "'RSQM' AS UNIT     "
			+ "FROM    "
			+ "public.dblink('dbname='|| ?2 ||' port=' || ?13 || ' host='|| ?1 ||' user='|| ?4  || ' password='|| ?5 ,   "
			+ "'select     "
			+ "f7k.KHATA_NO,    "
			+ "f7k.FNAME,   "
			+ "f7k.MNAME,   "
			+ "f7k.LNAME,    "
			+ "F7K.topan_name,  "
			+ "f7k.TOTAL_AREA_H,   "
			+ "f7k.POTKHARABA,    "
			+ "f7k.ASSESSMENT,    "
			+ "f7k.PIN,   "
			+ "f7k.PIN1,f7k.PIN2,f7k.PIN3,f7k.PIN4,f7k.PIN5,f7k.PIN6,f7k.PIN7,f7k.PIN8,   "
			+ "MVC.IMAGINARY_MUT_NO_CHECK,   "
			+ "f7.MUT_NO ,   "
			+ "f7a.NA_AREA_H ,   "
			+ "f7k.CCODE ,     "
			+ "f7.TENURE_CODE,     "
			+ "f7.TENURE_SUB_CODE,     "
			+ "f7k.MARKED ,  "
			+ "F7A.total_potkharaba_h ,"
			+ "F7.KHAND_NO  "
			+ "from    "
			+ "'|| ?3 ||'.FORM7_KHATA F7K,    "
			+ "'|| ?3 ||'.FORM7_AREA F7A,    "
			+ "'|| ?3 ||'.FORM7 F7,    "
			+ "'|| ?3 ||'.M_VILLAGE_CENSUS_CHECK MVC    "
			+ "WHERE    "
			+ "F7K.CCODE = MVC.CCODE     "
			+ "AND F7.CCODE = F7A.CCODE     "
			+ "AND F7.CCODE = F7K.CCODE     "
			+ "AND F7.PIN = F7K.PIN     "
			+ "AND F7.PIN1 = F7K.PIN1     "
			+ "AND F7.PIN2 = F7K.PIN2     "
			+ "AND F7.PIN3 = F7K.PIN3     "
			+ "AND F7.PIN4 = F7K.PIN4     "
			+ "AND F7.PIN5 = F7K.PIN5     "
			+ "AND F7.PIN6 = F7K.PIN6     "
			+ "AND F7.PIN7 = F7K.PIN7     "
			+ "AND F7.PIN8 = F7K.PIN8     "
			+ "AND F7.PIN = F7A.PIN     "
			+ "AND F7.PIN1 = F7A.PIN1     "
			+ "AND F7.PIN2 = F7A.PIN2     "
			+ "AND F7.PIN3 = F7A.PIN3     "
			+ "AND F7.PIN4 = F7A.PIN4     "
			+ "AND F7.PIN5 = F7A.PIN5     "
			+ "AND F7.PIN6 = F7A.PIN6     "
			+ "AND F7.PIN7 = F7A.PIN7     "
			+ "AND F7.PIN8 = F7A.PIN8   "			
			+ "AND F7K.MARKED != ''Y'' "
			+ "AND F7K.CCODE= '|| ?12 ||'::text		"
			+ "AND F7.CCODE=  '|| ?12 ||'::text	 		"
			+ "AND F7A.CCODE=  '|| ?12 ||'::text' 		"
			+ ")AS ferfar   "
			+ "(   "
			+ "KHATA_NO text,    "
			+ "FNAME text,   "
			+ "MNAME text,   "
			+ "LNAME text,    "
			+ "topan_name text,  "
			+ "TOTAL_AREA_H numeric,   "
			+ "POTKHARABA  numeric,   "
			+ "ASSESSMENT  numeric,    "
			+ "PIN text,   "
			+ "PIN1 text,   "
			+ "PIN2 text,   "
			+ "PIN3 text,   "
			+ "PIN4 text,   "
			+ "PIN5 text,   "
			+ "PIN6 text,   "
			+ "PIN7 text,   "
			+ "PIN8 text,   "
			+ "IMAGINARY_MUT_NO_CHECK boolean,    "
			+ "MUT_NO text,   "
			+ "NA_AREA_H numeric,   "
			+ "CCODE text,     "
			+ "TENURE_CODE text,     "
			+ "TENURE_SUB_CODE  text,     "
			+ "MARKED character(1) ,  "
			+ "total_potkharaba_h  numeric ,"
			+ "KHAND_NO text "
			+ ")   "
			+ "where    "
			+ "KHAND_NO = '2' " // 12-02-2024 // Akrushak khate will be identified by khand number=2 and total NA area + Potkharaba H added
			//+ " NA_AREA_H != 0     "
			+ "AND trim(CCODE) = ?12     "
			+ "AND MARKED != 'Y'  "
			//+ "AND TENURE_CODE != '2'  " // // 02-11-2023  // show all type of bhogavatdar for demand // Bhushan// approved by Mr. Gire
			//+ "AND TENURE_SUB_CODE != '7' "
			+ "AND KHATA_NO NOT IN    "
			+ "(   "
			+ "SELECT khata_no       "
			+ "FROM public.dblink(   "
			+ "'dbname='|| ?7 ||' host='|| ?6 ||' user='||?9||' password='||?10 ,   "
			+ "'select khata_no,revenue_year,ccode,active_flag from '||?8||'.land_revenue_demand'   "
			+ ")AS   "
			+ "(   "
			+ "khata_no text,revenue_year text,ccode text,active_flag text   "
			+ ")    "
			+ "WHERE revenue_year =?11   "
			+ "AND trim(ccode) = ?12      "
			+ "AND active_flag in ('Y','M')   and khata_no is not NULL  "
			+ ")  "
			+ "AND CONCAT(PIN, '/', PIN1, '/', PIN2, '/', PIN3, '/', PIN4, '/', PIN5, '/', PIN6, '/', PIN7, '/', PIN8) not in    "
			+ "(    "
			+ "select CONCAT(PIN, '/', PIN1, '/', PIN2, '/', PIN3, '/', PIN4, '/', PIN5, '/', PIN6, '/', PIN7, '/', PIN8)    "
			+ "from public.dblink   "
			+ "(   "
			+ "'dbname='|| ?2 ||' host='|| ?1 ||' port=' || ?13 || ' user='|| ?4  || ' password='|| ?5 ,   "
			+ "'select pin,pin1,pin2,pin3,pin4,pin5,pin6,pin7,pin8,ccode,tenure_code,tenure_sub_code from '|| ?3 ||'.form7_khata f7k '   "
			+ ")   "
			+ "AS   "
			+ "(   "
			+ "pin text,pin1 text,pin2 text,pin3 text,pin4 text,pin5 text,pin6 text,pin7 text,pin8 text,ccode text "
			+ ")   "
			+ "where trim(ccode)= ?12 AND KHATA_NO = '500001'  "
			+ ")   "
			+ "AND KHATA_NO <> '500001'     "
			+ "GROUP BY    "
			+ "KHATA_NO   "
			+ "", nativeQuery = true)
	public List<Object[]> getLRForAkrushik(
			String fAugHost,String fDbName,String fSchemaName,String fUser,String fPassword,
			String eHost,String eDbName,String eSchemaName,String eUser,String ePassword,
			String revenueYear,String cCode,String fAugPort
			);
	
	/*@Query(value = "SELECT  "
			+ "trim(KHATA_NO),  "
			+ "KHATEDAR_NAMES ,  "
			+ "CROP_CODE,  "
			+ "CROP_NAME,  "
			+ "CONST_IRR_SUM   "
			+ "FROM  "
			+ "(  "
			+ "SELECT DISTINCT  "
			+ "t1.KHATA_NO,  "
			+ "KHATEDAR_NAMES,  "
			+ "CROP_CODE,  "
			+ "CROP_NAME,  "
			+ "CONST_IRR_SUM   "
			+ "FROM  "
			+ "(  "
			+ "select   "
			+ "f7kk.KHATA_NO,  "
			+ "mcc.CROP_CODE,  "
			+ "CROP_NAME,  "
			+ "CONST_IRR_AREA_H AS CONST_IRR_SUM from   "
			+ "(select * from public.dblink  "
			+ "('dbname='|| ?2 ||' host='|| ?1 ||' user='|| ?4 || ' password='|| ?5 ,  "
			+ "'select f7k.KHATA_NO,f7k.MARKED,f7k.PIN,f7k.PIN1,f7k.PIN2,f7k.PIN3,f7k.PIN4,f7k.PIN5,f7k.PIN6,f7k.PIN7,f7k.PIN8,f7k.ccode  from  "
			+ "'||?3||'.FORM7_KHATA F7K where ccode='|| ?13 ||' ::text  where '  "
			+ ")  "
			+ "AS(  "
			+ "KHATA_NO TEXT,  "
			+ "MARKED TEXT,  "
			+ "PIN TEXT,  "
			+ "PIN1 TEXT,  "
			+ "PIN2 TEXT,  "
			+ "PIN3 TEXT,  "
			+ "PIN4 TEXT,  "
			+ "PIN5 TEXT,  "
			+ "PIN6 TEXT,  "
			+ "PIN7 TEXT,  "
			+ "PIN8 TEXT,  "
			+ "ccode text  "
			+ ")  "
			+ "where trim(CCODE) = ?13  "
			+ "AND MARKED <> 'Y'   "
			+ "AND KHATA_NO <> '500001'    "
			+ ")as f7kk,  "
			+ "(  "
			+ "select * from public.dblink  "
			+ "(  "
			+ "'dbname='||?2 ||' host='|| ?1 ||' user='|| ?4 || ' password='|| ?5 ,  "
			+ "'select mc.CROP_NAME,mc.CROP_CODE from '||?3||'.M_CROP_NAME MC'  "
			+ ")  "
			+ "AS(  "
			+ "CROP_NAME TEXT,  "
			+ "CROP_CODE TEXT  "
			+ ")  "
			+ ")  "
			+ "as mcc,  "
			+ "(  "
			+ "select * from public.dblink  "
			//+ "('dbname='||?2 ||' host='|| ?1 ||' user='|| ?4 || ' password='|| ?5 ,  "
			+ "	('dbname='||?2||'crop' ||' host='|| ?1 ||' user='||?4|| ' password='||?5 ,  "
			+ "'select f12.CONST_CROP_CODE,f12.CONST_IRR_AREA_H,f12.NUMERIC_PIN,f12.YEAR_CULTI,F12.CCODE   "
			+ " from '||?3||'.FORM12 F12 where ccode='|| ?13 ||' ::text and YEAR_CULTI='|| ?11 ||' ')  "
			+ "AS  "
			+ "(  "
			+ "CONST_CROP_CODE TEXT,  "
			+ "CONST_IRR_AREA_H numeric(18,2),  "
			+ "NUMERIC_PIN numeric(18),  "
			+ "YEAR_CULTI int,  "
			+ "ccode text  "
			+ ")where trim(CCODE) = ?13  "
			+ "  "
			+ ")as f12cr  "
			+ "WHERE   "
			+ "F7KK.KHATA_NO = TEXT(F12cr.NUMERIC_PIN)   "
			+ "AND F7KK.CCODE = F12cr.CCODE  "
			+ "AND F12cr.CONST_CROP_CODE = MCC.CROP_CODE  "
			+ "AND f12cr.YEAR_CULTI =?11   "
			+ "AND f12cr.CONST_IRR_AREA_H <> '0'   "
			+ "AND CAST(f12cr.CONST_CROP_CODE AS VARCHAR) LIKE '27%'   "
			+ "AND F12cr.CONST_CROP_CODE = MCC.CROP_CODE  "
			+ "AND KHATA_NO NOT IN(  "
			+ "SELECT KHATA_NO FROM  "
			+ "public.dblink  "
			+ "('dbname='||?7 ||' host='|| ?6 ||' user='|| ?9 || ' password='|| ?10 ,  "
			+ " 'SELECT LRD.KHATA_NO,LRD.CCODE,LRD.revenue_year FROM '||?8||'.LAND_REVENUE_DEMAND LRD where ccode='|| ?13 ||' ::text and active_flag=''Y''  '  "
			+ " )AS(  "
			+ " KHATA_NO text,CCODE text, revenue_year text  "
			+ " )  "
			+ " WHERE trim(CCODE)= ?13   and revenue_year = ?12 and khata_no <> '' and khata_no is not NULL )  "
			+ ")AS T1   "
			+ "INNER JOIN  "
			+ "(  "
			+ "SELECT DISTINCT  "
			+ "KHATA_NO,  "
			//+ "STRING_AGG(DISTINCT CONCAT(FNAME, ' ', MNAME, ' ', LNAME), ' | ') AS KHATEDAR_NAMES   "
			+ "STRING_AGG(DISTINCT CONCAT(FNAME, ' ', MNAME, ' ', LNAME,' ',topan_name), ' | ') AS KHATEDAR_NAMES  "
			+ "FROM  "
			+ "public.dblink  "
			+ "('dbname='||?2 ||' host='|| ?1 ||' user='||?4|| ' password='||?5 ,  "
			+ "'select KHATA_NO,FNAME,MNAME,LNAME,ccode,topan_name FROM   "
			+ "'||?3||'.FORM7_KHATA F7K where  ccode='|| ?13 ||' ::text ')  "
			+ "AS(KHATA_NO TEXT,FNAME TEXT,MNAME TEXT,LNAME TEXT,ccode text, topan_name text)   "
			+ "WHERE  "
			+ "trim(CCODE) = ?13   "
			+ "AND KHATA_NO NOT IN  "
			+ "(  "
			+ "SELECT KHATA_NO FROM  "
			+ "public.dblink  "
			+ "('dbname='||?7||' host='|| ?6 ||' user='|| ?9 || ' password='||?10 ,  "
			+ " 'SELECT LRD.KHATA_NO,LRD.CCODE,LRD.revenue_year FROM '||?8||'.LAND_REVENUE_DEMAND LRD where ccode='|| ?13 ||' ::text and active_flag=''Y''   '  "
			+ " )AS(  "
			+ " KHATA_NO text,CCODE text, revenue_year text  "
			+ " )  "
			+ " WHERE trim(CCODE)= ?13   and revenue_year = ?12 and khata_no <> '' and khata_no is not NULL )  "
			+ " GROUP BY  "
			+ "KHATA_NO   "
			+ ")  "
			+ "T2   "
			+ "ON (T1.KHATA_NO = T2.KHATA_NO)  "
			+ ")A  "
			+ ""
			+ "", nativeQuery = true)
			public List<Object[]> getLRForEducationalCess(
			String fHost,String fDbName,String fSchemaName,String fUser,String fPassword,
			String eHost,String eDbName,String eSchemaName,String eUser,String ePassword,
			Integer year,String revenueYear,String cCode);*/
	
	
	@Query(value = "SELECT  "
			+ "trim(KHATA_NO),  "
			+ "KHATEDAR_NAMES ,  "
			+ "CROP_CODE,  "
			+ "CROP_NAME,  "
			+ "CONST_IRR_SUM   "
			+ "FROM  "
			+ "(  "
			+ "SELECT DISTINCT  "
			+ "t1.KHATA_NO,  "
			+ "KHATEDAR_NAMES,  "
			+ "CROP_CODE,  "
			+ "CROP_NAME,  "
			+ "CONST_IRR_SUM   "
			+ "FROM  "
			+ "(  "
			+ "select   "
			+ "f7kk.KHATA_NO,  "
			+ "mcc.CROP_CODE,  "
			+ "CROP_NAME,  "
			+ "CONST_IRR_AREA_H AS CONST_IRR_SUM from   "
			+ "(select * from public.dblink  "
			+ "('dbname='|| ?2 ||' host='|| ?1 ||' user='|| ?4 || ' password='|| ?5 ,  "
			+ "'select f7k.KHATA_NO,f7k.MARKED,f7k.PIN,f7k.PIN1,f7k.PIN2,f7k.PIN3,f7k.PIN4,f7k.PIN5,f7k.PIN6,f7k.PIN7,f7k.PIN8,f7k.ccode  from  "
			+ "'||?3||'.FORM7_KHATA F7K where ccode='|| ?13 ||' ::text '  "
			+ ")  "
			+ "AS(  "
			+ "KHATA_NO TEXT,  "
			+ "MARKED TEXT,  "
			+ "PIN TEXT,  "
			+ "PIN1 TEXT,  "
			+ "PIN2 TEXT,  "
			+ "PIN3 TEXT,  "
			+ "PIN4 TEXT,  "
			+ "PIN5 TEXT,  "
			+ "PIN6 TEXT,  "
			+ "PIN7 TEXT,  "
			+ "PIN8 TEXT,  "
			+ "ccode text  "
			+ ")  "
			+ "where trim(CCODE) = ?13  "
			+ "AND MARKED <> 'Y'   "
			+ "AND KHATA_NO <> '500001'    "
			+ ")as f7kk,  "
			+ "(  "
			+ "select * from public.dblink  "
			+ "(  "
			+ "'dbname='||?2 ||' host='|| ?1 ||' user='|| ?4 || ' password='|| ?5 ,  "
			+ "'select mc.CROP_NAME,mc.CROP_CODE from '||?3||'.M_CROP_NAME MC'  "
			+ ")  "
			+ "AS(  "
			+ "CROP_NAME TEXT,  "
			+ "CROP_CODE TEXT  "
			+ ")  "
			+ ")  "
			+ "as mcc,  "
			+ "(  "
			+ "select * from public.dblink  "
			//+ "('dbname='||?2 ||' host='|| ?1 ||' user='|| ?4 || ' password='|| ?5 ,  "
			+ "	('dbname='||?2||'crop' ||' host='|| ?1 ||' user='||?4|| ' password='||?5 ,  "
			+ "'select f12.CONST_CROP_CODE,f12.CONST_IRR_AREA_H,f12.NUMERIC_PIN,f12.YEAR_CULTI,F12.CCODE   "
			+ " from '||?3||'.FORM12 F12 where ccode='|| ?13 ||' ::text and YEAR_CULTI='|| ?11 ||' ')  "
			+ "AS  "
			+ "(  "
			+ "CONST_CROP_CODE TEXT,  "
			+ "CONST_IRR_AREA_H numeric(18,2),  "
			+ "NUMERIC_PIN numeric(18),  "
			+ "YEAR_CULTI int,  "
			+ "ccode text  "
			+ ")where trim(CCODE) = ?13  "
			+ "  "
			+ ")as f12cr  "
			+ "WHERE   "
			+ "F7KK.KHATA_NO = TEXT(F12cr.NUMERIC_PIN)   "
			+ "AND F7KK.CCODE = F12cr.CCODE  "
			+ "AND F12cr.CONST_CROP_CODE = MCC.CROP_CODE  "
			+ "AND f12cr.YEAR_CULTI =?11   "
			+ "AND f12cr.CONST_IRR_AREA_H <> '0'   "
			+ "AND CAST(f12cr.CONST_CROP_CODE AS VARCHAR) LIKE '27%'   "
			+ "AND F12cr.CONST_CROP_CODE = MCC.CROP_CODE  "
			+ "AND KHATA_NO NOT IN(  "
			+ "SELECT KHATA_NO FROM  "
			+ "public.dblink  "
			+ "('dbname='||?7 ||' host='|| ?6 ||' user='|| ?9 || ' password='|| ?10 ,  "
			+ " 'SELECT LRD.KHATA_NO,LRD.CCODE,LRD.revenue_year FROM '||?8||'.LAND_REVENUE_DEMAND LRD where ccode='|| ?13 ||' ::text and active_flag=''Y''  '  "
			+ " )AS(  "
			+ " KHATA_NO text,CCODE text, revenue_year text  "
			+ " )  "
			+ " WHERE trim(CCODE)= ?13   and revenue_year = ?12 and khata_no <> '' and khata_no is not NULL )  "
			+ ")AS T1   "
			+ "INNER JOIN  "
			+ "(  "
			+ "SELECT DISTINCT  "
			+ "KHATA_NO,  "
			//+ "STRING_AGG(DISTINCT CONCAT(FNAME, ' ', MNAME, ' ', LNAME), ' | ') AS KHATEDAR_NAMES   "
			+ "STRING_AGG(DISTINCT CONCAT(FNAME, ' ', MNAME, ' ', LNAME,' ',topan_name), ' | ') AS KHATEDAR_NAMES  "
			+ "FROM  "
			+ "public.dblink  "
			+ "('dbname='||?2 ||' host='|| ?1 ||' user='||?4|| ' password='||?5 ,  "
			+ "'select KHATA_NO,FNAME,MNAME,LNAME,ccode,topan_name FROM   "
			+ "'||?3||'.FORM7_KHATA F7K where ccode='|| ?13 ||' ::text ')  "
			+ "AS(KHATA_NO TEXT,FNAME TEXT,MNAME TEXT,LNAME TEXT,ccode text, topan_name text)   "
			+ "WHERE  "
			+ "trim(CCODE) = ?13   "
			+ "AND KHATA_NO NOT IN  "
			+ "(  "
			+ "SELECT KHATA_NO FROM  "
			+ "public.dblink  "
			+ "('dbname='||?7||' host='|| ?6 ||' user='|| ?9 || ' password='||?10 ,  "
			+ " 'SELECT LRD.KHATA_NO,LRD.CCODE,LRD.revenue_year FROM '||?8||'.LAND_REVENUE_DEMAND LRD where ccode='|| ?13 ||' ::text and active_flag=''Y''   '  "
			+ " )AS(  "
			+ " KHATA_NO text,CCODE text, revenue_year text  "
			+ " )  "
			+ " WHERE trim(CCODE)= ?13   and revenue_year = ?12 and khata_no <> '' and khata_no is not NULL )  "
			+ " GROUP BY  "
			+ "KHATA_NO   "
			+ ")  "
			+ "T2   "
			+ "ON (T1.KHATA_NO = T2.KHATA_NO)  "
			+ ")A  "
			+ ""
			+ "", nativeQuery = true)
			public List<Object[]> getLRForEducationalCess(
			String fHost,String fDbName,String fSchemaName,String fUser,String fPassword,
			String eHost,String eDbName,String eSchemaName,String eUser,String ePassword,
			Integer year,String revenueYear,String cCode);

			//const un irr
			@Query(value = "SELECT    "
			+ "trim(KHATA_NO),    "
			+ "KHATEDAR_NAMES,     "
			+ "CROP_CODE,    "
			+ "CROP_NAME,    "
			+ "CONST_UNIRR_SUM     "
			+ "FROM    "
			+ "(    "
			+ "SELECT DISTINCT    "
			+ "t1.KHATA_NO,    "
			+ " KHATEDAR_NAMES,    "
			+ "CROP_CODE,    "
			+ "CROP_NAME,    "
			+ "CONST_UNIRR_SUM     "
			+ "    "
			+ "FROM    "
			+ "(    "
			+ "select     "
			+ "f7kk.KHATA_NO,    "
			+ "mcc.CROP_CODE,    "
			+ "CROP_NAME,    "
			+ "CONST_UNIRR_AREA_H AS CONST_UNIRR_SUM from     "
			+ "(select * from public.dblink    "
			+ "('dbname='||?2 ||' host='|| ?1 ||' user='|| ?4 || ' password='|| ?5 ,    "
			+ "'select f7k.KHATA_NO,f7k.MARKED,f7k.PIN,f7k.PIN1,f7k.PIN2,f7k.PIN3,f7k.PIN4,f7k.PIN5,f7k.PIN6,f7k.PIN7,f7k.PIN8,f7k.ccode from    "
			+ "'||?3||'.FORM7_KHATA F7K where ccode='|| ?13 ||' ::text'    "
			+ ")AS(    "
			+ "KHATA_NO TEXT,    "
			+ "MARKED TEXT,    "
			+ "PIN TEXT,    "
			+ "PIN1 TEXT,    "
			+ "PIN2 TEXT,    "
			+ "PIN3 TEXT,    "
			+ "PIN4 TEXT,    "
			+ "PIN5 TEXT,    "
			+ "PIN6 TEXT,    "
			+ "PIN7 TEXT,    "
			+ "PIN8 TEXT,    "
			+ "ccode text    "
			+ ")    "
			+ "where trim(CCODE) = ?13    "
			+ "AND MARKED <> 'Y'     "
			+ "AND KHATA_NO <> '500001'      "
			+ ")as f7kk,    "
			+ "(    "
			+ "select * from public.dblink    "
			+ "(    "
			+ "'dbname='||?2 ||' host='|| ?1 ||' user='|| ?4 || ' password='|| ?5 ,    "
			+ "'select mc.CROP_NAME,mc.CROP_CODE from '||?3||'.M_CROP_NAME MC'    "
			+ ")    "
			+ "AS(    "
			+ "CROP_NAME TEXT,    "
			+ "CROP_CODE TEXT    "
			+ ")    "
			+ ")    "
			+ "as mcc,    "
			+ "(    "
			+ "select * from public.dblink    "
			//+ "('dbname='||?2||' host='|| ?1 ||' user='|| ?4 || ' password='|| ?5 ,  "
			+ "	('dbname='||?2||'crop' ||' host='|| ?1 ||' user='||?4|| ' password='||?5 ,  "
			+ "'select f12.CONST_CROP_CODE,f12.CONST_UNIRR_AREA_H,f12.NUMERIC_PIN,f12.YEAR_CULTI,F12.CCODE     "
			+ " from '||?3||'.FORM12 F12 where ccode='|| ?13 ||' ::text and YEAR_CULTI='|| ?11 ||' ')    "
			+ "AS    "
			+ "(    "
			+ "CONST_CROP_CODE TEXT,    "
			+ "CONST_UNIRR_AREA_H numeric(18,2),    "
			+ "NUMERIC_PIN numeric(18),    "
			+ "YEAR_CULTI int,    "
			+ "ccode text    "
			+ ")where trim(CCODE) = ?13    "
			+ "    "
			+ ")as f12cr    "
			+ "WHERE     "
			+ "F7KK.KHATA_NO = TEXT(F12cr.NUMERIC_PIN)     "
			+ "AND F7KK.CCODE = F12cr.CCODE   "
			+ "AND F12cr.CONST_CROP_CODE = MCC.CROP_CODE   "
			+ "AND f12cr.YEAR_CULTI =?11     "
			+ "AND f12cr.CONST_UNIRR_AREA_H <> '0'     "
			+ "AND CAST(f12cr.CONST_CROP_CODE AS VARCHAR) LIKE '27%'     "
			+ "    "
			+ "AND KHATA_NO NOT IN(    "
			+ "SELECT KHATA_NO FROM    "
			+ "public.dblink    "
			+ "('dbname='||?7 ||' host='|| ?6 ||' user='|| ?9 || ' password='|| ?10 ,    "
			+ " 'SELECT LRD.KHATA_NO,LRD.CCODE,LRD.revenue_year FROM '||?8||'.LAND_REVENUE_DEMAND LRD where ccode='|| ?13 ||' ::text and active_flag=''Y''   '    "
			+ " )AS(    "
			+ " KHATA_NO text,CCODE text, revenue_year text    "
			+ " )    "
			+ " WHERE trim(CCODE)= ?13   and revenue_year = ?12 and khata_no <> '' and khata_no is not NULL )    "
			+ ")AS T1     "
			+ "INNER JOIN    "
			+ "(    "
			+ "SELECT DISTINCT    "
			+ "KHATA_NO,    "
			//+ "STRING_AGG(DISTINCT CONCAT(FNAME, ' ', MNAME, ' ', LNAME), ' | ') AS KHATEDAR_NAMES     "
			+ "STRING_AGG(DISTINCT CONCAT(FNAME, ' ', MNAME, ' ', LNAME,' ',topan_name), ' | ') AS KHATEDAR_NAMES  "
			+ "FROM    "
			+ "public.dblink    "
			+ "('dbname='||?2 ||' host='|| ?1 ||' user='|| ?4 || ' password='|| ?5 ,    "
			+ "'select KHATA_NO,FNAME,MNAME,LNAME,ccode,topan_name FROM     "
			+ "'||?3||'.FORM7_KHATA F7K where ccode='|| ?13 ||' ::text  ')    "
			+ "AS(KHATA_NO TEXT,FNAME TEXT,MNAME TEXT,LNAME TEXT,ccode text,topan_name text)     "
			+ "WHERE    "
			+ "trim(CCODE) = ?13     "
			+ "AND KHATA_NO NOT IN    "
			+ "(    "
			+ "SELECT KHATA_NO FROM    "
			+ "public.dblink    "
			+ "('dbname='||?7 ||' host='|| ?6 ||' user='|| ?9 || ' password='|| ?10 ,    "
			+ " 'SELECT LRD.KHATA_NO,LRD.CCODE,LRD.revenue_year FROM '||?8||'.LAND_REVENUE_DEMAND LRD where ccode='|| ?13 ||' ::text and active_flag=''Y''  '    "
			+ " )AS(    "
			+ " KHATA_NO text,CCODE text, revenue_year text    "
			+ " )    "
			+ " WHERE trim(CCODE)= ?13    and revenue_year = ?12 and khata_no <> '' and khata_no is not NULL )    "
			+ " GROUP BY    "
			+ "KHATA_NO     "
			+ ")    "
			+ "T2     "
			+ "ON (T1.KHATA_NO = T2.KHATA_NO)    "
			+ ")A"
			+ "", nativeQuery = true)
			public List<Object[]> getLRForEducationalCessConstUnIrr(
			String fHost,String fDbName,String fSchemaName,String fUser,String fPassword,
			String eHost,String eDbName,String eSchemaName,String eUser,String ePassword,
			Integer year,String revenueYear,String cCode);

			//pure irrigated
			@Query(value = "SELECT    "
			+ "trim(KHATA_NO),    "
			+ "KHATEDAR_NAMES,     "
			+ "CROP_CODE,    "
			+ "CROP_NAME,    "
			+ "PURE_IRR_SUM     "
			+ "FROM    "
			+ "(    "
			+ "SELECT DISTINCT    "
			+ "t1.KHATA_NO,    "
			+ "KHATEDAR_NAMES,    "
			+ "CROP_CODE,    "
			+ "CROP_NAME,    "
			+ "PURE_IRR_SUM     "
			+ "FROM    "
			+ "(    "
			+ "select     "
			+ "f7kk.KHATA_NO,    "
			+ "mcc.CROP_CODE,    "
			+ "CROP_NAME,    "
			+ "PURE_IRR_AREA_H AS PURE_IRR_SUM from     "
			+ "(select * from public.dblink    "
			+ "('dbname='||?2 ||' host='|| ?1 ||' user='|| ?4 || ' password='|| ?5 ,    "
			+ "'select f7k.KHATA_NO,f7k.MARKED,f7k.PIN,f7k.PIN1,f7k.PIN2,f7k.PIN3,f7k.PIN4,f7k.PIN5,f7k.PIN6,f7k.PIN7,f7k.PIN8,f7k.ccode from    "
			+ "'||?3||'.FORM7_KHATA F7K where ccode='|| ?13 ||' ::text '    "
			+ ")    "
			+ "AS(    "
			+ "KHATA_NO TEXT,    "
			+ "MARKED TEXT,    "
			+ "PIN TEXT,    "
			+ "PIN1 TEXT,    "
			+ "PIN2 TEXT,    "
			+ "PIN3 TEXT,    "
			+ "PIN4 TEXT,    "
			+ "PIN5 TEXT,    "
			+ "PIN6 TEXT,    "
			+ "PIN7 TEXT,    "
			+ "PIN8 TEXT,    "
			+ "ccode text    "
			+ ")    "
			+ "where trim(CCODE) = ?13    "
			+ "AND MARKED <> 'Y'     "
			+ "AND KHATA_NO <> '500001'      "
			+ ")as f7kk,    "
			+ "(    "
			+ "select * from public.dblink    "
			+ "    "
			+ "(    "
			+ "'dbname='||?2 ||' host='|| ?1 ||' user='|| ?4 || ' password='|| ?5 ,    "
			+ "'select mc.CROP_NAME,mc.CROP_CODE from '||?3||'.M_CROP_NAME MC'    "
			+ ")    "
			+ "AS(    "
			+ "CROP_NAME TEXT,    "
			+ "CROP_CODE TEXT    "
			+ ")    "
			+ ")    "
			+ "as mcc,    "
			+ "(    "
			+ "select * from public.dblink    "
			//+ "('dbname='||?2||' host='|| ?1 ||' user='|| ?4 || ' password='|| ?5 ,  "
			+ "	('dbname='||?2||'crop' ||' host='|| ?1 ||' user='||?4|| ' password='||?5 ,  "
			+ "'select f12.PURE_CROP_CODE,f12.PURE_IRR_AREA_H,f12.NUMERIC_PIN,f12.YEAR_CULTI,F12.CCODE     "
			+ " from '||?3||'.FORM12 F12 where ccode='|| ?13 ||' ::text and YEAR_CULTI='|| ?11 ||' ')    "
			+ "AS    "
			+ "(    "
			+ "PURE_CROP_CODE TEXT,    "
			+ "PURE_IRR_AREA_H numeric(18,2),    "
			+ "NUMERIC_PIN numeric(18),    "
			+ "YEAR_CULTI int,           "
			+ "ccode text    "
			+ ")where trim(CCODE) = ?13    "
			+ "     "
			+ ")as f12cr    "
			+ "WHERE     "
			+ "F7KK.KHATA_NO = TEXT(F12cr.NUMERIC_PIN)     "
			+ "AND F7KK.CCODE = F12cr.CCODE     "
			+ "AND F12cr.PURE_CROP_CODE = MCC.CROP_CODE "
			+ "AND f12cr.YEAR_CULTI =?11    "
			+ "AND f12cr.PURE_IRR_AREA_H <> '0'     "
			+ "AND CAST(f12cr.PURE_CROP_CODE AS VARCHAR) LIKE '27%'     "
			+ "     "
			+ "AND KHATA_NO NOT IN(    "
			+ "SELECT KHATA_NO FROM    "
			+ "public.dblink    "
			+ "('dbname='||?7 ||' host='|| ?6 ||' user='|| ?9 || ' password='|| ?10 ,    "
			+ " 'SELECT LRD.KHATA_NO,LRD.CCODE,LRD.revenue_year FROM '||?8||'.LAND_REVENUE_DEMAND LRD where ccode='|| ?13 ||' ::text  and active_flag=''Y''   '    "
			+ " )AS(    "
			+ " KHATA_NO text,CCODE text, revenue_year text    "
			+ " )    "
			+ " WHERE trim(CCODE)= ?13    and revenue_year = ?12 and khata_no <> '' and khata_no is not NULL )    "
			+ ")AS T1    "
			+ "INNER JOIN    "
			+ "    "
			+ "(    "
			+ "SELECT DISTINCT    "
			+ "KHATA_NO,    "
			//+ "STRING_AGG(DISTINCT CONCAT(FNAME, ' ', MNAME, ' ', LNAME), ' | ') AS KHATEDAR_NAMES     "
			+ "STRING_AGG(DISTINCT CONCAT(FNAME, ' ', MNAME, ' ', LNAME,' ',topan_name), ' | ') AS KHATEDAR_NAMES  "
			+ "FROM    "
			+ "public.dblink    "
			+ "('dbname='||?2 ||' host='|| ?1 ||' user='|| ?4 || ' password='|| ?5 ,    "
			+ "'select KHATA_NO,FNAME,MNAME,LNAME,ccode,topan_name FROM     "
			+ "'||?3||'.FORM7_KHATA F7K where ccode='|| ?13 ||' ::text  ')    "
			+ "AS(KHATA_NO TEXT,FNAME TEXT,MNAME TEXT,LNAME TEXT,ccode text,topan_name text)     "
			+ "WHERE    "
			+ "trim(CCODE) = ?13     "
			+ "AND KHATA_NO NOT IN    "
			+ "(    "
			+ "SELECT KHATA_NO FROM    "
			+ "public.dblink    "
			+ "('dbname='||?7 ||' host='|| ?6 ||' user='|| ?9 || ' password='|| ?10 ,    "
			+ " 'SELECT LRD.KHATA_NO,LRD.CCODE,LRD.revenue_year FROM '||?8||'.LAND_REVENUE_DEMAND LRD where ccode='|| ?13 ||' ::text and active_flag=''Y''    '    "
			+ " )AS(    "
			+ " KHATA_NO text,CCODE text, revenue_year text    "
			+ " )    "
			+ " WHERE trim(CCODE)= ?13    and revenue_year = ?12 and khata_no <> '' and khata_no is not NULL )    "
			+ " GROUP BY    "
			+ "KHATA_NO     "
			+ ")    "
			+ "T2     "
			+ "ON (T1.KHATA_NO = T2.KHATA_NO)    "
			+ ")A    "
			+ " "
			+ "", nativeQuery = true)
			public List<Object[]> getLRForEducationalCessPureIrr(
			String fHost,String fDbName,String fSchemaName,String fUser,String fPassword,
			String eHost,String eDbName,String eSchemaName,String eUser,String ePassword,
			Integer year,String revenueYear,String cCode);

			// pure un-irrigated
			@Query(value = "SELECT    "
			+ "trim(KHATA_NO),    "
			+ "KHATEDAR_NAMES,     "
			+ "CROP_CODE,    "
			+ "CROP_NAME,    "
			+ "PURE_UNIRR_SUM     "
			+ "FROM    "
			+ "(    "
			+ "SELECT DISTINCT    "
			+ "t1.KHATA_NO,    "
			+ "KHATEDAR_NAMES,    "
			+ "CROP_CODE,    "
			+ "CROP_NAME,    "
			+ "PURE_UNIRR_SUM     "
			+ "FROM    "
			+ "(    "
			+ "select     "
			+ "f7kk.KHATA_NO,    "
			+ "mcc.CROP_CODE,    "
			+ "CROP_NAME,    "
			+ "PURE_UNIRR_AREA_H AS PURE_UNIRR_SUM from     "
			+ "(select * from public.dblink    "
			+ "('dbname='||?2 ||' host='|| ?1 ||' user='|| ?4 || ' password='|| ?5 ,    "
			+ "'select f7k.KHATA_NO,f7k.MARKED,f7k.PIN,f7k.PIN1,f7k.PIN2,f7k.PIN3,f7k.PIN4,f7k.PIN5,f7k.PIN6,f7k.PIN7,f7k.PIN8,f7k.ccode from    "
			+ "    "
			+ "'||?3||'.FORM7_KHATA F7K where ccode='|| ?13 ||' ::text  '    "
			+ ")    "
			+ "AS(    "
			+ "KHATA_NO TEXT,    "
			+ "MARKED TEXT,    "
			+ "PIN TEXT,    "
			+ "PIN1 TEXT,    "
			+ "PIN2 TEXT,    "
			+ "PIN3 TEXT,    "
			+ "PIN4 TEXT,    "
			+ "PIN5 TEXT,    "
			+ "PIN6 TEXT,    "
			+ "PIN7 TEXT,    "
			+ "PIN8 TEXT,    "
			+ "ccode text    "
			+ ")    "
			+ "where trim(CCODE) = ?13    "
			+ "AND MARKED <> 'Y'     "
			+ "AND KHATA_NO <> '500001'      "
			+ ")as f7kk,    "
			+ "(    "
			+ "select * from public.dblink    "
			+ "    "
			+ "(    "
			+ "'dbname='||?2 ||' host='|| ?1 ||' user='|| ?4 || ' password='|| ?5 ,    "
			+ "'select mc.CROP_NAME,mc."
			+ "CROP_CODE from '||?3||'.M_CROP_NAME MC'    "
			+ ")    "
			+ "AS(    "
			+ "CROP_NAME TEXT,    "
			+ "CROP_CODE TEXT    "
			+ ")    "
			+ ")    "
			+ "as mcc,    "
			+ "(    "
			+ "select * from public.dblink    "
		//	+ "('dbname='||?2||' host='|| ?1 ||' user='|| ?4 || ' password='|| ?5 ,  "
			+ "	('dbname='||?2||'crop' ||' host='|| ?1 ||' user='||?4|| ' password='||?5 ,  "
			+ "'select f12.PURE_CROP_CODE,f12.PURE_UNIRR_AREA_H,f12.NUMERIC_PIN,f12.YEAR_CULTI,F12.CCODE     "
			+ " from '||?3||'.FORM12 F12 where ccode='|| ?13 ||' ::text  and YEAR_CULTI='|| ?11 ||' ')    "
			+ "AS    "
			+ "(    "
			+ "PURE_CROP_CODE TEXT,    "
			+ "PURE_UNIRR_AREA_H numeric(18,2),    "
			+ "NUMERIC_PIN numeric(18),    "
			+ "YEAR_CULTI int,           "
			+ "ccode text    "
			+ ")where trim(CCODE) = ?13    "
			+ "     "
			+ ")as f12cr    "
			+ "WHERE     "
			+ "F7KK.KHATA_NO = TEXT(F12cr.NUMERIC_PIN)     "
			+ "AND F7KK.CCODE = F12cr.CCODE     "
			+ "AND F12cr.PURE_CROP_CODE = MCC.CROP_CODE "
			+ "AND f12cr.YEAR_CULTI =?11    "
			+ "AND f12cr.PURE_UNIRR_AREA_H <> '0'     "
			+ "AND CAST(f12cr.PURE_CROP_CODE AS VARCHAR) LIKE '27%'     "
			+ "     "
			+ "AND KHATA_NO NOT IN(    "
			+ "SELECT KHATA_NO FROM    "
			+ "public.dblink    "
			+ "('dbname='||?7 ||' host='|| ?6 ||' user='||?9|| ' password='||?10 ,    "
			+ " 'SELECT LRD.KHATA_NO,LRD.CCODE,LRD.revenue_year FROM '||?8||'.LAND_REVENUE_DEMAND LRD where ccode='|| ?13 ||' ::text  and active_flag=''Y'' '  "
			+ " )AS(    "
			+ " KHATA_NO text,CCODE text, revenue_year text    "
			+ " )    "
			+ " WHERE trim(CCODE)= ?13  and revenue_year = ?12 and khata_no <> '' and khata_no is not NULL )    "
			+ ")AS T1    "
			+ "INNER JOIN    "
			+ "    "
			+ "(    "
			+ "SELECT DISTINCT    "
			+ "KHATA_NO,    "
			//+ "STRING_AGG(DISTINCT CONCAT(FNAME, ' ', MNAME, ' ', LNAME), ' | ') AS KHATEDAR_NAMES     "
			+ "STRING_AGG(DISTINCT CONCAT(FNAME, ' ', MNAME, ' ', LNAME,' ',topan_name), ' | ') AS KHATEDAR_NAMES  "
			+ "FROM    "
			+ "public.dblink    "
			+ "('dbname='||?2 ||' host='|| ?1 ||' user='||?4|| ' password='||?5 ,    "
			+ "'select KHATA_NO,FNAME,MNAME,LNAME,ccode,topan_name FROM     "
			+ "'||?3||'.FORM7_KHATA F7K where ccode='|| ?13 ||' ::text  ')    "
			+ "AS(KHATA_NO TEXT,FNAME TEXT,MNAME TEXT,LNAME TEXT,ccode text,topan_name text)     "
			+ "WHERE    "
			+ "trim(CCODE) = ?13     "
			+ "AND KHATA_NO NOT IN    "
			+ "(    "
			+ "SELECT KHATA_NO FROM    "
			+ "public.dblink    "
			+ "('dbname='||?7 ||' host='|| ?6 ||' user='||?9|| ' password='||?10 ,    "
			+ " 'SELECT LRD.KHATA_NO,LRD.CCODE,LRD.revenue_year FROM '||?8||'.LAND_REVENUE_DEMAND LRD where ccode='|| ?13 ||' ::text and active_flag=''Y'' '    "
			+ " )AS(    "
			+ " KHATA_NO text,CCODE text, revenue_year text    "
			+ " )    "
			+ " WHERE trim(CCODE)= ?13  and revenue_year = ?12 and khata_no <> '' and khata_no is not NULL )    "
			+ " GROUP BY    "
			+ "KHATA_NO     "
			+ ")    "
			+ "T2     "
			+ "ON (T1.KHATA_NO = T2.KHATA_NO)    "
			+ ")A"
			+ "", nativeQuery = true)
			public List<Object[]> getLRForEducationalCessPureUnIrr(
			String fHost,String fDbName,String fSchemaName,String fUser,String fPassword,
			String eHost,String eDbName,String eSchemaName,String eUser,String ePassword,
			Integer year,String revenueYear,String cCode);


	/* Commented on 02-09-2024 By Tushar For new function from form12 
	 * Tested and approved in UAT
	 * @Query(value = "select  "
			+ "trim(khata_no), "
			+ "STRING_AGG(DISTINCT CONCAT(FNAME, ' ', MNAME, ' ', LNAME), ' | ') AS KHATEDAR_NAMES, "
			+ "sum(bagayat_area_h) "
			+ "from public.dblink "
			+ "( "
			+ "'dbname='||?2||' host='||?1||' port=' || ?13 ||' user='||?4||' password='||?5, "
			+ "'select  "
			+ "f7.ccode,f7.pin,f7.pin1,f7.pin2,f7.pin3,f7.pin4,f7.pin5,f7.pin6,f7.pin7,f7.pin8,f7k.khata_no,f7k.fname,f7k.mname,f7k.lname, "
			+ "f7a.bagayat_area_h,bagayat_area_r,bagayat_area_sqm,f7k.marked  "
			+ "from '||?3||'.form7 f7,'||?3||'.form7_area f7a,'||?3||'.form7_khata f7k "
			+ "where "
			+ "f7.ccode=f7k.ccode "
			+ "and f7.pin=f7k.pin "
			+ "and f7.pin1=f7k.pin1 "
			+ "and f7.pin2=f7k.pin2 "
			+ "and f7.pin3=f7k.pin3	 "
			+ "and f7.pin4=f7k.pin4 "
			+ "and f7.pin5=f7k.pin5 "
			+ "and f7.pin6=f7k.pin6 "
			+ "and f7.pin7=f7k.pin7 "
			+ "and f7.pin8=f7k.pin8 "
			+ "and f7.ccode=f7a.ccode "
			+ "and f7.pin=f7a.pin "
			+ "and f7.pin1=f7a.pin1 "
			+ "and f7.pin2=f7a.pin2 "
			+ "and f7.pin3=f7a.pin3	 "
			+ "and f7.pin4=f7a.pin4 "
			+ "and f7.pin5=f7a.pin5 "
			+ "and f7.pin6=f7a.pin6 "
			+ "and f7.pin7=f7a.pin7 "
			+ "and f7.pin8=f7a.pin8 "
			+ "AND F7K.CCODE= '|| ?12 ||'::text		"
			+ "AND F7.CCODE=  '|| ?12 ||'::text	 		"
			+ "AND F7A.CCODE=  '|| ?12 ||'::text 		"
			+ "' "
			+ ") "
			+ "AS "
			+ "( "
			+ "ccode text,pin text,pin1 text,pin2 text,pin3 text,pin4 text, "
			+ "pin5 text,pin6 text,pin7 text,pin8 text,khata_no text,fname text, "
			+ "mname text,lname text,bagayat_area_h numeric,bagayat_area_r numeric,bagayat_area_sqm numeric, "
			+ "marked character(1) "
			+ ") "
			+ "where  "
			+ "trim(ccode)=?12 AND MARKED <> 'Y' and khata_no <> '500001' and bagayat_area_h <> 0 "
			+ "AND KHATA_NO NOT IN "
			+ "( "
			+ "SELECT KHATA_NO FROM "
			+ "public.dblink "
			+ "('dbname='||?7 ||' host='|| ?6 ||' user='||?9|| ' password='||?10 , "
			+ " 'SELECT LRD.KHATA_NO,LRD.CCODE,LRD.revenue_year FROM '||?8||'.LAND_REVENUE_DEMAND LRD' "
			+ ") "
			+ "AS "
			+ "( "
			+ " KHATA_NO text,CCODE text, revenue_year text "
			+ ") "
			+ "WHERE trim(CCODE)= ?12  and revenue_year = ?11 and khata_no <> '' and khata_no is not NULL  "
			+ ")"
			+ "group by khata_no "
			+ "order by cast(khata_no as integer) "
			+ "", nativeQuery = true)
	public List<Object[]> getLRForEmployeeGuaranteeScheme(
			String fAugHost,String fDbName,String fSchemaName,String fUser,String fPassword,
			String eHost,String eDbName,String eSchemaName,String eUser,String ePassword,
			String revenueYear,String cCode,String fAugPort); */
			
			@Query(value = "SELECT  " 
					+ "trim(KHATA_NO),  " 
					+ "KHATEDAR_NAMES ,  " 
					+ "SUM(CONST_IRR_SUM+mix_irr_area_sum+pure_irr_area_sum)  as bagayat_area_h " 
					+ "FROM  " 
					+ "(  " 
					+ "	SELECT DISTINCT "  
					+ "	t1.KHATA_NO,  " 
					+ "	KHATEDAR_NAMES, "  
					+ "	CONST_IRR_SUM ," 
					+ "	mix_irr_area_sum," 
					+ "	pure_irr_area_sum  " 
					+ "	FROM  " 
					+ " (  " 
					+ "	select "   
					+ "	f7kk.KHATA_NO, "    
					//+ "	STRING_AGG(DISTINCT CONCAT(f7kk.FNAME, ' ', f7kk.MNAME, ' ', f7kk.LNAME), ' | ') AS KHATEDAR_NAMES, " 
					+ "STRING_AGG(DISTINCT CONCAT(f7kk.FNAME, ' ', f7kk.MNAME, ' ', f7kk.LNAME,' ',f7kk.topan_name), ' | ') AS KHATEDAR_NAMES,  "
					+ "	f12cr.CONST_IRR_AREA_H AS CONST_IRR_SUM , " 
					+ "	f12cr.mix_irr_area_h AS mix_irr_area_sum , " 
					+ "	f12cr.pure_irr_area_h AS pure_irr_area_sum " 
					+ "	from   " 
					+ "	(  select * from public.dblink  " 
					+ "	('dbname='|| ?2 ||' host='|| ?1 ||' user='|| ?4 || ' password='|| ?5 , "  
					+ "	'select f7k.KHATA_NO,f7k.MARKED,f7k.PIN,f7k.PIN1,f7k.PIN2,f7k.PIN3,f7k.PIN4,f7k.PIN5,f7k.PIN6,f7k.PIN7,f7k.PIN8,f7k.ccode,FNAME,MNAME,LNAME,topan_name  from "  
					+ "	'|| ?3 ||'.FORM7_KHATA F7K where ccode='|| ?12 ||' ::text  '  " 
					+ "	)  " 
					+ "	AS(  " 
					+ "	KHATA_NO TEXT,"   
					+ "	MARKED TEXT,  " 
					+ "	PIN TEXT,  " 
					+ "	PIN1 TEXT,  " 
					+ "	PIN2 TEXT,  " 
					+ "	PIN3 TEXT,  " 
					+ "	PIN4 TEXT,  " 
					+ "	PIN5 TEXT,  " 
					+ "	PIN6 TEXT,  " 
					+ "	PIN7 TEXT,  " 
					+ "	PIN8 TEXT,  " 
					+ "	ccode text,FNAME TEXT,MNAME TEXT,LNAME TEXT,topan_name TEXT  " 
					+ "	)  " 
					+ "	where trim(CCODE) = ?12  "  
					+ "	AND MARKED <> 'Y'   " 
					+ "	AND KHATA_NO <> '500001' "    
					+ "	)as f7kk  left join " 
					+ "	(  " 
					+ "	select * from public.dblink  " 
					+ "	(" 
					+ "	'dbname='||?2||'crop' ||' host='|| ?1 ||' user='||?4|| ' password='||?5 ,  "
					+ "	'select f12.CONST_CROP_CODE,f12.CONST_IRR_AREA_H,f12.NUMERIC_PIN,f12.YEAR_CULTI,F12.CCODE , f12.mix_irr_area_h, f12.pure_irr_area_h "  
					+ "	from '|| ?3 ||'.FORM12 F12 where ccode='|| ?12 ||' ::text   and YEAR_CULTI='|| ?13 ||' '" 
					+ "	)  " 
					+ "	AS (  " 
					+ "	CONST_CROP_CODE TEXT, "  
					+ "	CONST_IRR_AREA_H numeric(18,2),"   
					+ "	NUMERIC_PIN numeric(18),  " 
					+ "	YEAR_CULTI INTEGER,  " 
					+ "	ccode text , " 
					+ "	mix_irr_area_h numeric(18,2),  " 
					+ "	pure_irr_area_h numeric(18,2)" 
					+ "	)" 
					+ "	where trim(CCODE) = ?12   " 
					+ "	)as f12cr  		    " 
					+ "	on  F7KK.CCODE = F12cr.CCODE  " 
					+ "	AND F7KK.KHATA_NO = TEXT(F12cr.NUMERIC_PIN) " 
					+ "	WHERE    	 " 
					+ "	f12cr.YEAR_CULTI = ?13  "    
					//+ "	AND f12cr.CONST_IRR_AREA_H <> '0' "      
					+ "	AND F7KK.KHATA_NO  NOT IN " 
					+ "	(	" 
					+ "	SELECT KHATA_NO FROM "   
					+ "	public.dblink  " 
					+ "	('dbname='|| ?7 ||' host='|| ?6 ||' user='|| ?9 || ' password='|| ?10 , "  
					+ "	'SELECT LRD.KHATA_NO,LRD.CCODE,LRD.revenue_year FROM '|| ?8 ||'.LAND_REVENUE_DEMAND LRD where ccode='|| ?12 ||'::text  and active_flag=''Y'' ' "  
					+ "	)AS(  " 
					+ "	KHATA_NO text,CCODE text, revenue_year text  " 
					+ "	)  " 
					+ "	WHERE trim(CCODE)= ?12 and revenue_year = ?11 and khata_no <> '' and khata_no is not NULL " 
					+ "	) 	" 
					+ "	group by f7kk.KHATA_NO ,CONST_IRR_SUM, mix_irr_area_sum, pure_irr_area_sum " 
					+ "	)AS T1   	 " 
					+ ") A  group by khata_no, KHATEDAR_NAMES" 
					+ "", nativeQuery = true)
					public List<Object[]> getLRForEmployeeGuaranteeScheme(
								String fAugHost,String fDbName,String fSchemaName,String fUser,String fPassword,
								String eHost,String eDbName,String eSchemaName,String eUser,String ePassword,
								String revenueYear,String cCode, int cultiYear);
	
	@Query(value="SELECT *     "
			+ "FROM   "
			+ "public.dblink( "
			+ "'dbname='||?2||' host='|| ?1 ||' user='||?4|| ' password='|| ?5, "
			+ "'select * from '||?3||'.land_revenue_demand') "
			+ "AS "
			+ "( "
			+ " id bigint , "
			+ "    version numeric, "
			+ "    created_user_id bigint, "
			+ "    create_dt_tm timestamp without time zone, "
			+ "    update_userid bigint, "
			+ "    update_dt_tm timestamp without time zone, "
			+ "    revenue_year text  , "
			+ "    district_code integer, "
			+ "    taluka_code integer, "
			+ "    ccode text  , "
			+ "    khata_no text  , "
			+ "    khata_owner_name text  , "
			+ "    area numeric, "
			+ "    assessment numeric, "
			+ "    suvery_hissa text  , "
			+ "    area_unit text  , "
			+ "    jm_bindumala numeric, "
			+ "    zp_bindumala numeric, "
			+ "    gp_bindumala numeric, "
			+ "    jm_dumala numeric, "
			+ "    zp_dumala numeric, "
			+ "    gp_dumala numeric, "
			+ "    jm_akrushik numeric, "
			+ "    zp_akrushik numeric, "
			+ "    gp_akrushik numeric, "
			+ "    jm_sankirn numeric, "
			+ "    zp_sankirn numeric, "
			+ "    gp_sankirn numeric, "
			+ "    jm_vajasut numeric, "
			+ "    zp_vajasut numeric, "
			+ "    gp_vajasut numeric, "
			+ "    addl_land_revenue numeric, "
			+ "    educational_cess numeric, "
			+ "    addl_educational_cess numeric, "
			+ "    employee_guarantee_scheme numeric, "
			+ "    pre_year_pending_jm numeric, "
			+ "    pre_year_pending_zp numeric, "
			+ "    pre_year_pending_gp numeric, "
			+ "    pre_year_pending_addl_land_revenue numeric, "
			+ "    pre_year_pending_educational_cess numeric, "
			+ "    pre_year_pending_addl_educational_cess numeric, "
			+ "    pre_year_pending_employee_guarantee_scheme numeric, "
			+ "    miscellaneous_amount numeric, "
			+ "    net_amount numeric, "
			+ "    net_received numeric, "
			+ "    net_pending numeric, "
			+ "    date_of_notice timestamp without time zone, "
			+ "    notice_amount numeric, "
			+ "    receipt_no text  , "
			+ "    receipt_date text  , "
			+ "    mode_of_payment text  , "
			+ "    dd_name text  , "
			+ "    dd_branch text  , "
			+ "    dd_number text  , "
			+ "    dd_date date, "
			+ "    dd_amount numeric, "
			+ "    is_money_deposited_bank character(1)  , "
			+ "    challan_id bigint, "
			+ "    active_flag character(1)  , "
			+ "    land_revenue_form17_id bigint, "
			+ "    survey_hissa text  , "
			+ "    cheque_amount numeric(19,2), "
			+ "    cheque_branch character varying(255)  , "
			+ "    cheque_date timestamp without time zone, "
			+ "    cheque_name character varying(255)  , "
			+ "    cheque_number character varying(255)  , "
			+ "    pre_year_notice_fee numeric(19,2), "
			+ "    pre_year_pending_na_cess numeric(19,2), "
			+ "    pre_year_sankirn_jm_with numeric(19,2), "
			+ "    pre_year_sankirn_jm_without numeric(19,2), "
			+ "	makhtakhata_no text, "
			//+ "	remarks text, "
			+ "	agau_vasuli_remarks text "
			+ "    "
			+ ")  "
			+ " "
			+ "WHERE  revenue_year = ?6  "
			+ "AND trim(ccode) = ?7 "
			+ "AND khata_no IN  "
			+ "( "
			+ "SELECT khata_no     "
			+ "FROM public.dblink( "
			+ "'dbname='|| ?2 ||' host='|| ?1 ||' user='||?4|| ' password='|| ?5, "
			+ "'select khata_no,revenue_year,ccode,active_flag from '||?3||'.land_revenue_demand' "
			+ ")AS "
			+ "( "
			+ "khata_no text,revenue_year text,ccode text,active_flag text "
			+ ")  "
			+ "WHERE revenue_year =?6 "
			+ "AND trim(ccode) = ?7    "
			+ "AND active_flag = ?8 "
			+ ")   "
			+ "order by cast(khata_no  as integer) asc  "
			+ "",nativeQuery=true)
	public List<LandRevenueDemand> getDetailByinps(
			String ehost,String edbName,String eschemaName,String euser,String epassword,
		    String revenueYear,String cCode,Character activeFlag
		    );

	@Query(value="select    "
			+ "khatano,   "
			+ "netreceived,   "
			+ "netamount,    "
			+ "NetReceiveAmountwithSankirn,   "
			+ "demmandgeneratedkhatano   "
			+ "from    "
			+ "(select      "
			+ "sum(net_amount) as netamount,      "
			+ "count(distinct khata_no) as demmandgeneratedkhatano,   "
			+ "ccode,   "
			+ "revenue_year   "
			+ "from       "
			+ "public.dblink(        "
			+ "'dbname='|| ?2 ||' host='|| ?1  ||' user='|| ?4 || ' password='|| ?5 ,        "
			+ "'SELECT        "
			+ "khata_no,       "
			+ "net_amount,        "
			+ "net_received,         "
			+ "ccode,        "
			+ "revenue_year,       "
			+ "active_flag       "
			+ "from '|| ?3 ||'.land_revenue_demand where khata_owner_name is not null ')         "
			+ "AS demand(khata_no text,net_amount numeric,net_received numeric,ccode text,revenue_year text,active_flag character(1))        "
			+ "where active_flag='Y'       "
			+ "group by ccode,revenue_year   "
			+ ")t1   "
			+ "left join   "
			+ "(   "
			+ "select sum(net_amount) as netreceived,   "
			+ "count(net_amount) as khatano,  "
			+ "ccode,     "
			+ "sum(zp_sankirn+gp_sankirn+jm_sankirn+notice_amount) as NetReceiveAmountwithSankirn  "
			+ "from       "
			+ "public.dblink(        "
			+ "'dbname='|| ?2 ||' host='|| ?1  ||' user='|| ?4 || ' password='|| ?5 ,        "
			+ "'SELECT   "
			+ "net_amount,   "
			+ "ccode,   "
			+ "mode_of_payment,   "
			+ "active_flag,   "
			+ "revenue_year,  "
			+ "zp_sankirn ,     "
			+ "gp_sankirn ,     "
			+ "jm_sankirn ,  "
			+ "notice_amount , "
			+ "receipt_no  "
			+ "from '|| ?3 ||'.land_revenue_demand')         "
			+ "AS demand(net_amount numeric,ccode text,mode_of_payment text,active_flag character(1),revenue_year text,  "
			+ "zp_sankirn numeric,gp_sankirn numeric,jm_sankirn numeric,notice_amount numeric,receipt_no text)    "
			//+ "where active_flag='Y' and mode_of_payment is not null group by ccode   "
			+ "where active_flag='Y' and receipt_no is not null and revenue_year = ?7 group by ccode   "
			+ ")   "
			+ "t2    "
			+ "on(t1.ccode=t2.ccode)   "
			+ "where  trim(t1.ccode) = ?6 and revenue_year = ?7  "
			+ ""
			+ "",nativeQuery=true)
	public List<Object[]> getDashBoardTotalDemandDetails(String ehost,String edbName,String eschemaName,String euser,String epassword, String cCode,String revenueYear );

//	@Query(value=
//			"select  "
//			+ "count(distinct khata_no) as khataNo, "
//			+ "count( distinct pin||'/' ||  pin1 || '/'|| pin2 || '/'|| pin3 || '/' || pin4 || '/' || pin5 || '/' || pin6 || '/' || pin7 || '/' || pin8) as pin "
//			+ "from     "
//			+ "public.dblink(    "
//			+ "'dbname='|| ?2 ||' host='|| ?1 ||' user='|| ?4 || ' password='|| ?5 ,    "
//			+ "'SELECT f7k.khata_no,f7k.MARKED,f7a.ccode,f7a.pin,f7a.pin1,f7a.pin2,f7a.pin3,f7a.pin4,f7a.pin5,f7a.pin6,f7a.pin7,f7a.pin8   "
//			+ "from    "
//			+ "' || ?3 ||'.form7_area f7a,   "
//			+ "' || ?3 ||'.form7_khata f7k    "
//			+ "where    "
//			+ "f7a.ccode=f7k.ccode   "
//			+ "and f7a.pin=f7k.pin   "
//			+ "and f7a.pin1=f7k.pin1   "
//			+ "and f7a.pin2=f7k.pin2   "
//			+ "and f7a.pin3=f7k.pin3   "
//			+ "and f7a.pin4=f7k.pin4   "
//			+ "and f7a.pin5=f7k.pin5   "
//			+ "and f7a.pin6=f7k.pin6   "
//			+ "and f7a.pin7=f7k.pin7   "
//			+ "and f7a.pin8=f7k.pin8')   "
//			+ "AS khata(khata_no text,MARKED character(1),ccode text,pin text,pin1 text,pin2 text,pin3 text,pin4 text,pin5 text,pin6 text,pin7 text,pin8 text )    "
//			+ "where trim(ccode) = ?6  "
//			+ "AND CONCAT(PIN, '/', PIN1, '/', PIN2, '/', PIN3, '/', PIN4, '/', PIN5, '/', PIN6, '/', PIN7, '/', PIN8) not in   "
//			+ "(   "
//			+ "select CONCAT(PIN, '/', PIN1, '/', PIN2, '/', PIN3, '/', PIN4, '/', PIN5, '/', PIN6, '/', PIN7, '/', PIN8)   "
//			+ "from public.dblink   "
//			+ "(   "
//			+ "'dbname='|| ?2 ||' host='|| ?1 ||' user='|| ?4  || ' password='|| ?5 ,   "
//			+ "'select pin,pin1,pin2,pin3,pin4,pin5,pin6,pin7,pin8,ccode,tenure_code,tenure_sub_code from '|| ?3 ||'.form7_khata f7k '   "
//			+ ")   "
//			+ "AS   "
//			+ "(   "
//			+ "pin text,pin1 text,pin2 text,pin3 text,pin4 text,pin5 text,pin6 text,pin7 text,pin8 text,ccode text "
//			+ ")   "
//			+ "where trim(ccode)= ?6 AND KHATA_NO = '500001' "
//			+ ")   "
//			+ "AND KHATA_NO <> '500001' and MARKED <> 'Y'  "
//			+ ""
//			+ "",nativeQuery=true)
//	public List<Object[]> getDashBoardKhataNoDetails(String fhost,String fdbName,String fschemaName,String fuser,String fpassword, String cCode);
 
	
	
	//landrevenuCount
	@Query( value = "select (SELECT count(distinct khata_no)  FROM "
			+" public.dblink('dbname='|| ?2 ||' host='|| ?1 ||' port=5432 user='|| ?4 ||' password='|| ?5 , "
			+ "'SELECT lrd.khata_no,revenue_year, ccode "
			+ "FROM '  || ?3 ||'.land_revenue_demand lrd where active_flag=''Y'':: text and khata_no is not null  "
			+ "  ') "
			+ "AS data (khata_no text, revenue_year text , ccode text) where revenue_year=?7 and  ccode= ?6 ) +(select count(distinct  khata_no)  FROM "
			+ "public.dblink('dbname='|| ?2 || ' host='|| ?1 || ' port=5432  user='|| ?4 || ' password='|| ?5 , "
			+ "'SELECT o.khata_no "
			+ "FROM '|| ?3 || '.echawdi_khata_merge_dtl o where ccode= '' ?6 '' :: text  and active_flag=''Y'' ')"
			+ "AS data (khata_no text)) - (select count(distinct  khata_no)  FROM "
			+ "public.dblink('dbname='|| ?2 || ' host='|| ?1 || ' port=5432  user='|| ?4 || ' password='|| ?5 ,"
			+ "'SELECT o.khata_no "
			+ "FROM '|| ?3 || '.echawdi_khata_merge o where ccode= '' ?6  '' :: text  and active_flag=''Y'' ') "
			+ "AS data (khata_no text)) AS result  "
			+"",nativeQuery = true )
			public List<Object[]> lrdData(String ehost,String edbName,String eschemaName,String euser,String epassword, String cCode,String revenueYear );
	
			@Query( value = "select ( (SELECT count(distinct khata_no)  FROM "
					+" public.dblink('dbname='|| ?2 ||' host='|| ?1 ||' port=5432 user='|| ?4 ||' password='|| ?5 , "
					+ "'SELECT lrd.khata_no,revenue_year, ccode "
					+ "FROM '  || ?3 ||'.land_revenue_demand lrd where khata_owner_name is not null and active_flag=''Y'':: text and khata_no is not null  "
					+ "  ') "
					+ "AS data (khata_no text, revenue_year text , ccode text) where revenue_year=?7 and  ccode= ?6 ) + (select count(distinct  khata_no)  FROM "
					+ "public.dblink('dbname='|| ?2 || ' host='|| ?1 || ' port=5432  user='|| ?4 || ' password='|| ?5 , "
					+ "'SELECT o.khata_no,ccode "
					+ "FROM '|| ?3 || '.echawdi_khata_merge_dtl o where   active_flag=''Y'' ')"
					+ "AS data (khata_no text,ccode text) where   ccode= ?6 ) - (select count(distinct  khata_no)  FROM "
					+ "public.dblink('dbname='|| ?2 || ' host='|| ?1 || ' port=5432  user='|| ?4 || ' password='|| ?5 ,"
					+ "'SELECT o.khata_no,ccode "
					+ "FROM '|| ?3 || '.echawdi_khata_merge o where   active_flag=''Y'' ') "
					+ "AS data (khata_no text,ccode text) where   ccode= ?6)   ) "
					+"",nativeQuery = true )
					public List<Object[]>  lrdDataUpdated(String ehost,String edbName,String eschemaName,String euser,String epassword, String cCode,String revenueYear );
			
					
					
					@Query( value = "select ( (SELECT count(distinct khata_no)  FROM "
							+" public.dblink('dbname='|| ?2 ||' host='|| ?1 ||' port=5432 user='|| ?4 ||' password='|| ?5 , "
							+ "'SELECT lrd.khata_no,revenue_year, ccode "
							+ "FROM '  || ?3 ||'.land_revenue_demand lrd where khata_owner_name is not null and active_flag=''Y'':: text and khata_no is not null  "
							+ "  ') "
							+ "AS data (khata_no text, revenue_year text , ccode text) where revenue_year=?7 and  ccode= ?6 ) + (select count(distinct  khata_no)  FROM "
							+ "public.dblink('dbname='|| ?2 || ' host='|| ?1 || ' port=5432  user='|| ?4 || ' password='|| ?5 , "
							+ "'SELECT o.khata_no,ccode "
							+ "FROM '|| ?3 || '.echawdi_khata_merge_dtl o where   active_flag=''Y'' ')"
							+ "AS data (khata_no text,ccode text) where   ccode= ?6 ) - (select count(distinct  khata_no)  FROM "
							+ "public.dblink('dbname='|| ?2 || ' host='|| ?1 || ' port=5432  user='|| ?4 || ' password='|| ?5 ,"
							+ "'SELECT o.khata_no,ccode "
							+ "FROM '|| ?3 || '.echawdi_khata_merge o where   active_flag=''Y'' ') "
							+ "AS data (khata_no text,ccode text) where   ccode= ?6)   ) "
							+"",nativeQuery = true )
  /*public List<Object[]>*/ Long lrdDataUpdatede(
							String ehost,
							String edbName,
							String eschemaName,
							String euser,
							String epassword,
							String cCode,
							String revenueYear );
					
					
					

					
	
					
					@Query( value = "select (select ((a.lrdkhata + b.echkhrgdtl)-c.khamrgcount) as tot  from (SELECT count(distinct khata_no)  as lrdkhata,ccode  FROM "
							+" public.dblink('dbname='|| ?2 ||' host='|| ?1 ||' port=5432 user='|| ?4 ||' password='|| ?5 , "
							+ "'SELECT lrd.khata_no,revenue_year, ccode "
							+ "FROM '  || ?3 ||'.land_revenue_demand lrd where khata_owner_name is not null and active_flag=''Y'':: text and khata_no is not null  "
							+ "  ') "
							+ "AS data (khata_no text, revenue_year text , ccode text) where revenue_year=?7 and  ccode= ?6 group by ccode ) a inner join  (select count(distinct  khata_no) as echkhrgdtl, ccode  FROM "
							+ "public.dblink('dbname='|| ?2 || ' host='|| ?1 || ' port=5432  user='|| ?4 || ' password='|| ?5 , "
							+ "'SELECT o.khata_no,ccode "
							+ "FROM '|| ?3 || '.echawdi_khata_merge_dtl o  where  o.revenue_year=''?7'' and   active_flag=''Y'' ')"
							+ "AS data (khata_no text,ccode text) where   ccode= ?6 group by ccode ) b  on a.ccode=b.ccode inner join  (select count(distinct  khata_no) as khamrgcount, ccode  FROM "
							+ "public.dblink('dbname='|| ?2 || ' host='|| ?1 || ' port=5432  user='|| ?4 || ' password='|| ?5 ,"
							+ "'SELECT oo.khata_no,ccode "
							+ "FROM '|| ?3 || '.echawdi_khata_merge oo where oo.revenue_year=''?7'' and    oo.active_flag=''Y'' ') "
							+ "AS data (khata_no text,ccode text) where   ccode= ?6 group by ccode) c on a.ccode=b.ccode ) as newlygendemandcount   "
							+"",nativeQuery = true )
							/*public List<Object[]>*/ Long  lrdDataUpdatedd(String ehost,String edbName,String eschemaName,String euser,String epassword, String cCode,String revenueYear );
				
					@Query(value =
					        "WITH lrd_cnt AS ( " +
					        "   SELECT COUNT(DISTINCT khata_no) AS cnt " +
					        "   FROM public.dblink( " +
					        "       'dbname='|| ?2 || ' host='|| ?1 || ' port=5432 user='|| ?4 || ' password='|| ?5, " +
					        "       'SELECT lrd.khata_no, revenue_year, ccode " +
					        "        FROM ' || ?3 || '.land_revenue_demand lrd " +
					        "        WHERE khata_owner_name IS NOT NULL " +
					        "          AND active_flag = ''Y'' " +
					        "          AND khata_no IS NOT NULL' " +
					        "   ) AS data(khata_no TEXT, revenue_year TEXT, ccode TEXT) " +
					        "   WHERE revenue_year = ?7 AND ccode = ?6 " +
					        "), " +
					        "merge_dtl_cnt AS ( " +
					        "   SELECT COUNT(DISTINCT khata_no) AS cnt " +
					        "   FROM public.dblink( " +
					        "       'dbname='|| ?2 || ' host='|| ?1 || ' port=5432 user='|| ?4 || ' password='|| ?5, " +
					        "       'SELECT o.khata_no, ccode " +
					        "        FROM ' || ?3 || '.echawdi_khata_merge_dtl o " +
					        "        WHERE active_flag = ''Y''' " +
					        "   ) AS data(khata_no TEXT, ccode TEXT) " +
					        "   WHERE ccode = ?6 " +
					        "), " +
					        "merge_cnt AS ( " +
					        "   SELECT COUNT(DISTINCT khata_no) AS cnt " +
					        "   FROM public.dblink( " +
					        "       'dbname='|| ?2 || ' host='|| ?1 || ' port=5432 user='|| ?4 || ' password='|| ?5, " +
					        "       'SELECT o.khata_no, ccode " +
					        "        FROM ' || ?3 || '.echawdi_khata_merge o " +
					        "        WHERE active_flag = ''Y''' " +
					        "   ) AS data(khata_no TEXT, ccode TEXT) " +
					        "   WHERE ccode = ?6 " +
					        ") " +
					        "SELECT " +
					        "   lrd_cnt.cnt + merge_dtl_cnt.cnt - merge_cnt.cnt AS total_count " +
					        "FROM lrd_cnt " +
					        "CROSS JOIN merge_dtl_cnt " +
					        "CROSS JOIN merge_cnt",
					        nativeQuery = true)
					Long lrdDataUpdatedddd(
					        String ehost,
					        String edbName,
					        String eschemaName,
					        String euser,
					        String epassword,
					        String cCode,
					        String revenueYear
					);

						
			
	//khata 7/12 sankhya 
	@Query(value="  select khata_count, sathbara_count from  "
			//+ "	  public.dblink ('dbname='||'echmst'||' host='||'10.10.248.108'||' port=5432  user='||'postgres'|| ' password='||'jduri%D^7f9gf' ,    "
			+ "	  public.dblink ('dbname='||'echmst'||' host='||'10.48.65.110'||' port=5432  user='||'postgres'|| ' password='||'jduri%D^7f9gf' ,    "
			+ "	  'select khata_count, sathbara_count, ccode, taluka_code  from '|| 'public' || '.e_lgd_mapper f')  "
			+ "	  AS F7 (khata_count integer, sathbara_count integer, ccode character varying, taluka_code character(2) )   "
			+ "	  where ccode = ?1 and taluka_code=?2 "
		    + "",nativeQuery=true)
	public List<Object[]> getDashBoardKhataNoDetails(
			String cCode, String talukacode);
	
	
	@Query(value=
			"select  "
			+ "count(distinct khata_no) as khataNo, "
			+ "count( distinct pin||'/' ||  pin1 || '/'|| pin2 || '/'|| pin3 || '/' || pin4 || '/' || pin5 || '/' || pin6 || '/' || pin7 || '/' || pin8) as pin "
			+ "from     "
			+ "public.dblink(    "
			+ "'dbname='|| ?2 ||' host='|| ?1 ||' port='|| ?7 ||' user='|| ?4 || ' password='|| ?5 ,    "
			+ "'SELECT f7k.khata_no,f7k.MARKED,f7a.ccode,f7a.pin,f7a.pin1,f7a.pin2,f7a.pin3,f7a.pin4,f7a.pin5,f7a.pin6,f7a.pin7,f7a.pin8   "
			+ "from    "
			+ "' || ?3 ||'.form7_area f7a,   "
			+ "' || ?3 ||'.form7_khata f7k    "
			+ "where    "
			+ "f7a.ccode=f7k.ccode   "
			+ "and f7a.pin=f7k.pin   "
			+ "and f7a.pin1=f7k.pin1   "
			+ "and f7a.pin2=f7k.pin2   "
			+ "and f7a.pin3=f7k.pin3   "
			+ "and f7a.pin4=f7k.pin4   "
			+ "and f7a.pin5=f7k.pin5   "
			+ "and f7a.pin6=f7k.pin6   "
			+ "and f7a.pin7=f7k.pin7   "
			+ "and f7a.pin8=f7k.pin8')   "
			+ "AS khata(khata_no text,MARKED character(1),ccode text,pin text,pin1 text,pin2 text,pin3 text,pin4 text,pin5 text,pin6 text,pin7 text,pin8 text )    "
			+ "where trim(ccode) = ?6  "
			+ "AND CONCAT(PIN, '/', PIN1, '/', PIN2, '/', PIN3, '/', PIN4, '/', PIN5, '/', PIN6, '/', PIN7, '/', PIN8) not in   "
			+ "(   "
			+ "select CONCAT(PIN, '/', PIN1, '/', PIN2, '/', PIN3, '/', PIN4, '/', PIN5, '/', PIN6, '/', PIN7, '/', PIN8)   "
			+ "from public.dblink   "
			+ "(   "
			+ "'dbname='|| ?2 ||' host='|| ?1 ||' user='|| ?4  || ' password='|| ?5 ,   "
			+ "'select pin,pin1,pin2,pin3,pin4,pin5,pin6,pin7,pin8,ccode,tenure_code,tenure_sub_code from '|| ?3 ||'.form7_khata f7k '   "
			+ ")   "
			+ "AS   "
			+ "(   "
			+ "pin text,pin1 text,pin2 text,pin3 text,pin4 text,pin5 text,pin6 text,pin7 text,pin8 text,ccode text "
			+ ")   "
			+ "where trim(ccode)= ?6 AND KHATA_NO = '500001' "
			+ ")   "
			+ "AND KHATA_NO <> '500001' and MARKED <> 'Y'  "
			+ ""
			+ "",nativeQuery=true)
	public List<Object[]> getDashBoardKhataNoDetailsAug(String fhost,String fdbName,String fschemaName,String fuser,String fpassword, String cCode, String port);

	
	
	//vasuli khatedar patra
	@Query(value =  "SELECT "
			+ "  COUNT(DISTINCT khata_no) AS khatedar_patra "
			+ "FROM "
			+ "  public.dblink( "
			+ "    'dbname=' || ?2 || ' host=' || ?1 || ' port=' || '5432' || ' user=' || ?4 || ' password=' || ?5, "
			+ "    'select khata_no, active_flag, revenue_year, net_amount, ccode "
			+ "   FROM ' || ?3 || '.land_revenue_demand' "
			+ "  ) AS patra_sankhya ( "
			+ "    khata_no TEXT, "
			+ "    active_flag CHARACTER(1), "
			+ "    revenue_year TEXT, "
			+ "    net_amount DOUBLE PRECISION, "
			+ "    ccode text "
			+ "  ) "
			+ "WHERE "
			+ "  net_amount > 0 "
			+ "  AND active_flag = 'Y' "
			+ "  AND revenue_year = ?7 "
			+ "  AND ccode = ?6"
			+ "",nativeQuery=true)
	public List<Object[]> getKhatedarPatraSankhya(String echhost,String echdbName,String echschemaName,String echuser,String echpassword,
		String cCode, String revenueYear);
	
	/*old Query 8D 10 March
	 * @Query(value="select      "
			+ "receipt_date,    "
			+ "receipt_no,    "
			+ "net_amount as receiptAmount,    "
			+ "challan_date,    "
			+ "challan_no,    "
			+ "total_amount as chalanAmount,"
			+ "status,"
			+ "challan_no_0045      "
			+ "from      "
			+ "(    "
			+ "(    "
			+ "select    "
			+ "*    "
			+ "from public.dblink    "
			+ "(    "
			+ "'dbname='||?2||' host='||?1||' user='||?4||' password='||?5,    "
			+ "'select    "
			+ "challan_id,    "
			+ "receipt_date,    "
			+ "receipt_no,    "
			+ "net_amount,    "
			+ "ccode,    "
			+ "revenue_year,"
			+ "active_flag    "
			+ "from    "
			+ "'||?3||'.land_revenue_demand'    "
			+ ")    "
			+ "AS    "
			+ "(    "
			+ "challan_id bigint,    "
			+ "receipt_date text,    "
			+ "receipt_no text,   "
			+ "net_amount numeric,    "
			+ "ccode text,    "
			+ "revenue_year text,"
			+ "active_flag character(1)    "
			+ ")   "
			+ "where active_flag='Y' "
			+ ")      "
			+ "as t1      "
			+ "left join      "
			+ "(    "
			+ "select * from public.dblink    "
			+ "(    "
			+ "'dbname='||?2||' host='||?1||' user='||?4||' password='||?5,    "
			+ "'select id,    "
			+ "challan_date,    "
			+ "challan_no, "
			+ "total_amount ,"
			+ "status,"
			+ "active_flag  ,"
			+ "challan_no_0045  "
			+ "from '||?3||'.challan_dtl'      "
			+ ")    "
			+ "AS    "
			+ "(    "
			+ "id bigint,    "
			+ "challan_date text,    "
			+ "challan_no  text,    "
			+ "total_amount numeric,"
			+ "status text,"
			+ "active_flag character(1),"
			+ "challan_no_0045 text    "
			+ ")  "
			+ "where active_flag='Y'  "
			+ ") as t2      "
			+ "on(t1.challan_id=t2.id)    "
			+ ")    "
			+ "A where    "
			+ "trim(CCODE) = ?6    "
			+ "and revenue_year=?7   "
			+ "and receipt_no is not null    "
			+ "order by receipt_date    "
			+ "",nativeQuery=true)
	public List<Object[]> find8DBycCodeAndReceiptNoIsNotNull(
			String host, String dbName, String schemaName, String echuser,String echpassword, 
			String cCode, String revenueYear);*/
	
	
	//new query after Online payment update : 10 March 2025
		@Query(value="select   "
				+ "receipt_date,  "
				+ "receipt_no,  "
				+ "receiptAmount,  "
				+ "case when challan_date is not null then challan_date else transaction_date_time end as challan_date,  "
				+ "challan_no,  "
				+ "case when chalanAmount is not null then chalanAmount else total_amount_0029 end as chalanAmount,  "
				+ "case when status is not null then status else 'Deposited' end as status,  "
				+ "challan_no_0045,  "
				+ "case when bank_receipt_number is not null then bank_receipt_number else grn_no_0029 end as bank_receipt_number,  "
				+ "case when bank_receipt_number_0045 is not null then bank_receipt_number_0045 else grn_no_0045 end as bank_receipt_number_0045,  "
				+ "case when mode_of_payment is not null then mode_of_payment else 'Online' end as mode_of_payment,  "
				+ "gras_status_0029,  "
				+ "gras_status_0045  "
				+ "from   "
				+ "(  "
				+ "select        "
				+ "t1.id,	  "
				+ "t1.receipt_date,  "
				+ "t1.receipt_no,  "
				+ "t1.net_amount AS receiptAmount,  "
				+ "t2.challan_date,  "
				+ "t2.challan_no,  "
				+ "t2.total_amount AS chalanAmount,  "
				+ "t2.status,  "
				+ "t2.challan_no_0045,  "
				+ "t2.bank_receipt_number,  "
				+ "t2.bank_receipt_number_0045,  "
				+ "t2.mode_of_payment,  "
				+ "t1.ccode,  "
				+ "t1.revenue_year    "
				+ "from            "
				+ "(      "
				+ "select      "
				+ "*      "
				+ "from public.dblink      "
				+ "(      "
				+ "'dbname='|| ?2 ||' host='|| ?1 ||' user='|| ?4 ||' password='|| ?5 ,      "
				+ "'select   "
				+ "id,     "
				+ "challan_id,      "
				+ "receipt_date,      "
				+ "receipt_no,      "
				+ "net_amount,      "
				+ "ccode,      "
				+ "revenue_year,  "
				+ "active_flag      "
				+ "from      "
				+ "'|| ?3 ||'.land_revenue_demand'      "
				+ ")      "
				+ "AS      "
				+ "(  "
				+ "id bigint,  "
				+ "challan_id bigint,      "
				+ "receipt_date text,      "
				+ "receipt_no text,     "
				+ "net_amount numeric,      "
				+ "ccode text,      "
				+ "revenue_year text,  "
				+ "active_flag character(1)      "
				+ ")     "
				+ "where active_flag='Y'   "
				+ ")        "
				+ "as t1        "
				+ "left join        "
				+ "(      "
				+ "select * from public.dblink      "
				+ "(      "
				+ "'dbname='|| ?2 ||' host='|| ?1 ||' user='|| ?4 ||' password='|| ?5 ,      "
				+ "'select id,      "
				+ "challan_date,      "
				+ "challan_no,   "
				+ "total_amount ,  "
				+ "status,  "
				+ "active_flag  ,  "
				+ "challan_no_0045,  "
				+ "bank_receipt_number,  "
				+ "bank_receipt_number_0045,  "
				+ "mode_of_payment    "
				+ "from '|| ?3 ||'.challan_dtl'        "
				+ ")      "
				+ "AS      "
				+ "(      "
				+ "id bigint,      "
				+ "challan_date text,      "
				+ "challan_no  text,      "
				+ "total_amount numeric,  "
				+ "status text,  "
				+ "active_flag character(1),  "
				+ "challan_no_0045 text,  "
				+ "bank_receipt_number text ,  "
				+ "bank_receipt_number_0045 text,  "
				+ "mode_of_payment text      "
				+ ")    "
				+ "where active_flag='Y'    "
				+ ") as t2 ON (t1.challan_id = t2.id)   "
				+ ") T  "
				+ "left join        "
				+ "(      "
				+ "select * from public.dblink      "
				+ "(      "
				+ "'dbname='|| ?2 ||' host='|| ?1 ||' user='|| ?4 ||' password='|| ?5 ,      "
				+ "'select   "
				+ "echawadi_demand_id,  "
				+ "grn_no_0029,  "
				+ "grn_no_0045,  "
				+ "total_amount_0029,  "
				+ "total_amount_0045,  "
				+ "gras_status_0029,  "
				+ "gras_status_0045,  "
				+ "active_flag,  "
				+ "transaction_date_time  "
				+ "from '|| ?3 ||'.payment_request_dtls'        "
				+ ")      "
				+ "AS      "
				+ "(          "
				+ "echawadi_demand_id bigint,  "
				+ "grn_no_0029 text,  "
				+ "grn_no_0045 text,  "
				+ "total_amount_0029 numeric,  "
				+ "total_amount_0045 numeric,  "
				+ "gras_status_0029 text,  "
				+ "gras_status_0045 text ,  "
				+ "active_flag character(1),  "
				+ "transaction_date_time text  "
				+ ")    "
				+ "where active_flag='Y'    "
				+ ") as t3       "
				+ "on T.id = t3.echawadi_demand_id      "
				+ "where      "
				+ "trim(CCODE) = ?6     "
				+ "and revenue_year = ?7    "
				+ "and receipt_no is not null      "
				+ "order by receipt_date "
				+ "" ,nativeQuery=true)
		public List<Object[]> find8DBycCodeAndReceiptNoIsNotNull(
				String host, String dbName, String schemaName, String echuser,String echpassword, 
				String cCode, String revenueYear);
	
	
	
	//pre
	
	@Query(value="select      "
			+ "receipt_date,    "
			+ "receipt_no,    "
			+ "net_amount as receiptAmount,    "
			+ "challan_date,    "
			+ "challan_no,    "
			+ "total_amount as chalanAmount,"
			+ "status,"
			+ "challan_no_0045      "
			+ "from      "
			+ "(    "
			+ "(    "
			+ "select    "
			+ "*    "
			+ "from public.dblink    "
			+ "(    "
			+ "'dbname='||?2||' host='||?1||' user='||?4||' password='||?5,    "
			+ "'select    "
			+ "challan_id,    "
			+ "receipt_date,    "
			+ "receipt_no,    "
			+ "net_amount,    "
			+ "ccode,    "
			+ "revenue_year,"
			+ "active_flag    "
			+ "from    "
			+ "'||?3||'.land_revenue_demand_2023'    "
			+ ")    "
			+ "AS    "
			+ "(    "
			+ "challan_id bigint,    "
			+ "receipt_date text,    "
			+ "receipt_no text,   "
			+ "net_amount numeric,    "
			+ "ccode text,    "
			+ "revenue_year text,"
			+ "active_flag character(1)    "
			+ ")   "
			+ "where active_flag='Y' "
			+ ")      "
			+ "as t1      "
			+ "left join      "
			+ "(    "
			+ "select * from public.dblink    "
			+ "(    "
			+ "'dbname='||?2||' host='||?1||' user='||?4||' password='||?5,    "
			+ "'select id,    "
			+ "challan_date,    "
			+ "challan_no, "
			+ "total_amount ,"
			+ "status,"
			+ "active_flag  ,"
			+ "challan_no_0045  "
			+ "from '||?3||'.challan_dtl'      "
			+ ")    "
			+ "AS    "
			+ "(    "
			+ "id bigint,    "
			+ "challan_date text,    "
			+ "challan_no  text,    "
			+ "total_amount numeric,"
			+ "status text,"
			+ "active_flag character(1),"
			+ "challan_no_0045 text    "
			+ ")  "
			+ "where active_flag='Y'  "
			+ ") as t2      "
			+ "on(t1.challan_id=t2.id)    "
			+ ")    "
			+ "A where    "
			+ "trim(CCODE) = ?6    "
			+ "and revenue_year=?7   "
			+ "and receipt_no is not null    "
			+ "order by receipt_date    "
			+ "",nativeQuery=true)
	public List<Object[]> find8DBycCodeAndReceiptNoIsNotNullPre(
			String host, String dbName, String schemaName, String echuser,String echpassword, 
			String cCode, String revenueYear);

	public List<LandRevenueDemand> findBycCodeAndKhataOwnerNameContaining(String cCode, String khataOwnerName);
	

	public boolean existsBycCodeAndKhataNoAndRevenueYearAndActiveFlag(String getcCode, String khataNo, String revenueYear,Character activeFlag);
	
	public boolean existsBycCodeAndKhataNoAndActiveFlag(String getcCode, String khataNo,Character activeFlag);

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
			+ "from '|| ?3 ||'.land_revenue_demand lrd')     "
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
			 @Param ("khataOwnerName") String  khataOwnerName,String revenueYear); 
	
	/////////////////////Dynamic.////////////////////////////
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
	public List<Object[]> findByKhataOwnerNameDynamic(String eHost,String eDbName,String eSchemaName,String eUser,String ePassword,String cCode,
			 @Param ("khataOwnerName") String  khataOwnerName,String revenueYear, String singStringrep); 
	
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
			+ "from '|| ?3 ||'.land_revenue_demand lrd')     "
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
			String cCode, String khataNo, String revenueYear);
	
	//////////Dynamic/////////////////////
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
	public  List<Object[]> findByKhataNoDynamic(String eHost,String eDbName,String eSchemaName,String eUser,String ePassword,
			String cCode, String khataNo, String revenueYear,String singStringrep);
	
	
	
	@Query(value="select ccode,flag from   "
			+ "public.dblink (    "
			+ "'dbname='|| ?2 ||' host='|| ?1 ||' user='|| ?4  || ' password='|| ?5  ,  "
			+ "'SELECT  ccode,flag FROM '  "
			+ "|| ?3 ||'.m_paise_village ')   "
			+ "AS data (ccode text,flag text) "
			+ "where trim(ccode)= ?6 "
	+ "",nativeQuery=true)
	public  List<Object[]> findAkrushikRateforRural(String fHost,String fDbName,String fSchemaName,String fUser,String fPassword,
			String cCode);
	
	@Query(value="select   "
			+ "KHATA_NO,  "
			+ "STRING_AGG(DISTINCT CONCAT(FNAME, ' ', MNAME, ' ', LNAME), ' | ') AS KHATEDAR_NAMES,   "
			+ "SUM(TOTAL_AREA_H) AS AREA,    "
			+ "SUM(ASSESSMENT) AS AKRUSHIK_ASSESSMENT,  "
			+ "rate    "
			+ "from  "
			+ "(  "
			+ "select * from  "
			+ "public.dblink('dbname='|| ?2 ||' host='|| ?1 ||' user='|| ?4 || ' password='|| ?5 ,    "
			+ "'select      "
			+ "f7k.KHATA_NO,    "
			+ "f7k.FNAME,    "
			+ "f7k.MNAME,    "
			+ "f7k.LNAME,    "
			+ "f7k.TOTAL_AREA_H,    "
			+ "f7k.POTKHARABA,    "
			+ "f7k.ASSESSMENT,    "
			+ "f7k.PIN,    "
			+ "f7k.PIN1,f7k.PIN2,f7k.PIN3,f7k.PIN4,f7k.PIN5,f7k.PIN6,f7k.PIN7,f7k.PIN8,    "
			+ "f7.MUT_NO ,    "
			+ "f7a.NA_AREA_H ,    "
			+ "f7k.CCODE ,      "
			+ "f7.TENURE_CODE,      "
			+ "f7.TENURE_SUB_CODE,      "
			+ "f7k.MARKED  "
			+ "from    "
			+ "'|| ?3 ||'.FORM7_KHATA F7K,    "
			+ "'|| ?3 ||'.FORM7_AREA F7A,    "
			+ "'|| ?3 ||'.FORM7 F7   "
			+ "WHERE   F7.CCODE = F7A.CCODE      "
			+ "AND F7.CCODE = F7K.CCODE   "
			+ "AND F7.PIN = F7K.PIN      "
			+ "AND F7.PIN1 = F7K.PIN1      "
			+ "AND F7.PIN2 = F7K.PIN2      "
			+ "AND F7.PIN3 = F7K.PIN3      "
			+ "AND F7.PIN4 = F7K.PIN4      "
			+ "AND F7.PIN5 = F7K.PIN5      "
			+ "AND F7.PIN6 = F7K.PIN6      "
			+ "AND F7.PIN7 = F7K.PIN7      "
			+ "AND F7.PIN8 = F7K.PIN8      "
			+ "AND F7.PIN = F7A.PIN      "
			+ "AND F7.PIN1 = F7A.PIN1      "
			+ "AND F7.PIN2 = F7A.PIN2      "
			+ "AND F7.PIN3 = F7A.PIN3      "
			+ "AND F7.PIN4 = F7A.PIN4      "
			+ "AND F7.PIN5 = F7A.PIN5      "
			+ "AND F7.PIN6 = F7A.PIN6      "
			+ "AND F7.PIN7 = F7A.PIN7      "
			+ "AND F7.PIN8 = F7A.PIN8  "
			+ "AND F7K.CCODE= '|| ?6 ||'::text		"
			+ "AND F7.CCODE=  '|| ?6 ||'::text	 		"
			+ "AND F7A.CCODE=  '|| ?6 ||'::text 		"
			+ "AND F7.TENURE_SUB_CODE != ''7'' 		"
			+ "AND F7K.MARKED != ''Y''   		" 
			+ "AND F7A.NA_AREA_H != 0     		"
			+ "AND F7K.KHATA_NO !=''500001''   ' 		"
			+ ")As(  "
			+ "KHATA_NO text,    "
			+ "FNAME text,    "
			+ "MNAME text,    "
			+ "LNAME text,    "
			+ "TOTAL_AREA_H numeric,    "
			+ "POTKHARABA  numeric,    "
			+ "ASSESSMENT  numeric,    "
			+ "PIN text,    "
			+ "PIN1 text,    "
			+ "PIN2 text,    "
			+ "PIN3 text,    "
			+ "PIN4 text,    "
			+ "PIN5 text,    "
			+ "PIN6 text,    "
			+ "PIN7 text,    "
			+ "PIN8 text,    "
			+ "MUT_NO text,    "
			+ "NA_AREA_H numeric,    "
			+ "CCODE text,      "
			+ "TENURE_CODE text,      "
			+ "TENURE_SUB_CODE  text,      "
			+ "MARKED character(1)  "
			+ ")   "
			+ "where       "
			+ "  trim(CCODE) = ?6      "
			//+ "AND MARKED != 'Y'    "
			// //+ "AND TENURE_CODE != '2'  "
			//+ "AND TENURE_SUB_CODE != '7' "
			//+ "AND CONCAT(PIN, '/', PIN1, '/', PIN2, '/', PIN3, '/', PIN4, '/', PIN5, '/', PIN6, '/', PIN7, '/', PIN8) not in    "
			//+ "(    "
			//+ "select CONCAT(PIN, '/', PIN1, '/', PIN2, '/', PIN3, '/', PIN4, '/', PIN5, '/', PIN6, '/', PIN7, '/', PIN8)    "
			//+ "from public.dblink    "
			//+ "(    "
			//+ "'dbname='|| ?2 ||' host='|| ?1 ||' user='|| ?4  || ' password='|| ?5 ,    "
			//+ "'select pin,pin1,pin2,pin3,pin4,pin5,pin6,pin7,pin8,ccode,tenure_code,tenure_sub_code from '|| ?3 ||'.form7_khata f7k '    "
			//+ ")    "
			//+ "AS    "
			//+ "(    "
			//+ "pin text,pin1 text,pin2 text,pin3 text,pin4 text,pin5 text,pin6 text,pin7 text,pin8 text,ccode text  "
			//+ ")    "
			//+ "where trim(ccode)= ?6 AND KHATA_NO = '500001'  "
			//+ ")    "
			+ "AND KHATA_NO <> '500001'     "
			+ ") as A   "
			+ "inner join  "
			+ "(select *   "
			+ "from   "
			+ "public.dblink    "
			+ "(    "
			+ "'dbname='|| ?2 ||' host='|| ?1 ||' user='|| ?4  || ' password='|| ?5 ,  "
			+ "'select mn.rate,mn.CCODE,mn.PIN,mn.PIN1,mn.PIN2,mn.PIN3,mn.PIN4,mn.PIN5,mn.PIN6,mn.PIN7,mn.PIN8 from  '|| ?3 ||'.m_manapa_npa_village mn where trim(ccode)= '|| ?6 ||'::text ' )As(  "
			+ "rate text,  "
			+ "ccode text,  "
			+ "PIN text,    "
			+ "PIN1 text,    "
			+ "PIN2 text,    "
			+ "PIN3 text,    "
			+ "PIN4 text,    "
			+ "PIN5 text,    "
			+ "PIN6 text,    "
			+ "PIN7 text,    "
			+ "PIN8 text  "
			+ ") where trim(ccode)= ?6 )   "
			+ "as B   "
			+ "on   "
			+ "(A.ccode=B.ccode   "
			+ "and A.PIN=B.PIN   "
			+ "and A.PIN1=B.PIN1  "
			+ "and A.PIN2=B.PIN2  "
			+ "and A.PIN3=B.PIN3  "
			+ "and A.PIN4=B.PIN4  "
			+ "and A.PIN5=B.PIN5  "
			+ "and A.PIN6=B.PIN6  "
			+ "and A.PIN7=B.PIN7  "
			+ "and A.PIN8=B.PIN8)  "
			+ "group by khata_no,rate  order by cast(khata_no as integer) "
			+ "",nativeQuery=true)
	public  List<Object[]> findAkrushikRateforNaPaAndManapa(String fHost,String fDbName,String fSchemaName,String fUser,String fPassword,
			String cCode);
	
	
	
	
	@Query(value="select distinct khata_no,     "
			+ "khata_owner_name,  "
			+ "area,  "
			+ "assessment     "
			+ "from      "
			+ "public.dblink(' dbname='|| ?2 ||' host='|| ?1 ||' user='|| ?4 ||' password='|| ?5 ,     "
			+ "'select distinct khata_no,khata_owner_name,area,assessment,ccode,active_flag,revenue_year from '|| ?3 ||'.land_revenue_demand')     "
			+ "As(khata_no text,khata_owner_name text,area text,assessment text,ccode text,active_flag character(1),revenue_year text)     "
			+ "where trim(ccode)= ?6  and active_flag in ('Y','M','E') and revenue_year= ?8 and khata_no= ?7   "
	+ "", nativeQuery = true)
	public List<Object[]> getLrdetails(
	String eHost,String eDbName,String eSchemaName,String eUser,String ePassword,
	String cCode,String khataNo,String revenueYear);
	
	
	@Query(value="select     "
			+ "distinct khata_no, t1.ccode,"
			+ "t1.pin||'/' || t1.pin1 || '/'||t1.pin2 || '/'||t1.pin3 || '/' || t1.pin4 || '/' || t1.pin5 || '/' || t1.pin6 || '/' || t1.pin7 || '/' || t1.pin8 surveyHissaNo    "
			+ ",case when purpose_of_use = 'form2.table.partA.1' then 'निवासी'      "
			+ "when purpose_of_use = 'form2.table.partA.2' then 'औदयोगिक'     "
			+ "when purpose_of_use = 'form2.table.partA.3' then 'वाणिज्य'     "
			+ "when purpose_of_use = 'form2.table.partA.4' then 'इतर'    "
			+ "when purpose_of_use = 'form2.table.partA.5' then 'महसूलमाफ' end as purpose_of_use,    "
			+ "case when purpose_of_use = 'form2.table.partA.1' then '1'      "
			+ "when purpose_of_use = 'form2.table.partA.2' then '1.5'     "
			+ "when purpose_of_use = 'form2.table.partA.3' then '2'     "
			+ "when purpose_of_use = 'form2.table.partA.4' then '1.5'     "
			+ "when purpose_of_use = 'form2.table.partA.5' then '0' end as rate     "
			+ "from     "
			+ "(select ccode,    "
			+ "case when trim(pin)='' then '' else trim(pin) end as pin,    "
			+ "case when trim(pin1)='' then '' else trim(pin1) end as pin1,    "
			+ "case when trim(pin2)='' then '' else trim(pin2) end as pin2,    "
			+ "case when trim(pin3)='' then '' else trim(pin3) end as pin3,    "
			+ "case when trim(pin4)='' then '' else trim(pin4) end as pin4,    "
			+ "case when trim(pin5)='' then '' else trim(pin5) end as pin5,    "
			+ "case when trim(pin6)='' then '' else trim(pin6) end as pin6,    "
			+ "case when trim(pin7)='' then '' else trim(pin7) end as pin7,    "
			+ "case when trim(pin8)='' then '' else trim(pin8) end as pin8,    "
			+ "purpose_of_use    "
			+ "from    "
			+ "public.dblink(    "
			+ "' dbname='|| ?7 ||' host=' || ?6 ||' user=' || ?9 || ' password=' || ?10 ,    "
			+ "'select ccode,pin,pin1,pin2,pin3,pin4,pin5,pin6,pin7,pin8,purpose_of_use,active_flag from ' || ?8 ||'.echawdi_form2'    "
			+ ")     "
			+ "As(ccode text,pin text,pin1 text,pin2 text,pin3 text,pin4 text,pin5 text,pin6 text,pin7 text,    "
			+ "pin8 text,purpose_of_use text,active_flag character(1))    "
			+ "where trim(ccode)=?11 and active_flag='Y')as t1    "
			+ "left join    "
			+ "(    "
			+ "select * from     "
			+ "public.dblink(    "
			+ "' dbname='|| ?2 ||' host=' || ?1 ||' user=' || ?4 || ' password=' || ?5 ,    "
			+ "'select distinct f7k.khata_no,f7k.ccode,f7k.pin,f7k.pin1,f7k.pin2,f7k.pin3,f7k.pin4,f7k.pin5,f7k.pin6,f7k.pin7,f7k.pin8    "
			+ ",f7k.marked,na_area_h,TENURE_CODE,TENURE_SUB_CODE "
			+ "from ' || ?3 ||'.form7_khata f7k,'|| ?3 ||'.form7_area f7a,'|| ?3 ||'.form7 f7    "
			+ "where f7k.ccode=f7.ccode    "
			+ "and f7k.ccode=f7a.ccode    "
			+ "and f7k.pin=f7.pin    "
			+ "and f7k.pin1=f7.pin1    "
			+ "and f7k.pin2=f7.pin2    "
			+ "and f7k.pin3=f7.pin3    "
			+ "and f7k.pin4=f7.pin4    "
			+ "and f7k.pin5=f7.pin5    "
			+ "and f7k.pin6=f7.pin6    "
			+ "and f7k.pin7=f7.pin7    "
			+ "and f7k.pin8=f7.pin8    "
			+ "and f7k.ccode=f7a.ccode    "
			+ "and f7k.pin=f7a.pin    "
			+ "and f7k.pin1=f7a.pin1    "
			+ "and f7k.pin2=f7a.pin2    "
			+ "and f7k.pin3=f7a.pin3    "
			+ "and f7k.pin4=f7a.pin4    "
			+ "and f7k.pin5=f7a.pin5    "
			+ "and f7k.pin6=f7a.pin6    "
			+ "and f7k.pin7=f7a.pin7    "
			+ "and f7k.pin8=f7a.pin8    "
			+ "AND f7k.CCODE= '|| ?11 ||'::text		"
			+ "AND f7.CCODE=  '|| ?11 ||'::text	 		"
			+ "AND f7a.CCODE=  '|| ?11 ||'::text') 		"
			+ "As(khata_no text,ccode text,pin text,pin1 text,pin2 text,pin3 text,pin4 text,pin5 text,pin6 text,pin7 text,    "
			+ "pin8 text,marked character(1),na_area_h numeric, TENURE_CODE text,  TENURE_SUB_CODE  text  )    "
			+ "where   trim(ccode)= ?11  and marked <> 'Y'    "
			+ "and na_area_h !=0     "
			//+ " AND TENURE_CODE != '2'     "
			+ " AND TENURE_SUB_CODE != '7'    "
			+ "AND CONCAT(PIN, '/', PIN1, '/', PIN2, '/', PIN3, '/', PIN4, '/', PIN5, '/', PIN6, '/', PIN7, '/', PIN8) not in      "
			+ "(      "
			+ "select CONCAT(PIN, '/', PIN1, '/', PIN2, '/', PIN3, '/', PIN4, '/', PIN5, '/', PIN6, '/', PIN7, '/', PIN8)      "
			+ "from public.dblink    "
			+ "(    "
			+ "' dbname='|| ?2 ||' host=' || ?1 ||' user=' || ?4 || ' password=' || ?5 ,    "
			+ "'select fk.pin,fk.pin1,fk.pin2,fk.pin3,fk.pin4,fk.pin5,fk.pin6,fk.pin7,fk.pin8,ccode from '|| ?3 ||'.form7_khata fk'    "
			+ ")    "
			+ "AS    "
			+ "(    "
			+ "pin text,pin1 text,pin2 text,pin3 text,pin4 text,pin5 text,pin6 text,pin7 text,pin8 text,ccode text    "
			+ ")    "
			+ "where trim(ccode)=?11  AND KHATA_NO = '500001'      "
			+ "    "
			+ ")AND KHATA_NO <> '500001'   )  as t2    "
			+ "on (t1.ccode=t2.ccode    "
			+ "and t1.pin=t2.pin    "
			+ "and t1.pin1=t2.pin1    "
			+ "and t1.pin2=t2.pin2    "
			+ "and t1.pin3=t2.pin3    "
			+ "and t1.pin4=t2.pin4    "
			+ "and t1.pin5=t2.pin5    "
			+ "and t1.pin6=t2.pin6    "
			+ "and t1.pin7=t2.pin7    "
			+ "and t1.pin8=t2.pin8) "
	+ "", nativeQuery = true)
	public List<Object[]> getForm2PrayojanType(
	String fHost,String fDbName,String fSchemaName,String fUser,String fPassword,
	String eHost,String eDbName,String eSchemaName,String eUser,String ePassword,
	String cCode);

	@Query(value="     "
			+ "SELECT         "
			+ "KHATA_NO,        "
			+ "SUM(BAGAYAT_AREA_H) AS BAGAYAT_AREA_H,      "
			+ "SUM(JIRAYAT_AREA_H) AS JIRAYAT_AREA_H        "
			+ "FROM           "
			+ "public.dblink(           "
			+ "'dbname=' || ?2 || ' host=' || ?1 || ' user=' || ?4 || ' password=' || ?5 ,        "
			+ "'SELECT DISTINCT           "
			+ "F7K.KHATA_NO,           "
			+ "F7K.TOTAL_AREA_H,           "
			+ "F7K.ASSESSMENT,      "
			+ "F7A.BAGAYAT_AREA_H,      "
			+ "F7A.JIRAYAT_AREA_H,        "
			+ "F7.KHAND_NO,        "
			+ "MVC.IMAGINARY_MUT_NO_CHECK,        "
			+ "F7K.MUT_NO,        "
			+ "F7K.ccode,f7k.pin,f7k.pin1,f7k.pin2,f7k.pin3,f7k.pin4,f7k.pin5,f7k.pin6,f7k.pin7,f7k.pin8,        "
			+ "F7K.MARKED,            "
			+ "F7.TENURE_CODE,            "
			+ "F7.TENURE_SUB_CODE         "
			+ "FROM            "
			+ "'|| ?3 ||'.FORM7_KHATA F7K,           "
			+ "'|| ?3 ||'.FORM7 F7,        "
			+ "'|| ?3 ||'.FORM7_area F7A,         "
			+ "'|| ?3 ||'.M_VILLAGE_CENSUS_CHECK MVC            "
			+ "WHERE            "
			+ "F7K.CCODE = F7.CCODE            "
			+ "AND F7K.CCODE = MVC.CCODE            "
			+ "AND F7.PIN = F7K.PIN            "
			+ "AND F7.PIN1 = F7K.PIN1            "
			+ "AND F7.PIN2 = F7K.PIN2            "
			+ "AND F7.PIN3 = F7K.PIN3            "
			+ "AND F7.PIN4 = F7K.PIN4            "
			+ "AND F7.PIN5 = F7K.PIN5            "
			+ "AND F7.PIN6 = F7K.PIN6            "
			+ "AND F7.PIN7 = F7K.PIN7            "
			+ "AND F7.PIN8 = F7K.PIN8      "
			+ "AND F7A.CCODE = F7K.CCODE            "
			+ "AND F7A.CCODE = MVC.CCODE            "
			+ "AND F7A.PIN = F7K.PIN            "
			+ "AND F7A.PIN1 = F7K.PIN1            "
			+ "AND F7A.PIN2 = F7K.PIN2            "
			+ "AND F7A.PIN3 = F7K.PIN3            "
			+ "AND F7A.PIN4 = F7K.PIN4            "
			+ "AND F7A.PIN5 = F7K.PIN5            "
			+ "AND F7A.PIN6 = F7K.PIN6            "
			+ "AND F7A.PIN7 = F7K.PIN7            "
			+ "AND F7A.PIN8 = F7K.PIN8            "
			+ "AND F7K.CCODE= '|| ?7 ||'::text		"
			+ "AND F7.CCODE=  '|| ?7 ||'::text	 		"
			+ "AND F7A.CCODE=  '|| ?7 ||'::text' 		"
			+ ")        "
			+ "AS 	        "
			+ "Fer1        "
			+ "(        "
			+ "KHATA_NO text,           "
			+ "TOTAL_AREA_H numeric,           "
			+ "ASSESSMENT numeric,      "
			+ "BAGAYAT_AREA_H NUMERIC,      "
			+ "JIRAYAT_AREA_H NUMERIC,       "
			+ "KHAND_NO  text,        "
			+ "IMAGINARY_MUT_NO_CHECK character(1),        "
			+ "MUT_NO  text,        "
			+ "ccode text,        "
			+ "pin  text,        "
			+ "pin1 text,        "
			+ "pin2 text,        "
			+ "pin3 text,        "
			+ "pin4 text,        "
			+ "pin5 text,        "
			+ "pin6 text,        "
			+ "pin7 text,        "
			+ "pin8 text,        "
			+ "MARKED text,        "
			+ "TENURE_CODE text,        "
			+ "TENURE_SUB_CODE text        "
			+ ")        "
			+ "where      "
			+ "trim(CCODE) = ?7              "
			+ "AND MARKED != 'Y'            "
			+ "AND KHAND_NO != '2'  "
			+ "and khata_no= ?6       "
			+ "AND CONCAT(PIN, '/', PIN1, '/', PIN2, '/', PIN3, '/', PIN4, '/', PIN5, '/', PIN6, '/', PIN7, '/', PIN8) not in        "
			+ "(        "
			+ "select CONCAT(PIN, '/', PIN1, '/', PIN2, '/', PIN3, '/', PIN4, '/', PIN5, '/', PIN6, '/', PIN7, '/', PIN8)        "
			+ "from public.dblink      "
			+ "(      "
			+ "'dbname=' || ?2 || ' host=' || ?1 || ' user=' || ?4 || ' password=' || ?5 ,      "
			+ "'select fk.pin,fk.pin1,fk.pin2,fk.pin3,fk.pin4,fk.pin5,fk.pin6,fk.pin7,fk.pin8,ccode from '|| ?3 ||'.form7_khata fk'      "
			+ ")      "
			+ "AS      "
			+ "(      "
			+ "pin text,pin1 text,pin2 text,pin3 text,pin4 text,pin5 text,pin6 text,pin7 text,pin8 text,ccode text      "
			+ ")      "
			+ "where trim(ccode)= ?7  AND KHATA_NO = '500001'        "
			+ ")       "
			+ "GROUP BY           "
			+ "KHATA_NO      "
			+ ""
	+ "", nativeQuery = true)
	public List<Object[]> getBagayatJirayatDetails(
	String fHost,String fDbName,String fSchemaName,String fUser,String fPassword,
	String khataNo,String cCode);
	
	
	/*@Query(value="select distinct khata_no,   "
			+ "sum(CASE WHEN jm_bindumala <=10 THEN '0' ELSE jm_bindumala END + CASE WHEN jm_dumala <=10 THEN '0' ELSE jm_dumala END+pre_year_pending_jm) as preYearPendingJm,   "
			+ "sum(CASE WHEN zp_bindumala < 35 THEN '0' ELSE zp_bindumala END + CASE WHEN zp_dumala < 35 THEN '0' ELSE zp_dumala END+zp_akrushik+pre_year_pending_zp) as preYearPendingZp,   "
			+ "sum(CASE WHEN gp_bindumala < 5 THEN '0' ELSE gp_bindumala END + CASE WHEN gp_dumala < 5 THEN '0' ELSE gp_dumala END+gp_akrushik+pre_year_pending_gp) as preYearPendingGp,   "
			+ "sum(addl_land_revenue+pre_year_pending_addl_land_revenue) as preYearPendingAddlLandRevenue,   "
			+ "sum(educational_cess+pre_year_pending_educational_cess) as preYearPendingEducationalCess,   "
			+ "sum(addl_educational_cess+pre_year_pending_addl_educational_cess) as preYearPendingAddlEducationalCess,   "
			+ "sum(employee_guarantee_scheme+pre_year_pending_employee_guarantee_scheme) as preYearPendingEmployeeGuaranteeScheme,   "
			+ "sum(jm_akrushik+pre_year_pending_na_cess) as preYearPendingNaCess,   "
			+ "sum(gp_sankirn+zp_sankirn+pre_year_sankirn_jm_with) as preYearSankirnJmWith,   "
			+ "sum(jm_sankirn+pre_year_sankirn_jm_without) as preYearSankirnJmWithout,   "
			+ "SUM(COALESCE(notice_amount, 0) + COALESCE(pre_year_notice_fee, 0)) as preYearNoticeFee,   "
			+ "ccode from public.dblink     "
			+ "('dbname='|| ?2 ||' host='|| ?1 ||' user='|| ?4 || ' password='|| ?5 ,     "
			+ "'select khata_no,jm_bindumala,jm_dumala,pre_year_pending_jm,zp_bindumala,zp_dumala,zp_akrushik,pre_year_pending_zp,   "
			+ "gp_bindumala,gp_dumala,gp_akrushik,pre_year_pending_gp,addl_land_revenue,pre_year_pending_addl_land_revenue,   "
			+ "educational_cess,pre_year_pending_educational_cess,addl_educational_cess,pre_year_pending_addl_educational_cess,   "
			+ "employee_guarantee_scheme,pre_year_pending_employee_guarantee_scheme,jm_akrushik,pre_year_pending_na_cess,   "
			+ "gp_sankirn,zp_sankirn,pre_year_sankirn_jm_with,jm_sankirn,pre_year_sankirn_jm_without,   "
			+ "notice_amount,pre_year_notice_fee,ccode,mode_of_payment,active_flag,revenue_year from     "
			+ "'|| ?3 ||'.land_revenue_demand where makhtakhata_no is null '     "
			+ ")AS(KHATA_NO TEXT,jm_bindumala numeric,jm_dumala numeric,pre_year_pending_jm numeric,   "
			+ "zp_bindumala numeric,zp_dumala numeric,zp_akrushik numeric,pre_year_pending_zp numeric,   "
			+ "gp_bindumala numeric,gp_dumala numeric,gp_akrushik numeric,pre_year_pending_gp numeric,   "
			+ "addl_land_revenue numeric,pre_year_pending_addl_land_revenue numeric,educational_cess numeric,   "
			+ "pre_year_pending_educational_cess numeric,addl_educational_cess numeric,   "
			+ "pre_year_pending_addl_educational_cess numeric,   "
			+ "employee_guarantee_scheme numeric,pre_year_pending_employee_guarantee_scheme numeric,   "
			+ "jm_akrushik numeric,pre_year_pending_na_cess numeric,gp_sankirn numeric,zp_sankirn numeric,   "
			+ "pre_year_sankirn_jm_with numeric,jm_sankirn numeric,pre_year_sankirn_jm_without numeric,   "
			+ "notice_amount numeric,pre_year_notice_fee numeric,   "
			+ "ccode text,mode_of_payment text,active_flag character(1),revenue_year text)     "
			+ "where trim(CCODE) = ?6  and revenue_year= ?7  "
			+ "AND active_flag= ?8 and mode_of_payment is null      "
			+ "group by khata_no,ccode   "
			, nativeQuery = true)
	List<Object[]> findPreYearPendingDetails(
			String eHost,String eDbName,String eSchemaName,String eUser,String ePassword,
			String cCode,String revenueYear,Character activeFlag
			);*/
	
	@Query(value="select distinct khata_no,   "
			+ "sum(CASE WHEN jm_bindumala <=10 THEN '0' ELSE jm_bindumala END + CASE WHEN jm_dumala <=10 THEN '0' ELSE jm_dumala END+pre_year_pending_jm) as preYearPendingJm,   "
			+ "sum(CASE WHEN zp_bindumala < 35 THEN '0' ELSE zp_bindumala END + CASE WHEN zp_dumala < 35 THEN '0' ELSE zp_dumala END+zp_akrushik+pre_year_pending_zp) as preYearPendingZp,   "
			+ "sum(CASE WHEN gp_bindumala < 5 THEN '0' ELSE gp_bindumala END + CASE WHEN gp_dumala < 5 THEN '0' ELSE gp_dumala END+gp_akrushik+pre_year_pending_gp) as preYearPendingGp,   "
			+ "sum(addl_land_revenue+pre_year_pending_addl_land_revenue) as preYearPendingAddlLandRevenue,   "
			+ "sum(educational_cess+pre_year_pending_educational_cess) as preYearPendingEducationalCess,   "
			+ "sum(addl_educational_cess+pre_year_pending_addl_educational_cess) as preYearPendingAddlEducationalCess,   "
			+ "sum(employee_guarantee_scheme+pre_year_pending_employee_guarantee_scheme) as preYearPendingEmployeeGuaranteeScheme,   "
			+ "sum(jm_akrushik+pre_year_pending_na_cess) as preYearPendingNaCess,   "
			+ "sum(gp_sankirn+zp_sankirn+pre_year_sankirn_jm_with) as preYearSankirnJmWith,   "
			+ "sum(jm_sankirn+pre_year_sankirn_jm_without) as preYearSankirnJmWithout,   "
			+ "SUM(COALESCE(notice_amount, 0) + COALESCE(pre_year_notice_fee, 0)) as preYearNoticeFee,   "
			+ "ccode from public.dblink     "
			+ "('dbname='|| ?2 ||' host='|| ?1 ||' user='|| ?4 || ' password='|| ?5 ,     "
			+ "'select khata_no,jm_bindumala,jm_dumala,pre_year_pending_jm,zp_bindumala,zp_dumala,zp_akrushik,pre_year_pending_zp,   "
			+ "gp_bindumala,gp_dumala,gp_akrushik,pre_year_pending_gp,addl_land_revenue,pre_year_pending_addl_land_revenue,   "
			+ "educational_cess,pre_year_pending_educational_cess,addl_educational_cess,pre_year_pending_addl_educational_cess,   "
			+ "employee_guarantee_scheme,pre_year_pending_employee_guarantee_scheme,jm_akrushik,pre_year_pending_na_cess,   "
			+ "gp_sankirn,zp_sankirn,pre_year_sankirn_jm_with,jm_sankirn,pre_year_sankirn_jm_without,   "
			+ "notice_amount,pre_year_notice_fee,ccode,mode_of_payment,active_flag,revenue_year from     "
			+ "'|| ?3 ||'.land_revenue_demand_2023 where makhtakhata_no is null '     "
			+ ")AS(KHATA_NO TEXT,jm_bindumala numeric,jm_dumala numeric,pre_year_pending_jm numeric,   "
			+ "zp_bindumala numeric,zp_dumala numeric,zp_akrushik numeric,pre_year_pending_zp numeric,   "
			+ "gp_bindumala numeric,gp_dumala numeric,gp_akrushik numeric,pre_year_pending_gp numeric,   "
			+ "addl_land_revenue numeric,pre_year_pending_addl_land_revenue numeric,educational_cess numeric,   "
			+ "pre_year_pending_educational_cess numeric,addl_educational_cess numeric,   "
			+ "pre_year_pending_addl_educational_cess numeric,   "
			+ "employee_guarantee_scheme numeric,pre_year_pending_employee_guarantee_scheme numeric,   "
			+ "jm_akrushik numeric,pre_year_pending_na_cess numeric,gp_sankirn numeric,zp_sankirn numeric,   "
			+ "pre_year_sankirn_jm_with numeric,jm_sankirn numeric,pre_year_sankirn_jm_without numeric,   "
			+ "notice_amount numeric,pre_year_notice_fee numeric,   "
			+ "ccode text,mode_of_payment text,active_flag character(1),revenue_year text)     "
			+ "where trim(CCODE) = ?6  and revenue_year= ?7  "
			+ "AND active_flag= ?8 and mode_of_payment is null "
			+ "and khata_no not in (select khata_no from public.dblink  "
			+ "('dbname='|| ?2 ||' host='|| ?1 ||' user='|| ?4 || ' password='|| ?5 ,     "			
			+ " 'select distinct khata_no,ccode from '|| ?3 ||'.land_revenue_demand where active_flag=''Y''  '  ) AS (khata_no TEXT,ccode TEXT) where trim(CCODE) = ?6 )    "
			+ "group by khata_no,ccode   "
			, nativeQuery = true)
	List<Object[]> findPreYearPendingDetails(
			String eHost,String eDbName,String eSchemaName,String eUser,String ePassword,
			String cCode,String revenueYear,Character activeFlag
			);

	
	
	
	
	//RojGAr Hami Query
	
	  @Query(value = "SELECT "
			  + "  KHATEDAR_NAMES, "
			  + "  trim(KHATA_NO) as khata_no, "
			  + "  STRING_AGG(hissaNof, ', ') as hissa_Nos, "
			  + "  season_code, "
			  + "  sum( "
			  + "    CONST_IRR_SUM + mix_irr_area_sum + pure_irr_area_sum "
			  + "  ) as total_area, "
			  + "  employee_guarantee_scheme "
			  + "FROM "
			  + "  ( "
			  + "    SELECT "
			  + "      DISTINCT t1.KHATA_NO, "
			  + "      KHATEDAR_NAMES, "
			  + "      CONST_IRR_SUM, "
			  + "      mix_irr_area_sum, "
			  + "      pure_irr_area_sum, "
			  + "      season_code, "
			  + "      hissaNof, "
			  + "      lrd2.employee_guarantee_scheme "
			  + "    FROM "
			  + "      ( "
			  + "        select "
			  + "          distinct f7kk.KHATA_NO, "
			  + "          STRING_AGG( "
			  + "            DISTINCT CONCAT( "
			  + "              f7kk.FNAME, ' ', f7kk.MNAME, ' ', f7kk.LNAME "
			  + "            ), "
			  + "            ' | ' "
			  + "          ) AS KHATEDAR_NAMES, "
			  + "          f12cr.CONST_IRR_AREA_H AS CONST_IRR_SUM, "
			  + "          f12cr.mix_irr_area_h AS mix_irr_area_sum, "
			  + "          f12cr.pure_irr_area_h AS pure_irr_area_sum, "
			  + "          f12cr.season_code, "
			  + "          f12cr.hissaNof "
			  + "        from "
			  + "          ( "
			  + "            select "
			  + "              * "
			  + "            from "
			  + "              public.dblink ( "
			  + "                'dbname=' || ?2 || ' host=' || ?1 || ' user=' || ?4 || ' password=' || ?5, "
			  + "                'select f7k.KHATA_NO,f7k.MARKED,f7k.PIN,f7k.PIN1,f7k.PIN2,f7k.PIN3,f7k.PIN4,f7k.PIN5,f7k.PIN6,f7k.PIN7,f7k.PIN8,f7k.ccode,FNAME,MNAME,LNAME  from "
			  + "  ' || ?3 || '.FORM7_KHATA F7K where CCODE= ' || ?11 || '::text  ' "
			  + "              ) AS( "
			  + "                KHATA_NO TEXT, MARKED TEXT, PIN TEXT, "
			  + "                PIN1 TEXT, PIN2 TEXT, PIN3 TEXT, PIN4 TEXT, "
			  + "                PIN5 TEXT, PIN6 TEXT, PIN7 TEXT, PIN8 TEXT, "
			  + "                ccode text, FNAME TEXT, MNAME TEXT, "
			  + "                LNAME TEXT "
			  + "              ) "
			  + "            where "
			  + "              trim(CCODE) = ?11 "
			  + "              AND MARKED <> 'Y' "
			  + "              AND KHATA_NO <> '500001' "
			  + "          ) as f7kk "
			  + "          left join ( "
			  + "            select "
			  + "              CONST_CROP_CODE, "
			  + "              CONST_IRR_AREA_H, "
			  + "              NUMERIC_PIN, "
			  + "              YEAR_CULTI, "
			  + "              CCODE, "
			  + "              mix_irr_area_h, "
			  + "              pure_irr_area_h, "
			  + "              season_code, "
			  + "              CONCAT( "
			  + "                CONCAT(CASE WHEN PIN != '' THEN PIN END), "
			  + "                CONCAT( "
			  + "                  CASE WHEN PIN1 != '' THEN '/' || PIN1 END "
			  + "                ), "
			  + "                CONCAT( "
			  + "                  CASE WHEN PIN2 != '' THEN '/' || PIN2 END "
			  + "                ), "
			  + "                CONCAT( "
			  + "                  CASE WHEN PIN3 != '' THEN '/' || PIN3 END "
			  + "                ), "
			  + "                CONCAT( "
			  + "                  CASE WHEN PIN4 != '' THEN '/' || PIN4 END "
			  + "                ), "
			  + "                CONCAT( "
			  + "                  CASE WHEN PIN5 != '' THEN '/' || PIN5 END "
			  + "                ), "
			  + "                CONCAT( "
			  + "                  CASE WHEN PIN6 != '' THEN '/' || PIN6 END "
			  + "                ), "
			  + "                CONCAT( "
			  + "                  CASE WHEN PIN7 != '' THEN '/' || PIN7 END "
			  + "                ), "
			  + "                CONCAT(CASE WHEN PIN8 != '' THEN PIN8 END) "
			  + "              ) as hissaNof "
			  + "            from "
			  + "              public.dblink ( "
			  + "                'dbname=' || ?2 || 'crop' || ' host=' || ?1 || ' user=' || ?4 || ' password=' || ?5, "
			  + "                'select f12.CONST_CROP_CODE,f12.CONST_IRR_AREA_H,f12.NUMERIC_PIN,f12.YEAR_CULTI,F12.CCODE , f12.mix_irr_area_h, f12.pure_irr_area_h,f12.season_code, "
			  + "  f12.PIN,f12.PIN1,f12.PIN2,f12.PIN3,f12.PIN4,f12.PIN5,f12.PIN6,f12.PIN7,f12.PIN8 "
			  + "  from ' || ?3 || '.FORM12 F12   where CCODE= ' || ?11 || '::text  ' "
			  + "              ) AS ( "
			  + "                CONST_CROP_CODE TEXT, "
			  + "                CONST_IRR_AREA_H numeric(18, 2), "
			  + "                NUMERIC_PIN numeric(18), "
			  + "                YEAR_CULTI INTEGER, "
			  + "                ccode text, "
			  + "                mix_irr_area_h numeric(18, 2), "
			  + "                pure_irr_area_h numeric(18, 2), "
			  + "                season_code integer, "
			  + "                PIN CHARACTER VARYING(20), "
			  + "                PIN1 CHARACTER VARYING(20), "
			  + "                PIN2 CHARACTER VARYING(20), "
			  + "                PIN3 CHARACTER VARYING(20), "
			  + "                PIN4 CHARACTER VARYING(20), "
			  + "                PIN5 CHARACTER VARYING(20), "
			  + "                PIN6 CHARACTER VARYING(20), "
			  + "                PIN7 CHARACTER VARYING(20), "
			  + "                PIN8 CHARACTER VARYING(20) "
			  + "              ) "
			  + "            where "
			  + "              trim(CCODE) = ?11 "
			  + "          ) as f12cr on F7KK.CCODE = F12cr.CCODE "
			  + "          AND F7KK.KHATA_NO = TEXT(F12cr.NUMERIC_PIN) "
			  + "        WHERE "
			  + "          f12cr.YEAR_CULTI = 2024 "
			  + "          AND F7KK.KHATA_NO IN ( "
			  + "            SELECT "
			  + "              KHATA_NO "
			  + "            FROM "
			  + "              public.dblink ( "
			  + "                'dbname=' || ?7 || ' host=' || ?6 || ' user=' || ?9 || ' password=' || ?10, "
			  + "                'SELECT LRD.KHATA_NO,LRD.CCODE,LRD.revenue_year,LRD.employee_guarantee_scheme FROM ' || ?8 || '.LAND_REVENUE_DEMAND LRD where CCODE= ' || ?11 || '::text  ' "
			  + "              ) AS( "
			  + "                KHATA_NO text, CCODE text, revenue_year text, "
			  + "                employee_guarantee_scheme double precision "
			  + "              ) "
			  + "            WHERE "
			  + "              trim(CCODE)= ?11 "
			  + "              and revenue_year = ?12 "
			  + "              and khata_no <> '' "
			  + "              and khata_no is not NULL "
			  + "          ) "
			  + "        group by "
			  + "          f7kk.KHATA_NO, "
			  + "          CONST_IRR_SUM, "
			  + "          mix_irr_area_sum, "
			  + "          pure_irr_area_sum, "
			  + "          season_code, "
			  + "          f12cr.hissaNof "
			  + "      ) AS T1 "
			  + "      left join ( "
			  + "        SELECT "
			  + "          KHATA_NO, "
			  + "          ccode, "
			  + "          revenue_year, "
			  + "          employee_guarantee_scheme "
			  + "        FROM "
			  + "          public.dblink ( "
			  + "            'dbname=' || ?7 || ' host=' || ?6 || ' user=' || ?9 || ' password=' || ?10, "
			  + "            'SELECT LRD1.KHATA_NO,LRD1.CCODE,LRD1.revenue_year,LRD1.employee_guarantee_scheme FROM ' || ?8 || '.LAND_REVENUE_DEMAND LRD1 where CCODE= ' || '272400110296420000' || '::text  ' "
			  + "          ) AS( "
			  + "            KHATA_NO text, CCODE text, revenue_year text, "
			  + "            employee_guarantee_scheme double precision "
			  + "          ) "
			  + "      ) as lrd2 on lrd2.khata_no = TEXT(t1.khata_no) "
			  + "    WHERE "
			  + "      trim(CCODE)= ?11 "
			  + "      and revenue_year = ?12 "
			  + "      and lrd2.khata_no <> '' "
			  + "      and lrd2.khata_no is not NULL "
			  + "      and employee_guarantee_scheme > 0 "
			  + "  ) A "
			  + "group by "
			  + "  KHATEDAR_NAMES, "
			  + "  season_code, "
			  + "  khata_no, "
			  + "  employee_guarantee_scheme",
	  nativeQuery = true)
	List<Object[]> getRojGarHami(String fHost, String fDbName, String fSchemaName, String fUser,
			String fPassword, String eHost, String eDbName, String eSchemaName, String eUser, String ePassword,
			String cCode,String revenueYear);
	
	//Dynamic  Queries
		@Query(value =
			    "SELECT " +
			    "ccode, " +
			    "SUM(net_amount - COALESCE(jm_sankirn, 0) - COALESCE(gp_sankirn, 0) - COALESCE(zp_sankirn, 0)) AS net_amount, " + // Added alias
			    "SUM(COALESCE(jm_sankirn, 0) + COALESCE(gp_sankirn, 0) + COALESCE(zp_sankirn, 0)) AS net_Sankirn " +
			    "FROM dblink(" +
			    "'dbname=' || ?2 || ' host=' || ?1 || ' user=' || ?4 || ' password=' || ?5, " +
			    "'SELECT net_amount, jm_sankirn, gp_sankirn, zp_sankirn, ccode, active_flag, revenue_year " +
			    "FROM ' || ?3 || '.land_revenue_demand') " +
			    "AS t(net_amount DOUBLE PRECISION, jm_sankirn DOUBLE PRECISION, gp_sankirn DOUBLE PRECISION, zp_sankirn DOUBLE PRECISION, " +
			    "ccode TEXT, active_flag CHARACTER(1), revenue_year TEXT) " +
			    "WHERE active_flag = 'Y' " + // Fix for WHERE clause issue
			    "AND revenue_year = ?6 " +
			    "GROUP BY ccode",
			    nativeQuery = true)
			public List<Object[]> findBycCodeForTotalDemandAmt(String fhost,String fdbName,String fschemaName,String fuser,String fpassword, String cCode);
		
		
			@Query(value =
				    "SELECT " +
				    "ccode, " +
				    "SUM(net_amount - COALESCE(jm_sankirn, 0) - COALESCE(gp_sankirn, 0) - COALESCE(zp_sankirn, 0)) AS net_amount, " + // Added alias
				    "SUM(COALESCE(jm_sankirn, 0) + COALESCE(gp_sankirn, 0) + COALESCE(zp_sankirn, 0)) AS net_Sankirn " +
				    "FROM dblink(" +
				    "'dbname=' || ?2 || ' host=' || ?1 || ' user=' || ?4 || ' password=' || ?5, " +
				    "'SELECT net_amount, jm_sankirn, gp_sankirn, zp_sankirn, ccode, active_flag, revenue_year, receipt_no  " +
				    "FROM ' || ?3 || '.land_revenue_demand') " +
				    "AS t(net_amount DOUBLE PRECISION, jm_sankirn DOUBLE PRECISION, gp_sankirn DOUBLE PRECISION, zp_sankirn DOUBLE PRECISION, " +
				    "ccode TEXT, active_flag CHARACTER(1), revenue_year TEXT , receipt_no text) " +
				    "WHERE active_flag = 'Y' " + // Fix for WHERE clause issue
				    "AND revenue_year = ?6 " +
				    "AND receipt_no is not null  " +
				    "GROUP BY ccode",
				    nativeQuery = true)
				public List<Object[]> findBycCodeForCollectedAmt(String fhost,String fdbName,String fschemaName,String fuser,String fpassword, String cCode);

		
	
	
	public List<LandRevenueDemand> findBycCodeAndTalukaCodeAndRevenueYearAndActiveFlag(String cCode, Integer talukaCode,
			String revenueYear, Character activeFlags);
	public LandRevenueDemand findBycCodeAndKhataNoAndActiveFlag(String ccodes, String khataNo,Character activeFlag);
	
	
	
	
	
	//new editlrdemand Query
	@Query(value =
		    "SELECT l.id, l.revenue_year, l.ccode, l.khata_no, l.active_flag, " +
		    "l.taluka_code, l.makhtakhata_no, l.khata_owner_name, l.area, l.survey_hissa, " +
		    "l.area_unit, l.assessment, l.jm_bindumala, l.zp_bindumala, l.gp_bindumala, " +
		    "l.jm_dumala, l.zp_dumala, l.gp_dumala, l.jm_akrushik, l.zp_akrushik, " +
		    "l.gp_akrushik, l.jm_sankirn, l.zp_sankirn, l.gp_sankirn, l.jm_vajasut, " +
		    "l.zp_vajasut, l.gp_vajasut, l.addl_land_revenue, l.educational_cess, " +
		    "l.addl_educational_cess, l.employee_guarantee_scheme, l.pre_year_pending_jm, " +
		    "l.pre_year_pending_zp, l.pre_year_pending_gp, l.pre_year_pending_addl_land_revenue, " +
		    "l.pre_year_pending_educational_cess, l.pre_year_pending_addl_educational_cess, " +
		    "l.pre_year_pending_employee_guarantee_scheme, l.miscellaneous_amount, " +
		    "l.pre_year_pending_na_cess, l.pre_year_sankirn_jm_with, l.pre_year_sankirn_jm_without, " +
		    "l.pre_year_notice_fee, l.net_amount, l.net_received, l.net_pending, " +
		    "l.notice_amount, l.date_of_notice, l.receipt_no, l.receipt_date, " +
		    "l.mode_of_payment, l.dd_name, l.dd_branch, l.dd_number, l.dd_date, " +
		    "l.dd_amount, l.cheque_name, l.cheque_branch, l.cheque_number, " +
		    "l.cheque_date, l.cheque_amount, l.challan_id, l.is_money_deposited_bank, " +
		    "l.land_revenue_form17_id, l.agau_vasuli_remarks, " +
		    "l.create_dt_tm, l.update_dt_tm, l.created_user_id, l.update_userid, " +
		    "l.district_code, l.version " +
		    "FROM " +
		    "public.dblink( " +
		    "'dbname=' || ?2 || ' host=' || ?1 || ' user=' || ?4 || ' password=' || ?5, " +
		    // Make sure to select ALL columns your entity needs
		    "'SELECT id, revenue_year, ccode, khata_no, active_flag, " +
		    "taluka_code, makhtakhata_no, khata_owner_name, area, survey_hissa, " +
		    "area_unit, assessment, jm_bindumala, zp_bindumala, gp_bindumala, " +
		    "jm_dumala, zp_dumala, gp_dumala, jm_akrushik, zp_akrushik, " +
		    "gp_akrushik, jm_sankirn, zp_sankirn, gp_sankirn, jm_vajasut, " +
		    "zp_vajasut, gp_vajasut, addl_land_revenue, educational_cess, " +
		    "addl_educational_cess, employee_guarantee_scheme, pre_year_pending_jm, " +
		    "pre_year_pending_zp, pre_year_pending_gp, pre_year_pending_addl_land_revenue, " +
		    "pre_year_pending_educational_cess, pre_year_pending_addl_educational_cess, " +
		    "pre_year_pending_employee_guarantee_scheme, miscellaneous_amount, " +
		    "pre_year_pending_na_cess, pre_year_sankirn_jm_with, pre_year_sankirn_jm_without, " +
		    "pre_year_notice_fee, net_amount, net_received, net_pending, " +
		    "notice_amount, date_of_notice, receipt_no, receipt_date, " +
		    "mode_of_payment, dd_name, dd_branch, dd_number, dd_date, " +
		    "dd_amount, cheque_name, cheque_branch, cheque_number, " +
		    "cheque_date, cheque_amount, challan_id, is_money_deposited_bank, " +
		    "land_revenue_form17_id, agau_vasuli_remarks, " +
		    "create_dt_tm, update_dt_tm, created_user_id, update_userid, " +
		    "district_code, version " +  // Don't forget auditable columns
		    "FROM ' || ?3 || '.land_revenue_demand' " +
		    ") AS l( " +
		    // Define ALL columns in the same order as SELECT
		    "   id BIGINT, " +
		    "   revenue_year TEXT, " +
		    "   ccode TEXT, " +
		    "   khata_no TEXT, " +
		    "   active_flag CHAR(1), " +
		    "   taluka_code INTEGER, " +
		    "   makhtakhata_no TEXT, " +
		    "   khata_owner_name TEXT, " +
		    "   area NUMERIC, " +
		    "   survey_hissa TEXT, " +
		    "   area_unit TEXT, " +
		    "   assessment NUMERIC, " +
		    "   jm_bindumala NUMERIC, " +
		    "   zp_bindumala NUMERIC, " +
		    "   gp_bindumala NUMERIC, " +
		    "   jm_dumala NUMERIC, " +
		    "   zp_dumala NUMERIC, " +
		    "   gp_dumala NUMERIC, " +
		    "   jm_akrushik NUMERIC, " +
		    "   zp_akrushik NUMERIC, " +
		    "   gp_akrushik NUMERIC, " +
		    "   jm_sankirn NUMERIC, " +
		    "   zp_sankirn NUMERIC, " +
		    "   gp_sankirn NUMERIC, " +
		    "   jm_vajasut NUMERIC, " +
		    "   zp_vajasut NUMERIC, " +
		    "   gp_vajasut NUMERIC, " +
		    "   addl_land_revenue NUMERIC, " +
		    "   educational_cess NUMERIC, " +
		    "   addl_educational_cess NUMERIC, " +
		    "   employee_guarantee_scheme NUMERIC, " +
		    "   pre_year_pending_jm NUMERIC, " +
		    "   pre_year_pending_zp NUMERIC, " +
		    "   pre_year_pending_gp NUMERIC, " +
		    "   pre_year_pending_addl_land_revenue NUMERIC, " +
		    "   pre_year_pending_educational_cess NUMERIC, " +
		    "   pre_year_pending_addl_educational_cess NUMERIC, " +
		    "   pre_year_pending_employee_guarantee_scheme NUMERIC, " +
		    "   miscellaneous_amount NUMERIC, " +
		    "   pre_year_pending_na_cess NUMERIC, " +
		    "   pre_year_sankirn_jm_with NUMERIC, " +
		    "   pre_year_sankirn_jm_without NUMERIC, " +
		    "   pre_year_notice_fee NUMERIC, " +
		    "   net_amount NUMERIC, " +
		    "   net_received NUMERIC, " +
		    "   net_pending NUMERIC, " +
		    "   notice_amount NUMERIC, " +
		    "   date_of_notice TIMESTAMP, " +
		    "   receipt_no TEXT, " +
		    "   receipt_date TIMESTAMP, " +
		    "   mode_of_payment TEXT, " +
		    "   dd_name TEXT, " +
		    "   dd_branch TEXT, " +
		    "   dd_number TEXT, " +
		    "   dd_date TIMESTAMP, " +
		    "   dd_amount NUMERIC, " +
		    "   cheque_name TEXT, " +
		    "   cheque_branch TEXT, " +
		    "   cheque_number TEXT, " +
		    "   cheque_date TIMESTAMP, " +
		    "   cheque_amount NUMERIC, " +
		    "   challan_id BIGINT, " +
		    "   is_money_deposited_bank CHAR(1), " +
		    "   land_revenue_form17_id BIGINT, " +
		    "   agau_vasuli_remarks TEXT, " +
		    "   create_dt_tm TIMESTAMP, " +
		    "   update_dt_tm TIMESTAMP, " +
		    "   created_user_id BIGINT, " +
		    "   update_userid BIGINT, " +
		    "   district_code INTEGER, " +
		    "   version INTEGER " +
		    ") " +
		    "WHERE l.ccode = ?6 " +
		    "AND l.khata_no = ?7 " +
		    "AND l.revenue_year = ?8 " +
		    "AND l.active_flag = 'Y'",
		    nativeQuery = true)
		LandRevenueDemand findActiveRecord(
		    String eHost,        
		    String eDbName,      
		    String eSchemaName,  
		    String eUser,        
		    String ePassword,    
		    String ccode,       
		    String khataNo,      
		    String revenueYear
		);
	
	
	
	@Query(value = "SELECT EXISTS ( " +
		    "SELECT 1 					" +
		    "FROM public.dblink(		" +
		    "    'dbname=' || ?1 || ' host=' || ?2 || ' port=5432 user=' || ?3 || ' password=' || ?4 ," +
		    "    'SELECT ccode, active_flag, revenue_year FROM '|| ?5 ||'.land_revenue_demand_2024' " +
		    ") AS ( 								" +
		    "    ccode character(18),				" +
		    "    active_flag character(1),			" +
		    "    revenue_year character varying(10)	" +
		    ")										" +
		    "WHERE trim(ccode) = ?6 " +
		    "  AND active_flag = ?7				" +
		    "  AND revenue_year = ?8			" +
			")										" 
			,nativeQuery = true)
	boolean isRemoteDemandYearAvailable(
			String echDbname, 
			String echHost,
			String echUser, 
			String echPassword, 
			String echSchemaName,
	        String ccode,
	        String activeFlag,
	        String revenueYear);

	
	@Query(value =
		    "SELECT " +
		    "ccode, " +
		    // Bindumala
		    "SUM(COALESCE(jm_bindumala,0)) AS binJmDemand, " +
		    "SUM(COALESCE(gp_bindumala,0)) AS binGpDemand, " +
		    "SUM(COALESCE(zp_bindumala,0)) AS binZpDemand, " +
		    "SUM(COALESCE(jm_bindumala,0) + COALESCE(gp_bindumala,0) + COALESCE(zp_bindumala,0)) AS binTotalDemand, " +
		    // Akrushik
		    "SUM(COALESCE(jm_akrushik,0)) AS akJmDemand, " +
		    "SUM(COALESCE(gp_akrushik,0)) AS akGpDemand, " +
		    "SUM(COALESCE(zp_akrushik,0)) AS akZpDemand, " +
		    "SUM(COALESCE(jm_akrushik,0) + COALESCE(gp_akrushik,0) + COALESCE(zp_akrushik,0)) AS akTotalDemand, " +
		    // Sankirn
		    "SUM(COALESCE(jm_sankirn,0)) AS sanJmDemand, " +
		    "SUM(COALESCE(gp_sankirn,0)) AS sanGpDemand, " +
		    "SUM(COALESCE(zp_sankirn,0)) AS sanZpDemand, " +
		    "SUM(COALESCE(jm_sankirn,0) + COALESCE(gp_sankirn,0) + COALESCE(zp_sankirn,0)) AS sanTotalDemand, " +
		    // Overall
		    "SUM(COALESCE(jm_bindumala,0) + COALESCE(gp_bindumala,0) + COALESCE(zp_bindumala,0) + " +
		    "    COALESCE(jm_akrushik,0) + COALESCE(gp_akrushik,0) + COALESCE(zp_akrushik,0) + " +
		    "    COALESCE(jm_sankirn,0) + COALESCE(gp_sankirn,0) + COALESCE(zp_sankirn,0)) AS totalDemand " +
		    "FROM dblink(" +
		    "'dbname=' || ?2 || ' host=' || ?1 || ' user=' || ?4 || ' password=' || ?5, " +
		    "'SELECT ccode, revenue_year, active_flag, jm_bindumala, gp_bindumala, zp_bindumala, jm_akrushik, gp_akrushik, zp_akrushik, jm_sankirn, gp_sankirn, zp_sankirn " +
		    " FROM ' || ?3 || '.land_revenue_demand') " +
		    "AS t(ccode TEXT, revenue_year TEXT, active_flag CHARACTER(1), " +
		    "jm_bindumala DOUBLE PRECISION, gp_bindumala DOUBLE PRECISION, zp_bindumala DOUBLE PRECISION, " +
		    "jm_akrushik DOUBLE PRECISION, gp_akrushik DOUBLE PRECISION, zp_akrushik DOUBLE PRECISION, " +
		    "jm_sankirn DOUBLE PRECISION, gp_sankirn DOUBLE PRECISION, zp_sankirn DOUBLE PRECISION) " +
		    "WHERE active_flag = 'Y' " +
		    "AND revenue_year = ?7 " +
		    "AND ccode = ?6 " +
		    "GROUP BY ccode",
		    nativeQuery = true)
	List<Object[]> findByCcodeForDemand(String host, String dbName, String schemaName,
            String user, String password, String ccode, String revenueYear);


	
	
	//Collected
	@Query(value =
		    "SELECT " +
		    "ccode, " +
		    // Bindumala
		    "SUM(COALESCE(jm_bindumala,0)) AS binJmDemand, " +
		    "SUM(COALESCE(gp_bindumala,0)) AS binGpDemand, " +
		    "SUM(COALESCE(zp_bindumala,0)) AS binZpDemand, " +
		    "SUM(COALESCE(jm_bindumala,0) + COALESCE(gp_bindumala,0) + COALESCE(zp_bindumala,0)) AS binTotalDemand, " +
		    // Akrushik
		    "SUM(COALESCE(jm_akrushik,0)) AS akJmDemand, " +
		    "SUM(COALESCE(gp_akrushik,0)) AS akGpDemand, " +
		    "SUM(COALESCE(zp_akrushik,0)) AS akZpDemand, " +
		    "SUM(COALESCE(jm_akrushik,0) + COALESCE(gp_akrushik,0) + COALESCE(zp_akrushik,0)) AS akTotalDemand, " +
		    // Sankirn
		    "SUM(COALESCE(jm_sankirn,0)) AS sanJmDemand, " +
		    "SUM(COALESCE(gp_sankirn,0)) AS sanGpDemand, " +
		    "SUM(COALESCE(zp_sankirn,0)) AS sanZpDemand, " +
		    "SUM(COALESCE(jm_sankirn,0) + COALESCE(gp_sankirn,0) + COALESCE(zp_sankirn,0)) AS sanTotalDemand, " +
		    // Overall
		    "SUM(COALESCE(jm_bindumala,0) + COALESCE(gp_bindumala,0) + COALESCE(zp_bindumala,0) + " +
		    "    COALESCE(jm_akrushik,0) + COALESCE(gp_akrushik,0) + COALESCE(zp_akrushik,0) + " +
		    "    COALESCE(jm_sankirn,0) + COALESCE(gp_sankirn,0) + COALESCE(zp_sankirn,0)) AS totalDemand " +
		    "FROM dblink(" +
		    "'dbname=' || ?2 || ' host=' || ?1 || ' user=' || ?4 || ' password=' || ?5, " +
		    "'SELECT ccode, revenue_year, active_flag,receipt_no, jm_bindumala, gp_bindumala, zp_bindumala, jm_akrushik, gp_akrushik, zp_akrushik, jm_sankirn, gp_sankirn, zp_sankirn " +
		    " FROM ' || ?3 || '.land_revenue_demand') " +
		    "AS t(ccode TEXT, revenue_year TEXT, active_flag CHARACTER(1),receipt_no TEXT, " +
		    "jm_bindumala DOUBLE PRECISION, gp_bindumala DOUBLE PRECISION, zp_bindumala DOUBLE PRECISION, " +
		    "jm_akrushik DOUBLE PRECISION, gp_akrushik DOUBLE PRECISION, zp_akrushik DOUBLE PRECISION, " +
		    "jm_sankirn DOUBLE PRECISION, gp_sankirn DOUBLE PRECISION, zp_sankirn DOUBLE PRECISION) " +
		    "WHERE active_flag = 'Y' " +
		    "AND revenue_year = ?7 " +
		    "AND ccode = ?6 " +
		    "AND receipt_no IS NOT NULL " +
		    "GROUP BY ccode",
		    nativeQuery = true)
	List<Object[]> findByCcodeForCollected(String host, String dbName, String schemaName,
            String user, String password, String ccode, String revenueYear);
			
	
	
	// egs and rojgarHami query for inspection
			@Query(value =
			        "SELECT " +
			        "   COALESCE(SUM(employee_guarantee_scheme),0) AS egsDemand, " +
			        "   COALESCE(SUM(net_received),0) AS egsCollected, " +
			        "   COALESCE(SUM(educational_cess + addl_educational_cess),0) AS eduCessDemand, " +
			        "   COALESCE(SUM(net_received),0) AS eduCessCollected " +
			        "FROM dblink(" +
			        "   'dbname=' || ?2 || ' host=' || ?1 || ' user=' || ?4 || ' password=' || ?5, " +
			        "   'SELECT ccode, revenue_year, active_flag, employee_guarantee_scheme, net_received, educational_cess, addl_educational_cess " +
			        "    FROM ' || ?3 || '.land_revenue_demand') " +
			        "AS t(ccode TEXT, revenue_year TEXT, active_flag CHARACTER(1), employee_guarantee_scheme DOUBLE PRECISION, net_received DOUBLE PRECISION, educational_cess DOUBLE PRECISION, addl_educational_cess DOUBLE PRECISION) " +
			        "WHERE active_flag = 'Y' " +
			        "  AND revenue_year = ?7 " +
			        "  AND ccode = ?6",
			        nativeQuery = true)
			Object getCurrentYearSummary(String host,
			                            String dbName,
			                            String schemaName,
			                            String user,
			                            String password,
			                            String ccode,
			                            String revenueYear);
			
			
			/*@Query(value =
				    "SELECT " +
				    "   SUM(jm_sankirn) AS totalJmSankirn, " +
				    "   SUM(gp_sankirn) AS totalGpSankirn, " +
				    "   SUM(zp_sankirn) AS totalZpSankirn, " +
				    "   (SUM(jm_sankirn) + SUM(gp_sankirn) + SUM(zp_sankirn)) AS grandTotal, " +
				    "   (SELECT annual_village_target " +
				    "    FROM dblink('dbname=' || 'echmst' || ' host=' || ?1 || ' user=' || ?4 || ' password=' || ?5, " +
				    "        'SELECT annual_village_target, active_flag, revenue_year, ccode " +
				    "         FROM ' || 'public' || '.e_village_target') " +
				    "    AS t(annual_village_target TEXT, active_flag CHARACTER(1), revenue_year TEXT, ccode TEXT) " +
				    "    WHERE active_flag = 'Y' AND revenue_year ='2022-23' AND ccode = '272400110296420000'" +
				    "   ) AS annualVillageTarget " +
				    "FROM dblink('dbname=' || ?2 || ' host=' || ?1 || ' user=' || ?4 || ' password=' || ?5, " +
				    "    'SELECT ccode, active_flag, jm_sankirn, gp_sankirn, zp_sankirn, revenue_year, receipt_no " +
				    "     FROM ' || ?3 || '.land_revenue_demand') " +
				    "AS t(ccode TEXT, active_flag CHARACTER(1), jm_sankirn DOUBLE PRECISION, gp_sankirn DOUBLE PRECISION, zp_sankirn DOUBLE PRECISION, revenue_year TEXT, receipt_no TEXT) " +
				    "WHERE active_flag = 'Y' " +
				    "  AND receipt_no IS NOT NULL " +
				    "  AND revenue_year= ?6 " +
				    "  AND ccode = '272400110296420000' ",
				    nativeQuery = true)
			Object findByCcodeForSankirnCollected(String host,
			                            String dbName,
			                            String schemaName,
			                            String user,
			                            String password,
			                            String ccode,
			                            String revenueYear
			                            );*/
			
			
			@Query(value =
				    "SELECT " +
				    "   SUM(jm_sankirn) AS totalJmSankirn, " +
				    "   SUM(gp_sankirn) AS totalGpSankirn, " +
				    "   SUM(zp_sankirn) AS totalZpSankirn, " +
				    "   (SUM(jm_sankirn) + SUM(gp_sankirn) + SUM(zp_sankirn)) AS grandTotal, " +
				    "   (SELECT annual_village_target " +
				  //  "    FROM dblink('dbname=' || 'echmst' || ' host=' || '10.10.248.108' || ' user=' || 'postgres' || ' password=' || 'jduri%D^7f9gf', " +
				    "    FROM dblink('dbname=' || 'echmst' || ' host=' || '10.48.65.110' || ' user=' || 'postgres' || ' password=' || 'jduri%D^7f9gf', " +
				    "        'SELECT annual_village_target, active_flag, revenue_year, ccode " +
				    "         FROM ' || 'public' || '.e_village_target') " +
				    "    AS t(annual_village_target TEXT, active_flag CHARACTER(1), revenue_year TEXT, ccode TEXT) " +
				    "    WHERE active_flag = 'Y' AND revenue_year = ?6 AND ccode = ?7 " +
				    "   ) AS annualVillageTarget " +
				    "FROM dblink('dbname=' || ?2 || ' host=' || ?1 || ' user=' || ?4 || ' password=' || ?5, " +
				    "    'SELECT ccode, active_flag, jm_sankirn, gp_sankirn, zp_sankirn, revenue_year, receipt_no " +
				    "     FROM ' || ?3 || '.land_revenue_demand') " +
				    "AS t(ccode TEXT, active_flag CHARACTER(1), jm_sankirn DOUBLE PRECISION, gp_sankirn DOUBLE PRECISION, zp_sankirn DOUBLE PRECISION, revenue_year TEXT, receipt_no TEXT) " +
				    "WHERE active_flag = 'Y' " +
				    "  AND receipt_no IS NOT NULL " +
				    "  AND revenue_year= ?6 " +
				    "  AND ccode = ?7 ",
				    nativeQuery = true)
			Object findByCcodeForSankirnCollected(
					                   String host,
			                            String dbName,
			                            String schemaName,
			                            String user,
			                            String password,
			                            String ccode,
			                            String revenueYear
			                            );
			
			
			@Query(value =
				    "SELECT " + 
				    		 
				    		   " SUM(jm_sankirn) AS totalJmSankirn," +
				    		   " SUM(gp_sankirn) AS totalGpSankirn," +
				    		   " SUM(zp_sankirn) AS totalZpSankirn," + 
				    		    "(SUM(jm_sankirn) + SUM(gp_sankirn) + SUM(zp_sankirn)) AS grandTotal," +
				    		    "(" +
				    		    "  SELECT annual_village_target" +
				    		     " FROM dblink(" +
				    		          "'dbname=' || 'echmst' || ' host=' || ?1 || ' user=' || ?4 || ' password=' || ?5," + 
				    		         " 'SELECT annual_village_target, active_flag, revenue_year, ccode " +
				    		          " FROM public.e_village_target') " +
				    		      "AS t(annual_village_target TEXT, active_flag CHARACTER(1), revenue_year TEXT, ccode TEXT)" + 
				    		     " WHERE active_flag = 'Y' " +
				    		       " AND revenue_year = ?7" + 
				    		       " AND ccode = ?6" +
				    		    ") AS annualVillageTarget " +
				    		 "FROM dblink(" +
				    		     "'dbname=' || ?2 || ' host=' || ?1 || ' user=' || ?4 || ' password=' || ?5, " +
				    		     "'SELECT ccode, active_flag, jm_sankirn, gp_sankirn, zp_sankirn, revenue_year, receipt_no " +
				 			    "     FROM ' || ?3 || '.land_revenue_demand') " +
				    		"AS t(ccode TEXT, active_flag CHARACTER(1), jm_sankirn DOUBLE PRECISION, gp_sankirn DOUBLE PRECISION, zp_sankirn DOUBLE PRECISION, revenue_year TEXT," + "receipt_no TEXT) " +
				    		"WHERE active_flag = 'Y' " +
				    		  "AND receipt_no IS NOT NULL " +
				    		 " AND revenue_year = ?7 " +
				    		 "  AND ccode = ?6 ",
							    nativeQuery = true)
		Object findByCcodeForSankirnCollectedS(
				                   String host,
		                            String dbName,
		                            String schemaName,
		                            String user,
		                            String password,
		                            String ccode,
		                            String revenueYear
		                            );
			
			
			
			//Gram Adarsh takta Queries
			//Dynamic query
					@Query(value = "SELECT " +
					        "SUM(CASE WHEN area < 2.023 THEN area ELSE 0 END) AS below_2_023, " +
					        "SUM(CASE WHEN area > 2.023 THEN area ELSE 0 END) AS above_2_023, " +
					        "COUNT(DISTINCT CASE WHEN TRIM(khata_owner_name) <> '' THEN khata_no ELSE NULL END) AS khata_count_with_name, " +  
					        "COUNT(DISTINCT CASE WHEN TRIM(khata_owner_name) <> '' AND area < 2.023 THEN khata_no ELSE NULL END) AS khata_count_with_name_below_2_023, " +
					        "COUNT(DISTINCT CASE WHEN TRIM(khata_owner_name) <> '' AND area > 2.023 THEN khata_no ELSE NULL END) AS khata_count_with_name_above_2_023 " +
					        "FROM public.dblink(" +
					        "'dbname=' || ?2 || ' host=' || ?1 || ' user=' || ?4 || ' password=' || ?5, " +
					        "'SELECT area, khata_owner_name, khata_no,ccode, revenue_year, active_flag FROM '|| ?3 ||'.land_revenue_demand'" +
					        ") AS data(area numeric, khata_owner_name text, khata_no text,ccode text, revenue_year text, active_flag character(1))"+
					        "where trim(ccode) = ?6  and revenue_year= ?7 and active_flag='Y' "
					        ,nativeQuery = true)
					Object getLandRevenueSummary(String ehost,
							String echDbname,
							String echSchemaName,
							String user,
							String password,
				    		String ccode,
				    		String revenueYear);
					
					
					
					//tax calculations Dynamic
					@Query(value = "SELECT " +
						"T1.bidumala_niyat,T1.akrushak,T1.sankirn,T1.educational_cess,T1.employee_guarantee_scheme,T1.total_amount" +
						",T2.bidumala_niyat as bidumala_niyat_2023,T2.akrushak as akrushak_2023,T2.sankirn as sankirn_2023," +
						"T2.educational_cess as educational_cess_2023,T2.employee_guarantee_scheme as employee_guarantee_scheme_2023,T2.total_amount as total_amount_2023" +
						" from " +
						"(select ccode,sum(jm_bindumala + zp_bindumala + gp_bindumala + jm_dumala + zp_dumala + gp_dumala - jm_vajasut - gp_vajasut - zp_vajasut ) as bidumala_niyat, sum(jm_akrushik + zp_akrushik + gp_akrushik) as akrushak," +
						"sum(jm_sankirn + zp_sankirn + gp_sankirn) as sankirn" +
						", sum(educational_cess)as educational_cess, sum(employee_guarantee_scheme) as employee_guarantee_scheme, sum(net_amount)as total_amount  from public.dblink" +
						"('dbname='|| ?2 ||' host='|| ?1 ||'  port='|| ?3 ||' user='|| ?5 || ' password='|| ?6 ,  " +
						"'select " +
						"	c.ccode ," +
						"    c.jm_bindumala ," +
						"	c.zp_bindumala," +
						"    c.gp_bindumala," +
						"	c.jm_dumala," +
						"	c.zp_dumala," +
						"	c.gp_dumala," +
						"	c.jm_akrushik," +
						"    c.zp_akrushik," +
						"	c.gp_akrushik," +
						"    c.jm_sankirn," +
						"	c.zp_sankirn," +
						"	c.gp_sankirn," +
						"	c.educational_cess," +
						"	c.employee_guarantee_scheme," +
						"	c.net_amount," +
						"	c.jm_vajasut," +
						"	c.gp_vajasut," +
						"	c.zp_vajasut " +
						" 	from  '|| ?4 ||'.land_revenue_demand c' )" +
						"   As(  " +
						"    ccode text," +
						"    jm_bindumala double precision," +
						"	zp_bindumala double precision," +
						"    gp_bindumala double precision," +
						"	jm_dumala double precision," +
						"	zp_dumala double precision," +
						"	gp_dumala double precision," +
						"	jm_akrushik double precision," +
						"    zp_akrushik double precision," +
						"	gp_akrushik double precision," +
						"    jm_sankirn double precision," +
						"	zp_sankirn double precision," +
						"	gp_sankirn double precision," +
						"	educational_cess double precision," +
						"	employee_guarantee_scheme double precision," +
						"	net_amount double precision," +
						"	jm_vajasut double precision," +
						"	gp_vajasut double precision," +
						"	zp_vajasut double precision " +
						"    ) where trim(ccode)= ?7 group by ccode) as T1" +
						"	join " +
						"	(select ccode,sum(jm_bindumala + zp_bindumala + gp_bindumala + jm_dumala + zp_dumala + gp_dumala - jm_vajasut - gp_vajasut - zp_vajasut) as bidumala_niyat, sum(jm_akrushik + zp_akrushik + gp_akrushik) as akrushak," +
						"sum(jm_sankirn + zp_sankirn + gp_sankirn) as sankirn" +
						", sum(educational_cess)as educational_cess, sum(employee_guarantee_scheme) as employee_guarantee_scheme, sum(net_amount)as total_amount  from public.dblink" +
						"('dbname='|| ?2 ||' host='|| ?1 ||'  port='|| ?3 ||' user='|| ?5 || ' password='|| ?6 ,  " +
						"'select " +
						"	p.ccode ," +
						"    p.jm_bindumala ," +
						"	p.zp_bindumala," +
						"    p.gp_bindumala," +
						"	p.jm_dumala," +
						"	p.zp_dumala," +
						"	p.gp_dumala," +
						"	p.jm_akrushik," +
						"    p.zp_akrushik," +
						"	p.gp_akrushik," +
						"    p.jm_sankirn," +
						"	p.zp_sankirn," +
						"	p.gp_sankirn," +
						"	p.educational_cess," +
						"	p.employee_guarantee_scheme," +
						"	p.net_amount," +
						"	p.jm_vajasut," +
						"	p.gp_vajasut," +
						"	p.zp_vajasut" +
						" 	from  '|| ?4 ||' .land_revenue_demand_2023 p ' )" +
						"	As(  " +
						"	    ccode text," +
						"	    jm_bindumala double precision," +
						"		zp_bindumala double precision," +
						"	    gp_bindumala double precision," +
						"		jm_dumala double precision," +
						"		zp_dumala double precision," +
						"		gp_dumala double precision," +
						"		jm_akrushik double precision," +
						"	    zp_akrushik double precision," +
						"		gp_akrushik double precision," +
						"	    jm_sankirn double precision," +
						"		zp_sankirn double precision," +
						"		gp_sankirn double precision," +
						"		educational_cess double precision," +
						"		employee_guarantee_scheme double precision," +
						"		net_amount double precision," +
						"		jm_vajasut double precision," +
						"		gp_vajasut double precision," +
						"		zp_vajasut double precision " +
						"	    ) where trim(ccode)= ?7 group by ccode) as T2 ON (T1.ccode=T2.ccode)" 
						,nativeQuery = true)
							Object getAllTaxOfLandByCcode(
									String ehost,
									String echDbname,
									String port,
									String echSchemaName,
									String user,
									String password,
						    		String ccode);
					
					
					
					
					@Query(value = " SELECT    " +         
							"sum(T1.BAGAYAT_AREA_H + T1.JIRAYAT_AREA_H  + T1.TARI_AREA_H + T1.VARKAS_AREA_H + T1.ITAR_AREA_H ) as total_area	" +
							"FROM       " +
							"(    " +
							"SELECT  " +   
							"KHATA_NO,  " +    
							"SUM(BAGAYAT_AREA_H) AS BAGAYAT_AREA_H ," +
							"SUM(JIRAYAT_AREA_H) AS JIRAYAT_AREA_H, " +
							"SUM(TARI_AREA_H) AS TARI_AREA_H,         " +		
							"SUM(VARKAS_AREA_H) AS VARKAS_AREA_H,      	" +	
							"SUM(ITAR_AREA_H) AS ITAR_AREA_H,      			" +
							"CASE WHEN KHAND_NO = '2' THEN 'R.Sq.M.' ELSE 'H-R.Sq.M.' END AS UNIT " +    
							"FROM       " +
							"public.dblink(  " +     
							"'dbname=' || ?2 || ' host=' || ?1 || ' port=' || ?7 ||' user=' || ?4 || ' password=' || ?5 , " +   
							"'SELECT DISTINCT       " +
							"F7K.KHATA_NO,         " +
							"F7A.BAGAYAT_AREA_H,  " +
							"F7A.JIRAYAT_AREA_H, " +
							"F7A.TARI_AREA_H,     " +
							"F7A.VARKAS_AREA_H,     " +
							"F7A.ITAR_AREA_H,     " +
							"F7.KHAND_NO,    " +
							"MVC.IMAGINARY_MUT_NO_CHECK, " +   
							"F7K.MUT_NO,    " +
							"F7K.ccode,f7k.pin,f7k.pin1,f7k.pin2,f7k.pin3,f7k.pin4,f7k.pin5,f7k.pin6,f7k.pin7,f7k.pin8, " +   
							"F7K.MARKED,        " +
							"F7.TENURE_CODE,       " + 
							"F7.TENURE_SUB_CODE     " +
							"FROM        " +
							"'|| ?3 ||'.FORM7_KHATA F7K,   " +    
							"'|| ?3 ||'.FORM7 F7,    " +
							"'|| ?3 ||'.FORM7_area F7A, " +    
							"'|| ?3 ||'.M_VILLAGE_CENSUS_CHECK MVC " +       
							"WHERE        " +
							"F7K.CCODE = F7.CCODE     " +   
							"AND F7K.CCODE = MVC.CCODE   " +     
							"AND F7.PIN = F7K.PIN        " +
							"AND F7.PIN1 = F7K.PIN1        " +
							"AND F7.PIN2 = F7K.PIN2        " +
							"AND F7.PIN3 = F7K.PIN3        " +
							"AND F7.PIN4 = F7K.PIN4        " +
							"AND F7.PIN5 = F7K.PIN5        " +
							"AND F7.PIN6 = F7K.PIN6        " +
							"AND F7.PIN7 = F7K.PIN7        " +
							"AND F7.PIN8 = F7K.PIN8  " +
							"AND F7A.CCODE = F7K.CCODE  " +      
							"AND F7A.CCODE = MVC.CCODE     " +   
							"AND F7A.PIN = F7K.PIN        " +
							"AND F7A.PIN1 = F7K.PIN1        " +
							"AND F7A.PIN2 = F7K.PIN2        " +
							"AND F7A.PIN3 = F7K.PIN3        " +
							"AND F7A.PIN4 = F7K.PIN4        " +
							"AND F7A.PIN5 = F7K.PIN5        " +
							"AND F7A.PIN6 = F7K.PIN6        " +
							"AND F7A.PIN7 = F7K.PIN7        " +
							"AND F7A.PIN8 = F7K.PIN8        " +
							"AND F7.KHAND_NO != ''2'' " +
							"AND F7K.MARKED != ''Y'' " +
							"AND F7K.KHATA_NO != ''500001''  " +
							"AND F7K.CCODE= '|| ?6 ||'::text	" +	
							"AND F7.CCODE=  '|| ?6 ||'::text	 	" +	
							"AND F7A.CCODE= '|| ?6 ||'::text 		" +
							"AND MVC.CCODE= '|| ?6 ||'::text' 		" +
							")    " +
							"AS 	 " +
							"Fer1    " +
							"(    " +
							"KHATA_NO text,   " +      
							"BAGAYAT_AREA_H NUMERIC,  " +
							"JIRAYAT_AREA_H NUMERIC,   " +
							"TARI_AREA_H NUMERIC,    " +
							"VARKAS_AREA_H NUMERIC,    " +
							"ITAR_AREA_H NUMERIC,    " +
							"KHAND_NO  text,    " +
							"IMAGINARY_MUT_NO_CHECK character(1)," +    
							"MUT_NO  text,    " +
							"ccode text,    " +
							"pin  text,    " +
							"pin1 text,    " +
							"pin2 text,    " +
							"pin3 text,    " +
							"pin4 text,    " +
							"pin5 text,    " +
							"pin6 text,    " +
							"pin7 text,    " +
							"pin8 text,    " +
							"MARKED text,    " +
							"TENURE_CODE text,  " +  
							"TENURE_SUB_CODE text  " +  
							")    " +
							"where  " +
							" trim(CCODE) = ?6 " +          
							"GROUP BY       " +
							"KHATA_NO ,UNIT   " +
							")       " +
							"T1" 
							,nativeQuery = true)
					Double getBindumalaAreaDynamic( 
							 String fhost,
					   		 String mhrDbName,
					   		 String mhrSchemaname,
					   		 String mhrUser,
					   		 String mhrPass,
					   		 String ccode,
					   		 String mhrPort
							 );
					
					
					@Query(value = " SELECT    " +          
							"SUM(T1.BAGAYAT_AREA_H + T1.JIRAYAT_AREA_H) as Dumala_total_area  " +
							"FROM     " +
							"(    " +
							"SELECT  " +   
							"KHATA_NO,  " +    
							"SUM(BAGAYAT_AREA_H) AS BAGAYAT_AREA_H,  " +
							"SUM(JIRAYAT_AREA_H) AS JIRAYAT_AREA_H,     " + 
							"CASE WHEN KHAND_NO = '2' THEN 'R.Sq.M.' ELSE 'H-R.Sq.M.' END AS UNIT " +    
							"FROM       " +
							"public.dblink(       " +
							"'dbname=' || ?2 || ' host=' || ?1 || ' port=' || ?7 ||' user=' || ?4 || ' password=' || ?5 , " +   			
							"'SELECT DISTINCT       " +
							"F7K.KHATA_NO,       " +
							"F7K.TOTAL_AREA_H,      " + 
							"F7K.ASSESSMENT,  " +
							"F7A.BAGAYAT_AREA_H, " + 
							"F7A.JIRAYAT_AREA_H,    " +
							"F7.KHAND_NO,    " +
							"MVC.IMAGINARY_MUT_NO_CHECK,  " +  
							"F7K.MUT_NO,    " +
							"F7K.ccode,f7k.pin,f7k.pin1,f7k.pin2,f7k.pin3,f7k.pin4,f7k.pin5,f7k.pin6,f7k.pin7,f7k.pin8, " +   
							"F7K.MARKED,   " +
							"F7A.NA_AREA_H,   " +    
							"F7.TENURE_CODE,     " +   
							"F7.TENURE_SUB_CODE     " +
							"FROM        " +
							"'|| ?3 ||'.FORM7_KHATA F7K, " +      
							"'|| ?3 ||'.FORM7 F7,    " +
							"'|| ?3 ||'.FORM7_area F7A, " +    
							"'|| ?3 ||'.M_VILLAGE_CENSUS_CHECK MVC  " +      
							"WHERE        " +
							"F7K.CCODE = F7.CCODE        " +
							"AND F7K.CCODE = MVC.CCODE      " +  
							"AND F7.PIN = F7K.PIN        " +
							"AND F7.PIN1 = F7K.PIN1        " +
							"AND F7.PIN2 = F7K.PIN2        " +
							"AND F7.PIN3 = F7K.PIN3        " +
							"AND F7.PIN4 = F7K.PIN4        " +
							"AND F7.PIN5 = F7K.PIN5        " +
							"AND F7.PIN6 = F7K.PIN6        " +
							"AND F7.PIN7 = F7K.PIN7        " +
							"AND F7.PIN8 = F7K.PIN8  " +
							"AND F7A.CCODE = F7K.CCODE  " +      
							"AND F7A.CCODE = MVC.CCODE     " +   
							"AND F7A.PIN = F7K.PIN        " +
							"AND F7A.PIN1 = F7K.PIN1        " +
							"AND F7A.PIN2 = F7K.PIN2        " +
							"AND F7A.PIN3 = F7K.PIN3        " +
							"AND F7A.PIN4 = F7K.PIN4        " +
							"AND F7A.PIN5 = F7K.PIN5        " +
							"AND F7A.PIN6 = F7K.PIN6        " +
							"AND F7A.PIN7 = F7K.PIN7        " +
							"AND F7A.PIN8 = F7K.PIN8        " +
							"AND F7K.CCODE= '|| ?6 ||'::text		" +
							"AND F7.CCODE=  '|| ?6 ||'::text	 		" +
							"AND F7A.CCODE= '|| ?6 ||'::text' 		" +
							")    " +
							"AS 	    " +
							"Fer1    " +
							"(    " +
							"KHATA_NO text,       " +
							"TOTAL_AREA_H numeric,   " +    
							"ASSESSMENT numeric,  " +
							"BAGAYAT_AREA_H NUMERIC, " + 
							"JIRAYAT_AREA_H NUMERIC,   " +
							"KHAND_NO  text,    " +
							"IMAGINARY_MUT_NO_CHECK character(1), " +   
							"MUT_NO  text,    " +
							"ccode text,    " +
							"pin  text,    " +
							"pin1 text,    " +
							"pin2 text,    " +
							"pin3 text,    " +
							"pin4 text,    " +
							"pin5 text,    " +
							"pin6 text,    " +
							"pin7 text,    " +
							"pin8 text,    " +
							"MARKED text,  " +
							"NA_AREA_H numeric, " +   
							"TENURE_CODE text,    " +
							"TENURE_SUB_CODE text    " +
							")    " +
							"where  " +
							" trim(CCODE) = ?6  " +         
							"AND MARKED != 'Y'   " +
							"AND NA_AREA_H = 0  " +
							"AND TENURE_CODE = '2'  " +
							"AND TENURE_SUB_CODE = '7'  " +     
							"AND KHAND_NO != '2'        " +
							"AND CONCAT(PIN, '/', PIN1, '/', PIN2, '/', PIN3, '/', PIN4, '/', PIN5, '/', PIN6, '/', PIN7, '/', PIN8) not in  " +  
							"(    " +
							"select CONCAT(PIN, '/', PIN1, '/', PIN2, '/', PIN3, '/', PIN4, '/', PIN5, '/', PIN6, '/', PIN7, '/', PIN8)    " +
							"from public.dblink  " +
							"(  " +
							"'dbname=' || ?2 || ' host=' || ?1 || ' port=' || ?7 ||' user=' || ?4 || ' password=' || ?5 , " +   	
							"'select fk.pin,fk.pin1,fk.pin2,fk.pin3,fk.pin4,fk.pin5,fk.pin6,fk.pin7,fk.pin8,ccode from '|| ?3 ||'.form7_khata fk'  " +
							")  " +
							"AS  " +
							"(  " +
							"pin text,pin1 text,pin2 text,pin3 text,pin4 text,pin5 text,pin6 text,pin7 text,pin8 text,ccode text  " +
							")  " +
							"where trim(ccode)= ?6  AND KHATA_NO = '500001'  " +  
							")   " +
							"GROUP BY " +      
							"KHATA_NO ,UNIT " +  
							")       " +
							"T1" 
							,nativeQuery = true)
					Double getDumalaAreaDynamic( 
							 String fhost,
				    		 String mhrDbName,
				    		 String mhrSchemaname,
				    		 String mhrUser,
				    		 String mhrPass,
				    		 String ccode,
				    		 String mhrPort
							 );
					
					
					
					@Query(value =
					        "SELECT COUNT(*) AS total_count " +
					        "FROM public.dblink( " +
					        "    'dbname=' || ?2 || ' host=' || ?1 || ' user=' || ?4 || ' password=' || ?5, " +
					        "    'SELECT ccode, active_flag FROM ' || ?3 || '.echawdi_form1_dyslr' " +
					        ") AS t(ccode text, active_flag character(1)) " +
					        "WHERE trim(ccode) = ?6 " +
					        "  AND active_flag = 'N' ",
					        nativeQuery = true)
					Long getDyslrAkarbandDeletedCount(
					        String echHost,
					        String echDbname,
					        String echSchemaName,
					        String echUser,
					        String echPassword,
					        String ccode
					);
	
	
					
					@Query(value =
				            "SELECT " +
				            "SUM(COALESCE(net_amount,0)) AS total_demand, " +
				            "SUM(COALESCE(employee_guarantee_scheme,0)) AS total_egs_demand, " +
				            "SUM(COALESCE(educational_cess,0)) AS total_edu_demand " +
				            "FROM dblink(" +
				            "'dbname=' || ?2 || ' host=' || ?1 || ' user=' || ?4 || ' password=' || ?5, " +
				            "'SELECT net_amount, employee_guarantee_scheme, educational_cess " +
				            "FROM ' || ?3 || '.land_revenue_demand_2024 " +
				            "WHERE active_flag=''Y'' " +
				            "AND revenue_year=''' || ?7 || ''' " +
				            "AND ccode=''' || ?6 || '''') " +
				            "AS t(net_amount DOUBLE PRECISION, " +
				            "employee_guarantee_scheme DOUBLE PRECISION, " +
				            "educational_cess DOUBLE PRECISION)",
				            nativeQuery = true)
				    List<Object[]> getTotalDemand(
				            String host, String dbName, String schema,
				            String user, String password,
				            String ccode, String revenueYear);


				    // 🔹 COLLECTED QUERY
				    @Query(value =
				            "SELECT " +
				            "SUM(COALESCE(net_received,0)) AS total_collected, " +
				            "SUM(COALESCE(employee_guarantee_scheme,0)) AS total_egs_collected, " +
				            "SUM(COALESCE(educational_cess,0)) AS total_edu_collected " +
				            "FROM dblink(" +
				            "'dbname=' || ?2 || ' host=' || ?1 || ' user=' || ?4 || ' password=' || ?5, " +
				            "'SELECT net_received, employee_guarantee_scheme, educational_cess " +
				            "FROM ' || ?3 || '.land_revenue_demand_2024 " +
				            "WHERE active_flag=''Y'' " +
				            "AND receipt_no IS NOT NULL " +
				            "AND revenue_year=''' || ?7 || ''' " +
				            "AND ccode=''' || ?6 || '''') " +
				            "AS t(net_received DOUBLE PRECISION, " +
				            "employee_guarantee_scheme DOUBLE PRECISION, " +
				            "educational_cess DOUBLE PRECISION)",
				            nativeQuery = true)
				    List<Object[]> getTotalCollected(
				            String host, String dbName, String schema,
				            String user, String password,
				            String ccode, String revenueYear);

				    
				    
				    
				 /*   @Query(value =
						    "select * from ( " +
						    " select * from public.dblink( " +
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
						    "    from '|| ?3 ||'.land_revenue_demand " +
						    "    where trim(ccode)='''|| ?6 ||''' " +
						    "    and trim(revenue_year)='''|| ?7 ||''' " +   // 🔹 Removed receipt_no filter
						    "    and active_flag=''Y'' ' " +
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
						    "  dd_date date, cheque_date date, cheque_number text, dd_number text, cheque_name text ) " +
						    ")",
						    nativeQuery = true)
						List<Object[]> findCurrentYearData(
						        String host,
						        String dbName,
						        String schema,
						        String user,
						        String password,
						        String cCode,
						        String revenueYear); */
				    
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
									"    from '|| ?3 ||'.land_revenue_demand " +
									"    where trim(ccode)='''|| ?6 ||''' " +
									"    and trim(revenue_year)='''|| ?7 ||''' " +
								//	"    and active_flag=''Y'' ' " +
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
						List<Object[]> findCurrentYearData(
						        String host,
						        String dbName,
						        String schema,
						        String user,
						        String password,
						        String cCode,
						        String revenueYear);

					
@Query(value = "SELECT COUNT(DISTINCT khata_no) AS total_unique_khata " +
			        "FROM public.dblink(" +
			        "    'dbname=' || ?2 || ' host=' || ?1 || ' user=' || ?4 || ' password=' || ?5, " +
			        "    'SELECT khata_no, ccode, active_flag, revenue_year FROM ' || ?3 || '.land_revenue_demand' " +
			        ") AS t(khata_no text, ccode text, active_flag character(1), revenue_year text) " +
			        "WHERE trim(ccode) = ?6 " +
			       // "  AND active_flag = ''E'' " +
			        "  AND active_flag = ?7 " +
			        "  AND revenue_year = ?8",
			nativeQuery = true)
			Long MaganiDurustiKhatedarCount(
					String echHost,
					String echDbname, 
					String echSchemaName,
					String echUser, 
					String echPassword, 
			        String ccode,
			        Character activeFlag,
			        String revenueYear);

@Query(value =
	        "SELECT COUNT(*) AS total_count " +
	        "FROM public.dblink( " +
	        "    'dbname=' || ?2 || ' host=' || ?1 || ' user=' || ?4 || ' password=' || ?5, " +
	        "    'SELECT ccode, active_flag FROM ' || ?3 || '.echawdi_form1_dyslr' " +
	        ") AS t(ccode text, active_flag character(1)) " +
	        "WHERE trim(ccode) = ?6 " +
	        "  AND active_flag = 'E' ",
	        nativeQuery = true)
	Long getDyslrDurustiCount(
	        String echHost,
	        String echDbname,
	        String echSchemaName,
	        String echUser,
	        String echPassword,
	        String ccode
	);


@Query(value="SELECT "
        +"khata_no,"
        +"khata_owner_name,"
        +"employee_guarantee_scheme "
        +"FROM "
        +"public.dblink("
        +"'dbname='|| ?2 ||' host='|| ?1 ||' user='|| ?4 ||' password='|| ?5  ,  "
        +"'SELECT "
        +"lrd.khata_no  ,"
        +"lrd.khata_owner_name,  "
        +"lrd.employee_guarantee_scheme "
        +"FROM '|| ?3 ||' .land_revenue_demand lrd "
        +" WHERE   lrd.active_flag=''Y'' :: character(1) "
        +" AND  lrd.ccode= '''|| ?6 ||''' :: text "
        +" AND  lrd.revenue_year= '''|| ?7 ||''' :: text "
        +" AND  lrd.district_code= '''|| ?8 ||''' :: integer "
        +" AND  lrd.taluka_code= '''|| ?9 ||''' :: integer ') "
        +"AS data ("
        +"khata_no text,  "
        +"khata_owner_name text,  "
        +"employee_guarantee_scheme text "
        +")"
        +"",nativeQuery=true)
public  List<Object[]> getListOfRojgarHamiTaxx(String eHost,String eDbName,String eSchemaName,String eUser,String ePassword,
        String cCode, String revenueYear, Integer districtCode, Integer talukaCode);


@Query(value="SELECT "
        +"khata_no,"
        +"khata_owner_name,"
        +"addl_educational_cess,"
        +"educational_cess,"
        +"(COALESCE(CAST(addl_educational_cess AS numeric),0) + COALESCE(CAST(educational_cess AS numeric),0)) as total_shikshan_kar "
        +"FROM "
        +"public.dblink("
        +"'dbname='|| ?2 ||' host='|| ?1 ||' user='|| ?4 ||' password='|| ?5  ,  "
        +"'SELECT "
        +"lrd.khata_no  ,"
        +"lrd.khata_owner_name,  "
        +"lrd.addl_educational_cess, "
        +"lrd.educational_cess "
        +"FROM '|| ?3 ||' .land_revenue_demand lrd "
        +" WHERE   lrd.active_flag=''Y'' :: character(1) "
        +" AND  lrd.ccode= '''|| ?6 ||''' :: text "
        +" AND  lrd.revenue_year= '''|| ?7 ||''' :: text "
        +" AND  lrd.district_code= '''|| ?8 ||''' :: integer "
        +" AND  lrd.taluka_code= '''|| ?9 ||''' :: integer ') "
        +"AS data ("
        +"khata_no text,  "
        +"khata_owner_name text,  "
        +"addl_educational_cess text, "
        +"educational_cess text "
        +")"
        +"",nativeQuery=true)
public  List<Object[]> getEduCessTax(String eHost,String eDbName,String eSchemaName,String eUser,String ePassword,
        String cCode, String revenueYear, Integer districtCode, Integer talukaCode);


}
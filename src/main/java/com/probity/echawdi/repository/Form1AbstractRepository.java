/**
 * 
 */
package com.probity.echawdi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.probity.echawdi.entity.Form1Abstract;

/**
 * @author Bhagyashri Lolage
 *
 */
@Repository
public interface Form1AbstractRepository extends JpaRepository<Form1Abstract, Long> {

	boolean existsBycCode(String cCode);
	
	Form1Abstract findBycCode(String cCode);

	Form1Abstract findAllBycCodeAndActiveFlag(String cCode,Character activeFlag);

	@Query(value="select * from    "
			+ "(select ?11  ccode) as ccode,      "
			+ "(    "
			+ "select sum(total_area_h) tenure1Area  from public.dblink('dbname='||?2 ||' host='||?1||' user='||?4||' password='||?5,    "
			+ "'select f7a.total_area_h,f7.ccode,f7.pin,f7.pin1,f7.pin2,f7.pin3,f7.pin4,f7.pin5,f7.pin6,f7.pin7,f7.pin8,f7.tenure_code,   "
			+ "f7a.na_area_h,f7.MARKED     "
			+ "from  '||?3||'.form7_area f7a, '||?3||'.form7 f7, '||?3||'.m_tenure T where     "
			+ "f7.ccode=f7a.ccode and f7.pin=f7a.pin     "
			+ "and f7.pin1=f7a.pin1 and f7.pin2=f7a.pin2     "
			+ "and f7.pin3=f7a.pin3 and f7.pin4=f7a.pin4      "
			+ "and f7.pin5=f7a.pin5 and f7.pin6=f7a.pin6     "
			+ "and f7.pin7=f7a.pin7 and f7.pin8=f7a.pin8   "
			+ "and f7a.na_area_h =0    "
			+ "and T.TENURE_CODE=F7.TENURE_CODE')as ar(total_area_h numeric,ccode text,pin text,    "
			+ "pin1 text,pin2 text,pin3 text,pin4 text,pin5 text,pin6 text,pin7 text,pin8 text,tenure_code text,na_area_h text,MARKED text)where tenure_code<>'0'      "
			+ "and tenure_code='1' and trim(ccode)=?11  and MARKED<>'Y')t1ar,    "
			+ "(select sum(assessment) tenure1Assessment from public.dblink('dbname='||?2 ||' host='||?1||' user='||?4||' password='||?5,    "
			+ "'select f7a.assessment,f7.ccode,f7.pin,f7.pin1,f7.pin2,f7.pin3,f7.pin4,f7.pin5,f7.pin6,f7.pin7,f7.pin8,f7.tenure_code,   "
			+ "f7a.na_area_h,f7.MARKED      "
			+ "from  '||?3||'.form7_area f7a, '||?3||'.form7 f7 , '||?3||'.m_tenure T where     "
			+ "f7.ccode=f7a.ccode and f7.pin=f7a.pin     "
			+ "and f7.pin1=f7a.pin1 and f7.pin2=f7a.pin2     "
			+ "and f7.pin3=f7a.pin3 and f7.pin4=f7a.pin4      "
			+ "and f7.pin5=f7a.pin5 and f7.pin6=f7a.pin6     "
			+ "and f7.pin7=f7a.pin7 and f7.pin8=f7a.pin8   "
			+ "and f7a.na_area_h =0    "
			+ "and T.TENURE_CODE=F7.TENURE_CODE')as ar(assessment numeric,ccode text,pin text,    "
			+ "pin1 text,pin2 text,pin3 text,pin4 text,pin5 text,pin6 text,pin7 text,pin8 text,tenure_code text,na_area_h text,MARKED text)where tenure_code<>'0'      "
			+ "and tenure_code='1' and trim(ccode)=?11  and MARKED<>'Y')t1ass,    "
			+ "(    "
			+ "select sum(total_area_h) tenure2Area  from public.dblink('dbname='||?2 ||' host='||?1||' user='||?4||' password='||?5,    "
			+ "'select f7a.total_area_h,f7.ccode,f7.pin,f7.pin1,f7.pin2,f7.pin3,f7.pin4,f7.pin5,f7.pin6,f7.pin7,f7.pin8,f7.tenure_code,   "
			+ "f7a.na_area_h,f7.MARKED     "
			+ "from  '||?3||'.form7_area f7a, '||?3||'.form7 f7, '||?3||'.m_tenure T where     "
			+ "f7.ccode=f7a.ccode and f7.pin=f7a.pin     "
			+ "and f7.pin1=f7a.pin1 and f7.pin2=f7a.pin2     "
			+ "and f7.pin3=f7a.pin3 and f7.pin4=f7a.pin4      "
			+ "and f7.pin5=f7a.pin5 and f7.pin6=f7a.pin6     "
			+ "and f7.pin7=f7a.pin7 and f7.pin8=f7a.pin8   "
			+ "and f7a.na_area_h =0    "
			+ "and T.TENURE_CODE=F7.TENURE_CODE')as ar(total_area_h numeric,ccode text,pin text,    "
			+ "pin1 text,pin2 text,pin3 text,pin4 text,pin5 text,pin6 text,pin7 text,pin8 text,tenure_code text,na_area_h text,MARKED text)where tenure_code<>'0'      "
			+ "and tenure_code='2' and trim(ccode)=?11  and  MARKED<>'Y')t2ar,    "
			+ "(select sum(assessment) tenure2Assessment from public.dblink('dbname='||?2 ||' host='||?1||' user='||?4||' password='||?5,    "
			+ "'select f7a.assessment,f7.ccode,f7.pin,f7.pin1,f7.pin2,f7.pin3,f7.pin4,f7.pin5,f7.pin6,f7.pin7,f7.pin8,f7.tenure_code,   "
			+ "f7a.na_area_h,f7.MARKED     "
			+ "from  '||?3||'.form7_area f7a, '||?3||'.form7 f7, '||?3||'.m_tenure T where     "
			+ "f7.ccode=f7a.ccode and f7.pin=f7a.pin     "
			+ "and f7.pin1=f7a.pin1 and f7.pin2=f7a.pin2     "
			+ "and f7.pin3=f7a.pin3 and f7.pin4=f7a.pin4      "
			+ "and f7.pin5=f7a.pin5 and f7.pin6=f7a.pin6     "
			+ "and f7.pin7=f7a.pin7 and f7.pin8=f7a.pin8   "
			+ "and f7a.na_area_h =0    "
			+ "and T.TENURE_CODE=F7.TENURE_CODE')as ar(assessment numeric,ccode text,pin text,    "
			+ "pin1 text,pin2 text,pin3 text,pin4 text,pin5 text,pin6 text,pin7 text,pin8 text,tenure_code text,na_area_h text,MARKED text )where tenure_code<>'0'      "
			+ "and tenure_code='2' and trim(ccode)=?11  and  MARKED<>'Y' )t2ass,    "
			+ "(    "
			+ "select sum(total_area_h) tenure3Area  from public.dblink('dbname='||?2 ||' host='||?1||' user='||?4||' password='||?5,    "
			+ "'select f7a.total_area_h,f7.ccode,f7.pin,f7.pin1,f7.pin2,f7.pin3,f7.pin4,f7.pin5,f7.pin6,f7.pin7,f7.pin8,f7.tenure_code ,   "
			+ "f7a.na_area_h,f7.MARKED     "
			+ "from  '||?3||'.form7_area f7a, '||?3||'.form7 f7, '||?3||'.m_tenure T where     "
			+ "f7.ccode=f7a.ccode and f7.pin=f7a.pin     "
			+ "and f7.pin1=f7a.pin1 and f7.pin2=f7a.pin2     "
			+ "and f7.pin3=f7a.pin3 and f7.pin4=f7a.pin4      "
			+ "and f7.pin5=f7a.pin5 and f7.pin6=f7a.pin6     "
			+ "and f7.pin7=f7a.pin7 and f7.pin8=f7a.pin8   "
			+ "and f7a.na_area_h =0    "
			+ "and T.TENURE_CODE=F7.TENURE_CODE')as ar(total_area_h numeric,ccode text,pin text,    "
			+ "pin1 text,pin2 text,pin3 text,pin4 text,pin5 text,pin6 text,pin7 text,pin8 text,tenure_code text,na_area_h text,MARKED text)where tenure_code<>'0'      "
			+ "and tenure_code='3' and trim(ccode)=?11  and  MARKED<>'Y' )t3ar,    "
			+ "(select sum(assessment) tenure3Assessment from public.dblink('dbname='||?2 ||' host='||?1||' user='||?4||' password='||?5,    "
			+ "'select f7a.assessment,f7.ccode,f7.pin,f7.pin1,f7.pin2,f7.pin3,f7.pin4,f7.pin5,f7.pin6,f7.pin7,f7.pin8,f7.tenure_code,   "
			+ "f7a.na_area_h,f7.MARKED      "
			+ "from  '||?3||'.form7_area f7a, '||?3||'.form7 f7, '||?3||'.m_tenure T where     "
			+ "f7.ccode=f7a.ccode and f7.pin=f7a.pin     "
			+ "and f7.pin1=f7a.pin1 and f7.pin2=f7a.pin2     "
			+ "and f7.pin3=f7a.pin3 and f7.pin4=f7a.pin4      "
			+ "and f7.pin5=f7a.pin5 and f7.pin6=f7a.pin6     "
			+ "and f7.pin7=f7a.pin7 and f7.pin8=f7a.pin8   "
			+ "and f7a.na_area_h =0    "
			+ "and T.TENURE_CODE=F7.TENURE_CODE')as ar(assessment numeric,ccode text,pin text,    "
			+ "pin1 text,pin2 text,pin3 text,pin4 text,pin5 text,pin6 text,pin7 text,pin8 text,tenure_code text,na_area_h text,MARKED text)where tenure_code<>'0'      "
			+ "and tenure_code='3' and trim(ccode)=?11  and MARKED<>'Y' )t3ass,    "
			+ "(    "
			+ "select sum(total_area_h) tenure4Area  from public.dblink('dbname='||?2 ||' host='||?1||' user='||?4||' password='||?5,    "
			+ "'select f7a.total_area_h,f7.ccode,f7.pin,f7.pin1,f7.pin2,f7.pin3,f7.pin4,f7.pin5,f7.pin6,f7.pin7,f7.pin8,f7.tenure_code ,   "
			+ "f7a.na_area_h,f7.MARKED    "
			+ "from  '||?3||'.form7_area f7a, '||?3||'.form7 f7, '||?3||'.m_tenure T where     "
			+ "f7.ccode=f7a.ccode and f7.pin=f7a.pin     "
			+ "and f7.pin1=f7a.pin1 and f7.pin2=f7a.pin2     "
			+ "and f7.pin3=f7a.pin3 and f7.pin4=f7a.pin4      "
			+ "and f7.pin5=f7a.pin5 and f7.pin6=f7a.pin6     "
			+ "and f7.pin7=f7a.pin7 and f7.pin8=f7a.pin8   "
			+ "and f7a.na_area_h =0    "
			+ "and T.TENURE_CODE=F7.TENURE_CODE')as ar(total_area_h numeric,ccode text,pin text,    "
			+ "pin1 text,pin2 text,pin3 text,pin4 text,pin5 text,pin6 text,pin7 text,pin8 text,tenure_code text,na_area_h text,MARKED text)where tenure_code<>'0'      "
			+ "and tenure_code='4' and trim(ccode)=?11  and MARKED<>'Y')t4ar,    "
			+ "(select sum(assessment) tenure4Assessment from public.dblink('dbname='||?2 ||' host='||?1||' user='||?4||' password='||?5,    "
			+ "'select f7a.assessment,f7.ccode,f7.pin,f7.pin1,f7.pin2,f7.pin3,f7.pin4,f7.pin5,f7.pin6,f7.pin7,f7.pin8,f7.tenure_code,   "
			+ "f7a.na_area_h,f7.MARKED     "
			+ "from  '||?3||'.form7_area f7a, '||?3||'.form7 f7, '||?3||'.m_tenure T where     "
			+ "f7.ccode=f7a.ccode and f7.pin=f7a.pin     "
			+ "and f7.pin1=f7a.pin1 and f7.pin2=f7a.pin2     "
			+ "and f7.pin3=f7a.pin3 and f7.pin4=f7a.pin4      "
			+ "and f7.pin5=f7a.pin5 and f7.pin6=f7a.pin6     "
			+ "and f7.pin7=f7a.pin7 and f7.pin8=f7a.pin8   "
			+ "and f7a.na_area_h =0    "
			+ "and T.TENURE_CODE=F7.TENURE_CODE')as ar(assessment numeric,ccode text,pin text,    "
			+ "pin1 text,pin2 text,pin3 text,pin4 text,pin5 text,pin6 text,pin7 text,pin8 text,tenure_code text,na_area_h text,MARKED text)where tenure_code<>'0'      "
			+ "and tenure_code='4' and trim(ccode)=?11  and MARKED<>'Y')t4ass,    "
			+ "(select sum(potkharaba_a_h+potkharaba_b_h) potkharabaArea from public.dblink(    "
			+ "'dbname='||?2 ||' host='||?1||' user='||?4||' password='||?5,    "
			+ "'select f7a.potkharaba_a_h,f7a.potkharaba_b_h,ccode from '||?3||'.form7_area f7a')    "
			+ "as ar(potkharaba_a_h numeric,potkharaba_b_h numeric,ccode text)where trim(ccode)=?11 ) potkharaba,      "
			+ "   "
			+ "   "
			+ "(select sum(na_area_h) potkharabaAssessment from public.dblink(    "
			+ "'dbname='||?2 ||' host='||?1||' user='||?4||' password='||?5,    "
			+ "'select f7a.na_area_h,ccode from '||?3||'.form7_area f7a')    "
			+ "as ar(na_area_h numeric,ccode text)where trim(ccode)=?11 ) potkharabaAssessment,    "
			+ "   "
			+ "(select sum(river_area_h) form1ODCriverArea from public.dblink(    "
			+ "'dbname='||?2 ||' host='||?1||' user='||?4||' password='||?5,    "
			+ "'select river_area_h,ccode from '||?3||'.form1_basic ')    "
			+ "as ar(river_area_h numeric,ccode text)where trim(ccode)=?11 ) form1ODCriverArea ,    "
			+ "   "
			+ "(select sum(nala_area_h) form1ODCnalaArea from public.dblink(    "
			+ "'dbname='||?2 ||' host='||?1||' user='||?4||' password='||?5,    "
			+ "'select nala_area_h,ccode from '||?3||'.form1_basic ')    "
			+ "as ar(nala_area_h numeric,ccode text)where trim(ccode)=?11 ) form1ODCnalaArea,    "
			+ "   "
			+ "(select sum(net_culti_area_h) netCultiAreaH from public.dblink(        "
			+ "'dbname='|| ?2 ||' host='|| ?1 ||' user='|| ?4 ||' password='|| ?5 ,        "
			+ "'select net_culti_area_h,f7.ccode,TENURE_CODE,TENURE_SUB_CODE from '|| ?3 ||'.form7_area f7a,'|| ?3 ||'.form7 f7        "
			+ "where f7.ccode=f7a.ccode and f7.pin=f7a.pin  and f7.pin1=f7a.pin1  and f7.pin2=f7a.pin2  and f7.pin3=f7a.pin3  and f7.pin4=f7a.pin4  and f7.pin5=f7a.pin5  and f7.pin6=f7a.pin6  and f7.pin7=f7a.pin7  and f7.pin8=f7a.pin8        "
			+ "')        "
			+ "as ar(net_culti_area_h numeric,ccode text,TENURE_CODE text,TENURE_SUB_CODE text)where TENURE_CODE = '2' and TENURE_SUB_CODE IN ('7','2') and trim(ccode)=?11  ) form3netCultiAreaH,   "
			+ "   "
			+ "   "
			+ "(select sum(cast(ech_assement as numeric)) form3assessment from public.dblink(    "
			+ "'dbname='||?7 ||' host='||?6||' user='||?9||' password='||?10,    "
			+ "'select ech_assement,ccode from '||?8||'.echawdi_form3')    "
			+ "as ar(ech_assement text,ccode text)where trim(ccode)=?11 ) form3assessment,    "
			+ "   "
			+ "(select sum(total_area_h) unoccupiedGov  from public.dblink('dbname='||?2 ||' host='||?1||' user='||?4||' password='||?5,    "
			+ "'select f7a.total_area_h,f7.ccode,f7.pin,f7.pin1,f7.pin2,f7.pin3,f7.pin4,f7.pin5,f7.pin6,f7.pin7,f7.pin8,f7.tenure_code,f7a.assessment     "
			+ "from  '||?3||'.form7_area f7a, '||?3||'.form7 f7 where     "
			+ "f7.ccode=f7a.ccode and f7.pin=f7a.pin     "
			+ "and f7.pin1=f7a.pin1 and f7.pin2=f7a.pin2     "
			+ "and f7.pin3=f7a.pin3 and f7.pin4=f7a.pin4      "
			+ "and f7.pin5=f7a.pin5 and f7.pin6=f7a.pin6     "
			+ "and f7.pin7=f7a.pin7 and f7.pin8=f7a.pin8')as ar(total_area_h numeric,ccode text,pin text,    "
			+ "pin1 text,pin2 text,pin3 text,pin4 text,pin5 text,pin6 text,pin7 text,pin8 text,tenure_code text,assessment numeric)    "
			+ "where assessment = '0.00' and tenure_code='4'     "
			+ "and trim(ccode)=?11 )    "
			+ "unoccupiedGov    "
			+ "",nativeQuery=true)
	List<Object[]> fetchLiveData(String fHost,String fDbName,String fSchemaName,String fUser,String fPassword,
			String eHost,String eDbName,String eSchemaName,String eUser,String ePassword,
			String cCode);

	@Query(value="select * from  "
			+ "public.dblink(  "
			+ "'dbname='|| ?2 ||' host='|| ?1 ||' user='|| ?4 || ' password='|| ?5 ,  "
			+ "'select ccode,rivers_Nalas,nalas,village_Site,roads_And_Path from   "
			+ "'|| ?3 ||'.echawdi_form1abstract '  "
			+ ")AS(  "
			+ "ccode text,  "
			+ "rivers_Nalas text, "
			+ "nalas text, "
			+ "village_Site text, "
			+ "roads_And_Path text "
			+ ") WHERE  trim(ccode)=?6 "
			+ ""	
	+ "",nativeQuery=true)
	List<Object[]> getForm1Abstarctdetails(String eHost,String eDbName,String eSchemaName,String eUser,String ePassword, String cCode);
	
	
	
	
	//Gram adarsh takta Queries
		//Gram Adarsh Takta
			@Query(value = 
					"SELECT " +
					"    COALESCE(" +
					"        (SELECT SUM(area)" +
					"         FROM public.dblink(" +
					"      'dbname=' || ?2 || ' host=' || ?1 || ' user=' || ?4 || ' password=' || ?5, " +
					"      'SELECT area, ccode FROM ' || ?3 || '.echawdi_form1a ' " +
					"         ) AS form1a(area DOUBLE PRECISION, ccode TEXT)" +
					"         WHERE TRIM(ccode) = ?6 ), 0.0" +
					"    ) AS area," +
					"    COALESCE(" +
					"        (SELECT SUM(village_site)" +
					"         FROM public.dblink(" +
					"      'dbname=' || ?2 || ' host=' || ?1 || ' user=' || ?4 || ' password=' || ?5, " +
					 "      'SELECT area, ccode FROM ' || ?3 || '.echawdi_form1abstract' " +
					"         ) AS form1b(village_site NUMERIC(19,2), ccode TEXT)" +
					"         WHERE TRIM(ccode) = ?6 ), 0.0" +
					"    ) AS village_site",
						  nativeQuery = true)
			Object getForestAndGaothanKshetraByCcode(String fHost, String fDbName, String fSchemaName, String fUser, String fPassword, String cCode);
			
			
			
			//Dynamic Query of KamAkariArea
			@Query(value = "SELECT * FROM public.dblink(" +
			        "'dbname=' || ?2 || ' host=' || ?1 || ' user=' || ?4 || ' password=' || ?5, " +
			        "'SELECT COALESCE(SUM(Revenue_Or_Leasehold_Land_Under_Special_Agreement), 0.0) " +
			        "FROM ' || ?3 || '.echawdi_form1abstract WHERE TRIM(ccode) = ''' || ?6 || '''') " +
			        "AS result(sum double precision)",
			        nativeQuery = true)
			Double fetchMahasulMafOrKamakariJamini(
			        String eHost,
			        String eDbName,
			        String eSchemaName,
			        String eUser,
			        String ePassword,
			        String cCode
			);
			
	
}
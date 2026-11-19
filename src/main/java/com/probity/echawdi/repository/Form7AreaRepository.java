package com.probity.echawdi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.probity.echawdi.entity.Form7Area;

@Repository
public interface Form7AreaRepository extends JpaRepository<Form7Area, Long> {


	
	//Dynamic Query
	@Query(value =	"select sum(potkharaba_a_h + potkharaba_b_h) as potkharaba, sum(na_area_h) as akrushak  from public.dblink" +
			"('dbname='|| ?2 ||' host='|| ?1 ||' port='|| ?7 ||' user='|| ?4 || ' password='|| ?5 ,  " +
			"'select " +
			"ccode ," +
		    "potkharaba_a_h ," +
		    "potkharaba_b_h ," +
		    "na_area_h    " +
			"from  '|| ?3 ||'.form7_area' )" +
			"As(  " +
		    "ccode character(18)," +
		    "potkharaba_a_h numeric(18,4)," +
		    "potkharaba_b_h numeric(18,4)," +
		    "na_area_h numeric(18,4)" +
		    ") where trim(ccode)= ?6 " 
			,nativeQuery = true)
	Object getAkrushakAndPotKharabArea( 
			String ehost,
			String echDbname,
			String echSchemaName,
			String user,
			String password,
    		String ccode,
    		String port);
	
	
	//Static Query
	@Query(value =	"select sum(potkharaba_a_h + potkharaba_b_h) as potkharaba, sum(na_area_h) as akrushak  from public.dblink" +
			"('dbname='|| 'mhrorrai' ||' host='|| 'localhost' ||' port=5432 user='||'postgres'|| ' password='|| 'root' ,  " +
			"'select " +
			"ccode ," +
		    "potkharaba_a_h ," +
		    "potkharaba_b_h ," +
		    "na_area_h    " +
			"from  '|| 'mhrorrai_tal' ||'.form7_area' )" +
			"As(  " +
		    "ccode character(18)," +
		    "potkharaba_a_h numeric(18,4)," +
		    "potkharaba_b_h numeric(18,4)," +
		    "na_area_h numeric(18,4)" +
		    ") where trim(ccode)='272400110296420000'" 
			,nativeQuery = true)
	Object getAkrushakAndPotKharabArea( 
			 );
	
	
	
	
	@Query(value =
			"SELECT kuran_sum, bagayat_sum FROM (" +
			"  SELECT SUM(kuran) AS kuran_sum FROM public.dblink(" +
			"    'dbname=' || ?7 || ' host=' || ?6 || ' port=5432 user=' || ?9 || ' password=' || ?10, " +
			"    'SELECT ccode, kuran FROM ' || ?8 || '.echawdi_form1abstract WHERE ccode = ''' || ?11 || ''''" +
			"  ) AS (" +
			"    ccode TEXT, " +
			"    kuran NUMERIC(19,2)" +
			"  )" +
			") AS a, (" +
			"  SELECT SUM(BAGAYAT_AREA_H) AS bagayat_sum FROM public.dblink(" +
			"    'dbname=' || ?2 || ' host=' || ?1 || ' port=5432 user=' || ?4 || ' password=' || ?5, " +
			"    'SELECT ccode, BAGAYAT_AREA_H FROM ' || ?3 || '.form7_area WHERE ccode = ''' || ?11 || ''''" +
			"  ) AS (" +
			"    ccode TEXT, " +
			"    BAGAYAT_AREA_H NUMERIC(19,2)" +
			"  )" +
			") AS b",
			nativeQuery = true)
			List<Object[]> getKuranAndBgayatArea(
			   String fhost,          // ?1
			   String mhrDbName,      // ?2
			   String mhrSchemaName,  // ?3
			   String mhrUser,        // ?4
			   String mhrPass,        // ?5
			   String eHost,          // ?6
			   String echDbName,      // ?7
			   String echSchemaName,  // ?8
			   String eUser,          // ?9
			   String ePass,          // ?10
			   String ccode           // ?11 ← should be a String!
			);
			
			
			
	
}

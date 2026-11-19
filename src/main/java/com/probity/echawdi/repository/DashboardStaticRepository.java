package com.probity.echawdi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.probity.echawdi.entity.DashBoardStatic;

@Repository
public interface DashboardStaticRepository  extends JpaRepository<DashBoardStatic, Long>{
	

	@Query(value= "select vibhagjillaha1, jilhanchisankhya2,  talukasankhya3, totalgaonsankhya4,  "
			+ " bhagonegoansankhya5,   "
			+ " logingoansankhya6, vasuligoansankhya7, totalbharlalihiti8, year, code, sequence  "
			+ " from public.dblink "
		//	+ " ('dbname='|| 'echmst'  ||' host='|| '10.10.248.108' ||' user='|| 'postgres' ||' password='|| 'jduri%D^7f9gf',  "
			+ " ('dbname='|| 'echmst'  ||' host='|| '10.48.65.110' ||' user='|| 'postgres' ||' password='|| 'm%D^7f9gf',  "
			+ "' select vibhagjillaha1, jilhanchisankhya2,  talukasankhya3, totalgaonsankhya4, bhagonegoansankhya5,   "
			+ " logingoansankhya6, vasuligoansankhya7, totalbharlalihiti8, year, code, sequence  "
			+ " from   '|| 'public' ||'.collector_mis_2022_2023')  "
			+ " as (vibhagjillaha1 text, jilhanchisankhya2 integer,  talukasankhya3 integer, totalgaonsankhya4 integer,  "
			+ " bhagonegoansankhya5 integer, logingoansankhya6 integer, vasuligoansankhya7 integer, totalbharlalihiti8 integer,  "
			+ " year text, code text, sequence text ) "
			+ " where code=?1 and year=?2 order by sequence asc  "
	+ " ",nativeQuery = true)
	List <Object []> getStaticMIS(String code,String year);

}

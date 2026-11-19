package com.probity.echawdi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.probity.echawdi.entity.FerarInspection;

public interface FerarInspectionRepository extends JpaRepository<FerarInspection, Long> {
	
	
	@Query(
		    value =
		    "SELECT mut_no AS adesh_ferfar_mutNo,remark, type_of_remark, ferfar_type  FROM "
		 //   +"public.dblink('dbname='|| 'echmst' ||' host='|| '10.10.248.108' ||'  user='|| 'postgres' ||' password='|| 'jduri%D^7f9gf', "
		    +"public.dblink('dbname='|| 'echmst' ||' host='|| '10.48.65.110' ||'  user='|| 'postgres' ||' password='|| 'jduri%D^7f9gf', "
		    +"'SELECT o.mut_no, o.ccode, o.district_code, o.taluka_code, o.revenue_year, "
		    +"o.ferfar_type, o.active_flag, o.remark, o.type_of_remark "
		    +"FROM ' || 'public' || '.ferfar_inspection o') "
		    +"AS a1 ("
		    +"mut_no integer, "
		    +"ccode text, "
		    +"district_code integer, "
		    +"taluka_code integer, "
		    +"revenue_year character varying(255), "
		    +"ferfar_type integer, "
		    +"active_flag character(1), "
		    +"remark text, "
		    +"type_of_remark bigint) "
		    +"WHERE ferfar_type = ?6 "
		    + "AND ccode = ?1 "
            + "AND district_code = ?2 "
            + "AND taluka_code = ?3 "
            + "AND revenue_year = ?4 "
            + "AND active_flag = ?5",
		    nativeQuery = true
		)
		List<Object[]> getAdeshFerfarMutNo(
		    String cCode,
		    Integer districtCode,
		    Integer talukaCode,
		    String revenueYear,
		    Character activeFlag,
		    Integer ferFarType
		);
		
		
		@Query(
			    value =
			    "SELECT mut_no AS adesh_ferfar_mutNo,remark, type_of_remark, ferfar_type FROM "
			 //   +"public.dblink('dbname='|| 'echmst' ||' host='|| '10.10.248.108' ||'  user='|| 'postgres' ||' password='|| 'jduri%D^7f9gf', "
			    +"public.dblink('dbname='|| 'echmst' ||' host='|| '10.48.65.110' ||'  user='|| 'postgres' ||' password='|| 'jduri%D^7f9gf', "
			    +"'SELECT o.mut_no, o.ccode, o.district_code, o.taluka_code, o.revenue_year, "
			    +"o.ferfar_type, o.active_flag, o.remark, o.type_of_remark "
			    +"FROM ' || 'public' || '.ferfar_inspection o') "
			    +"AS a1 ("
			    +"mut_no integer, "
			    +"ccode text, "
			    +"district_code integer, "
			    +"taluka_code integer, "
			    +"revenue_year character varying(255), "
			    +"ferfar_type integer, "
			    +"active_flag character(1), "
			    +"remark text, "
			    +"type_of_remark bigint) "
			    +"WHERE  "
			    + " ccode = ?1 "
	            + "AND district_code = ?2 "
	            + "AND taluka_code = ?3 "
	            + "AND revenue_year = ?4 "
	            + "AND active_flag = ?5",
			    nativeQuery = true
			)
			List<Object[]> getAdeshFerfarMutNoWithoutFerfarType(
			    String cCode,
			    Integer districtCode,
			    Integer talukaCode,
			    String revenueYear,
			    Character activeFlag
			    
			);


		boolean existsByMutNo(Integer mutNo);
	
		
}

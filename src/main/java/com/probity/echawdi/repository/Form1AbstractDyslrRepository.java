package com.probity.echawdi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.probity.echawdi.entity.Form1Abstract_dyslr;

@Repository
public interface Form1AbstractDyslrRepository extends JpaRepository<Form1Abstract_dyslr, Long> {
		

		boolean existsBycCode(String cCode);
		
		Form1Abstract_dyslr findBycCode(String cCode);

		Form1Abstract_dyslr findAllBycCodeAndActiveFlag(String cCode, Character activeFlag);

		List<Form1Abstract_dyslr> findByDistrictCodeAndTalukaCode(Integer districtCode, Integer talukaCode);	


}

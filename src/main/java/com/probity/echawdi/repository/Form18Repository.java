/**
 * 
 */
package com.probity.echawdi.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.probity.echawdi.entity.Form18;

/**
 * @author Rushikesh Durgude
 *
 */
@Repository
public interface Form18Repository extends JpaRepository<Form18, Long> {

	Page<Form18> findBycCodeAndDistrictCodeAndTalukaCodeAndActiveFlag(String cCode, Integer districtCode,
			Integer talukaCode, char c, Pageable pageable);
}

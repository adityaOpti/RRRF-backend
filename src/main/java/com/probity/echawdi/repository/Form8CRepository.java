/**
 * 
 */
package com.probity.echawdi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.probity.echawdi.entity.Form8C;

/**
 * @author Bhagyashri Lolage
 *
 */
public interface Form8CRepository  extends JpaRepository<Form8C, Long> {

	Page<Form8C> findBycCodeAndDistrictCodeAndTalukaCodeAndActiveFlag(String cCode, Integer districtCode,
			Integer talukaCode, char c, Pageable pageable);
}
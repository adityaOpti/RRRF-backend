/**
 * 
 */
package com.probity.echawdi.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.probity.echawdi.entity.Form21;

/**
 * @author Rushikesh Durgude
 *
 */
@Repository
public interface Form21Repository extends JpaRepository<Form21, Long> {
	Page<Form21> findByUserIdAndActiveFlag(String userId,Character activeFlag, Pageable pageable);
}
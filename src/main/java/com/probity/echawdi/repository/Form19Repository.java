/**
 * 
 */
package com.probity.echawdi.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.probity.echawdi.entity.Form19;

/**
 * @author Rushikesh Durgude
 *
 */
@Repository
public interface Form19Repository extends JpaRepository<Form19, Long> {
	

	List<Form19> findByUserIdAndActiveFlag(String userId,Character activeFlag );
	
	List<Form19> findBySajjaCodeAndActiveFlag(String sajjaCode,Character activeFlag );
}

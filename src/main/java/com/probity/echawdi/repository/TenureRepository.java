/**
 * 
 */
package com.probity.echawdi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.probity.echawdi.entity.Tenure;

/**
 * @author Ashish Dahale
 *
 */

@Repository
public interface TenureRepository extends JpaRepository<Tenure, Long>{

	
}

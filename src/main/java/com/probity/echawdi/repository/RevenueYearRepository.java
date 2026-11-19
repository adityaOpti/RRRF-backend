/**
 * 
 */
package com.probity.echawdi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.probity.echawdi.entity.RevenueYear;


/**
 * @author Rushikesh Durgude
 *
 */
@Repository
public interface RevenueYearRepository extends JpaRepository<RevenueYear, Long>{

//	@Query(value=" select revenue_year,active_flag from revenue_year active_flag IN ('Y', 'N')"
//			+ "", nativeQuery = true)
	
	
	@Query(value = "SELECT revenue_year, active_flag " +
            "FROM revenue_year " +
            "WHERE active_flag IN ('Y', 'N') " +
            "ORDER BY revenue_year DESC",
    nativeQuery = true)
	List<Object[]> findByActiveFlag();
	
	List<RevenueYear> findAllByActiveFlag(Character activeFlag);

}

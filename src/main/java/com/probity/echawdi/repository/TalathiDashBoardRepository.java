/**
 * 
 */
package com.probity.echawdi.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.probity.echawdi.entity.TalathiDashBoard;

/**
 * @author Ashish Dahale
 *
 */

@Repository
public interface TalathiDashBoardRepository extends JpaRepository<TalathiDashBoard, Long>{

	boolean existsBycCodeAndRevenueYear(String cCode,String revenueYear);

	List<TalathiDashBoard> findBycCodeAndRevenueYearAndActiveFlag(String cCode, String revenueYear, char c);
	
	
}
/**
 * 
 */
package com.probity.echawdi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.probity.echawdi.entity.VillageEntryCountLive;

/**
 * @author Ashish Dahale
 *
 */

@Repository
public interface VillageEntryCountLiveRepository extends JpaRepository<VillageEntryCountLive, Long>{
	
	 VillageEntryCountLive findByDistrictAndTaluka(String district,String taluka);
}
/**
 * 
 */
package com.probity.echawdi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.probity.echawdi.entity.VillageEntryCountArchive;

/**
 * @author Ashish Dahale
 *
 */

@Repository
public interface VillageEntryCountArchiveRepository extends JpaRepository<VillageEntryCountArchive, Long>{
}
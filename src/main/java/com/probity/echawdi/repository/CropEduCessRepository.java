/**
 * 
 */
package com.probity.echawdi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.probity.echawdi.entity.CropEduCess;


/**
 * @author Sandeep Dubey
 *
 */
@Repository
public interface CropEduCessRepository extends JpaRepository<CropEduCess, Long>{

	List<CropEduCess> findAllByActiveFlag(Character activeFlag);

}

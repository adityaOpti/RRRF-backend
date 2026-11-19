package com.probity.echawdi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.probity.echawdi.entity.Form15;

/**
 * @author Bhagyashri Lolage
 *
 */
@Repository
public interface Form15Repository extends JpaRepository<Form15,Long>   {
	
	List<Form15> findBySajjaCodeAndActiveFlag (String sajjaCode,Character c);
	
	List<Form15> findByUserIdAndActiveFlag (String userId,Character c);
}

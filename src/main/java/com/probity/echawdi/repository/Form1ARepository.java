package com.probity.echawdi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.probity.echawdi.entity.Form1A;

/**
 * @author Rushikesh  Durgude
 *
 */

@Repository
public interface Form1ARepository extends JpaRepository<Form1A, Long> {
	
	List<Form1A> findBycCodeAndActiveFlag(String cCode,Character activeFlag);
}
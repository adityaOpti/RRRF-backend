/**
 * 
 */
package com.probity.echawdi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.probity.echawdi.entity.Form1E;
/**
 * @author Rushikesh  Durgude
 *
 */
@Repository
public interface Form1ERepository extends JpaRepository<Form1E, Long> {
	
	List<Form1E> findBycCodeAndPinAndActiveFlag(String cCode, String pin,Character activeFlag);

	List<Form1E> findBycCodeAndActiveFlag(String cCode, Character activeFlag);
}
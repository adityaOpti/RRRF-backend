package com.probity.echawdi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.probity.echawdi.entity.FerfarType;

@Repository
public interface FerfarTypeRepository extends JpaRepository<FerfarType, Long> {
	
	
	
}
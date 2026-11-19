package com.probity.echawdi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.probity.echawdi.entity.FerfarTypeMaster;

@Repository
public interface FerfarTypeMasterRepository extends JpaRepository<FerfarTypeMaster,Integer> {
	
	
}
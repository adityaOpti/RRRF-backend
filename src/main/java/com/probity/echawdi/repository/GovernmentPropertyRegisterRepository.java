package com.probity.echawdi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.probity.echawdi.entity.GovernmentPropertyRegister;

@Repository
public interface GovernmentPropertyRegisterRepository
        extends JpaRepository<GovernmentPropertyRegister, Long> {
	
	
	 List<GovernmentPropertyRegister> findBySevarthId(String sevarthId);

}
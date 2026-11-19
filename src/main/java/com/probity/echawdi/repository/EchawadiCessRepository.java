package com.probity.echawdi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.probity.echawdi.entity.EchawadiCess;

@Repository
public interface EchawadiCessRepository extends JpaRepository<EchawadiCess, Long> {

	Page<EchawadiCess> findAllByActiveFlag(Character activeFlag, Pageable pageable);
	
}
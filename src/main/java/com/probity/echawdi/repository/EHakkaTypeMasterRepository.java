package com.probity.echawdi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.probity.echawdi.entity.EHakkaTypeMaster;

@Repository
public interface EHakkaTypeMasterRepository extends JpaRepository<EHakkaTypeMaster, Long> {
    
    
   
}
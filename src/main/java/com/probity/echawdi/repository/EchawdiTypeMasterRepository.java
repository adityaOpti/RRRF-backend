package com.probity.echawdi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.probity.echawdi.entity.EChawdiTypeMaster;

@Repository
public interface EchawdiTypeMasterRepository extends JpaRepository<EChawdiTypeMaster, Long> {
    
    
   
}
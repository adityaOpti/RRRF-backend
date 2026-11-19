package com.probity.echawdi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.probity.echawdi.entity.GramAdarshTaktaEntity;

public interface GramAdarshTaktaRepository extends JpaRepository<GramAdarshTaktaEntity, Long> {
	
	
	 // Fetch with JOIN FETCH to load cropAreas eagerly
    @Query("SELECT DISTINCT g FROM GramAdarshTaktaEntity g LEFT JOIN FETCH g.cropAreas WHERE g.ccode = :ccode ")
    List<GramAdarshTaktaEntity> findByCcodeAndRevenueYear(@Param("ccode") String ccode);
    
    
    @Query("SELECT g.ccode FROM GramAdarshTaktaEntity g WHERE g.ccode = :ccode")
    String  findByCccode(@Param("ccode") String ccode);
	
}

package com.probity.echawdi.repository;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.probity.echawdi.entity.EHakkaInspection;

public interface EhakkaInspectionRepository extends JpaRepository<EHakkaInspection, Long> {
	
	@Query(value = "SELECT COUNT(*) > 0 " +
            "FROM e_hakka_inspection " +
            "WHERE application_id = :applicationId",
    nativeQuery = true
)
boolean existsByApplicationId(@Param("applicationId") Long applicationId);
	
}
